package org.kie.kogito.ansible;

import com.fasterxml.jackson.databind.JsonNode;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class Response {
    private String msg;
    private JsonNode output;

    public Response(String msg, JsonNode output) {
        this.msg = msg;
        this.output = output;
    }

    public String getMsg() {
        return msg;
    }

    public JsonNode getOutput() {
        return output;
    }
}
