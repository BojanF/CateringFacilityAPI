package com.apiApp.CateringFacilityAPI.events;

import org.springframework.context.ApplicationEvent;

public class MailFromScheduledTask extends ApplicationEvent {

    public MailFromScheduledTask(Object source) {
        super(source);
    }

}
