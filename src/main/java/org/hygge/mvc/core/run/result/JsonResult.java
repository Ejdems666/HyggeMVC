package org.hygge.mvc.core.run.result;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.nashorn.internal.ir.ObjectNode;

/**
 * Created by adam on 31/08/2017.
 */
public class JsonResult implements Result<String> {
    private String result;

    public JsonResult(ObjectNode objectNode) {
        result = objectNode.toString();
    }

    /**
     * Converts any kind of object into a json
     * Ideal for entity beans
     */
    public JsonResult(Object data) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            result = mapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getResult() {
        return result;
    }
}
