package pl.morecraft.dev.settler.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import pl.morecraft.dev.settler.domain.EmailTemplate;
import pl.morecraft.dev.settler.domain.dictionaries.EmailTemplateType;
import pl.morecraft.dev.settler.domain.dictionaries.Language;

public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, Long>, QuerydslPredicateExecutor<EmailTemplate> {

    EmailTemplate findOneByTypeAndLanguage(EmailTemplateType type, Language language);

}
