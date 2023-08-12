package com.sample.permissions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.paypal.svcs.services.PermissionsService;
import com.paypal.svcs.types.perm.RequestPermissionsRequest;
import com.paypal.svcs.types.perm.RequestPermissionsResponse;

// # RequestPermissions API
// Use the RequestPermissions API operation to request permissions to execute API operations on a PayPal account holder's behalf.
// This sample code uses Permissions Java SDK to make API call. You can
// download the SDKs [here](https://github.com/paypal/sdk-packages/tree/gh-pages/permissions-sdk/java)
public class RequestPermissions {

	public RequestPermissionsResponse requestPermissions() {

		Logger logger = Logger.getLogger(this.getClass().toString());

		// ##RequestPermissionsRequest
		// `Scope`, which can take at least 1 of the following permission
		// categories:
		// 
		// * EXPRESS_CHECKOUT
		// * DIRECT_PAYMENT
		// * AUTH_CAPTURE
		// * AIR_TRAVEL
		// * TRANSACTION_SEARCH
		// * RECURRING_PAYMENTS
		// * ACCOUNT_BALANCE
		// * ENCRYPTED_WEBSITE_PAYMENTS
		// * REFUND
		// * BILLING_AGREEMENT
		// * REFERENCE_TRANSACTION
		// * MASS_PAY
		// * TRANSACTION_DETAILS
		// * NON_REFERENCED_CREDIT
		// * SETTLEMENT_CONSOLIDATION
		// * SETTLEMENT_REPORTING
		// * BUTTON_MANAGER
		// * MANAGE_PENDING_TRANSACTION_STATUS
		// * RECURRING_PAYMENT_REPORT
		// * EXTENDED_PRO_PROCESSING_REPORT
		// * EXCEPTION_PROCESSING_REPORT
		// * ACCOUNT_MANAGEMENT_PERMISSION
		// * INVOICING
		// * ACCESS_BASIC_PERSONAL_DATA
		// * ACCESS_ADVANCED_PERSONAL_DATA
		List<String> scopeList = new ArrayList<String>();
		scopeList.add("INVOICING");
		scopeList.add("EXPRESS_CHECKOUT");

		// Create RequestPermissionsRequest object which takes mandatory params:
		// 
		// * `Scope`
		// * `Callback` - Your callback function that specifies actions to take
		// after the account holder grants or denies the request.
		RequestPermissionsRequest requestPermissionsRequest = new RequestPermissionsRequest(
				scopeList, "http://localhost/callback");

		// ## Creating service wrapper object
		// Creating service wrapper object to make API call and loading
		// configuration file for your credentials and endpoint
		PermissionsService service = null;
		try {
			service = new PermissionsService(
					"src/main/resources/sdk_config.properties");
		} catch (IOException e) {
			logger.severe("Error Message : " + e.getMessage());
		}

		RequestPermissionsResponse requestPermissionsResponse = null;
		try {
			// ## Making API call
			// Invoke the appropriate method corresponding to API in service
			// wrapper object
			requestPermissionsResponse = service
					.requestPermissions(requestPermissionsRequest);
		} catch (Exception e) {
			logger.severe("Error Message : " + e.getMessage());
		}

		// ## Accessing response parameters
		// You can access the response parameters using getter methods in
		// response object as shown below
		// ### Success values
		if (requestPermissionsResponse.getResponseEnvelope().getAck()
				.getValue().equalsIgnoreCase("Success")) {
			// ###Redirecting to PayPal
			// Once you get the success response, user needs to redirect to
			// paypal to authorize. Construct the `redirectUrl` as follows,
			// `redirectURL=https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_grant-permission&request_token="+requestPermissionsResponse.getToken();`
			// Once you are done with authorization, you will be returning
			// back to `callback` url mentioned in your request. While
			// returning, PayPal will send two parameters in request:
			// 
			// * `request_token`
			// * `token_verifier`
			// You have to use these values in `GetAccessToken` API call to
			// generate `AccessToken` and `TokenSecret`

			// A token from PayPal that enables the request to obtain
			// permissions.
			logger.info("Request_token : "
					+ requestPermissionsResponse.getToken());

		}
		// ### Error Values
		// Access error values from error list using getter methods
		else {
			logger.severe("API Error Message : "
					+ requestPermissionsResponse.getError().get(0).getMessage());
		}
		return requestPermissionsResponse;

	}
}
