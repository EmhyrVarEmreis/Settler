package pl.morecraft.dev.settler.domain;

import org.joda.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "mod_file")
public class FileObject extends PrivilegeObject {

    @Column(nullable = false, unique = true)
    private String name;

    @Column
    private String type;

    @Column
    private String extension;

    @Column(unique = true)
    private String path;

    @Column(nullable = false)
    private String originalName;

    @Column(nullable = false)
    private LocalDateTime updated;

    @Lob
    private byte[] content;

    public FileObject() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    private boolean isExternal() {
        return content == null;
    }

}
