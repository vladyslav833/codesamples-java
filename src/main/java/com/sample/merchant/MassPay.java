package com.sample.merchant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import urn.ebay.api.PayPalAPI.MassPayReq;
import urn.ebay.api.PayPalAPI.MassPayRequestItemType;
import urn.ebay.api.PayPalAPI.MassPayRequestType;
import urn.ebay.api.PayPalAPI.MassPayResponseType;
import urn.ebay.api.PayPalAPI.PayPalAPIInterfaceServiceService;
import urn.ebay.apis.CoreComponentTypes.BasicAmountType;
import urn.ebay.apis.eBLBaseComponents.CurrencyCodeType;
import urn.ebay.apis.eBLBaseComponents.ErrorType;

//# MassPay API
// The MassPay API operation makes a payment to one or more PayPal account
// holders.
// This sample code uses Merchant Java SDK to make API call. You can
// download the SDKs [here](https://github.com/paypal/sdk-packages/tree/gh-pages/merchant-sdk/java)
public class MassPay {
	
	public MassPayResponseType massPay() {

		Logger logger = Logger.getLogger(this.getClass().toString());

		// ## MassPayReq
		// Details of each payment.
		// `Note:
		// A single MassPayRequest can include up to 250 MassPayItems.`
		MassPayReq massPayReq = new MassPayReq();
		List<MassPayRequestItemType> massPayItemList = new ArrayList<MassPayRequestItemType>();

		// `Amount` for the payment which contains
		// 
		// * `Currency Code`
		// * `Amount`
		BasicAmountType amount1 = new BasicAmountType(CurrencyCodeType.USD,
				"4.00");
		MassPayRequestItemType massPayRequestItem1 = new MassPayRequestItemType(
				amount1);

		// Email Address of receiver
		massPayRequestItem1.setReceiverEmail("abc@paypal.com");

		// `Amount` for the payment which contains
		// 
		// * `Currency Code`
		// * `Amount`
		BasicAmountType amount2 = new BasicAmountType(CurrencyCodeType.USD,
				"3.00");
		MassPayRequestItemType massPayRequestItem2 = new MassPayRequestItemType(
				amount2);

		// Email Address of receiver
		massPayRequestItem2.setReceiverEmail("xyz@paypal.com");

		// `Amount` for the payment which contains
		// 
		// * `Currency Code`
		// * `Amount`
		BasicAmountType amount3 = new BasicAmountType(CurrencyCodeType.USD,
				"7.00");
		MassPayRequestItemType massPayRequestItem3 = new MassPayRequestItemType(
				amount3);

		// Email Address of receiver
		massPayRequestItem3.setReceiverEmail("def@paypal.com");
		massPayItemList.add(massPayRequestItem2);
		massPayItemList.add(massPayRequestItem1);
		massPayItemList.add(massPayRequestItem3);
		MassPayRequestType massPayRequest = new MassPayRequestType(
				massPayItemList);
		massPayReq.setMassPayRequest(massPayRequest);

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
		MassPayResponseType massPayResponse = null;
		try {
			// ## Making API call
			// Invoke the appropriate method corresponding to API in service
			// wrapper object
			 massPayResponse = service.massPay(massPayReq);
		} catch (Exception e) {
			logger.severe("Error Message : " + e.getMessage());
		}
			
		// ## Accessing response parameters
		// You can access the response parameters using getter methods in
		// response object as shown below
		// ### Success values
		if (massPayResponse.getAck().getValue().equalsIgnoreCase("success")) {

		}
		// ### Error Values
		// Access error values from error list using getter methods
		else {
			List<ErrorType> errorList = massPayResponse.getErrors();
			logger.severe("API Error Message : "
					+ errorList.get(0).getLongMessage());
		}
		return massPayResponse;
	}
}
