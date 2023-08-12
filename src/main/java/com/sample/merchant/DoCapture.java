package com.sample.merchant;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import urn.ebay.api.PayPalAPI.DoCaptureReq;
import urn.ebay.api.PayPalAPI.DoCaptureRequestType;
import urn.ebay.api.PayPalAPI.DoCaptureResponseType;
import urn.ebay.api.PayPalAPI.PayPalAPIInterfaceServiceService;
import urn.ebay.apis.CoreComponentTypes.BasicAmountType;
import urn.ebay.apis.eBLBaseComponents.CompleteCodeType;
import urn.ebay.apis.eBLBaseComponents.CurrencyCodeType;
import urn.ebay.apis.eBLBaseComponents.ErrorType;

// # DoCapture API
// Captures an authorized payment.
// This sample code uses Merchant Java SDK to make API call. You can
// download the SDKs [here](https://github.com/paypal/sdk-packages/tree/gh-pages/merchant-sdk/java)
public class DoCapture {

		public DoCaptureResponseType doCapture() {

		Logger logger = Logger.getLogger(this.getClass().toString());

		// ## DoCaptureReq
		DoCaptureReq doCaptureReq = new DoCaptureReq();

		// `Amount` to capture which takes mandatory params:
		// 
		// * `currencyCode`
		// * `amount`
		BasicAmountType amount = new BasicAmountType(CurrencyCodeType.USD,
				"4.00");

		// `DoCaptureRequest` which takes mandatory params:
		// 
		// * `Authorization ID` - Authorization identification number of the
		// payment you want to capture. This is the transaction ID returned from
		// DoExpressCheckoutPayment, DoDirectPayment, or CheckOut. For
		// point-of-sale transactions, this is the transaction ID returned by
		// the CheckOut call when the payment action is Authorization.
		// * `amount` - Amount to capture
		// * `CompleteCode` - Indicates whether or not this is your last capture.
		// It is one of the following values:
		//  * Complete – This is the last capture you intend to make.
		//  * NotComplete – You intend to make additional captures.
		// `Note:
		// If Complete, any remaining amount of the original authorized
		// transaction is automatically voided and all remaining open
		// authorizations are voided.`
		DoCaptureRequestType doCaptureRequest = new DoCaptureRequestType(
				"O-4VR15106P7416533H", amount, CompleteCodeType.NOTCOMPLETE);

		doCaptureReq.setDoCaptureRequest(doCaptureRequest);

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
		DoCaptureResponseType doCaptureResponse = null;
		try {
			// ## Making API call
			// Invoke the appropriate method corresponding to API in service
			// wrapper object
			 doCaptureResponse = service
					.doCapture(doCaptureReq);
		} catch (Exception e) {
			logger.severe("Error Message : " + e.getMessage());
		}

		// ## Accessing response parameters
		// You can access the response parameters using getter methods in
		// response object as shown below
		// ### Success values
		if (doCaptureResponse.getAck().getValue()
				.equalsIgnoreCase("success")) {
			
			// Authorization identification number
			logger.info("Authorization ID:"
					+ doCaptureResponse.getDoCaptureResponseDetails()
							.getAuthorizationID());
		}
		// ### Error Values
		// Access error values from error list using getter methods
		else {
			List<ErrorType> errorList = doCaptureResponse.getErrors();
			logger.severe("API Error Message : "
					+ errorList.get(0).getLongMessage());
		}
		return doCaptureResponse;
	}

}
