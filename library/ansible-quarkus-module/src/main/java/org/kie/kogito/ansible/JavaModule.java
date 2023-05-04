package org.kie.kogito.ansible;

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
            output(new Response("no arguments from java", null, null));
            return 0;
        }
        String inputFilePath = args[0];
        JsonNode input = objectMapper.readTree(Paths.get(inputFilePath).toFile());

        Workflow workflow = buildWorkflow("the definition....");
        JsonNodeModel workflowResponse = application.execute(workflow, (JsonNode) input.deepCopy());

        output(new Response("Response from java module", input, workflowResponse.getWorkflowdata()));
        return 0;
    }

    public Workflow buildWorkflow(String definition) {
        // define your flow using Fluent version Serverless workflow SDK or pare the defintion .swf.json
        return WorkflowBuilder.workflow("HelloWorld").start(inject(jsonObject().put("response", "Hello World from Workflow !!!"))).end().build();
    }

    private void output(Response response) {
        try {
            System.out.println(objectMapper.writeValueAsString(response));
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }
}
