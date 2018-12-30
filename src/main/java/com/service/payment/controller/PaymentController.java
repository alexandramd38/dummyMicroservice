package com.service.payment.controller;

import javax.xml.bind.JAXBElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.service.payment.model.MakePaymentRequestType;
import com.service.payment.model.MakePaymentResponseType;
import com.service.payment.model.ObjectFactory;

@Endpoint
public class PaymentController {

    private static final Logger LOG = LoggerFactory.getLogger(PaymentController.class);

    private static final String NAMESPACE_URI = "http://localhost:8080/payment";
    private static final String SUCCESS = "SUCCESS";
    private static final String ERROR = "ERROR";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "makePaymentRequest")
    @ResponsePayload
    public JAXBElement<MakePaymentResponseType> makePaymentRequest(@RequestPayload JAXBElement<MakePaymentRequestType> paymentRequest) {
        MakePaymentRequestType request = paymentRequest.getValue();
        LOG.info("Request received: firstName = {}, lastName = {}, paymentId = {}", request.getFirstName(), request.getLastName(), request.getPaymentId());
        MakePaymentResponseType response = new MakePaymentResponseType();
        response.setPaymentId(request.getPaymentId());
        response.setStatus(request.getPaymentId() % 2 == 0 ? SUCCESS : ERROR);
        LOG.info("Response sent: paymentId = {}, status = {}", response.getPaymentId(), response.getStatus());
        return new ObjectFactory().createMakePaymentResponse(response);
    }
}
