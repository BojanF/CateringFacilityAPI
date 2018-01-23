package com.apiApp.CateringFacilityAPI.events;

import org.springframework.context.ApplicationEvent;

public class NewPackageSendMail extends ApplicationEvent {

    public NewPackageSendMail(Object source) {
        super(source);
    }

}
