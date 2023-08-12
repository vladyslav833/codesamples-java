package com.sample.merchant;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import urn.ebay.api.PayPalAPI.DoReferenceTransactionReq;
import urn.ebay.api.PayPalAPI.DoReferenceTransactionRequestType;
import urn.ebay.api.PayPalAPI.DoReferenceTransactionResponseType;
import urn.ebay.api.PayPalAPI.PayPalAPIInterfaceServiceService;
import urn.ebay.apis.CoreComponentTypes.BasicAmountType;
import urn.ebay.apis.eBLBaseComponents.CurrencyCodeType;
import urn.ebay.apis.eBLBaseComponents.DoReferenceTransactionRequestDetailsType;
import urn.ebay.apis.eBLBaseComponents.ErrorType;
import urn.ebay.apis.eBLBaseComponents.PaymentActionCodeType;
import urn.ebay.apis.eBLBaseComponents.PaymentDetailsType;

//# DoReferenceTransaction API
// The DoReferenceTransaction API operation processes a payment from a
// buyer's account, which is identified by a previous transaction.
// This sample code uses Merchant Java SDK to make API call. You can
// download the SDKs [here](https://github.com/paypal/sdk-packages/tree/gh-pages/merchant-sdk/java)
public class DoReferenceTransaction {

	public DoReferenceTransactionResponseType doReferenceTransaction() {

		Logger logger = Logger.getLogger(this.getClass().toString());

		// ## DoReferenceTransactionReq
		DoReferenceTransactionReq doReferenceTransactionReq = new DoReferenceTransactionReq();

		// Information about the payment.
		PaymentDetailsType paymentDetails = new PaymentDetailsType();

		// The total cost of the transaction to the buyer. If shipping cost and
		// tax charges are known, include them in this value. If not, this value
		// should be the current subtotal of the order. 
		
		// If the transaction includes one or more one-time purchases, this field must be equal to
		// the sum of the purchases. Set this field to 0 if the transaction does
		// not include a one-time purchase such as when you set up a billing
		// agreement for a recurring payment that is not immediately charged.
		// When the field is set to 0, purchase-specific fields are ignored
		// 
		// * `Currency ID` - You must set the currencyID attribute to one of the
		// 3-character currency codes for any of the supported PayPal
		// currencies.
		// * `Amount`
		BasicAmountType orderTotal = new BasicAmountType(CurrencyCodeType.USD,
				"3.00");
		paymentDetails.setOrderTotal(orderTotal);
		
		// Your URL for receiving Instant Payment Notification (IPN) about this transaction. If you do not specify this value in the request, the notification URL from your Merchant Profile is used, if one exists.
		paymentDetails.setNotifyURL("http://localhost/ipn");

		// `DoReferenceTransactionRequestDetails` takes mandatory params:
		// 
		// * `Reference Id` - A transaction ID from a previous purchase, such as a
		// credit card charge using the DoDirectPayment API, or a billing
		// agreement ID.
		// * `Payment Action Code` - How you want to obtain payment. It is one of
		// the following values:
		//  * Authorization
		//  * Sale
		//  * Order
		//  * None
		// * `Payment Details`
		DoReferenceTransactionRequestDetailsType doReferenceTransactionRequestDetails = new DoReferenceTransactionRequestDetailsType(
				"97U72738FY126561H", PaymentActionCodeType.SALE, paymentDetails);
		
		
		DoReferenceTransactionRequestType doReferenceTransactionRequest = new DoReferenceTransactionRequestType(
				doReferenceTransactionRequestDetails);
		doReferenceTransactionReq
				.setDoReferenceTransactionRequest(doReferenceTransactionRequest);

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
		DoReferenceTransactionResponseType doReferenceTransactionResponse = null;
		try {
			// ## Making API call
			// Invoke the appropriate method corresponding to API in service
			// wrapper object
			 doReferenceTransactionResponse = service
					.doReferenceTransaction(doReferenceTransactionReq);
		} catch (Exception e) {
			logger.severe("Error Message : " + e.getMessage());
		}
			
		// ## Accessing response parameters
		// You can access the response parameters using getter methods in
		// response object as shown below
		// ### Success values
		if (doReferenceTransactionResponse.getAck().getValue()
				.equalsIgnoreCase("success")) {
			
			// The final amount charged, including any shipping and taxes from your Merchant Profile.
			logger.info("Amount: "
					+ doReferenceTransactionResponse
							.getDoReferenceTransactionResponseDetails()
							.getAmount().getValue()
					+ " "
					+ doReferenceTransactionResponse
							.getDoReferenceTransactionResponseDetails()
							.getAmount().getCurrencyID());

		}
		// ### Error Values
		// Access error values from error list using getter methods
		else {
			List<ErrorType> errorList = doReferenceTransactionResponse
					.getErrors();
			logger.severe("API Error Message : "
					+ errorList.get(0).getLongMessage());
		}
		return doReferenceTransactionResponse;
	}
}
