package com.apiApp.CateringFacilityAPI.events;

import org.springframework.context.ApplicationEvent;

public class UpdatedPackageSendMail extends ApplicationEvent {

    public UpdatedPackageSendMail(Object source) {
        super(source);
    }

}
