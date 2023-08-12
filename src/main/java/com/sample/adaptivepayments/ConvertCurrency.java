package com.sample.adaptivepayments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import com.paypal.svcs.services.AdaptivePaymentsService;
import com.paypal.svcs.types.ap.ConvertCurrencyRequest;
import com.paypal.svcs.types.ap.ConvertCurrencyResponse;
import com.paypal.svcs.types.ap.CurrencyCodeList;
import com.paypal.svcs.types.ap.CurrencyConversionList;
import com.paypal.svcs.types.ap.CurrencyList;
import com.paypal.svcs.types.common.CurrencyType;
import com.paypal.svcs.types.common.RequestEnvelope;

// # ConvertCurrency API
// Use the ConvertCurrency API operation to request the current foreign exchange (FX) rate for a specific amount and currency.
// This sample code uses AdaptivePayments Java SDK to make API call. You can
// download the SDKs [here](https://github.com/paypal/sdk-packages/tree/gh-pages/adaptivepayments-sdk/java)
public class ConvertCurrency {

	public ConvertCurrencyResponse convertCurrency() {

		Logger logger = Logger.getLogger(this.getClass().toString());

		// ##ConvertCurrencyRequest
		// The ConvertCurrencyRequest message enables you to have your
		// application get an estimated exchange rate for a list of amounts.
		// This API operation does not affect PayPal balances.

		// The code for the language in which errors are returned, which must be
		// en_US.
		RequestEnvelope requestEnvelope = new RequestEnvelope();
		requestEnvelope.setErrorLanguage("en_US");

		// CurrencyList which takes two arguments:
		// 
		// * `CurrencyCodeType` - The currency code. Allowable values are:
		//  * Australian Dollar - AUD
		//  * Brazilian Real - BRL
		//  `Note:
		//  The Real is supported as a payment currency and currency balance only
		//  for Brazilian PayPal accounts.`
		//  * Canadian Dollar - CAD
		//  * Czech Koruna - CZK
		//  * Danish Krone - DKK
		//  * Euro - EUR
		//  * Hong Kong Dollar - HKD
		//  * Hungarian Forint - HUF
		//  * Israeli New Sheqel - ILS
		//  * Japanese Yen - JPY
		//  * Malaysian Ringgit - MYR
		//  `Note:
		//  The Ringgit is supported as a payment currency and currency balance
		//  only for Malaysian PayPal accounts.`
		//  * Mexican Peso - MXN
		//  * Norwegian Krone - NOK
		//  * New Zealand Dollar - NZD
		//  * Philippine Peso - PHP
		//  * Polish Zloty - PLN
		//  * Pound Sterling - GBP
		//  * Singapore Dollar - SGD
		//  * Swedish Krona - SEK
		//  * Swiss Franc - CHF
		//  * Taiwan New Dollar - TWD
		//  * Thai Baht - THB
		//  * Turkish Lira - TRY
		//  `Note:
		//  The Turkish Lira is supported as a payment currency and currency
		//  balance only for Turkish PayPal accounts.`
		//  * U.S. Dollar - USD
		// * `amount`
		List<CurrencyType> currencyList = new ArrayList<CurrencyType>();
		CurrencyType currency = new CurrencyType("USD",
				Double.parseDouble("4.00"));
		currencyList.add(currency);
		CurrencyList baseAmountList = new CurrencyList(currencyList);

		// CurrencyCodeList which contains
		// 
		// * `Currency Code` - Allowable values are:
		//  * Australian Dollar - AUD
		//  * Brazilian Real - BRL
		//  `Note:
		//  The Real is supported as a payment currency and currency balance only
		//  for Brazilian PayPal accounts.`
		//  * Canadian Dollar - CAD
		//  * Czech Koruna - CZK
		//  * Danish Krone - DKK
		//  * Euro - EUR
		//  * Hong Kong Dollar - HKD
		//  * Hungarian Forint - HUF
		//  * Israeli New Sheqel - ILS
		//  * Japanese Yen - JPY
		//  * Malaysian Ringgit - MYR
		//  `Note:
		//  The Ringgit is supported as a payment currency and currency balance
		//  only for Malaysian PayPal accounts.`
		//  * Mexican Peso - MXN
		//  * Norwegian Krone - NOK
		//  * New Zealand Dollar - NZD
		//  * Philippine Peso - PHP
		//  * Polish Zloty - PLN
		//  * Pound Sterling - GBP
		//  * Singapore Dollar - SGD
		//  * Swedish Krona - SEK
		//  * Swiss Franc - CHF
		//  * Taiwan New Dollar - TWD
		//  * Thai Baht - THB
		//  * Turkish Lira - TRY
		//  `Note:
		//  The Turkish Lira is supported as a payment currency and currency
		//  balance only for Turkish PayPal accounts.`
		//  * U.S. Dollar - USD
		List<String> currencyCodeList = new ArrayList<String>();
		currencyCodeList.add("GBP");
		CurrencyCodeList convertToCurrencyList = new CurrencyCodeList(
				currencyCodeList);

		// ConvertCurrencyRequest which takes params:
		// 
		// * `Request Envelope` - Information common to each API operation, such
		// as the language in which an error message is returned
		// * `BaseAmountList` - A list of amounts with associated currencies to
		// be converted.
		// * `ConvertToCurrencyList` - A list of currencies to convert to.
		ConvertCurrencyRequest convertCurrencyRequest = new ConvertCurrencyRequest(
				requestEnvelope, baseAmountList, convertToCurrencyList);

		// ## Creating service wrapper object
		// Creating service wrapper object to make API call and loading
		// configuration file for your credentials and endpoint
		AdaptivePaymentsService service = null;
		try {
			service = new AdaptivePaymentsService(
					"src/main/resources/sdk_config.properties");
		} catch (IOException e) {
			logger.severe("Error Message : " + e.getMessage());
		}
		ConvertCurrencyResponse convertCurrencyResponse = null;
		try {

			// ## Making API call
			// invoke the appropriate method corresponding to API in service
			// wrapper object
			 convertCurrencyResponse = service
					.convertCurrency(convertCurrencyRequest);
		} catch (Exception e) {
			logger.severe("Error Message : " + e.getMessage());
		}

		// ## Accessing response parameters
		// You can access the response parameters using getter methods in
		// response object as shown below
		// ### Success values
		if (convertCurrencyResponse.getResponseEnvelope().getAck()
				.getValue().equalsIgnoreCase("Success")) {

			if (convertCurrencyResponse.getEstimatedAmountTable()
					.getCurrencyConversionList() != null
					&& convertCurrencyResponse.getEstimatedAmountTable()
							.getCurrencyConversionList().size() > 0) {
				Iterator<CurrencyConversionList> iterator = convertCurrencyResponse
						.getEstimatedAmountTable()
						.getCurrencyConversionList().iterator();
				while (iterator.hasNext()) {
					CurrencyConversionList currencyConversion = iterator
							.next();
					logger.info("Amount to be Converted : "
							+ currencyConversion.getBaseAmount()
									.getAmount()
							+ currencyConversion.getBaseAmount().getCode());
					Iterator<CurrencyType> currencyIterator = currencyConversion
							.getCurrencyList().getCurrency().iterator();
					while (currencyIterator.hasNext()) {
						CurrencyType currencyType = currencyIterator.next();
						logger.info("Converted amount : "
								+ currencyType.getAmount()
								+ currencyType.getCode());
					}

				}
			}
		}
		// ### Error Values
		// Access error values from error list using getter methods
		else {
			logger.severe("API Error Message : "
					+ convertCurrencyResponse.getError().get(0)
							.getMessage());
		}
		return convertCurrencyResponse;

	}
}
