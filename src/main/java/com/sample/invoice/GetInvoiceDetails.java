package com.sample.invoice;

import java.io.IOException;
import java.util.logging.Logger;

import com.paypal.svcs.services.InvoiceService;
import com.paypal.svcs.types.common.RequestEnvelope;
import com.paypal.svcs.types.pt.GetInvoiceDetailsRequest;
import com.paypal.svcs.types.pt.GetInvoiceDetailsResponse;

// # GetInvoiceDetails API
// Use the GetInvoiceDetails API operation to get detailed information about an invoice.
// This sample code uses Invoice Java SDK to make API call. You can
// download the SDKs [here](https://github.com/paypal/sdk-packages/tree/gh-pages/invoice-sdk/java)
public class GetInvoiceDetails {

	public GetInvoiceDetailsResponse getInvoiceDetails() {

		Logger logger = Logger.getLogger(this.getClass().toString());

		// ##GetInvoiceDetailsRequest
		// Use the GetInvoiceDetailsRequest message to get detailed information
		// about an invoice.

		// The code for the language in which errors are returned, which must be
		// en_US.
		RequestEnvelope requestEnvelope = new RequestEnvelope();
		requestEnvelope.setErrorLanguage("en_US");

		// GetInvoiceDetailsRequest which takes mandatory params:
		// 
		// * `Request Envelope` - Information common to each API operation, such
		// as the language in which an error message is returned.
		// * `Invoice ID` - ID of the invoice to retrieve.
		GetInvoiceDetailsRequest getInvoiceDetailsRequest = new GetInvoiceDetailsRequest(
				requestEnvelope, "INV2-ZC9R-X6MS-RK8H-4VKJ");

		// ## Creating service wrapper object
		// Creating service wrapper object to make API call and loading
		// configuration file for your credentials and endpoint
		InvoiceService service = null;
		try {
			service = new InvoiceService(
					"src/main/resources/sdk_config.properties");
		} catch (IOException e) {
			logger.severe("Error Message : " + e.getMessage());
		}
		GetInvoiceDetailsResponse getInvoiceDetailsResponse = null;
		try {

			// ## Making API call
			// Invoke the appropriate method corresponding to API in service
			// wrapper object
			getInvoiceDetailsResponse = service
					.getInvoiceDetails(getInvoiceDetailsRequest);
		} catch (Exception e) {
			logger.severe("Error Message : " + e.getMessage());
		}

		// ## Accessing response parameters
		// You can access the response parameters using getter methods in
		// response object as shown below
		// ### Success values
		if (getInvoiceDetailsResponse.getResponseEnvelope().getAck()
				.getValue().equalsIgnoreCase("Success")) {

			// Status of the invoice searched.
			logger.info("Status : "
					+ getInvoiceDetailsResponse.getInvoiceDetails()
							.getStatus());

		}
		// ### Error Values
		// Access error values from error list using getter methods
		else {
			logger.severe("API Error Message : "
					+ getInvoiceDetailsResponse.getError().get(0)
							.getMessage());
		}
		return getInvoiceDetailsResponse;

	}

}
