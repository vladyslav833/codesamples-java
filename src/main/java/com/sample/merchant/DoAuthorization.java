package com.sample.merchant;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import urn.ebay.api.PayPalAPI.DoAuthorizationReq;
import urn.ebay.api.PayPalAPI.DoAuthorizationRequestType;
import urn.ebay.api.PayPalAPI.DoAuthorizationResponseType;
import urn.ebay.api.PayPalAPI.PayPalAPIInterfaceServiceService;
import urn.ebay.apis.CoreComponentTypes.BasicAmountType;
import urn.ebay.apis.eBLBaseComponents.CurrencyCodeType;
import urn.ebay.apis.eBLBaseComponents.ErrorType;

//# DoAuthorization API
// Authorize a payment.
// This sample code uses Merchant Java SDK to make API call. You can
// download the SDKs
// [here](https://github.com/paypal/sdk-packages/tree/gh-pages/merchant-sdk/java)
public class DoAuthorization {

	public DoAuthorizationResponseType doAuthorization() {

		Logger logger = Logger.getLogger(this.getClass().toString());

		// ## DoAuthorizationReq
		DoAuthorizationReq doAuthorizationReq = new DoAuthorizationReq();

		// `Amount` which takes mandatory params:
		// 
		// * `currencyCode`
		// * `amount`
		BasicAmountType amount = new BasicAmountType(CurrencyCodeType.USD,
				"4.00");

		// `DoAuthorizationRequest` which takes mandatory params:
		// 
		// * `Transaction ID` - Value of the order's transaction identification
		// number returned by PayPal.
		// * `Amount` - Amount to authorize.
		DoAuthorizationRequestType doAuthorizationRequest = new DoAuthorizationRequestType(
				"O-4VR15106P7416533H", amount);
		doAuthorizationReq.setDoAuthorizationRequest(doAuthorizationRequest);
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
		DoAuthorizationResponseType doAuthorizationResponse = null;
		try {
			// ## Making API call
			// Invoke the appropriate method corresponding to API in service
			// wrapper object
			doAuthorizationResponse = service
					.doAuthorization(doAuthorizationReq);
		} catch (Exception e) {
			logger.severe("Error Message : " + e.getMessage());
		}

		// ## Accessing response parameters
		// You can access the response parameters using getter methods in
		// response object as shown below
		// ### Success values
		if (doAuthorizationResponse.getAck().getValue()
				.equalsIgnoreCase("success")) {

			// Authorization identification number
			logger.info("Transaction ID:"
					+ doAuthorizationResponse.getTransactionID());
		}
		// ### Error Values
		// Access error values from error list using getter methods
		else {
			List<ErrorType> errorList = doAuthorizationResponse.getErrors();
			logger.severe("API Error Message : "
					+ errorList.get(0).getLongMessage());
		}
		return doAuthorizationResponse;
	}
}
