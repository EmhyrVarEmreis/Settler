package pl.morecraft.dev.settler.domain.dictionaries.internal;

import com.fasterxml.jackson.annotation.JsonFormat;
import pl.morecraft.dev.settler.domain.dictionaries.proto.DefaultDictionary;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SocialEnum implements DefaultDictionary {

    FACEBOOK("Facebook", "Facebook"),
    GOOGLE("Google", "Google");

    private String code;
    private String description;

    SocialEnum(String code, String description) {
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
