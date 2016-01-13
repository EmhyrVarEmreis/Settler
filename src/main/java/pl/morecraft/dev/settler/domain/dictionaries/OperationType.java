package pl.morecraft.dev.settler.domain.dictionaries;

import com.fasterxml.jackson.annotation.JsonFormat;
import pl.morecraft.dev.settler.domain.dictionaries.proto.DefaultDictionary;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum OperationType implements DefaultDictionary {

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

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }

}
