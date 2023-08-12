package com.sample;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.paypal.svcs.types.ap.ConvertCurrencyResponse;
import com.paypal.svcs.types.ap.ExecutePaymentResponse;
import com.paypal.svcs.types.ap.GetPaymentOptionsResponse;
import com.paypal.svcs.types.ap.PayResponse;
import com.paypal.svcs.types.ap.PaymentDetailsResponse;
import com.paypal.svcs.types.ap.PreapprovalDetailsResponse;
import com.paypal.svcs.types.ap.PreapprovalResponse;
import com.paypal.svcs.types.ap.RefundResponse;
import com.paypal.svcs.types.ap.SetPaymentOptionsResponse;
import com.sample.adaptivepayments.ConvertCurrency;
import com.sample.adaptivepayments.ExecutePayment;
import com.sample.adaptivepayments.GetPaymentOptions;
import com.sample.adaptivepayments.Pay;
import com.sample.adaptivepayments.PaymentDetails;
import com.sample.adaptivepayments.Preapproval;
import com.sample.adaptivepayments.PreapprovalDetails;
import com.sample.adaptivepayments.Refund;
import com.sample.adaptivepayments.SetPaymentOptions;

public class AdaptivePaymentsTest {

	Preapproval preapproval = null;
	PreapprovalDetails preapprovalDetails = null;
	ConvertCurrency convertCurrency = null;
	Pay pay = null;
	PaymentDetails paymentDetails = null;
	Refund refund = null;
	SetPaymentOptions setPaymentOptions = null;
	GetPaymentOptions getPaymentOptions = null;
	ExecutePayment executePayment = null;
	String payKey;

	@Test(groups = { "automate" })
	public void payTest() {
		PayResponse payResponse = pay.simplePay();
		Assert.assertNotNull(payResponse.getPayKey());
		payResponse = pay.chainPay();
		Assert.assertNotNull(payResponse.getPayKey());
		payResponse = pay.parallelPay();
		Assert.assertNotNull(payResponse.getPayKey());

	}

	@Test(groups = { "automate" })
	public void paymentDetailsTest() {
		PaymentDetailsResponse paymentDetailsResponse = paymentDetails
				.paymentDetails();
		Assert.assertNotNull(paymentDetailsResponse.getStatus());
	}

	@Test(groups = { "automate" })
	public void preapprovalTest() {
		PreapprovalResponse preapprovalResponse = preapproval.preapproval();
		Assert.assertNotNull(preapprovalResponse.getPreapprovalKey());
	}

	@Test(groups = { "automate" })
	public void preapprovalDetailsTest() {
		PreapprovalDetailsResponse preapprovalDetailsResponse = preapprovalDetails
				.preapprovalDetails();
		Assert.assertNotNull(preapprovalDetailsResponse.getStatus());
	}

	@Test(groups = { "automate" })
	public void convertCurrencyTest() {
		ConvertCurrencyResponse convertCurrencyResponse = convertCurrency
				.convertCurrency();
		Assert.assertTrue(convertCurrencyResponse.getEstimatedAmountTable()
				.getCurrencyConversionList().size() > 0);
	}

	@Test(groups = { "automate" })
	public void setPaymentOptionsTest() {
		SetPaymentOptionsResponse setPaymentOptionsResponse = setPaymentOptions
				.setPaymentOptions();
		Assert.assertEquals(setPaymentOptionsResponse.getResponseEnvelope()
				.getAck().getValue().toString(), "Success");
	}

	@Test(groups = { "automate" })
	public void getPaymentOptionsTest() {
		GetPaymentOptionsResponse getPaymentOptionsResponse = getPaymentOptions
				.getPaymentOptions();
		Assert.assertEquals(getPaymentOptionsResponse.getResponseEnvelope()
				.getAck().getValue().toString(), "Success");
	}

	@Test(groups = { "manual" })
	public void executePaymentTest() {
		ExecutePaymentResponse executePaymentResponse = executePayment
				.executePayment();
		Assert.assertEquals(executePaymentResponse.getResponseEnvelope()
				.getAck().getValue().toString(), "Success");
	}

	@Test(groups = { "automate" })
	public void refundTest() {
		RefundResponse refundResponse = refund.refund();
		Assert.assertEquals(refundResponse.getResponseEnvelope().getAck()
				.getValue().toString(), "Success");
	}

	@BeforeClass(groups = { "automate", "manual" })
	public void beforeClass() {
		preapproval = new Preapproval();
		preapprovalDetails = new PreapprovalDetails();
		convertCurrency = new ConvertCurrency();
		pay = new Pay();
		paymentDetails = new PaymentDetails();
		refund = new Refund();
		setPaymentOptions = new SetPaymentOptions();
		getPaymentOptions = new GetPaymentOptions();
		executePayment = new ExecutePayment();
	}

	@AfterClass(groups = { "automate", "manual" })
	public void afterClass() {
		preapproval = null;
		preapprovalDetails = null;
		convertCurrency = null;
		pay = null;
		paymentDetails = null;
		refund = null;
		setPaymentOptions = null;
		getPaymentOptions = null;
		executePayment = null;
	}

}
