package rest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

@SuppressWarnings("unused")
public class RootApi extends ResourceSupport {

    private final String version;

    @JsonCreator
    public RootApi(@JsonProperty("version") String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }
}
