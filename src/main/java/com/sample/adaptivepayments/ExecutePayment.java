package com.sample.adaptivepayments;

import java.io.IOException;
import java.util.logging.Logger;

import com.paypal.svcs.services.AdaptivePaymentsService;
import com.paypal.svcs.types.ap.ExecutePaymentRequest;
import com.paypal.svcs.types.ap.ExecutePaymentResponse;
import com.paypal.svcs.types.common.RequestEnvelope;

// # ExecutePayment API
// The ExecutePayment API operation lets you execute a payment set up with the Pay API operation with the actionType CREATE. To pay receivers identified in the Pay call, set the pay key from the PayResponse message in the ExecutePaymentRequest message.
// This sample code uses AdaptivePayments Java SDK to make API call. You can
// download the SDK [here](https://github.com/paypal/sdk-packages/tree/gh-pages/adaptivepayments-sdk/java)
public class ExecutePayment {

	public ExecutePaymentResponse executePayment() {

		Logger logger = Logger.getLogger(this.getClass().toString());

		// ##ExecutePaymentRequest
		// The code for the language in which errors are returned, which must be
		// en_US.
		RequestEnvelope requestEnvelope = new RequestEnvelope();
		requestEnvelope.setErrorLanguage("en_US");

		// ExecutePaymentRequest which takes,
		// 
		// * `Request Envelope` - Information common to each API operation, such
		// as the language in which an error message is returned.
		// * `Pay Key` - The pay key that identifies the payment for which you
		// want to set payment options. This is the pay key returned in the
		// PayResponse message. Action Type in PayRequest must be `CREATE` and must set `SenderEmail`
		ExecutePaymentRequest executePaymentRequest = new ExecutePaymentRequest(
				requestEnvelope, "AP-1VB65877N5917862M");

		// The ID of the funding plan from which to make this payment.
		executePaymentRequest.setFundingPlanId("0");

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
		ExecutePaymentResponse executePaymentResponse = null;
		try {

			// ## Making API call
			// Invoke the appropriate method corresponding to API in service
			// wrapper object
			executePaymentResponse = service
					.executePayment(executePaymentRequest);
		} catch (Exception e) {
			logger.severe("Error Message : " + e.getMessage());
		}

		// ## Accessing response parameters
		// You can access the response parameters using getter methods in
		// response object as shown below
		// ### Success values
		if (executePaymentResponse.getResponseEnvelope().getAck()
				.getValue().equalsIgnoreCase("Success")) {

			// The status of the payment. Possible values are:
			// 
			// * CREATED - The payment request was received; funds will be
			// transferred once the payment is approved
			// * COMPLETED - The payment was successful
			// * INCOMPLETE - Some transfers succeeded and some failed for a
			// parallel payment
			// * ERROR - The payment failed and all attempted transfers
			// failed
			// or all completed transfers were successfully reversed
			// * REVERSALERROR - One or more transfers failed when
			// attempting
			// to reverse a payment
			logger.info("Payment Execution Status : "
					+ executePaymentResponse.getPaymentExecStatus());
		}
		// ### Error Values
		// Access error values from error list using getter methods
		else {
			logger.severe("API Error Message : "
					+ executePaymentResponse.getError().get(0).getMessage());
		}
		return executePaymentResponse;

	}

}
