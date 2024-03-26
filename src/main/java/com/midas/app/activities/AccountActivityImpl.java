package com.midas.app.activities;

import com.midas.app.models.Account;
import com.midas.app.providers.payment.PaymentProvider;
import com.midas.app.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor
public class AccountActivityImpl implements AccountActivity {

  @Autowired private AccountRepository accountRepository;
  private PaymentProvider stripePaymentProvider;

  @Override
  public Account saveAccount(Account account) {
    return accountRepository.save(account);
  }

  @Override
  public Account createPaymentAccount(Account account) {
    return stripePaymentProvider.createAccount(account);

    /**
     * Below is the remnants of my testing trying to isolate and solve an error, and test the Stripe
     * api key, by moving what should be in StripePaymentProvider to here. When this was run, the
     * apikey was not working.
     */
    /*
        Stripe.apiKey = configuration.getApiKey();

        CustomerCreateParams params =
            CustomerCreateParams.builder()
                .setName(account.getFirstName() + " " + account.getLastName())
                .setEmail(account.getEmail())
                .build();

        try {
          Customer customer = Customer.create(params);
        } catch (StripeException e) {
          throw new RuntimeException(e);
        }

        return account;
    */

  }
}
