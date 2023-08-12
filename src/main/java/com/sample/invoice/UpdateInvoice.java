package com.sample.invoice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.paypal.svcs.services.InvoiceService;
import com.paypal.svcs.types.common.RequestEnvelope;
import com.paypal.svcs.types.pt.UpdateInvoiceRequest;
import com.paypal.svcs.types.pt.UpdateInvoiceResponse;
import com.paypal.svcs.types.pt.InvoiceItemListType;
import com.paypal.svcs.types.pt.InvoiceItemType;
import com.paypal.svcs.types.pt.InvoiceType;
import com.paypal.svcs.types.pt.PaymentTermsType;

// # UpdateInvoice API
// Use the UpdateInvoice API operation to update an invoice
// This sample code uses Invoice Java SDK to make API call. You can
// download the SDKs [here](https://github.com/paypal/sdk-packages/tree/gh-pages/invoice-sdk/java)
public class UpdateInvoice {

	public UpdateInvoiceResponse updateInvoice() {
		Logger logger = Logger.getLogger(this.getClass().toString());

		// ##UpdateInvoiceRequest
		// Use the UpdateInvoiceRequest message to update an invoice.

		// The code for the language in which errors are returned, which must be
		// en_US.
		RequestEnvelope requestEnvelope = new RequestEnvelope();
		requestEnvelope.setErrorLanguage("en_US");

		List<InvoiceItemType> invoiceItemList = new ArrayList<InvoiceItemType>();

		// InvoiceItemType which takes mandatory params:
		// 
		// * `Item Name` - SKU or name of the item.
		// * `Quantity` - Item count.
		// * `Amount` - Price of the item, in the currency specified by the
		// invoice.
		InvoiceItemType invoiceItem = new InvoiceItemType("Item",
				Double.parseDouble("2"), Double.parseDouble("4.00"));
		invoiceItemList.add(invoiceItem);

		// Invoice item.
		InvoiceItemListType itemList = new InvoiceItemListType(invoiceItemList);

		// InvoiceType which takes mandatory params:
		// 
		// * `Merchant Email` - Merchant email address.
		// * `Personal Email` - Payer email address.
		// * `InvoiceItemList` - List of items included in this invoice.
		// * `CurrencyCode` - Currency used for all invoice item amounts and
		// totals.
		// * `PaymentTerms` - Terms by which the invoice payment is due. It is
		// one of the following values:
		//  * DueOnReceipt - Payment is due when the payer receives the invoice.
		//  * DueOnDateSpecified - Payment is due on the date specified in the
		//  invoice.
		//  * Net10 - Payment is due 10 days from the invoice date.
		//  * Net15 - Payment is due 15 days from the invoice date.
		//  * Net30 - Payment is due 30 days from the invoice date.
		//  * Net45 - Payment is due 45 days from the invoice date.
		InvoiceType invoice = new InvoiceType("jb-us-seller@paypal.com",
				"jbui-us-personal1@paypal.com", itemList, "USD",
				PaymentTermsType.DUEONRECEIPT);

		// UpdateInvoiceRequest which takes mandatory params:
		// 
		// * `Request Envelope` - Information common to each API operation, such
		// as the language in which an error message is returned.
		// * `Invoice ID` - ID of the invoice to update.
		// * `Invoice` - Merchant, payer, and invoice information.
		UpdateInvoiceRequest updateInvoiceRequest = new UpdateInvoiceRequest(
				requestEnvelope, "INV2-ZC9R-X6MS-RK8H-4VKJ", invoice);

		// ## Creating service wrapper object
		// Creating service wrapper object to make API call and loading
		// configuration file for your credentials and endpoint
		InvoiceService service = null;
		try {
			service = new InvoiceService(
					"src/main/resources/sdk_config.properties");
		} catch (IOException e) {
			logger.severe("Error Message : " + e.getMessage());
		}
		UpdateInvoiceResponse updateInvoiceResponse = null;
		try {

			// ## Making API call
			// Invoke the appropriate method corresponding to API in service
			// wrapper object
			 updateInvoiceResponse = service
					.updateInvoice(updateInvoiceRequest);
		} catch (Exception e) {
			logger.severe("Error Message : " + e.getMessage());
		}

		// ## Accessing response parameters
		// You can access the response parameters using getter methods in
		// response object as shown below
		// ### Success values
		if (updateInvoiceResponse.getResponseEnvelope().getAck().getValue()
				.equalsIgnoreCase("Success")) {

			// ID of the created invoice.
			logger.info("Invoice ID : "
					+ updateInvoiceResponse.getInvoiceID());

		}
		// ### Error Values
		// Access error values from error list using getter methods
		else {
			logger.severe("API Error Message : "
					+ updateInvoiceResponse.getError().get(0).getMessage());
		}
		return updateInvoiceResponse;
	}
}
