package com.midas.app.workflows;

import com.midas.app.activities.AccountActivity;
import com.midas.app.models.Account;
import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;
import java.time.Duration;

public class CreateAccountWorkflowImpl implements CreateAccountWorkflow {

  // Define the Activity Execution options
  ActivityOptions options =
      ActivityOptions.newBuilder().setStartToCloseTimeout(Duration.ofSeconds(10)).build();

  // Create a client stub to activities that implements the interface
  private final AccountActivity activities =
      Workflow.newActivityStub(AccountActivity.class, options);

  // Create the activities for creating an account in both the payment provider and the database.
  @Override
  public Account createAccount(Account details) {
    return activities.saveAccount(details);

    /**
     * This next section was a part of testing to check the source of the NPE. When uncommented and
     * the return line at the top is commented out, we observe the NPE is thrown elsewhere instead,
     * showing that this is the source of the first NPE.
     */
    /*
    Account account;
        try {
          account = activities.createPaymentAccount(details);
        } catch (NullPointerException npe) {
          // It's fine if createAccount throws a NPE
        }
        return account;
    */

    /**
     * This next section is remnants from testing performed trying to troubleshoot issues, once
     * saving an account to the account repo is working, then removing the return line at the top
     * and uncommenting the lines below would activate the Stripe account creation process.
     */
    // Account account = activities.createPaymentAccount(details);
    // return activities.saveAccount(details);
  }
}
