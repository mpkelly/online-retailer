package rest.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class Json {
    private Json(){}

    public static Map<String, Object> jsonToMap(String json)  {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, new TypeReference<HashMap<String,Object>>(){});
        } catch (IOException e) {
            //Can't happen for readValue calls that pass in a String
            throw new RuntimeException(e);
        }
    }
}
