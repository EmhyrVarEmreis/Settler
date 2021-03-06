package pl.morecraft.dev.settler.domain.dictionaries;

import com.fasterxml.jackson.annotation.JsonFormat;
import pl.morecraft.dev.settler.domain.dictionaries.proto.DefaultDictionary;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Language implements DefaultDictionary {

    PL_PL("PL_PL", "Polish"),
    EN_GB("EN_GB", "English (Great Britain)");

    private String code;
    private String description;

    Language(String code, String description) {
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
