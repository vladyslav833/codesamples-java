package com.sample.merchant;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import urn.ebay.api.PayPalAPI.PayPalAPIInterfaceServiceService;
import urn.ebay.api.PayPalAPI.TransactionSearchReq;
import urn.ebay.api.PayPalAPI.TransactionSearchRequestType;
import urn.ebay.api.PayPalAPI.TransactionSearchResponseType;
import urn.ebay.apis.eBLBaseComponents.ErrorType;
import urn.ebay.apis.eBLBaseComponents.PaymentTransactionSearchResultType;

//# TransactionSearch API
// The TransactionSearch API searches transaction history for transactions
// that meet the specified criteria.
// `Note:
// The maximum number of transactions that can be returned from a
// TransactionSearch API call is 100.`
// This sample code uses Merchant Java SDK to make API call. You can
// download the SDKs
// [here](https://github.com/paypal/sdk-packages/tree/gh-pages/merchant-sdk/java)
public class TransactionSearch {

	public TransactionSearchResponseType transactionSearch() {

		Logger logger = Logger.getLogger(this.getClass().toString());

		// ## TransactionSearchReq
		TransactionSearchReq transactionSearchReq = new TransactionSearchReq();

		// `TransactionSearchRequestType` which takes mandatory argument:
		// 
		// * `Start Date` - The earliest transaction date at which to start the
		// search.
		TransactionSearchRequestType transactionSearchRequest = new TransactionSearchRequestType(
				"2012-12-25T00:00:00+0530");
		transactionSearchReq
				.setTransactionSearchRequest(transactionSearchRequest);

		// ## Creating service wrapper object
		// Creating service wrapper object to make API call and loading
		// configuration file for your credentials and endpoint
		PayPalAPIInterfaceServiceService service = null;
		try {
			service = new PayPalAPIInterfaceServiceService(
					"src/main/resources/sdk_config.properties");
		} catch (IOException e) {
			logger.severe("Error Message : " + e.getMessage());
		}
		TransactionSearchResponseType transactionSearchResponse = null;
		try {
			// ## Making API call
			// Invoke the appropriate method corresponding to API in service
			// wrapper object
			transactionSearchResponse = service
					.transactionSearch(transactionSearchReq);
		} catch (Exception e) {
			logger.severe("Error Message : " + e.getMessage());
		}
		// ## Accessing response parameters
		// You can access the response parameters using getter methods in
		// response object as shown below
		// ### Success values
		if (transactionSearchResponse.getAck().getValue()
				.equalsIgnoreCase("success")) {

			// Search Results
			Iterator<PaymentTransactionSearchResultType> iterator = transactionSearchResponse
					.getPaymentTransactions().iterator();
			while (iterator.hasNext()) {
				PaymentTransactionSearchResultType searchResult = iterator
						.next();

				// Merchant's transaction ID.
				logger.info("Transaction ID : "
						+ searchResult.getTransactionID());
			}
		}
		// ### Error Values
		// Access error values from error list using getter methods
		else {
			List<ErrorType> errorList = transactionSearchResponse.getErrors();
			logger.severe("API Error Message : "
					+ errorList.get(0).getLongMessage());
		}

		return transactionSearchResponse;
	}

}
