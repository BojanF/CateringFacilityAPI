package com.apiApp.CateringFacilityAPI.event_handlers;

import com.apiApp.CateringFacilityAPI.components.IEmailService;
import com.apiApp.CateringFacilityAPI.custom.MailData;
import com.apiApp.CateringFacilityAPI.events.CreatedInvoice;
import com.apiApp.CateringFacilityAPI.events.MailFromScheduledTask;
import com.apiApp.CateringFacilityAPI.events.NewPackageSendMail;
import com.apiApp.CateringFacilityAPI.events.UpdatedPackageSendMail;
import com.apiApp.CateringFacilityAPI.model.jpa.Developer;
import com.apiApp.CateringFacilityAPI.model.jpa.Facility;
import com.apiApp.CateringFacilityAPI.service.IDeveloperService;
import com.apiApp.CateringFacilityAPI.service.IFacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class MailHandler {

    @Autowired
    private IDeveloperService developerService;

    @Autowired
    private IFacilityService facilityService;

    @Autowired
    private IEmailService emailService;

    @EventListener(NewPackageSendMail.class)
    @Async
    public void sendMailsForNewlyCreatedPackage(NewPackageSendMail event) {
        String packageInfo = (String)event.getSource();
        String messageContent = "";

        Iterable<Developer> developers = developerService.findAll();
        Iterable<Facility> facilities = facilityService.findAll();

        for(Developer d : developers){
            messageContent = String.format("<h3>Dear %s</h3> <div><p> %s </p> <p> Sincerely yours,</p> <p> CateringAPI team. </p> </div>",
                    d.getUser().getUsername(),
                    packageInfo);
            emailService.sendSimpleMessage(d.getUser().getEmail(), "New package available", messageContent, true);
        }

        for(Facility f : facilities){
            messageContent = String.format("<h3>Dear %s</h3> <div><p> %s </p> <p> Sincerely yours,</p> <p> CateringAPI team. </p> </div>",
                    f.getUser().getUsername(),
                    packageInfo);
            emailService.sendSimpleMessage(f.getUser().getEmail(), "New package available", messageContent, true);
        }

        System.out.println("Mail successfully sent");
    }

    @EventListener(UpdatedPackageSendMail.class)
    @Async
    public void sendMailUpdatedPackage(UpdatedPackageSendMail event) {
        String packageInfo = (String)event.getSource();
        String messageContent = "";
        Iterable<Developer> developers = developerService.findAll();
        Iterable<Facility> facilities = facilityService.findAll();

        for(Developer d : developers){
            messageContent = String.format("<h3>Dear %s</h3> <div><p> %s </p> <p> Sincerely yours,</p> <p> CateringAPI team. </p> </div>",
                    d.getUser().getUsername(),
                    packageInfo);
            emailService.sendSimpleMessage(d.getUser().getEmail(), "Update for package", messageContent, true);
        }

        for(Facility f : facilities){
            messageContent = String.format("<h3>Dear %s</h3> <div><p> %s </p> <p> Sincerely yours,</p> <p> CateringAPI team. </p> </div>",
                    f.getUser().getUsername(),
                    packageInfo);
            emailService.sendSimpleMessage(f.getUser().getEmail(), "Update for package", messageContent, true);
        }

        System.out.println("Update mail successfully sent");

        System.out.println("Update mail sending event ends");
    }

    @EventListener(CreatedInvoice.class)
    @Async
    public void sendMailForNewInvoice(CreatedInvoice event){
        MailData mailData = (MailData)event.getSource();
        emailService.sendSimpleMessage(mailData.getSendTo(), mailData.getMessageSubject(), mailData.getMessageContent(), true);
        System.out.println("Email for created facility invoice is sent");
    }

    @EventListener(MailFromScheduledTask.class)
    @Async
    public void mailFromScheduledTask(MailFromScheduledTask event){
        MailData mailData = (MailData)event.getSource();
        emailService.sendSimpleMessage(mailData.getSendTo(), mailData.getMessageSubject(), mailData.getMessageContent(), true);
    }

}