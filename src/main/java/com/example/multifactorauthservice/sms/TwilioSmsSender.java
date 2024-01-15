package com.example.multifactorauthservice.sms;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("twilio")
public class TwilioSmsSender implements SmsSender{

    public static final Logger LOGGER = LoggerFactory.getLogger((TwilioSmsSender.class));

    private final TwilioConfiguration twilioConfiguration;

    @Autowired
    public TwilioSmsSender(TwilioConfiguration twilioConfiguration) {
        this.twilioConfiguration = twilioConfiguration;
    }

    @Override
    public void sendSms(SmsRequest smsRequest) {
        if (isPhoneValid(smsRequest.getPhoneNumber())){
        PhoneNumber to = new PhoneNumber(smsRequest.getPhoneNumber());
        PhoneNumber from = new PhoneNumber(twilioConfiguration.getTrialNumber());
        String message = smsRequest.getMessage();
        MessageCreator messageCreator = Message.creator(to, from, message);

        messageCreator.create();
        LOGGER.info("Sending this sms: " + smsRequest);
        } else {
            throw new IllegalArgumentException("Phone number : " + smsRequest.getPhoneNumber() + "isn't valid");
        }
    }

    private boolean isPhoneValid(String phoneNumber){
        //TODO: fa si tu ceva vere
        return true;
    }
}
