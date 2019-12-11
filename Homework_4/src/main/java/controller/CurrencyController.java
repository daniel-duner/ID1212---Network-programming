package se.kth.id1212.hw4.Homework_4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.kth.id1212.hw4.Homework_4.model.Currency;
import se.kth.id1212.hw4.Homework_4.service.CurrencyService;

@RequestMapping("api/currency")
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CurrencyController {
    private final CurrencyService currencyService;

    @Autowired
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
        currencyService.addExchange();
        currencyService.addCurrency(new Currency("NOK",1.5));
        currencyService.addCurrency(new Currency("SEK",1.3));
        currencyService.addCurrency(new Currency("EUR",1.8));
        currencyService.addCurrency(new Currency("USD",1.9));
        currencyService.addCurrency(new Currency("GBP",2.4));
    }
    /*
    @PostMapping("/add")
    public void addCurrency(@RequestBody Currency currency){
        currencyService.addCurrency(currency);
    }

    @PostMapping("/convert")
    public Exchange conversion(@RequestBody Exchange exchange){
       return exchange;
       //return currencyService.conversion(exchange.getFrom(),exchange.getTo(), exchange.getAmount());
    }

    @GetMapping("/get")
    public Currency getCurrency(@RequestBody JsonVal id){
        return currencyService.getCurrency(id.getId());
    }

    @GetMapping("/all")
    public List<Currency> getAllCurrencies(){
        return currencyService.getAllCurrencies();
    }
    */
}
