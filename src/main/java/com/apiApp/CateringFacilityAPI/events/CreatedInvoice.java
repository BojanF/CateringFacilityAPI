package com.apiApp.CateringFacilityAPI.events;

import org.springframework.context.ApplicationEvent;

public class CreatedInvoice extends ApplicationEvent {

    public CreatedInvoice(Object source) {
        super(source);
    }

}
