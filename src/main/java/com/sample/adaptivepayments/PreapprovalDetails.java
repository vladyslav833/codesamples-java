package com.sample.adaptivepayments;

import java.io.IOException;
import java.util.logging.Logger;

import com.paypal.svcs.services.AdaptivePaymentsService;
import com.paypal.svcs.types.ap.PreapprovalDetailsRequest;
import com.paypal.svcs.types.ap.PreapprovalDetailsResponse;
import com.paypal.svcs.types.common.RequestEnvelope;

// # PreapprovalDetails API
// Use the PreapprovalDetails API operation to obtain information about an agreement between you and a sender for making payments on the sender's behalf.
// This sample code uses AdaptivePayments Java SDK to make API call. You can
// download the SDKs [here](https://github.com/paypal/sdk-packages/tree/gh-pages/adaptivepayments-sdk/java)
public class PreapprovalDetails {

	public PreapprovalDetailsResponse preapprovalDetails() {

		Logger logger = Logger.getLogger(this.getClass().toString());

		// ##PreapprovaDetailslRequest
		// The code for the language in which errors are returned, which must be
		// en_US.
		RequestEnvelope requestEnvelope = new RequestEnvelope();
		requestEnvelope.setErrorLanguage("en_US");

		// PreapprovalRequest object which takes mandatory params:
		// 
		// * `Request Envelope` - Information common to each API operation, such
		// as the language in which an error message is returned.
		// * `Preapproval Key` - A preapproval key that identifies the
		// preapproval for which you want to retrieve details. The preapproval
		// key is returned in the PreapprovalResponse message.
		PreapprovalDetailsRequest preapprovalDetailsRequest = new PreapprovalDetailsRequest(
				requestEnvelope, "PA-1KM93450LF5424305");

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
		PreapprovalDetailsResponse preapprovalDetailsResponse = null;
		try {

			// ## Making API call
			// invoke the appropriate method corresponding to API in service
			// wrapper object
			 preapprovalDetailsResponse = service
					.preapprovalDetails(preapprovalDetailsRequest);
		} catch (Exception e) {
			logger.severe("Error Message : " + e.getMessage());
		}

		// ## Accessing response parameters
		// You can access the response parameters using getter methods in
		// response object as shown below
		// ### Success values
		if (preapprovalDetailsResponse.getResponseEnvelope().getAck()
				.getValue().equalsIgnoreCase("Success")) {

			// First date for which the preapproval is valid.
			logger.info("Starting Date : "
					+ preapprovalDetailsResponse.getStartingDate());

		}
		// ### Error Values
		// Access error values from error list using getter methods
		else {
			logger.severe("API Error Message : "
					+ preapprovalDetailsResponse.getError().get(0)
							.getMessage());
		}
		return preapprovalDetailsResponse;

	}

}
