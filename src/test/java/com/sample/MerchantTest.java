package com.sample;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.sample.merchant.CreateRecurringPaymentsProfile;
import com.sample.merchant.DoAuthorization;
import com.sample.merchant.DoCapture;
import com.sample.merchant.DoDirectPayment;
import com.sample.merchant.DoExpressCheckout;
import com.sample.merchant.DoReauthorization;
import com.sample.merchant.DoReferenceTransaction;
import com.sample.merchant.DoVoid;
import com.sample.merchant.GetBalance;
import com.sample.merchant.GetExpressCheckout;
import com.sample.merchant.GetTransactionDetails;
import com.sample.merchant.MassPay;
import com.sample.merchant.RefundTransaction;
import com.sample.merchant.SetExpressCheckout;
import com.sample.merchant.TransactionSearch;

import urn.ebay.api.PayPalAPI.CreateRecurringPaymentsProfileResponseType;
import urn.ebay.api.PayPalAPI.DoAuthorizationResponseType;
import urn.ebay.api.PayPalAPI.DoCaptureResponseType;
import urn.ebay.api.PayPalAPI.DoDirectPaymentResponseType;
import urn.ebay.api.PayPalAPI.DoExpressCheckoutPaymentResponseType;
import urn.ebay.api.PayPalAPI.DoReauthorizationResponseType;
import urn.ebay.api.PayPalAPI.DoReferenceTransactionResponseType;
import urn.ebay.api.PayPalAPI.DoVoidResponseType;
import urn.ebay.api.PayPalAPI.GetBalanceResponseType;
import urn.ebay.api.PayPalAPI.GetExpressCheckoutDetailsResponseType;
import urn.ebay.api.PayPalAPI.GetTransactionDetailsResponseType;
import urn.ebay.api.PayPalAPI.MassPayResponseType;
import urn.ebay.api.PayPalAPI.RefundTransactionResponseType;
import urn.ebay.api.PayPalAPI.SetExpressCheckoutResponseType;
import urn.ebay.api.PayPalAPI.TransactionSearchResponseType;

public class MerchantTest {

	SetExpressCheckout setExpressCheckout;
	GetExpressCheckout getExpressCheckout;
	DoExpressCheckout doExpressCheckout;
	DoDirectPayment doDirectPayment;
	TransactionSearch transactionSearch;
	GetTransactionDetails getTransactionDetails;
	GetBalance getBalance;
	DoAuthorization doAuthorization;
	DoReauthorization doReauthorization;
	DoVoid doVoid;
	DoCapture doCapture;
	CreateRecurringPaymentsProfile createRecurringPaymentsProfile;
	DoReferenceTransaction doReferenceTransaction;
	MassPay massPay;
	RefundTransaction refundTransaction;

	@BeforeClass(groups = { "automate", "manual" })
	public void beforeClass() {
		setExpressCheckout = new SetExpressCheckout();
		getExpressCheckout = new GetExpressCheckout();
		doExpressCheckout = new DoExpressCheckout();
		doDirectPayment = new DoDirectPayment();
		transactionSearch = new TransactionSearch();
		getTransactionDetails = new GetTransactionDetails();
		getBalance = new GetBalance();
		doAuthorization = new DoAuthorization();
		doReauthorization = new DoReauthorization();
		doVoid = new DoVoid();
		doCapture = new DoCapture();
		createRecurringPaymentsProfile = new CreateRecurringPaymentsProfile();
		doReferenceTransaction = new DoReferenceTransaction();
		massPay = new MassPay();
		refundTransaction = new RefundTransaction();
	}

	@Test(groups = { "automate" })
	public void setExpressCheckoutTest() {
		SetExpressCheckoutResponseType setExpressCheckoutResponse = setExpressCheckout
				.setExpressCheckout();
		Assert.assertEquals(setExpressCheckoutResponse.getAck().getValue()
				.toString(), "Success");
		Assert.assertNotNull(setExpressCheckoutResponse.getToken());

	}

	@Test(groups = { "manual" })
	public void getExpressCheckoutTest() {
		GetExpressCheckoutDetailsResponseType getExpressCheckoutDetailsResponse = getExpressCheckout
				.getExpressCheckout();
		Assert.assertEquals(getExpressCheckoutDetailsResponse.getAck()
				.getValue().toString(), "Success");
	}

	@Test(groups = { "manual" })
	public void doExpressCheckoutTest() {
		DoExpressCheckoutPaymentResponseType doExpressCheckoutPaymentResponse = doExpressCheckout
				.doExpressCheckout();
		Assert.assertEquals(doExpressCheckoutPaymentResponse.getAck()
				.getValue().toString(), "Success");
		Assert.assertNotNull(doExpressCheckoutPaymentResponse
				.getDoExpressCheckoutPaymentResponseDetails().getPaymentInfo()
				.get(0).getTransactionID());
	}

	@Test(groups = { "automate" })
	public void doDirectPaymentTest() {
		DoDirectPaymentResponseType doDirectPaymentResponse = doDirectPayment
				.doDirectPayment();
		Assert.assertEquals(doDirectPaymentResponse.getAck().getValue()
				.toString(), "Success");
		Assert.assertNotNull(doDirectPaymentResponse.getTransactionID());
	}

	@Test(groups = { "manual" })
	public void transactionSearchTest() {
		TransactionSearchResponseType transactionSearchResponse = transactionSearch
				.transactionSearch();
		Assert.assertEquals(transactionSearchResponse.getAck().getValue()
				.toString(), "Success");
	}

	@Test(groups = { "automate" })
	public void getTransactionDetailsTest() {
		GetTransactionDetailsResponseType getTransactionDetailsResponse = getTransactionDetails
				.getTransactionDetails();
		Assert.assertEquals(getTransactionDetailsResponse.getAck().getValue()
				.toString(), "Success");
		Assert.assertNotNull(getTransactionDetailsResponse
				.getPaymentTransactionDetails().getPayerInfo().getPayerID());
	}

	@Test(groups = { "automate" })
	public void getBalanceTest() {
		GetBalanceResponseType getBalanceResponse = getBalance.getBalance();
		Assert.assertEquals(getBalanceResponse.getAck().getValue().toString(),
				"Success");
		Assert.assertTrue(getBalanceResponse.getBalanceHoldings().size() > 0);
	}

	@Test(groups = { "manual" })
	public void doAuthorizationTest() {
		DoAuthorizationResponseType doAuthorizationResponse = doAuthorization
				.doAuthorization();
		Assert.assertEquals(doAuthorizationResponse.getAck().getValue()
				.toString(), "Success");
		Assert.assertNotNull(doAuthorizationResponse.getTransactionID());
	}

	@Test(groups = { "manual" })
	public void doReauthorizationTest() {
		DoReauthorizationResponseType doReauthorizationResponse = doReauthorization
				.doReauthorization();
		Assert.assertEquals(doReauthorizationResponse.getAck().getValue()
				.toString(), "Success");
		Assert.assertNotNull(doReauthorizationResponse.getAuthorizationID());

	}

	@Test(groups = { "manual" })
	public void doCaptureTest() {
		DoCaptureResponseType doCaptureResponse = doCapture.doCapture();
		Assert.assertEquals(doCaptureResponse.getAck().getValue().toString(),
				"Success");
	}

	@Test(groups = { "manual" })
	public void doVoidTest() {
		DoVoidResponseType doVoidResponse = doVoid.doVoid();
		Assert.assertEquals(doVoidResponse.getAck().getValue().toString(),
				"Success");
	}

	@Test(groups = { "automate" })
	public void createRecurringPaymentsProfileTest() {
		CreateRecurringPaymentsProfileResponseType createRecurringPaymentsProfileResponse = createRecurringPaymentsProfile
				.createRecurringPayment();
		Assert.assertEquals(createRecurringPaymentsProfileResponse.getAck()
				.getValue().toString(), "Success");
		Assert.assertNotNull(createRecurringPaymentsProfileResponse
				.getCreateRecurringPaymentsProfileResponseDetails()
				.getProfileID());
	}

	@Test(groups = { "automate" })
	public void doReferenceTransactionTest() {
		DoReferenceTransactionResponseType doReferenceTransactionResponse = doReferenceTransaction
				.doReferenceTransaction();
		Assert.assertEquals(doReferenceTransactionResponse.getAck().getValue()
				.toString(), "Success");
	}

	@Test(groups = { "automate" })
	public void massPayTest() {
		MassPayResponseType massPayResponse = massPay.massPay();
		Assert.assertEquals(massPayResponse.getAck().getValue().toString(),
				"Success");
	}
	
	@Test(groups = { "manual" })
	public void refundTransactionTest(){
		RefundTransactionResponseType refundTransactionResponse = refundTransaction.refundTransaction();
		Assert.assertEquals(refundTransactionResponse.getAck().getValue().toString(),
				"Success");
	}

	@AfterClass(groups = { "automate", "manual" })
	public void afterClass() {
		setExpressCheckout = null;
		getExpressCheckout = null;
		doExpressCheckout = null;
		doDirectPayment = null;
		transactionSearch = null;
		getTransactionDetails = null;
		getBalance = null;
		doAuthorization = null;
		doReauthorization = null;
		doVoid = null;
		doCapture = null;
		createRecurringPaymentsProfile = null;
		doReferenceTransaction = null;
		massPay = null;
		refundTransaction = null;
	}

}
