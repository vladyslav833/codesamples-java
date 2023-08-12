package com.sample;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.paypal.svcs.types.perm.GetAccessTokenResponse;
import com.paypal.svcs.types.perm.RequestPermissionsResponse;
import com.sample.permissions.GetAccessToken;
import com.sample.permissions.RequestPermissions;

public class PermissionsTest {

	RequestPermissions requestPermissions;
	GetAccessToken getAccessToken;

	@Test(groups = { "automate" })
	public void requestPermissionsTest() {
		RequestPermissionsResponse requestPermissionsResponse = requestPermissions
				.requestPermissions();
		Assert.assertEquals(requestPermissionsResponse.getResponseEnvelope()
				.getAck().getValue().toString(), "Success");
		Assert.assertNotNull(requestPermissionsResponse.getToken());
	}

	@Test(groups = { "manual" })
	public void getAccessTokenTest() {
		GetAccessTokenResponse getAccessTokenResponse = getAccessToken
				.getAccessToken();
		Assert.assertEquals(getAccessTokenResponse.getResponseEnvelope()
				.getAck().getValue().toString(), "Success");
		Assert.assertNotNull(getAccessTokenResponse.getToken());
		Assert.assertNotNull(getAccessTokenResponse.getTokenSecret());
	}

	@BeforeClass(groups = { "automate", "manual" })
	public void beforeClass() {
		requestPermissions = new RequestPermissions();
		getAccessToken = new GetAccessToken();
	}

	@AfterClass(groups = { "automate", "manual" })
	public void afterClass() {
		requestPermissions = null;
		getAccessToken = null;
	}

}
