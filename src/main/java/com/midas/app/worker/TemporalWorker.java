package com.midas.app.worker;

import com.midas.app.activities.AccountActivityImpl;
import com.midas.app.workflows.CreateAccountWorkflowImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import lombok.RequiredArgsConstructor;

/**
 * TemporalWorker has been set up to run statically to break down the process of starting a workflow
 * and the worker. This can later be incorporated to run automatically when a workflow is created by
 * checking to see if a worker exists to handle the newly created workflow, then creating one if a
 * worker does not exist.
 *
 * <p>Temporal Worker will take in the queue, workflow, and activites, then create a worker that
 * will perform the tasks,
 */
@RequiredArgsConstructor
public class TemporalWorker {
  public static void main(String[] args) {

    String QUEUE_NAME = "create-account-workflow";

    // Generate the gRPC stubs
    WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();

    // Initialize the Temporal Client, passing in the gRPC stubs
    WorkflowClient client = WorkflowClient.newInstance(service);

    // Initialize a WorkerFactory, passing in the Temporal Client (WorkflowClient)
    WorkerFactory factory = WorkerFactory.newInstance(client);

    // Create a new Worker
    Worker worker = factory.newWorker(QUEUE_NAME); // "create-account-workflow"

    // Register the Workflow by passing in the class to the worker
    worker.registerWorkflowImplementationTypes(CreateAccountWorkflowImpl.class);

    // Register the Activities by passing in an Activities object used for execution
    worker.registerActivitiesImplementations(new AccountActivityImpl());

    // Start the Worker
    factory.start();
    // }
  }
}
