package com.in28minutes.microservice.springcurrencyconversionservice.controller;

import com.in28minutes.microservice.springcurrencyconversionservice.bean.CurrencyConversionBean;
import com.in28minutes.microservice.springcurrencyconversionservice.service.CurrencyExchangeServiceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CurrencyConversionController {
    @Autowired
    public CurrencyExchangeServiceProxy proxy;
    @GetMapping("/currency-exchange/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean convertCurrency(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
        Map<String, String> uriVariable = new HashMap<>();
        uriVariable.put("from", from);
        uriVariable.put("to", to);
        ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}"
                , CurrencyConversionBean.class
                , uriVariable);
        CurrencyConversionBean responseBody = responseEntity.getBody();
        HttpHeaders header = responseEntity.getHeaders();
        //System.out.println("Hesders"+header);
        return new CurrencyConversionBean(responseBody.getId(), from, to, responseBody.getConversionMultiple(), quantity, quantity.multiply(responseBody.getConversionMultiple()), 0);
    }
     //Feign Clients
     @GetMapping("/currency-exchange-feign/from/{from}/to/{to}/quantity/{quantity}")
     public CurrencyConversionBean convertCurrencyFeign(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {

         CurrencyConversionBean responseBody = proxy.retriveExchangeValue(from,to);
         return new CurrencyConversionBean(responseBody.getId(), from, to, responseBody.getConversionMultiple(), quantity, quantity.multiply(responseBody.getConversionMultiple()), responseBody.getPort());
     }


    @GetMapping("/list")
    public ResponseEntity<?> retriveList() {
        CurrencyConversionBean bean = new CurrencyConversionBean(10009, "USD", "INR", BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, 0);
        CurrencyConversionBean bean1 = new CurrencyConversionBean(100010, "INR", "INR", BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, 0);
        CurrencyConversionBean bean2 = new CurrencyConversionBean(100011, "CAD", "INR", BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, 0);
        List<CurrencyConversionBean> list = new ArrayList<>();
        list.add(bean);
        list.add(bean1);
        list.add(bean2);
        if (list.isEmpty())
            return new ResponseEntity<>("Not Found Data", HttpStatus.NOT_FOUND);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Baeldung-Example-Header",
                "Value-ResponseEntityBuilderWithHttpHeaders");
        ResponseEntity<?> responseEntity = ResponseEntity.status(HttpStatus.OK)
                .body(list);
        //  return new ResponseEntity<>(list,responseHeaders, HttpStatus.ACCEPTED);
        return responseEntity;
    }


}
