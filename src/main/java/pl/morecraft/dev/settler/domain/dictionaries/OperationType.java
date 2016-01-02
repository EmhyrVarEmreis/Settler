package pl.morecraft.dev.settler.domain.dictionaries;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum OperationType {

    ADM("ADM", "Administrative"),
    EDT("EDT", "Editable"),
    RDM("RDM", "Readable"),
    CRT("CRT", "Creatable");

    private String code;
    private String description;

    OperationType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

}
