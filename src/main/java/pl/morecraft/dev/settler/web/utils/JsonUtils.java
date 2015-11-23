package pl.morecraft.dev.settler.web.utils;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonUtils {

    public ObjectNode wrapLong(String name, Long object) {
        ObjectNode on = JsonNodeFactory.instance.objectNode();
        on.put(name, object);

        return on;
    }
}
