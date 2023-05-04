package org.kie.kogito.ansible;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.kie.kogito.serverless.workflow.executor.StaticWorkflowApplication;

@ApplicationScoped
public class WorkflowModule {
    @Produces
    StaticWorkflowApplication application() {
        return StaticWorkflowApplication.create();
    }
}
