package com.sample.adaptivepayments;

import java.io.IOException;
import java.util.logging.Logger;

import com.paypal.svcs.services.AdaptivePaymentsService;
import com.paypal.svcs.types.ap.GetPaymentOptionsRequest;
import com.paypal.svcs.types.ap.GetPaymentOptionsResponse;
import com.paypal.svcs.types.common.RequestEnvelope;

// # GetPaymentOptions API
// You use the GetPaymentOptions API operation to retrieve the payment options passed with the SetPaymentOptionsRequest.
// This sample code uses AdaptivePayments Java SDK to make API call. You can
// download the SDK [here](https://github.com/paypal/sdk-packages/tree/gh-pages/adaptivepayments-sdk/java)
public class GetPaymentOptions {

	public GetPaymentOptionsResponse getPaymentOptions() {

		Logger logger = Logger.getLogger(this.getClass().toString());

		// ##GetPaymentOptionsRequest
		// The code for the language in which errors are returned, which must be
		// en_US.
		RequestEnvelope requestEnvelope = new RequestEnvelope();
		requestEnvelope.setErrorLanguage("en_US");

		// GetPaymentOptionsRequest which takes,
		// 
		// * `Request Envelope` - Information common to each API operation, such
		// as the language in which an error message is returned.
		// * `Pay Key` - The pay key that identifies the payment for which you
		// want to set payment options. This is the pay key returned in the
		// PayResponse message. Action Type in PayRequest must be `CREATE`
		GetPaymentOptionsRequest getPaymentOptionsRequest = new GetPaymentOptionsRequest(
				requestEnvelope, "AP-1VB65877N5917862M");

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
		GetPaymentOptionsResponse getPaymentOptionsResponse = null;
		try {

			// ## Making API call
			// Invoke the appropriate method corresponding to API in service
			// wrapper object
			 getPaymentOptionsResponse = service
					.getPaymentOptions(getPaymentOptionsRequest);
		} catch (Exception e) {
			logger.severe("Error Message : " + e.getMessage());
		}

		// ## Accessing response parameters
		// You can access the response parameters using getter methods in
		// response object as shown below
		// ### Success values
		if (getPaymentOptionsResponse.getResponseEnvelope().getAck()
				.getValue().equalsIgnoreCase("Success")) {

			// Business Name you set in SetPaymentOptions
			logger.info("Business Name : "
					+ getPaymentOptionsResponse.getDisplayOptions()
							.getBusinessName());
		}
		// ### Error Values
		// Access error values from error list using getter methods
		else {
			logger.severe("API Error Message : "
					+ getPaymentOptionsResponse.getError().get(0)
							.getMessage());
		}
		return getPaymentOptionsResponse;
	}

}
