package com.sample;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.paypal.svcs.types.aa.CreateAccountResponse;
import com.sample.adaptiveaccounts.CreateAccount;

public class AdaptiveAccountsTest {

	CreateAccount createAccount;

	@Test(groups = { "automate" })
	public void createAccountTest() {
		CreateAccountResponse createAccountResponse = createAccount
				.createPersonalAccount();
		Assert.assertEquals(createAccountResponse.getResponseEnvelope()
				.getAck().getValue().toString(), "Success");
		Assert.assertNotNull(createAccountResponse.getCreateAccountKey());
		createAccountResponse = createAccount.createPremierAccount();
		Assert.assertEquals(createAccountResponse.getResponseEnvelope()
				.getAck().getValue().toString(), "Success");
		Assert.assertNotNull(createAccountResponse.getCreateAccountKey());
		createAccountResponse = createAccount.createBusinessAccount();
		Assert.assertEquals(createAccountResponse.getResponseEnvelope()
				.getAck().getValue().toString(), "Success");
		Assert.assertNotNull(createAccountResponse.getCreateAccountKey());
	}

	@BeforeClass(groups = { "automate" })
	public void beforeClass() {
		createAccount = new CreateAccount();
	}

	@AfterClass(groups = { "automate" })
	public void afterClass() {
		createAccount = null;
	}

}
