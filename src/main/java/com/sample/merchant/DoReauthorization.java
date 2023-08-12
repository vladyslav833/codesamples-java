package com.sample.merchant;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import urn.ebay.api.PayPalAPI.DoReauthorizationReq;
import urn.ebay.api.PayPalAPI.DoReauthorizationRequestType;
import urn.ebay.api.PayPalAPI.DoReauthorizationResponseType;
import urn.ebay.api.PayPalAPI.PayPalAPIInterfaceServiceService;
import urn.ebay.apis.CoreComponentTypes.BasicAmountType;
import urn.ebay.apis.eBLBaseComponents.CurrencyCodeType;
import urn.ebay.apis.eBLBaseComponents.ErrorType;

//# DoReauthorization API
// Authorize a payment.
// This sample code uses Merchant Java SDK to make API call. You can
// download the SDKs [here](https://github.com/paypal/sdk-packages/tree/gh-pages/merchant-sdk/java)
public class DoReauthorization {

	public DoReauthorizationResponseType doReauthorization() {

		Logger logger = Logger.getLogger(this.getClass().toString());

		// ## DoAuthorizationReq
		DoReauthorizationReq doReauthorizationReq = new DoReauthorizationReq();

		// `Amount` to reauthorize which takes mandatory params:
		// 
		// * `currencyCode`
		// * `amount`
		BasicAmountType amount = new BasicAmountType(CurrencyCodeType.USD,
				"3.00");

		// `DoReauthorizationRequest` which takes mandatory params:
		// 
		// * `Authorization Id` - Value of a previously authorized transaction
		// identification number returned by PayPal.
		// * `amount`
		DoReauthorizationRequestType doReauthorizationRequest = new DoReauthorizationRequestType(
				"9B2288061E685550E", amount);
		doReauthorizationReq
				.setDoReauthorizationRequest(doReauthorizationRequest);
		
		// ## Creating service wrapper object
		// Creating service wrapper object to make an API call and loading
		// configuration file for your credentials and endpoint
		PayPalAPIInterfaceServiceService service = null;
		try {

			service = new PayPalAPIInterfaceServiceService(
					"src/main/resources/sdk_config.properties");
		} catch (IOException e) {
			logger.severe("Error Message : " + e.getMessage());
		}
		DoReauthorizationResponseType doReauthorizationResponse = null;
		try {
			// ## Making API call
			// Invoke the appropriate method corresponding to API in service
			// wrapper object
			 doReauthorizationResponse = service
					.doReauthorization(doReauthorizationReq);
		} catch (Exception e) {
			logger.severe("Error Message : " + e.getMessage());
		}

		// ## Accessing response parameters
		// You can access the response parameters using getter methods in
		// response object as shown below
		// ### Success values
		if (doReauthorizationResponse.getAck().getValue()
				.equalsIgnoreCase("success")) {
			
			// Authorization identification number
			logger.info("Authorization ID:"
					+ doReauthorizationResponse.getAuthorizationID());
		}
		// ### Error Values
		// Access error values from error list using getter methods
		else {
			List<ErrorType> errorList = doReauthorizationResponse
					.getErrors();
			logger.severe("API Error Message : "
					+ errorList.get(0).getLongMessage());
		}
		return doReauthorizationResponse;
	}
}
