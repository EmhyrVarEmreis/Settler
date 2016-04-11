package pl.morecraft.dev.settler.web.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CategoryDTO {

    private Long id;

    @NotNull
    @Size(min = 3, max = 3)
    @Pattern(regexp = "^[A-Z]*$")
    private String code;

    @NotNull
    @Size(min = 3, max = 128)
    private String description;

    public CategoryDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
