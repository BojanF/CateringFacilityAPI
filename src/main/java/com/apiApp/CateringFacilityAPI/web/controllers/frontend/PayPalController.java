package com.apiApp.CateringFacilityAPI.web.controllers.frontend;

import com.apiApp.CateringFacilityAPI.service.impl.PayPalServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(value = "/paypal")
public class PayPalController {

    private final PayPalServiceImpl payPalService;

    @Autowired
    PayPalController(PayPalServiceImpl payPalService){
        this.payPalService = payPalService;
    }

    @RequestMapping(value = "/make/payment", method = RequestMethod.POST)
    public Map<String, Object> makePayment(@RequestParam("sum") String sum){
        return payPalService.createPayment(sum);
    }

    @PostMapping(value = "/complete/payment")
    public Map<String, Object> completePayment(HttpServletRequest request){
        return payPalService.completePayment(request);
    }
}
