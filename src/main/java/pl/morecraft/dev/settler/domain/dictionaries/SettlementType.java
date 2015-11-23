package pl.morecraft.dev.settler.domain.dictionaries;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SettlementType {

    NOR("NOR", "Normal"),
    OTH("OTH", "Other");

    private String code;
    private String description;

    SettlementType(String code, String description) {
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
