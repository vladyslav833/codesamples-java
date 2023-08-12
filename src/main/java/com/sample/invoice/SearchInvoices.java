package com.sample.invoice;

import java.io.IOException;
import java.util.logging.Logger;

import com.paypal.svcs.services.InvoiceService;
import com.paypal.svcs.types.common.RequestEnvelope;
import com.paypal.svcs.types.pt.SearchInvoicesRequest;
import com.paypal.svcs.types.pt.SearchInvoicesResponse;
import com.paypal.svcs.types.pt.SearchParametersType;

// # SearchInvoices API
// Use the SearchInvoice API operation to search an invoice.
// This sample code uses Invoice Java SDK to make API call. You can
//  the SDKs [here](https://github.com/paypal/sdk-packages/tree/gh-pages/invoice-sdk/java)
public class SearchInvoices {

	public SearchInvoicesResponse searchInvoices() {

		Logger logger = Logger.getLogger(this.getClass().toString());

		// ##SearchInvoicesRequest
		// Use the SearchInvoiceRequest message to search an invoice.

		// The code for the language in which errors are returned, which must be
		// en_US.
		RequestEnvelope requestEnvelope = new RequestEnvelope();
		requestEnvelope.setErrorLanguage("en_US");

		SearchParametersType parameters = new SearchParametersType();

		// Invoice amount search. It specifies the smallest amount to be
		// returned. If you pass a value for this field, you must also pass a
		// currencyCode value.
		parameters.setUpperAmount(Double.parseDouble("4.00"));

		// Currency used for lower and upper amounts. It is required when you
		// specify lowerAmount or upperAmount.
		parameters.setCurrencyCode("USD");

		// SearchInvoicesRequest which takes mandatory params:
		// 
		// * `Request Envelope` - Information common to each API operation, such
		// as the language in which an error message is returned.
		// * `Merchant Email` - Email address of invoice creator.
		// * `SearchParameters` - Parameters constraining the search.
		// * `Page` - Page number of result set, starting with 1.
		// * `Page Size` - Number of results per page, between 1 and 100.
		SearchInvoicesRequest searchInvoicesRequest = new SearchInvoicesRequest(
				requestEnvelope, "jb-us-seller@paypal.com", parameters,
				Integer.parseInt("1"), Integer.parseInt("10"));

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
		SearchInvoicesResponse searchInvoicesResponse = null;
		try {

			// ## Making API call
			// Invoke the appropriate method corresponding to API in service
			// wrapper object
			searchInvoicesResponse = service
					.searchInvoices(searchInvoicesRequest);
		} catch (Exception e) {
			logger.severe("Error Message : " + e.getMessage());
		}

		// ## Accessing response parameters
		// You can access the response parameters using getter methods in
		// response object as shown below
		// ### Success values
		if (searchInvoicesResponse.getResponseEnvelope().getAck()
				.getValue().equalsIgnoreCase("Success")) {

			// Number of invoices that matched the request.
			logger.info("Count : " + searchInvoicesResponse.getCount());

		}
		// ### Error Values
		// Access error values from error list using getter methods
		else {
			logger.severe("API Error Message : "
					+ searchInvoicesResponse.getError().get(0).getMessage());
		}
		return searchInvoicesResponse;

	}
}
