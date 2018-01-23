package com.apiApp.CateringFacilityAPI.custom;

public class MailData {

    private String sendTo;
    private String messageSubject;
    private String messageContent;

    public MailData(String sendTo, String messageSubject, String messageContent) {
        this.sendTo = sendTo;
        this.messageSubject = messageSubject;
        this.messageContent = messageContent;
    }

    public String getSendTo() {
        return sendTo;
    }

    public String getMessageSubject() {
        return messageSubject;
    }

    public String getMessageContent() {
        return messageContent;
    }
}
