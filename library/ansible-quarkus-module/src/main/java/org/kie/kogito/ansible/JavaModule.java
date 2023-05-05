package org.kie.kogito.ansible;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Paths;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import io.serverlessworkflow.api.Workflow;
import jakarta.inject.Inject;
import org.kie.kogito.serverless.workflow.executor.StaticWorkflowApplication;
import org.kie.kogito.serverless.workflow.fluent.WorkflowBuilder;
import org.kie.kogito.serverless.workflow.models.JsonNodeModel;
import org.kie.kogito.serverless.workflow.utils.ServerlessWorkflowUtils;
import org.kie.kogito.serverless.workflow.utils.WorkflowFormat;

import static org.kie.kogito.serverless.workflow.fluent.StateBuilder.inject;
import static org.kie.kogito.serverless.workflow.fluent.WorkflowBuilder.jsonObject;

@QuarkusMain
public class JavaModule implements QuarkusApplication {

    @Inject
    ObjectMapper objectMapper;

    @Inject
    StaticWorkflowApplication application;

    @Override
    public int run(String... args) throws Exception {
        if (args.length < 1) {
            output(new Response("no arguments from java",  null));
            return 0;
        }
        //Ansible send the input parameters as a json which is stored in the filesystem
        //The input parameter arg[0] is the URI of this Json
        String inputFilePath = args[0];
        JsonNode input = objectMapper.readTree(Paths.get(inputFilePath).toFile());

        JsonNode definition = input.get("definition");
        Workflow workflow = buildWorkflow(definition.asText());
        JsonNodeModel workflowResponse = application.execute(workflow, input);

        output(new Response("Response from java module", workflowResponse.getWorkflowdata()));
        return 0;
    }

    public Workflow buildWorkflow(String definitionFilePath) {
        try (Reader reader = new FileReader(definitionFilePath)) {
            return ServerlessWorkflowUtils.getWorkflow(reader, WorkflowFormat.JSON);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public Workflow getDefaultWorkflow(){
        return WorkflowBuilder.workflow("HelloWorld").start(inject(jsonObject().put("response", "Hello World from Workflow !!!"))).end().build();
    }

    private void output(Response response) {
        try {
            //ansible receives the response from the console output as a Json
            System.out.println(objectMapper.writeValueAsString(response));
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }
}
