package com.sample.permissions;

import java.io.IOException;
import java.util.logging.Logger;

import com.paypal.svcs.services.PermissionsService;
import com.paypal.svcs.types.perm.GetAccessTokenRequest;
import com.paypal.svcs.types.perm.GetAccessTokenResponse;

// # GetAccessToken API
// Use the GetAccessToken API operation to obtain an access token for a set of permissions.
// This sample code uses Permissions Java SDK to make API call. You can
// download the SDKs [here](https://github.com/paypal/sdk-packages/tree/gh-pages/permissions-sdk/java)
public class GetAccessToken {

	public GetAccessTokenResponse getAccessToken() {

		Logger logger = Logger.getLogger(this.getClass().toString());

		// ## GetAccessTokenRequest
		GetAccessTokenRequest getAccessTokenRequest = new GetAccessTokenRequest();

		// The request token from the response to RequestPermissions.
		getAccessTokenRequest.setToken("AAAAAAAXO-JZhFLpTLLe");

		// The verification code returned in the redirect from PayPal to the
		// return URL after `RequestPermissions` call
		getAccessTokenRequest.setVerifier("R.X1BWK7QEv-dcjQEzk2xg");

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
		GetAccessTokenResponse getAccessTokenResponse = null;
		try {

			// ## Making API call
			// Invoke the appropriate method corresponding to API in service
			// wrapper object
			getAccessTokenResponse = service
					.getAccessToken(getAccessTokenRequest);
		} catch (Exception e) {
			logger.severe("Error Message : " + e.getMessage());
		}

		// ## Accessing response parameters
		// You can access the response parameters using getter methods in
		// response object as shown below
		// ### Success values
		if (getAccessTokenResponse.getResponseEnvelope().getAck().getValue()
				.equalsIgnoreCase("Success")) {

			// The access token that identifies a set of permissions.
			logger.info("Access Token : " + getAccessTokenResponse.getToken());

			// The secret associated with the access token.
			logger.info("Token Secret : "
					+ getAccessTokenResponse.getTokenSecret());
		}
		// ### Error Values
		// Access error values from error list using getter methods
		else {
			logger.severe("API Error Message : "
					+ getAccessTokenResponse.getError().get(0).getMessage());
		}

		return getAccessTokenResponse;

	}

}
