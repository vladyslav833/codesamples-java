package com.sample.merchant;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import urn.ebay.api.PayPalAPI.GetBalanceReq;
import urn.ebay.api.PayPalAPI.GetBalanceRequestType;
import urn.ebay.api.PayPalAPI.GetBalanceResponseType;
import urn.ebay.api.PayPalAPI.PayPalAPIInterfaceServiceService;
import urn.ebay.apis.CoreComponentTypes.BasicAmountType;
import urn.ebay.apis.eBLBaseComponents.ErrorType;

// # GetBalance API
// The GetBalance API Operation obtains the available balance for a PayPal account.
// This sample code uses Merchant Java SDK to make API call. You can
// download the SDKs [here](https://github.com/paypal/sdk-packages/tree/gh-pages/merchant-sdk/java)
public class GetBalance {
	public GetBalanceResponseType getBalance() {

		Logger logger = Logger.getLogger(this.getClass().toString());

		// ## GetBalanceReq
		GetBalanceReq getBalanceReq = new GetBalanceReq();
		GetBalanceRequestType getBalanceRequest = new GetBalanceRequestType();

		// Indicates whether to return all currencies. It is one of the
		// following values:
		// 
		// * 0 – Return only the balance for the primary currency holding.
		// * 1 – Return the balance for each currency holding.
		getBalanceRequest.setReturnAllCurrencies("1");
		getBalanceReq.setGetBalanceRequest(getBalanceRequest);

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
		GetBalanceResponseType getBalanceResponse = null;
		try {
			// ## Making API call
			// Invoke the appropriate method corresponding to API in service
			// wrapper object
			 getBalanceResponse = service
					.getBalance(getBalanceReq);
		} catch (Exception e) {
			logger.severe("Error Message : " + e.getMessage());
		}
		// ## Accessing response parameters
		// You can access the response parameters using getter methods in
		// response object as shown below
		// ### Success values
		if (getBalanceResponse.getAck().getValue()
				.equalsIgnoreCase("success")) {

			Iterator<BasicAmountType> iterator = getBalanceResponse
					.getBalanceHoldings().iterator();
			while (iterator.hasNext()) {
				BasicAmountType amount = iterator.next();
				
				// Available balance and associated currency code for each currency held, including the primary currency. The first currency is the primary currency.
				logger.info("Balance Holdings : " + amount.getValue() + " "
						+ amount.getCurrencyID());
			}
		}
		// ### Error Values
		// Access error values from error list using getter methods
		else {
			List<ErrorType> errorList = getBalanceResponse.getErrors();
			logger.severe("API Error Message : "
					+ errorList.get(0).getLongMessage());
		}
		return getBalanceResponse;
	}
}
