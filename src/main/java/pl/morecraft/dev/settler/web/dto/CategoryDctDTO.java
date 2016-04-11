package pl.morecraft.dev.settler.web.dto;

public class CategoryDctDTO {

    private String code;
    private String description;

    public CategoryDctDTO() {

    }

    public CategoryDctDTO(String code, String description) {
        this.code = code;
        this.description = description;
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
