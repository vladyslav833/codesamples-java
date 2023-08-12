package com.sample.adaptivepayments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.paypal.svcs.services.AdaptivePaymentsService;
import com.paypal.svcs.types.ap.PayRequest;
import com.paypal.svcs.types.ap.PayResponse;
import com.paypal.svcs.types.ap.Receiver;
import com.paypal.svcs.types.ap.ReceiverList;
import com.paypal.svcs.types.common.RequestEnvelope;

// # Pay API
// Use the Pay API operation to transfer funds from a sender's PayPal account to one or more receivers' PayPal accounts. You can use the Pay API operation to make simple payments, chained payments, or parallel payments; these payments can be explicitly approved, preapproved, or implicitly approved.
// This sample code uses AdaptivePayments Java SDK to make API call. You can
// download the SDK [here](https://github.com/paypal/sdk-packages/tree/gh-pages/adaptivepayments-sdk/java)
public class Pay {

	PayRequest payRequest;
	PayResponse payResponse;

	public PayResponse simplePay() {

		// ##PayRequest
		// The code for the language in which errors are returned, which must be
		// en_US.
		RequestEnvelope requestEnvelope = new RequestEnvelope();
		requestEnvelope.setErrorLanguage("en_US");

		List<Receiver> receiverLst = new ArrayList<Receiver>();

		// Amount to be credited to the receiver's account
		Receiver receiver = new Receiver(Double.parseDouble("4.00"));

		// A receiver's email address
		receiver.setEmail("abc@paypal.com");
		receiverLst.add(receiver);
		ReceiverList receiverList = new ReceiverList(receiverLst);

		// PayRequest which takes mandatory params:
		// 
		// * `Request Envelope` - Information common to each API operation, such
		// as the language in which an error message is returned.
		// * `Action Type` - The action for this request. Possible values are:
		//  * PAY - Use this option if you are not using the Pay request in
		//  combination with ExecutePayment.
		//  * CREATE - Use this option to set up the payment instructions with
		//  SetPaymentOptions and then execute the payment at a later time with
		//  the ExecutePayment.
		//  * PAY_PRIMARY - For chained payments only, specify this value to
		//  delay payments to the secondary receivers; only the payment to the primary
		//  receiver is processed.
		// * `Cancel URL` - URL to redirect the sender's browser to after
		// canceling the approval for a payment; it is always required but only
		// used for payments that require approval (explicit payments)
		// * `Currency Code` - The code for the currency in which the payment is
		// made; you can specify only one currency, regardless of the number of
		// receivers
		// * `Recevier List` - List of receivers
		// * `Return URL` - URL to redirect the sender's browser to after the
		// sender has logged into PayPal and approved a payment; it is always
		// required but only used if a payment requires explicit approval
		payRequest = new PayRequest(requestEnvelope, "PAY",
				"http://localhost/cancel", "USD", receiverList,
				"http://localhost/return");

		// The URL to which you want all IPN messages for this payment to be
		// sent.
		// This URL supersedes the IPN notification URL in your profile
		payRequest.setIpnNotificationUrl("http://localhost/ipn");

		payResponse = makeAPICall(payRequest);
		return payResponse;

	}

	// ##ChainedPayment
	// `Note:
	// For chained Payment all the above mentioned request parameters in
	// simplePay() are required, but in receiverList alone we have to make
	// receiver as Primary Receiver or Not Primary Receiver`
	public PayResponse chainPay() {

		List<Receiver> receiverLst = new ArrayList<Receiver>();

		// Amount to be credited to the receiver's account
		Receiver receiver1 = new Receiver(Double.parseDouble("4.00"));

		// A receiver's email address
		receiver1.setEmail("enduser_biz@gmail.com");

		// Set to true to indicate a chained payment; only one receiver can be a
		// primary receiver. Omit this field, or set it to false for simple and
		// parallel payments.
		receiver1.setPrimary(Boolean.TRUE);
		receiverLst.add(receiver1);

		// Amount to be credited to the receiver's account
		Receiver receiver2 = new Receiver(Double.parseDouble("2.00"));

		// A receiver's email address
		receiver2.setEmail("xyz@paypal.com");

		// Set to true to indicate a chained payment; only one receiver can be a
		// primary receiver. Omit this field, or set it to false for simple and
		// parallel payments.
		receiver2.setPrimary(Boolean.FALSE);
		receiverLst.add(receiver2);

		ReceiverList receiverList = new ReceiverList(receiverLst);
		payRequest.setReceiverList(receiverList);
		return payResponse = makeAPICall(payRequest);
	}

	// ##Parallel Payment
	// `Note:
	// For parallel Payment all the above mentioned request parameters in
	// simplePay() are required, but in receiverList we can have multiple
	// receivers`
	public PayResponse parallelPay() {

		List<Receiver> receiverLst = new ArrayList<Receiver>();

		// Amount to be credited to the receiver's account
		Receiver receiver1 = new Receiver(Double.parseDouble("4.00"));

		// A receiver's email address
		receiver1.setEmail("abc@paypal.com");
		receiverLst.add(receiver1);

		// Amount to be credited to the receiver's account
		Receiver receiver2 = new Receiver(Double.parseDouble("2.00"));

		// A receiver's email address
		receiver2.setEmail("xyz@paypal.com");
		receiverLst.add(receiver2);

		ReceiverList receiverList = new ReceiverList(receiverLst);
		payRequest.setReceiverList(receiverList);
		return payResponse = makeAPICall(payRequest);

	}

	private PayResponse makeAPICall(PayRequest payRequest) {

		Logger logger = Logger.getLogger(this.getClass().toString());

		// ## Creating service wrapper object
		// Creating service wrapper object to make API call and loading
		// configuration file for your credentials and endpoint
		AdaptivePaymentsService service = null;
		try {
			service = new AdaptivePaymentsService(
					"src/main/resources/sdk_config.properties");
		} catch (IOException e) {
			logger.severe("Error Message : " + e.getMessage());
		}
		PayResponse payResponse = null;
		try {

			// ## Making API call
			// Invoke the appropriate method corresponding to API in service
			// wrapper object
			payResponse = service.pay(payRequest);
		} catch (Exception e) {
			logger.severe("Error Message : " + e.getMessage());
		}
			
		// ## Accessing response parameters
		// You can access the response parameters using getter methods in
		// response object as shown below
		// ### Success values
		if (payResponse.getResponseEnvelope().getAck().getValue()
				.equalsIgnoreCase("Success")) {

			// The pay key, which is a token you use in other Adaptive
			// Payment APIs (such as the Refund Method) to identify this
			// payment. The pay key is valid for 3 hours; the payment must
			// be approved while the pay key is valid.
			logger.info("Pay Key : " + payResponse.getPayKey());

			// Once you get success response, user has to redirect to PayPal
			// for the payment. Construct redirectURL as follows,
			// `redirectURL=https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_ap-payment&paykey="
			// + payResponse.getPayKey();`
		}
		// ### Error Values
		// Access error values from error list using getter methods
		else {
			logger.severe("API Error Message : "
					+ payResponse.getError().get(0).getMessage());
		}
		return payResponse;

	}
}
