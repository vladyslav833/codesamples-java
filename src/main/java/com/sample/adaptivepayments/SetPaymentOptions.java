package com.sample.adaptivepayments;

import java.io.IOException;
import java.util.logging.Logger;

import com.paypal.svcs.services.AdaptivePaymentsService;
import com.paypal.svcs.types.ap.SetPaymentOptionsRequest;
import com.paypal.svcs.types.ap.SetPaymentOptionsResponse;
import com.paypal.svcs.types.common.RequestEnvelope;

// # SetPaymentOptions API
// You use the SetPaymentOptions API operation to specify settings for a payment of the actionType `CREATE`. This actionType is specified in the PayRequest message.
// This sample code uses AdaptivePayments Java SDK to make API call. You can
// download the SDK [here](https://github.com/paypal/sdk-packages/tree/gh-pages/adaptivepayments-sdk/java)
public class SetPaymentOptions {

	public SetPaymentOptionsResponse setPaymentOptions() {

		Logger logger = Logger.getLogger(this.getClass().toString());

		// ##SetPaymentOptionsRequest
		// The code for the language in which errors are returned, which must be
		// en_US.
		RequestEnvelope requestEnvelope = new RequestEnvelope();
		requestEnvelope.setErrorLanguage("en_US");

		// SetPaymentOptionsRequest which takes,
		// 
		// * `Request Envelope` - Information common to each API operation, such
		// as the language in which an error message is returned.
		// * `Pay Key` - The pay key that identifies the payment for which you
		// want to set payment options. This is the pay key returned in the
		// PayResponse message. Action Type in PayRequest must be `CREATE`
		SetPaymentOptionsRequest setPaymentOptionsRequest = new SetPaymentOptionsRequest(
				requestEnvelope, "AP-1VB65877N5917862M");

		// You can set options as shown below:
		// 
		// Specifies display items in payment flows and emails.
		// DisplayOptions displayOptions = new DisplayOptions();
		// The business name to display. The name cannot exceed 128 characters.
		// displayOptions.setBusinessName("Toy Shop");
		// setPaymentOptionsRequest.setDisplayOptions(displayOptions);

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
		SetPaymentOptionsResponse setPaymentOptionsResponse = null;
		try {

			// ## Making API call
			// Invoke the appropriate method corresponding to API in service
			// wrapper object
			 setPaymentOptionsResponse = service
					.setPaymentOptions(setPaymentOptionsRequest);
		} catch (Exception e) {
			logger.severe("Error Message : " + e.getMessage());
		}

		// ## Accessing response parameters
		// You can access the response parameters using getter methods in
		// response object as shown below
		// ### Success values
		if (setPaymentOptionsResponse.getResponseEnvelope().getAck()
				.getValue().equalsIgnoreCase("Success")) {

		}
		// ### Error Values
		// Access error values from error list using getter methods
		else {
			logger.severe("API Error Message : "
					+ setPaymentOptionsResponse.getError().get(0)
							.getMessage());
		}
		return setPaymentOptionsResponse;

	}
}
