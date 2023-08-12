package com.sample.adaptivepayments;

import java.io.IOException;
import java.util.logging.Logger;

import com.paypal.svcs.services.AdaptivePaymentsService;
import com.paypal.svcs.types.ap.PreapprovalRequest;
import com.paypal.svcs.types.ap.PreapprovalResponse;
import com.paypal.svcs.types.common.RequestEnvelope;

// # Preapproval API
// Use the Preapproval API operation to set up an agreement between yourself
// and a sender for making payments on the sender's behalf. You set up a
// preapprovals for a specific maximum amount over a specific period of time
// and, optionally, by any of the following constraints:
// 
// * the number of payments
// * a maximum per-payment amount
// * a specific day of the week or the month
// * whether or not a PIN is required for each payment request.
// 
// This sample code uses AdaptivePayments Java SDK to make API call. You can
// download the SDK [here](https://github.com/paypal/sdk-packages/tree/gh-pages/adaptivepayments-sdk/java)

public class Preapproval {

	public PreapprovalResponse preapproval() {

		Logger logger = Logger.getLogger(this.getClass().toString());

		// ##PreapprovalRequest
		// The code for the language in which errors are returned, which must be
		// en_US.
		RequestEnvelope requestEnvelope = new RequestEnvelope();
		requestEnvelope.setErrorLanguage("en_US");

		// PreapprovalRequest takes mandatory params:
		// 
		// * `RequestEnvelope` - Information common to each API operation, such
		// as the language in which an error message is returned.
		// * `Cancel URL` - URL to redirect the sender's browser to after
		// canceling the preapproval
		// * `Currency Code` - The code for the currency in which the payment is
		// made; you can specify only one currency, regardless of the number of
		// receivers
		// * `Return URL` - URL to redirect the sender's browser to after the
		// sender has logged into PayPal and confirmed the preapproval
		// * `Starting Date` - First date for which the preapproval is valid. It
		// cannot be before today's date or after the ending date.
		PreapprovalRequest preapprovalRequest = new PreapprovalRequest(
				requestEnvelope, "http://localhost/cancel", "USD",
				"http://localhost/return", "2013-12-18");

		// The URL to which you want all IPN messages for this preapproval to be
		// sent.
		// This URL supersedes the IPN notification URL in your profile
		preapprovalRequest.setIpnNotificationUrl("http://localhost/ipn");

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
		PreapprovalResponse preapprovalResponse = null;
		try {

			// ## Making API call
			// invoke the appropriate method corresponding to API in service
			// wrapper object
			preapprovalResponse = service.preapproval(preapprovalRequest);
		} catch (Exception e) {
			logger.severe("Error Message : " + e.getMessage());
		}

		// ## Accessing response parameters
		// You can access the response parameters using getter methods in
		// response object as shown below
		// ### Success values
		if (preapprovalResponse.getResponseEnvelope().getAck().getValue()
				.equalsIgnoreCase("Success")) {

			// A preapproval key that identifies the preapproval requested.
			// You can use this key in other Adaptive Payment requests to
			// identify this preapproval.
			logger.info("Preapproval Key : "
					+ preapprovalResponse.getPreapprovalKey());

			// Once you get success response, user has to redirect to PayPal
			// to preapprove the payment. Construct redirectURL as follows,
			// `redirectURL=https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_ap-preapproval&preapprovalkey="
			// + preapprovalResponse.getPreapprovalKey();`
		}
		// ### Error Values
		// Access error values from error list using getter methods
		else {
			logger.severe("API Error Message : "
					+ preapprovalResponse.getError().get(0).getMessage());
		}
		return preapprovalResponse;
	}
}
