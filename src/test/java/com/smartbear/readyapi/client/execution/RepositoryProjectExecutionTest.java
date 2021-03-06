package com.smartbear.readyapi.client.execution;

import com.smartbear.readyapi.client.ExecutionListener;
import com.smartbear.readyapi.client.RepositoryProjectExecutionRequest;
import com.smartbear.readyapi.client.model.ProjectResultReport;
import io.swagger.client.auth.HttpBasicAuth;
import org.junit.Before;
import org.junit.Test;

import static com.smartbear.readyapi.client.execution.ExecutionTestHelper.makeFinishedReport;
import static com.smartbear.readyapi.client.execution.ExecutionTestHelper.makeRunningReport;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RepositoryProjectExecutionTest extends ProjectExecutionTestBase {

    private RepositoryProjectExecutionRequest executionRequest;

    @Before
    public void setUp() throws Exception {
        executionRequest = createRepositoryProjectExecutionRequest();
    }

    @Test
    public void executesProjectFromRepository() throws Exception {
        ProjectResultReport endReport = makeFinishedReport("executionId");
        when(apiWrapper.postRepositoryProject(eq(executionRequest), eq(false), any(HttpBasicAuth.class))).thenReturn(endReport);

        Execution execution = projectExecutor.executeRepositoryProject(executionRequest);//recipeExecutor.submitRepositoryProject(executionRequest) for async
        assertThat(execution.getCurrentStatus(), is(ProjectResultReport.StatusEnum.FINISHED));
    }

    @Test
    public void sendsNotificationsOnAsynchronousExecutionRequests() throws Exception {
        String executionID = "the_id";
        ProjectResultReport startReport = makeRunningReport(executionID);
        ProjectResultReport endReport = makeFinishedReport(executionID);
        when(apiWrapper.postRepositoryProject(eq(executionRequest), eq(true), any(HttpBasicAuth.class))).thenReturn(startReport);
        when(apiWrapper.getExecutionStatus(eq(executionID), any(HttpBasicAuth.class))).thenReturn(endReport);
        ExecutionListener executionListener = mock(ExecutionListener.class);

        projectExecutor.addExecutionListener(executionListener);
        projectExecutor.submitRepositoryProject(executionRequest);
        Thread.sleep(1500);
        verify(executionListener).requestSent(startReport);
        verify(executionListener).executionFinished(endReport);
    }


    private RepositoryProjectExecutionRequest createRepositoryProjectExecutionRequest() {
        return RepositoryProjectExecutionRequest.Builder.newInstance()
                .fromRepository("compositeprojects")
                .forProject("Environment-test.xml")
                .forEnvironment("staging")
                .forTestSuite("TestSuite-1")
                .forTestCase("TestCase-1")
                .build();
    }
}
