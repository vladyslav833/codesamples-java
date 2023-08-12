package com.sample.merchant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import urn.ebay.api.PayPalAPI.PayPalAPIInterfaceServiceService;
import urn.ebay.api.PayPalAPI.SetExpressCheckoutReq;
import urn.ebay.api.PayPalAPI.SetExpressCheckoutRequestType;
import urn.ebay.api.PayPalAPI.SetExpressCheckoutResponseType;
import urn.ebay.apis.CoreComponentTypes.BasicAmountType;
import urn.ebay.apis.eBLBaseComponents.AddressType;
import urn.ebay.apis.eBLBaseComponents.CountryCodeType;
import urn.ebay.apis.eBLBaseComponents.CurrencyCodeType;
import urn.ebay.apis.eBLBaseComponents.ErrorType;
import urn.ebay.apis.eBLBaseComponents.PaymentActionCodeType;
import urn.ebay.apis.eBLBaseComponents.PaymentDetailsType;
import urn.ebay.apis.eBLBaseComponents.SellerDetailsType;
import urn.ebay.apis.eBLBaseComponents.SetExpressCheckoutRequestDetailsType;

//# SetExpressCheckout API
// The SetExpressCheckout API operation initiates an Express Checkout
// transaction.
// This sample code uses Merchant Java SDK to make API call. You can
// download the SDKs [here](https://github.com/paypal/sdk-packages/tree/gh-pages/merchant-sdk/java)
public class SetExpressCheckout {

	public SetExpressCheckoutResponseType setExpressCheckout() {

		Logger logger = Logger.getLogger(this.getClass().toString());

		// ## SetExpressCheckoutReq
		SetExpressCheckoutRequestDetailsType setExpressCheckoutRequestDetails = new SetExpressCheckoutRequestDetailsType();

		// URL to which the buyer's browser is returned after choosing to pay
		// with PayPal. For digital goods, you must add JavaScript to this page
		// to close the in-context experience.
		// `Note:
		// PayPal recommends that the value be the final review page on which
		// the buyer confirms the order and payment or billing agreement.`
		setExpressCheckoutRequestDetails
				.setReturnURL("http://localhost/return");

		// URL to which the buyer is returned if the buyer does not approve the
		// use of PayPal to pay you. For digital goods, you must add JavaScript
		// to this page to close the in-context experience.
		// `Note:
		// PayPal recommends that the value be the original page on which the
		// buyer chose to pay with PayPal or establish a billing agreement.`
		setExpressCheckoutRequestDetails
				.setCancelURL("http://localhost/cancel");

		// ### Payment Information
		// list of information about the payment
		List<PaymentDetailsType> paymentDetailsList = new ArrayList<PaymentDetailsType>();

		// information about the first payment
		PaymentDetailsType paymentDetails1 = new PaymentDetailsType();

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
		BasicAmountType orderTotal1 = new BasicAmountType(CurrencyCodeType.USD,
				"2.00");
		paymentDetails1.setOrderTotal(orderTotal1);

		// How you want to obtain payment. When implementing parallel payments,
		// this field is required and must be set to `Order`. When implementing
		// digital goods, this field is required and must be set to `Sale`. If
		// the
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
		paymentDetails1.setPaymentAction(PaymentActionCodeType.ORDER);

		// Unique identifier for the merchant. For parallel payments, this field
		// is required and must contain the Payer Id or the email address of the
		// merchant.
		SellerDetailsType sellerDetails1 = new SellerDetailsType();
		sellerDetails1.setPayPalAccountID("jb-us-seller@paypal.com");
		paymentDetails1.setSellerDetails(sellerDetails1);

		// A unique identifier of the specific payment request, which is
		// required for parallel payments.
		paymentDetails1.setPaymentRequestID("PaymentRequest1");

		// `Address` to which the order is shipped, which takes mandatory
		// params:
		// 
		// * `Street Name`
		// * `City`
		// * `State`
		// * `Country`
		// * `Postal Code`
		AddressType shipToAddress1 = new AddressType();
		shipToAddress1.setStreet1("Ape Way");
		shipToAddress1.setCityName("Austin");
		shipToAddress1.setStateOrProvince("TX");
		shipToAddress1.setCountry(CountryCodeType.US);
		shipToAddress1.setPostalCode("78750");

		// Your URL for receiving Instant Payment Notification (IPN) about this
		// transaction. If you do not specify this value in the request, the
		// notification URL from your Merchant Profile is used, if one exists.
		paymentDetails1.setNotifyURL("http://localhost/ipn");

		paymentDetails1.setShipToAddress(shipToAddress1);

		// information about the second payment
		PaymentDetailsType paymentDetails2 = new PaymentDetailsType();
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
		BasicAmountType orderTotal2 = new BasicAmountType(CurrencyCodeType.USD,
				"4.00");
		paymentDetails2.setOrderTotal(orderTotal2);

		// How you want to obtain payment. When implementing parallel payments,
		// this field is required and must be set to `Order`. When implementing
		// digital goods, this field is required and must be set to `Sale`. If
		// the
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
		paymentDetails2.setPaymentAction(PaymentActionCodeType.ORDER);

		// Unique identifier for the merchant. For parallel payments, this field
		// is required and must contain the Payer Id or the email address of the
		// merchant.
		SellerDetailsType sellerDetails2 = new SellerDetailsType();
		sellerDetails2.setPayPalAccountID("jb-us-seller@paypal.com");
		paymentDetails2.setSellerDetails(sellerDetails2);

		// A unique identifier of the specific payment request, which is
		// required for parallel payments.
		paymentDetails2.setPaymentRequestID("PaymentRequest2");

		// `Address` to which the order is shipped, which takes mandatory
		// params:
		// 
		// * `Street Name`
		// * `City`
		// * `State`
		// * `Country`
		// * `Postal Code`
		AddressType shipToAddress2 = new AddressType();
		shipToAddress2.setStreet1("Ape Way");
		shipToAddress2.setCityName("Austin");
		shipToAddress2.setStateOrProvince("TX");
		shipToAddress2.setCountry(CountryCodeType.US);
		shipToAddress2.setPostalCode("78750");

		// Your URL for receiving Instant Payment Notification (IPN) about this
		// transaction. If you do not specify this value in the request, the
		// notification URL from your Merchant Profile is used, if one exists.
		paymentDetails2.setNotifyURL("http://localhost/ipn");

		paymentDetails2.setShipToAddress(shipToAddress2);

		paymentDetailsList.add(paymentDetails1);
		paymentDetailsList.add(paymentDetails2);

		setExpressCheckoutRequestDetails.setPaymentDetails(paymentDetailsList);

		SetExpressCheckoutReq setExpressCheckoutReq = new SetExpressCheckoutReq();
		SetExpressCheckoutRequestType setExpressCheckoutRequest = new SetExpressCheckoutRequestType(
				setExpressCheckoutRequestDetails);

		setExpressCheckoutReq
				.setSetExpressCheckoutRequest(setExpressCheckoutRequest);

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
		SetExpressCheckoutResponseType setExpressCheckoutResponse = null;
		try {
			// ## Making API call
			// Invoke the appropriate method corresponding to API in service
			// wrapper object
			setExpressCheckoutResponse = service
					.setExpressCheckout(setExpressCheckoutReq);
		} catch (Exception e) {
			logger.severe("Error Message : " + e.getMessage());
		}
		// ## Accessing response parameters
		// You can access the response parameters using getter methods in
		// response object as shown below
		// ### Success values
		if (setExpressCheckoutResponse.getAck().getValue()
				.equalsIgnoreCase("success")) {

			// ### Redirecting to PayPal for authorization
			// Once you get the "Success" response, needs to authorise the
			// transaction by making buyer to login into PayPal. For that,
			// need to construct redirect url using EC token from response.
			// For example,
			// `redirectURL="https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token="+setExpressCheckoutResponse.getToken();`

			// Express Checkout Token
			logger.info("EC Token:" + setExpressCheckoutResponse.getToken());
		}
		// ### Error Values
		// Access error values from error list using getter methods
		else {
			List<ErrorType> errorList = setExpressCheckoutResponse.getErrors();
			logger.severe("API Error Message : "
					+ errorList.get(0).getLongMessage());
		}
		return setExpressCheckoutResponse;
	}
}
