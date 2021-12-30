package com.in28minutes.microservice.springcurrencyconversionservice.service;

import com.in28minutes.microservice.springcurrencyconversionservice.bean.CurrencyConversionBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "currency-exchange-service", url = "localhost:8000")
public interface CurrencyExchangeServiceProxy {
    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyConversionBean retriveExchangeValue(@PathVariable String from, @PathVariable String to);

}

