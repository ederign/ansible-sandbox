package org.kie.kogito.ansible;

import com.fasterxml.jackson.databind.JsonNode;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class Response {
    private String msg;
    private JsonNode params;

    public Response(String msg, JsonNode params) {
        this.msg = msg;
        this.params = params;
    }

    public String getMsg() {
        return msg;
    }

    public JsonNode getParams() {
        return params;
    }
}
