package pl.morecraft.dev.settler.domain.dictionaries;

import com.fasterxml.jackson.annotation.JsonFormat;
import pl.morecraft.dev.settler.domain.dictionaries.proto.DefaultDictionary;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum UserStatus implements DefaultDictionary {

    ACT("ACT", "Active"),
    DSB("DSB", "Disabled"),
    BLK("BLK", "Blocked"),
    HNG("HNG", "Hanged"),
    TMP("TMP", "Temporary");

    private String code;
    private String description;

    UserStatus(String code, String description) {
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
