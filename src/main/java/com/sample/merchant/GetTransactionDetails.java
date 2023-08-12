package com.sample.merchant;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import urn.ebay.api.PayPalAPI.GetTransactionDetailsReq;
import urn.ebay.api.PayPalAPI.GetTransactionDetailsRequestType;
import urn.ebay.api.PayPalAPI.GetTransactionDetailsResponseType;
import urn.ebay.api.PayPalAPI.PayPalAPIInterfaceServiceService;
import urn.ebay.apis.eBLBaseComponents.ErrorType;

//# GetTransactionDetails API
// The GetTransactionDetails API operation obtains information about a
// specific transaction.
// This sample code uses Merchant Java SDK to make API call. You can
// download the SDKs [here](https://github.com/paypal/sdk-packages/tree/gh-pages/merchant-sdk/java)
public class GetTransactionDetails {

	public GetTransactionDetailsResponseType getTransactionDetails() {

		Logger logger = Logger.getLogger(this.getClass().toString());

		// ## GetTransactionDetailsReq
		GetTransactionDetailsReq getTransactionDetailsReq = new GetTransactionDetailsReq();
		GetTransactionDetailsRequestType getTransactionDetailsRequest = new GetTransactionDetailsRequestType();

		// Unique identifier of a transaction.
		// `Note:
		// The details for some kinds of transactions cannot be retrieved with
		// GetTransactionDetails. You cannot obtain details of bank transfer
		// withdrawals, for example.`
		getTransactionDetailsRequest.setTransactionID("5AT5731435011481X");
		getTransactionDetailsReq
				.setGetTransactionDetailsRequest(getTransactionDetailsRequest);

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
		GetTransactionDetailsResponseType getTransactionDetailsResponse = null;
		try {
			// ## Making API call
			// Invoke the appropriate method corresponding to API in service
			// wrapper object
			 getTransactionDetailsResponse = service
					.getTransactionDetails(getTransactionDetailsReq);
		} catch (Exception e) {
			logger.severe("Error Message : " + e.getMessage());
		}
		// ## Accessing response parameters
		// You can access the response parameters using getter methods in
		// response object as shown below
		// ### Success values
		if (getTransactionDetailsResponse.getAck().getValue()
				.equalsIgnoreCase("success")) {

			// Unique PayPal Customer Account identification number.
			logger.info("Payer ID:"
					+ getTransactionDetailsResponse
							.getPaymentTransactionDetails().getPayerInfo()
							.getPayerID());
		}
		// ### Error Values
		// Access error values from error list using getter methods
		else {
			List<ErrorType> errorList = getTransactionDetailsResponse
					.getErrors();
			logger.severe("API Error Message : "
					+ errorList.get(0).getLongMessage());
		}
		return getTransactionDetailsResponse; 
	}

}
