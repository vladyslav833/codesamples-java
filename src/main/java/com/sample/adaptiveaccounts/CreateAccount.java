package com.sample.adaptiveaccounts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.paypal.svcs.services.AdaptiveAccountsService;
import com.paypal.svcs.types.aa.AddressType;
import com.paypal.svcs.types.aa.BusinessInfoType;
import com.paypal.svcs.types.aa.BusinessType;
import com.paypal.svcs.types.aa.CreateAccountRequest;
import com.paypal.svcs.types.aa.CreateAccountResponse;
import com.paypal.svcs.types.aa.CreateAccountWebOptionsType;
import com.paypal.svcs.types.aa.NameType;
import com.paypal.svcs.types.aa.SalesVenueType;
import com.paypal.svcs.types.common.RequestEnvelope;

// #CreateAccount API
// The CreateAccount API operation enables you to create a PayPal account on
// behalf of a third party.
// This sample code uses AdaptiveAccounts Java SDK to make API call. You can
// download the SDKs [here](https://github.com/paypal/sdk-packages/tree/gh-pages/adaptiveaccounts-sdk/java)
public class CreateAccount {

	Logger logger = Logger.getLogger(this.getClass().toString());

	// ##CreateAccountRequest
	// The CreateAccountRequest contains the information required to create a
	// PayPal account for a business customer.
	CreateAccountRequest createAccountRequest;

	CreateAccountResponse createAccountResponse;

	// ## Create Personal Account
	public CreateAccountResponse createPersonalAccount() {

		RequestEnvelope requestEnvelope = new RequestEnvelope();

		// The name of the person for whom the PayPal account is created, which
		// contains
		// 
		// * `FirstName` - First name of the account or payment card holder.
		// * `LastName` - Last name of the account or payment card holder.
		NameType name = new NameType("John", "David");

		// The address to be associated with the PayPal account, which contains
		// 
		// * `Street1` - The street address.
		// * `Countrycode` - The country code.
		// * `City` - The city.
		// * `State` - The state code.
		// * `Postalcode` - The zip or postal code.
		AddressType address = new AddressType("Ape Way", "US");
		address.setCity("Austin");
		address.setState("TX");
		address.setPostalCode("78750");

		// Instantiating createAccountRequest with mandatory arguments:
		// 
		// * `requestenvelope` - Information common to each API operation, such
		// as the language in which an error message is returned.
		// * `name` - The name of the person for whom the PayPal account is
		// created.
		// * `address` - The address to be associated with the PayPal account.
		// * `preferredlanguagecode`- The code indicating the language to be
		// associated with the account.
		// What value is allowed depends on the country code passed in the
		// countryCode parameter for the address.
		// For Example: United States (US) - en_US
		createAccountRequest = new CreateAccountRequest(requestEnvelope, name,
				address, "en_US");

		// The type of account to be created. Allowable values are:
		// 
		// * Personal - Personal account
		// * Premier - Premier account
		// * Business - Business account
		createAccountRequest.setAccountType("Personal");

		// The code of the country to be associated with the account.
		createAccountRequest.setCitizenshipCountryCode("US");

		// Phone Number to be associated with the account.
		createAccountRequest.setContactPhoneNumber("5126914160");

		// The three letter code for the currency to be associated with the
		// account.
		createAccountRequest.setCurrencyCode("USD");

		// Email address of person for whom the PayPal account is created.
		createAccountRequest.setEmailAddress("test@paypal.com");

		// This attribute determines whether a key or a URL is returned for the
		// redirect URL. Allowable value(s) currently supported:
		// `Web` - Returns a URL
		createAccountRequest.setRegistrationType("Web");

		// Used for configuration settings for the web flow
		CreateAccountWebOptionsType createAccountWebOptions = new CreateAccountWebOptionsType();

		// The URL to which the business redirects the PayPal user for PayPal
		// account setup completion
		createAccountWebOptions.setReturnUrl("http://localhost");
		createAccountRequest
				.setCreateAccountWebOptions(createAccountWebOptions);

		// The URL to post instant payment notification (IPN) messages to
		// regarding account creation. This URL supersedes the IPN notification
		// URL set in the merchant profile.
		createAccountRequest.setNotificationURL("https://localhost/ipn");

		createAccountResponse = makeAPICall(createAccountRequest);
		return createAccountResponse;

	}

	// ## Create PremierAccount
	public CreateAccountResponse createPremierAccount() {
		// Needs to set all the above parameters mentioned in personal account
		// creation, but account type needs to be `premier`.
		createAccountRequest.setAccountType("Premier");
		return makeAPICall(createAccountRequest);
	}

	// ## Create Business Account
	public CreateAccountResponse createBusinessAccount() {
		// Needs to set all the above parameters mentioned in personal account
		// creation, but account type needs to be `business`.
		createAccountRequest.setAccountType("Business");

		// The address for the business for which the PayPal account is created,
		// which contains
		// 
		// * `Street1` - The street address.
		// * `Countrycode` - The country code.
		// * `City` - The city.
		// * `State` - The state code.
		// * `Postalcode` - The zip or postal code.
		AddressType businessAddress = new AddressType("Ape Way", "US");
		businessAddress.setCity("Austin");
		businessAddress.setState("TX");
		businessAddress.setPostalCode("78750");

		// This field is required for business accounts which takes mandatory
		// arguments:
		// 
		// * `Business Name` - The name of the business for which the PayPal
		// account is created.
		// * `Business Address`
		// * `Contact Phone Number`
		BusinessInfoType businessInfo = new BusinessInfoType("Toy shop",
				businessAddress, "5126914161");

		// The type of the business for which the PayPal account is created.
		// Allowable values are:
		// 
		// * CORPORATION
		// * GOVERNMENT
		// * INDIVIDUAL
		// * NONPROFIT
		// * PARTNERSHIP
		// * PROPRIETORSHIP
		businessInfo.setBusinessType(BusinessType.INDIVIDUAL);

		// The average monthly transaction volume of the business for which the
		// PayPal account is created. Required for all countries except Japan
		// and Australia.
		businessInfo.setAverageMonthlyVolume(Double.parseDouble("400"));

		// The average price per transaction. Required for all countries except
		// Japan and Australia.
		businessInfo.setAveragePrice(Double.parseDouble("30"));

		// The email address for the customer service department of the
		// business.
		businessInfo.setCustomerServiceEmail("customercare@toy.com");

		// Required for US accounts
		businessInfo.setCustomerServicePhone("5126914162");

		// The category code for the business. state in which the business was
		// established. Required unless you specify both category and
		// subcategory. PayPal uses the industry standard Merchant Category
		// Codes.
		businessInfo.setMerchantCategoryCode(Integer.parseInt("1520"));

		// The date of establishment for the business. Optional for France
		// business accounts and required for business accounts in the following
		// countries: United States, United Kingdom, Canada, Germany, Spain,
		// Italy, Netherlands, Czech Republic, Sweden, and Denmark. Format needs
		// to be `YYYY-MM-DD`
		businessInfo.setDateOfEstablishment("2011-12-21");

		// The percentage of online sales for the business from 0 through 100.
		// Required for business accounts in the following countries: United
		// States, Canada, United Kingdom, France, Czech Republic, New Zealand,
		// Switzerland, and Israel.
		businessInfo.setPercentageRevenueFromOnline(Integer.parseInt("70"));

		// The venue type for sales. Required for business accounts in all
		// countries except Czech Republic and Australia. Allowable values are:
		// 
		// * WEB
		// * EBAY
		// * OTHER_MARKETPLACE
		// * OTHER
		List<SalesVenueType> salesVenueList = new ArrayList<SalesVenueType>();
		salesVenueList.add(SalesVenueType.OTHER);
		businessInfo.setSalesVenue(salesVenueList);

		// A description of the sales venue. Required if salesVenue is OTHER for
		// all countries except Czech Republic and Australia.
		businessInfo.setSalesVenueDesc("Other sales venue type");

		createAccountRequest.setBusinessInfo(businessInfo);
		createAccountResponse = makeAPICall(createAccountRequest);
		return createAccountResponse;

	}

	private CreateAccountResponse makeAPICall(
			CreateAccountRequest createAccountRequest) {
		AdaptiveAccountsService service = null;
		// ## Creating service wrapper object
		// Creating service wrapper object to make an API call and loading
		// configuration file for your credentials and endpoint
		try {
			service = new AdaptiveAccountsService(
					"src/main/resources/sdk_config.properties");
		} catch (IOException e) {
			logger.severe("Error Message : " + e.getMessage());
		}
		try {
			// ## Making API call
			// invoke the appropriate method corresponding to API in service
			// wrapper object
			createAccountResponse = service.createAccount(createAccountRequest);
		} catch (Exception e) {
			logger.severe("Error Message : " + e.getMessage());
		}

		// ## Accessing response parameters
		// You can access the response parameters using getter methods in
		// response object as shown below
		// ### Success values
		if (createAccountResponse.getResponseEnvelope().getAck().getValue()
				.equalsIgnoreCase("success")) {
			
			// A unique key that identifies the account that was created.
			logger.info("Create Account Key : "
					+ createAccountResponse.getCreateAccountKey());
			// ### Redirection to PayPal
			// Once you get the success response, user needs to redirect to
			// PayPal to enter password for the created account. For that,
			// you have to use the redirect URL from the response, like
			// createAccountResponse.getRedirectURL(). Using this url,
			// redirects the user to PayPal.

		}
		// ### Error Values
		// Access error values from error list using getter methods
		else {
			logger.severe("API Error Message : "
					+ createAccountResponse.getError().get(0).getMessage());
		}
		return createAccountResponse;

	}
}
