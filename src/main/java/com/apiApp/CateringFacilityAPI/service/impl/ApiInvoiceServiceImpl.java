package com.apiApp.CateringFacilityAPI.service.impl;

import com.apiApp.CateringFacilityAPI.custom.MailData;
import com.apiApp.CateringFacilityAPI.events.CreatedInvoice;
import com.apiApp.CateringFacilityAPI.model.enums.CustomerStatus;
import com.apiApp.CateringFacilityAPI.model.jpa.ApiInvoice;
import com.apiApp.CateringFacilityAPI.model.jpa.Developer;
import com.apiApp.CateringFacilityAPI.model.jpa.SubscriptionPackage;
import com.apiApp.CateringFacilityAPI.persistance.IApiInvoiceRepository;
import com.apiApp.CateringFacilityAPI.service.IApiInvoiceService;
import com.apiApp.CateringFacilityAPI.service.IDeveloperService;
import com.apiApp.CateringFacilityAPI.service.ITaxAmountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ApiInvoiceServiceImpl implements IApiInvoiceService {

    private static int RELIEF_PERIOD = 7;

    @Autowired
    private IApiInvoiceRepository apiInvoiceRepository;

    @Autowired
    private ITaxAmountService taxAmountService;

    @Autowired
    private IDeveloperService developerService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Override
    public ApiInvoice insertApiInvoice(SubscriptionPackage subscribe,
                                       Developer developer) {

        LocalDateTime createdAt;
        List<ApiInvoice> invoices = developerService.developerInvoices(developer.getId(), new PageRequest(0, 1));
        if(invoices.size() == 0){
            //first subscription
            createdAt = LocalDateTime.now();
        }
        else{
            //not first subscription
            ApiInvoice latestInvoice = invoices.get(0);
            LocalDateTime latestSubscriptionExpiryDate = latestInvoice.getCreatedAt().plusDays(latestInvoice.
                    getSubscribe().
                    getExpiresIn()-1);
            if(LocalDateTime.now().isBefore(latestSubscriptionExpiryDate)){
                //new subscription before old one expires
                createdAt = latestSubscriptionExpiryDate.plusDays(1);
            }
            else{
                //new subscription after old one expires
                createdAt = LocalDateTime.now();
            }
        }

        ApiInvoice apiInvoice = new ApiInvoice();
        apiInvoice.setSubscribe(subscribe);
        apiInvoice.setOriginalPackagePrice(subscribe.getPrice());
        apiInvoice.setTaxAmount(taxAmountService.getTaxAmount());
        apiInvoice.setCreatedAt(createdAt);
        apiInvoice.setPayUntil(createdAt.plusDays(subscribe.getExpiresIn()-1+RELIEF_PERIOD));
        apiInvoice.setDeveloper(developer);
        apiInvoice.setGrossPrice();
        apiInvoice.setInvoicePayed(false);

        if(developer.getStatus() == CustomerStatus.SUSPENDED){
            developer.setStatus(CustomerStatus.ACTIVE);
            developerService.update(developer);
        }

        apiInvoiceRepository.save(apiInvoice);
        String invoiceDetails =
                String.format("<p>Your subscription generated new invoice.Here are your invoice details:</p>" +
                                "<table border=\"3\"> " +
                                "<tr> <th> Package name: </th> <td> %s </td> </tr>" +
                                "<tr> <th> Price: </th> <td> %s &euro;</td> </tr>" +
                                "<tr> <th> Tax amount: </th> <td> %s %%</td> </tr>" +
                                "<tr> <th> Gross price: </th> <td> %s &euro;</td> </tr>" +
                                "<tr> <th> Expires in: </th> <td> %s days </td> </tr>" +
                                "<tr> <th> Pay deadline: </th> <td> %s </td> </tr>" +
                                "</table>\n",
                        apiInvoice.getSubscribe().getName(),
                        apiInvoice.getOriginalPackagePrice(),
                        apiInvoice.getTaxAmount(),
                        apiInvoice.getGrossPrice(),
                        apiInvoice.getSubscribe().getExpiresIn(),
                        parseLocalDateTime(apiInvoice.getPayUntil()));
        String messageContent = String.format("<h3>Dear %s</h3> <div><p> %s </p> <p> Sincerely yours,</p> <p> CateringAPI team. </p> </div>" +
                        " <p> *This mail is sent couple of minutes after invoice is generated.</p>",
                apiInvoice.getDeveloper().getUser().getUsername(),
                invoiceDetails);

        MailData mailData = new MailData(
                apiInvoice.getDeveloper().getUser().getEmail(),
                "Invoice for your subscription",
                messageContent);
        publisher.publishEvent(new CreatedInvoice(mailData));
        System.out.print("API invoice is created.");

        return apiInvoice;
    }

    @Override
    public ApiInvoice findOne(Long id) {
        return apiInvoiceRepository.findOne(id);
    }

    @Override
    public ApiInvoice update(ApiInvoice apiInvoice) {
        return apiInvoiceRepository.save(apiInvoice);
    }

    @Override
    public void delete(Long id) {
        apiInvoiceRepository.delete(id);
    }

    @Override
    public Iterable<ApiInvoice> findAll() {
        return apiInvoiceRepository.findAll();
    }

    @Override
    public List<ApiInvoice> getAllInvoicesSortedByCreatedAt() {
        return apiInvoiceRepository.getAllInvoicesSortedByCreatedAt();
    }

    @Override
    public Double countAllApiInvoices() {
        return apiInvoiceRepository.countAllApiInvoices();
    }

    @Override
    public Double countPaidApiInvoices() {
        return apiInvoiceRepository.countPaidApiInvoices();
    }

    @Override
    public List<ApiInvoice> notPaidInvoicesAfterReliefPeriod(LocalDateTime now) {
        return apiInvoiceRepository.notPaidInvoicesAfterReliefPeriod(now);
    }

    @Override
    public Double sumOfApiInvoices(boolean paid) {
        return apiInvoiceRepository.sumOfApiInvoices(paid);
    }

    @Override
    public List<Double> apiInvoicesIncomeStats() {
        List<Double> result = new ArrayList<Double>();
        result.add(sumOfApiInvoices(true));
        result.add(sumOfApiInvoices(false));
        return  result;
    }

    private String parseLocalDateTime(LocalDateTime dateTime){
        String result;
        result = String.format("%s.%s.%s - %s:%s ",
                dateTime.getDayOfMonth(),
                dateTime.getMonthValue(),
                dateTime.getYear(),
                dateTime.getHour(),
                dateTime.getMinute());
        return result;
    }
}
