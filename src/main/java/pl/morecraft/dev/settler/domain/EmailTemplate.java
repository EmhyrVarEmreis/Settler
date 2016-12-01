package pl.morecraft.dev.settler.domain;

import pl.morecraft.dev.settler.domain.dictionaries.EmailTemplateType;
import pl.morecraft.dev.settler.domain.dictionaries.Language;

import javax.persistence.*;

@Entity
@Table(name = "mod_email_template")
public class EmailTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EmailTemplateType type;

    @Enumerated(EnumType.STRING)
    private Language language;

    private String subject;
    private String content;

    public EmailTemplate() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EmailTemplateType getType() {
        return type;
    }

    public void setType(EmailTemplateType type) {
        this.type = type;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
