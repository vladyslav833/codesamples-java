package com.sample.merchant;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import urn.ebay.api.PayPalAPI.DoVoidReq;
import urn.ebay.api.PayPalAPI.DoVoidRequestType;
import urn.ebay.api.PayPalAPI.DoVoidResponseType;
import urn.ebay.api.PayPalAPI.PayPalAPIInterfaceServiceService;
import urn.ebay.apis.eBLBaseComponents.ErrorType;

//# DoVoid API
// Void an order or an authorization.
// This sample code uses Merchant Java SDK to make API call. You can
// download the SDKs [here](https://github.com/paypal/sdk-packages/tree/gh-pages/merchant-sdk/java)
public class DoVoid {

	public DoVoidResponseType doVoid() {

		Logger logger = Logger.getLogger(this.getClass().toString());

		// ## DoVoidReq
		DoVoidReq doVoidReq = new DoVoidReq();

		// DoVoidRequest which takes mandatory params:
		// 
		// * `Authorization ID` - Original authorization ID specifying the
		// authorization to void or, to void an order, the order ID.
		// `Important:
		// If you are voiding a transaction that has been reauthorized, use the
		// ID from the original authorization, and not the reauthorization.`
		DoVoidRequestType doVoidRequest = new DoVoidRequestType("9B2288061E685550E");
		doVoidReq.setDoVoidRequest(doVoidRequest);
		
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
		DoVoidResponseType doVoidResponse = null;
		try {
			// ## Making API call
			// Invoke the appropriate method corresponding to API in service
			// wrapper object
			 doVoidResponse = service.doVoid(doVoidReq);
		} catch (Exception e) {
			logger.severe("Error Message : " + e.getMessage());
		}

		// ## Accessing response parameters
		// You can access the response parameters using getter methods in
		// response object as shown below
		// ### Success values
		if (doVoidResponse.getAck().getValue().equalsIgnoreCase("success")) {
			
			// Authorization identification number you specified in the
			// request.
			logger.info("Authorization ID:"
					+ doVoidResponse.getAuthorizationID());
		}
		// ### Error Values
		// Access error values from error list using getter methods
		else {
			List<ErrorType> errorList = doVoidResponse.getErrors();
			logger.severe("API Error Message : "
					+ errorList.get(0).getLongMessage());
		}
		return doVoidResponse;
	}
}
