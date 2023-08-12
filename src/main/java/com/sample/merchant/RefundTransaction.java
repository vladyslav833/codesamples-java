package com.sample.merchant;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import urn.ebay.api.PayPalAPI.PayPalAPIInterfaceServiceService;
import urn.ebay.api.PayPalAPI.RefundTransactionReq;
import urn.ebay.api.PayPalAPI.RefundTransactionRequestType;
import urn.ebay.api.PayPalAPI.RefundTransactionResponseType;
import urn.ebay.apis.CoreComponentTypes.BasicAmountType;
import urn.ebay.apis.eBLBaseComponents.CurrencyCodeType;
import urn.ebay.apis.eBLBaseComponents.ErrorType;
import urn.ebay.apis.eBLBaseComponents.RefundType;

public class RefundTransaction {

	// # RefundTransaction API
	// The RefundTransaction API operation issues a refund to the PayPal account
	// holder associated with a transaction.
	// This sample code uses Merchant Java SDK to make API call. You can
	// download the SDKs [here](https://github.com/paypal/sdk-packages/tree/gh-pages/merchant-sdk/java)
	public RefundTransactionResponseType refundTransaction() {

		Logger logger = Logger.getLogger(this.getClass().toString());

		// ## RefundTransactionReq
		RefundTransactionReq refundTransactionReq = new RefundTransactionReq();
		RefundTransactionRequestType refundTransactionRequest = new RefundTransactionRequestType();

		// Either the `transaction ID` or the `payer ID` must be specified.
		// PayerID is unique encrypted merchant identification number
		// For setting `payerId`,
		// `refundTransactionRequest.setPayerID("A9BVYX8XCR9ZQ");`

		// Unique identifier of the transaction to be refunded.
		refundTransactionRequest.setTransactionID("1GF88795WC5643301");

		// Type of refund you are making. It is one of the following values:
		// 
		// * `Full` - Full refund (default).
		// * `Partial` - Partial refund.
		// * `ExternalDispute` - External dispute. (Value available since
		// version
		// 82.0)
		// * `Other` - Other type of refund. (Value available since version
		// 82.0)
		refundTransactionRequest.setRefundType(RefundType.PARTIAL);

		// `Refund amount`, which contains
		// 
		// * `Currency Code`
		// * `Amount`
		// The amount is required if RefundType is Partial.
		// `Note:
		// If RefundType is Full, do not set the amount.`
		BasicAmountType amount = new BasicAmountType(CurrencyCodeType.USD,
				"1.00");
		refundTransactionRequest.setAmount(amount);

		refundTransactionReq
				.setRefundTransactionRequest(refundTransactionRequest);

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
		RefundTransactionResponseType refundTransactionResponse = null;
		try {
			// ## Making API call
			// Invoke the appropriate method corresponding to API in service
			// wrapper object
			refundTransactionResponse = service
					.refundTransaction(refundTransactionReq);
		} catch (Exception e) {
			logger.severe("Error Message : " + e.getMessage());
		}

		// ## Accessing response parameters
		// You can access the response parameters using getter methods in
		// response object as shown below
		// ### Success values
		if (refundTransactionResponse.getAck().getValue()
				.equalsIgnoreCase("success")) {

			// Unique transaction ID of the refund.
			logger.info("Refund Transaction ID"
					+ refundTransactionResponse.getRefundTransactionID());
		}
		// ### Error Values
		// Access error values from error list using getter methods
		else {
			List<ErrorType> errorList = refundTransactionResponse
					.getErrors();
			logger.severe("API Error Message : "
					+ errorList.get(0).getLongMessage());
		}
		return refundTransactionResponse;

	}
}
