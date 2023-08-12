package com.sample.adaptivepayments;

import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Logger;

import com.paypal.svcs.services.AdaptivePaymentsService;
import com.paypal.svcs.types.ap.RefundInfo;
import com.paypal.svcs.types.ap.RefundRequest;
import com.paypal.svcs.types.ap.RefundResponse;
import com.paypal.svcs.types.common.RequestEnvelope;

// # Refund API
// Use the Refund API operation to refund all or part of a payment.
// This sample code uses AdaptivePayments Java SDK to make API call. You can
// download the SDK [here](https://github.com/paypal/sdk-packages/tree/gh-pages/adaptivepayments-sdk/java)
public class Refund {

	public RefundResponse refund() {

		Logger logger = Logger.getLogger(this.getClass().toString());

		// ##RefundRequest
		// The code for the language in which errors are returned, which must be
		// en_US.
		RequestEnvelope requestEnvelope = new RequestEnvelope();
		requestEnvelope.setErrorLanguage("en_US");

		// RefundRequest which takes,
		// 
		// `Request Envelope` - Information common to each API operation, such
		// as the language in which an error message is returned.
		RefundRequest refundRequest = new RefundRequest(requestEnvelope);

		// You must specify either,
		// 
		// * `Pay Key` - The pay key that identifies the payment for which you
		// want to retrieve details. This is the pay key returned in the
		// PayResponse message.
		// * `Transaction ID` - The PayPal transaction ID associated with the
		// payment. The IPN message associated with the payment contains the
		// transaction ID.
		// `refundRequest.setTransactionId(transactionId)`
		// * `Tracking ID` - The tracking ID that was specified for this payment
		// in the PayRequest message.
		// `refundRequest.setTrackingId(trackingId)`
		refundRequest.setPayKey("AP-86H50830VE600922B");

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
		RefundResponse refundResponse = null;
		try {

			// ## Making API call
			// Invoke the appropriate method corresponding to API in service
			// wrapper object
			refundResponse = service.refund(refundRequest);
		} catch (Exception e) {
			logger.severe("Error Message : " + e.getMessage());
		}

		// ## Accessing response parameters
		// You can access the response parameters using getter methods in
		// response object as shown below
		// ### Success values
		if (refundResponse.getResponseEnvelope().getAck().getValue()
				.equalsIgnoreCase("Success")) {

			// List of refunds associated with the payment.
			Iterator<RefundInfo> iterator = refundResponse
					.getRefundInfoList().getRefundInfo().iterator();
			while (iterator.hasNext()) {

				// Represents the refund attempt made to a receiver of a
				// PayRequest.
				RefundInfo refundInfo = iterator.next();

				// Status of the refund. It is one of the following values:
				// 
				// * REFUNDED - Refund successfully completed
				// * REFUNDED_PENDING - Refund awaiting transfer of funds;
				// for example, a refund paid by eCheck.
				// * NOT_PAID - Payment was never made; therefore, it cannot
				// be refunded.
				// * ALREADY_REVERSED_OR_REFUNDED - Request rejected because
				// the refund was already made, or the payment was reversed
				// prior to this request.
				// * NO_API_ACCESS_TO_RECEIVER - Request cannot be completed
				// because you do not have third-party access from the
				// receiver to make the refund.
				// * REFUND_NOT_ALLOWED - Refund is not allowed.
				// * INSUFFICIENT_BALANCE - Request rejected because the
				// receiver from which the refund is to be paid does not
				// have sufficient funds or the funding source cannot be
				// used to make a refund.
				// * AMOUNT_EXCEEDS_REFUNDABLE - Request rejected because
				// you attempted to refund more than the remaining amount of the
				// payment; call the PaymentDetails API operation to
				// determine the amount already refunded.
				// * PREVIOUS_REFUND_PENDING - Request rejected because a
				// refund is currently pending for this part of the payment
				// * NOT_PROCESSED - Request rejected because it cannot be
				// processed at this time
				// * REFUND_ERROR - Request rejected because of an internal
				// error
				// * PREVIOUS_REFUND_ERROR - Request rejected because
				// another part of this refund caused an internal error.
				logger.info("Refund Status : "
						+ refundInfo.getRefundStatus());
			}
		}
		// ### Error Values
		// Access error values from error list using getter methods
		else {
			logger.severe("API Error Message : "
					+ refundResponse.getError().get(0).getMessage());
		}
		return refundResponse;
	}

}
