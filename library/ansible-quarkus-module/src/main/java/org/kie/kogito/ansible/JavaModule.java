package org.kie.kogito.ansible;

import java.nio.file.Paths;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.inject.Inject;

@QuarkusMain
public class JavaModule implements QuarkusApplication {

    @Inject
    ObjectMapper objectMapper;

    @Override
    public int run(String... args) throws Exception {
        if (args.length < 1) {
            output(new Response("no arguments from java", null));
            return 0;
        }
        String inputFilePath = args[0];
        JsonNode input = objectMapper.readTree(Paths.get(inputFilePath).toFile());

        output(new Response("Response from java module", input));
        return 0;
    }

    private void output(Response response) {
        try {
            System.out.println(objectMapper.writeValueAsString(response));
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }
}
