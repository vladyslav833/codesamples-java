package com.sample.invoice;

import java.io.IOException;
import java.util.logging.Logger;

import com.paypal.svcs.services.InvoiceService;
import com.paypal.svcs.types.common.RequestEnvelope;
import com.paypal.svcs.types.pt.SendInvoiceRequest;
import com.paypal.svcs.types.pt.SendInvoiceResponse;

// # SendInvoice API
// Use the SendInvoice API operation to send an invoice to a payer, and notify the payer of the pending invoice.
// This sample code uses Invoice Java SDK to make API call. You can
// download the SDKs [here](https://github.com/paypal/sdk-packages/tree/gh-pages/invoice-sdk/java)
public class SendInvoice {

	public SendInvoiceResponse sendInvoice() {

		Logger logger = Logger.getLogger(this.getClass().toString());

		// ##SendInvoiceRequest
		// Use the SendInvoiceRequest message to send an invoice to a payer, and
		// notify the payer of the pending invoice.

		// The code for the language in which errors are returned, which must be
		// en_US.
		RequestEnvelope requestEnvelope = new RequestEnvelope();
		requestEnvelope.setErrorLanguage("en_US");

		// SendInvoiceRequest which takes mandatory params:
		// 
		// * `Request Envelope` - Information common to each API operation, such
		// as the language in which an error message is returned.
		// * `Invoice ID` - ID of the invoice to send.
		SendInvoiceRequest sendInvoiceRequest = new SendInvoiceRequest(
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
		SendInvoiceResponse sendInvoiceResponse = null;
		try {

			// ## Making API call
			// Invoke the appropriate method corresponding to API in service
			// wrapper object
			sendInvoiceResponse = service.sendInvoice(sendInvoiceRequest);
		} catch (Exception e) {
			logger.severe("Error Message : " + e.getMessage());
		}

		// ## Accessing response parameters
		// You can access the response parameters using getter methods in
		// response object as shown below
		// ### Success values
		if (sendInvoiceResponse.getResponseEnvelope().getAck().getValue()
				.equalsIgnoreCase("Success")) {

			// ID of the created invoice.
			logger.info("Invoice ID : "
					+ sendInvoiceResponse.getInvoiceID());

		}
		// ### Error Values
		// Access error values from error list using getter methods
		else {
			logger.severe("API Error Message : "
					+ sendInvoiceResponse.getError().get(0).getMessage());
		}
		return sendInvoiceResponse;

	}
}
