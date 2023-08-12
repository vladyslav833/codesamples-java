package com.sample;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.paypal.svcs.types.pt.CreateAndSendInvoiceResponse;
import com.paypal.svcs.types.pt.CreateInvoiceResponse;
import com.paypal.svcs.types.pt.GetInvoiceDetailsResponse;
import com.paypal.svcs.types.pt.SearchInvoicesResponse;
import com.paypal.svcs.types.pt.SendInvoiceResponse;
import com.paypal.svcs.types.pt.UpdateInvoiceResponse;
import com.sample.invoice.CreateAndSendInvoice;
import com.sample.invoice.CreateInvoice;
import com.sample.invoice.GetInvoiceDetails;
import com.sample.invoice.SearchInvoices;
import com.sample.invoice.SendInvoice;
import com.sample.invoice.UpdateInvoice;

public class InvoiceTest {

	CreateInvoice createInvoice;
	CreateAndSendInvoice createAndSendInvoice;
	SendInvoice sendInvoice;
	UpdateInvoice updateInvoice;
	SearchInvoices searchInvoices;
	GetInvoiceDetails getInvoiceDetails;

	@Test(groups = { "automate" })
	public void createInvoiceTest() {
		CreateInvoiceResponse createInvoiceResponse = createInvoice
				.createInvoice();
		Assert.assertEquals(createInvoiceResponse.getResponseEnvelope()
				.getAck().getValue().toString(), "Success");
		Assert.assertNotNull(createInvoiceResponse.getInvoiceID());
	}

	@Test(groups = { "automate" })
	public void createAndSendInvoiceTest() {
		CreateAndSendInvoiceResponse createAndSendInvoiceResponse = createAndSendInvoice
				.createAndSendInvoice();
		Assert.assertEquals(createAndSendInvoiceResponse.getResponseEnvelope()
				.getAck().getValue().toString(), "Success");
		Assert.assertNotNull(createAndSendInvoiceResponse.getInvoiceID());
	}

	@Test(groups = { "automate" })
	public void updateInvoiceTest() {
		UpdateInvoiceResponse updateInvoiceResponse = updateInvoice
				.updateInvoice();
		Assert.assertEquals(updateInvoiceResponse.getResponseEnvelope()
				.getAck().getValue().toString(), "Success");
	}

	@Test(groups = { "automate" })
	public void getInvoiceDetailsTest() {
		GetInvoiceDetailsResponse getInvoiceDetailsResponse = getInvoiceDetails
				.getInvoiceDetails();
		Assert.assertEquals(getInvoiceDetailsResponse.getResponseEnvelope()
				.getAck().getValue().toString(), "Success");
	}

	@Test(groups = { "manual" })
	public void sendInvoiceTest() {
		SendInvoiceResponse sendInvoiceResponse = sendInvoice.sendInvoice();
		Assert.assertEquals(sendInvoiceResponse.getResponseEnvelope().getAck()
				.getValue().toString(), "Success");
	}

	@Test(groups = { "automate" })
	public void searchInvoices() {
		SearchInvoicesResponse searchInvoicesResponse = searchInvoices
				.searchInvoices();
		Assert.assertEquals(searchInvoicesResponse.getResponseEnvelope()
				.getAck().getValue().toString(), "Success");
	}

	@BeforeClass(groups = { "automate", "manual" })
	public void beforeClass() {
		createInvoice = new CreateInvoice();
		createAndSendInvoice = new CreateAndSendInvoice();
		sendInvoice = new SendInvoice();
		updateInvoice = new UpdateInvoice();
		searchInvoices = new SearchInvoices();
		getInvoiceDetails = new GetInvoiceDetails();
	}

	@AfterClass(groups = { "automate", "manual" })
	public void afterClass() {
		createInvoice = null;
		createAndSendInvoice = null;
		sendInvoice = null;
		updateInvoice = null;
		searchInvoices = null;
		getInvoiceDetails = null;

	}

}
