package com.apiApp.CateringFacilityAPI.components;

public interface IEmailService {
    void sendSimpleMessage(String to, String subject, String text, boolean isHtml);
}
