package pl.morecraft.dev.settler.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.morecraft.dev.settler.configuration.MailConfiguration;
import pl.morecraft.dev.settler.dao.repository.EmailTemplateRepository;
import pl.morecraft.dev.settler.domain.EmailTemplate;
import pl.morecraft.dev.settler.domain.Transaction;
import pl.morecraft.dev.settler.domain.dictionaries.EmailTemplateType;
import pl.morecraft.dev.settler.domain.dictionaries.Language;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
public class EmailService {

    private static Logger log = LoggerFactory.getLogger(EmailService.class);

    private final EmailTemplateRepository emailTemplateRepository;
    private final MailConfiguration mailConfiguration;
    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(EmailTemplateRepository emailTemplateRepository, MailConfiguration mailConfiguration, JavaMailSender mailSender) {
        this.emailTemplateRepository = emailTemplateRepository;
        this.mailConfiguration = mailConfiguration;
        this.mailSender = mailSender;
    }

    public void sendNotificationEmailNewTransaction(Transaction transaction) {
        EmailTemplate emailTemplate = emailTemplateRepository.findOneByTypeAndLanguage(
                EmailTemplateType.NEW_TRANSACTION,
                Language.EN_GB
        );
        if (emailTemplate != null) {
            Stream.concat(
                    transaction.getContractors() == null ? Stream.empty() : transaction.getContractors().stream(),
                    transaction.getOwners() == null ? Stream.empty() : transaction.getOwners().stream()
            ).filter(
                    redistribution -> !redistribution.getId().getUser().getId().equals(transaction.getCreator().getId())
            ).forEach(
                    redistribution -> {
                        Map<String, String> options = new HashMap<>();
                        options.put("creator", transaction.getCreator().getLogin());
                        options.put("user", redistribution.getId().getUser().getFirstName());
                        options.put("transactionId", transaction.getReference());
                        options.put("value", String.format("%.2f", redistribution.getPercentage() * transaction.getValue() / 100.0));
                        options.put("total", String.format("%.2f", transaction.getValue()));
                        sendEmail(Collections.singletonList(redistribution.getId().getUser().getEmail()), emailTemplate, options);
                    }
            );
        }
    }

    @Async
    public void sendEmail(List<String> to, EmailTemplate emailTemplate, Map<String, String> options) {
        EmailTemplateTmp emailTemplateTmp = EmailTemplateTmp.from(emailTemplate);
        for (Map.Entry<String, String> optionsEntry : options.entrySet()) {
            emailTemplateTmp.subject = emailTemplateTmp.subject.replaceAll("\\$" + optionsEntry.getKey(), optionsEntry.getValue());
            emailTemplateTmp.content = emailTemplateTmp.content.replaceAll("\\$" + optionsEntry.getKey(), optionsEntry.getValue());
        }

        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            message.setTo(to.toArray(new String[to.size()]));
            message.setFrom(mailConfiguration.getFromAddress());
            message.setSubject(emailTemplateTmp.subject);
            message.setText(emailTemplateTmp.content, true);
        };
        try {
            mailSender.send(preparator);
        } catch (Exception e) {
            log.error("Error occurred during sendEmail", e);
        }
    }

    private final static class EmailTemplateTmp {

        private String subject;
        private String content;

        public EmailTemplateTmp(String subject, String content) {
            this.subject = subject;
            this.content = content;
        }

        private static EmailTemplateTmp from(EmailTemplate emailTemplate) {
            return new EmailTemplateTmp(emailTemplate.getSubject(), emailTemplate.getContent());
        }

    }

}
