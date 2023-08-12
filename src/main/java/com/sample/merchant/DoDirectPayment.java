package com.sample.merchant;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import urn.ebay.api.PayPalAPI.DoDirectPaymentReq;
import urn.ebay.api.PayPalAPI.DoDirectPaymentRequestType;
import urn.ebay.api.PayPalAPI.DoDirectPaymentResponseType;
import urn.ebay.api.PayPalAPI.PayPalAPIInterfaceServiceService;
import urn.ebay.apis.CoreComponentTypes.BasicAmountType;
import urn.ebay.apis.eBLBaseComponents.CreditCardDetailsType;
import urn.ebay.apis.eBLBaseComponents.CreditCardTypeType;
import urn.ebay.apis.eBLBaseComponents.CurrencyCodeType;
import urn.ebay.apis.eBLBaseComponents.DoDirectPaymentRequestDetailsType;
import urn.ebay.apis.eBLBaseComponents.ErrorType;
import urn.ebay.apis.eBLBaseComponents.PayerInfoType;
import urn.ebay.apis.eBLBaseComponents.PaymentActionCodeType;
import urn.ebay.apis.eBLBaseComponents.PaymentDetailsType;

//# DoDirectPayment API
// The DoDirectPayment API Operation enables you to process a credit card
// payment.
// This sample code uses Merchant Java SDK to make API call. You can
// download the SDKs
// [here](https://github.com/paypal/sdk-packages/tree/gh-pages/merchant-sdk/java)
public class DoDirectPayment {

	public DoDirectPaymentResponseType doDirectPayment() {

		Logger logger = Logger.getLogger(this.getClass().toString());

		// ## DoDirectPaymentReq
		DoDirectPaymentReq doDirectPaymentReq = new DoDirectPaymentReq();
		DoDirectPaymentRequestDetailsType doDirectPaymentRequestDetails = new DoDirectPaymentRequestDetailsType();

		// Information about the credit card to be charged.
		CreditCardDetailsType creditCard = new CreditCardDetailsType();

		// Type of credit card. For UK, only Maestro, MasterCard, Discover, and
		// Visa are allowable. For Canada, only MasterCard and Visa are
		// allowable and Interac debit cards are not supported. It is one of the
		// following values:
		//
		// * Visa
		// * MasterCard
		// * Discover
		// * Amex
		// * Solo
		// * Switch
		// * Maestro: See note.
		// `Note:
		// If the credit card type is Maestro, you must set currencyId to GBP.
		// In addition, you must specify either StartMonth and StartYear or
		// IssueNumber.`
		creditCard.setCreditCardType(CreditCardTypeType.VISA);

		// Credit Card number
		creditCard.setCreditCardNumber("4770461107194023");

		// ExpiryMonth of credit card
		creditCard.setExpMonth(Integer.parseInt("12"));

		// Expiry Year of credit card
		creditCard.setExpYear(Integer.parseInt("2021"));

		// Details about the owner of the credit card.
		PayerInfoType cardOwner = new PayerInfoType();

		// Email address of buyer.
		cardOwner.setPayer("enduser_biz@gmail.com");
		creditCard.setCardOwner(cardOwner);

		doDirectPaymentRequestDetails.setCreditCard(creditCard);

		// How you want to obtain payment. When implementing parallel payments,
        // this field is required and must be set to `Order`. When implementing
        // digital goods, this field is required and must be set to `Sale`. If the
        // transaction does not include a one-time purchase, this field is
        // ignored. It is one of the following values:
        //  
        // * `Sale` - This is a final sale for which you are requesting payment
        // (default).
        // * `Authorization` - This payment is a basic authorization subject to
        // settlement with PayPal Authorization and Capture.
        // * `Order` - This payment is an order authorization subject to
        // settlement with PayPal Authorization and Capture.
        // `Note:
        // You cannot set this field to Sale in SetExpressCheckout request and
        // then change the value to Authorization or Order in the
        // DoExpressCheckoutPayment request. If you set the field to
        // Authorization or Order in SetExpressCheckout, you may set the field
        // to Sale.` 
		doDirectPaymentRequestDetails
				.setPaymentAction(PaymentActionCodeType.SALE);

		// Information about the payment
		PaymentDetailsType paymentDetails = new PaymentDetailsType();

		// Total cost of the transaction to the buyer. If shipping cost and tax
		// charges are known, include them in this value. If not, this value
		// should be the current sub-total of the order.
		//
		// If the transaction includes one or more one-time purchases, this
		// field must be equal to
		// the sum of the purchases. Set this field to 0 if the transaction does
		// not include a one-time purchase such as when you set up a billing
		// agreement for a recurring payment that is not immediately charged.
		// When the field is set to 0, purchase-specific fields are ignored.
		//
		// * `Currency Code` - You must set the currencyID attribute to one of
		// the
		// 3-character currency codes for any of the supported PayPal
		// currencies.
		// * `Amount`
		BasicAmountType orderTotal = new BasicAmountType(CurrencyCodeType.USD,
				"4.00");
		paymentDetails.setOrderTotal(orderTotal);

		// Your URL for receiving Instant Payment Notification (IPN) about this
		// transaction. If you do not specify this value in the request, the
		// notification URL from your Merchant Profile is used, if one exists.
		paymentDetails.setNotifyURL("http://localhost/ipn");

		doDirectPaymentRequestDetails.setPaymentDetails(paymentDetails);

		// IP address of the buyer's browser.
		// `Note:
		// PayPal records this IP addresses as a means to detect possible
		// fraud.`
		doDirectPaymentRequestDetails.setIPAddress("127.0.0.1");

		DoDirectPaymentRequestType doDirectPaymentRequest = new DoDirectPaymentRequestType(
				doDirectPaymentRequestDetails);
		doDirectPaymentReq.setDoDirectPaymentRequest(doDirectPaymentRequest);

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

		DoDirectPaymentResponseType doDirectPaymentResponse = null;
		try {
			// ## Making API call
			// Invoke the appropriate method corresponding to API in service
			// wrapper object
			doDirectPaymentResponse = service
					.doDirectPayment(doDirectPaymentReq);
		} catch (Exception e) {
			logger.severe("Error Message : " + e.getMessage());
		}

		// ## Accessing response parameters
		// You can access the response parameters using getter methods in
		// response object as shown below
		// ### Success values
		if (doDirectPaymentResponse.getAck().getValue()
				.equalsIgnoreCase("success")) {

			// Unique identifier of the transaction
			logger.info("Transaction ID :"
					+ doDirectPaymentResponse.getTransactionID());
		}
		// ### Error Values
		// Access error values from error list using getter methods
		else {
			List<ErrorType> errorList = doDirectPaymentResponse.getErrors();
			logger.severe("API Error Message : "
					+ errorList.get(0).getLongMessage());
		}
		return doDirectPaymentResponse;
	}
}
