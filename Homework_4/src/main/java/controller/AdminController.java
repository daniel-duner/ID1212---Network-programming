package se.kth.id1212.hw4.Homework_4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import se.kth.id1212.hw4.Homework_4.model.Conversion;
import se.kth.id1212.hw4.Homework_4.model.Currency;
import se.kth.id1212.hw4.Homework_4.service.CurrencyService;

import javax.validation.Valid;

@Controller
public class AdminController {
   private final CurrencyService currencyService;

    @Autowired
    public AdminController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/admin")
    public String converterForm(Model model) {
        model.addAttribute("currencies", currencyService.getAllCurrencies());
        model.addAttribute("count", currencyService.getCounter().getCount());
        model.addAttribute("currency", new Currency());
        return "admin";
    }
    @PostMapping("/admin")
    public String changeRate(@ModelAttribute @Valid Currency currency, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            model.addAttribute("currencies", currencyService.getAllCurrencies());
            model.addAttribute("count", currencyService.getCounter().getCount());
            model.addAttribute("currency", new Currency());
            model.addAttribute("fail", true);
            return "admin";
        }
        currencyService.updateRate(currency);
        model.addAttribute("result","You have changed "+currency.getId()+" rate to "+currency.getRate());
        return "adminResult";
    }

    /*
    @PostMapping("/admin/result")
    public String addNewCurrencyt(@ModelAttribute Currency currency, Model model) {
        if(currencyService.addCurrency(currency)){
            model.addAttribute("result","You have changed "+currency.getId()+" rate to "+currency.getRate());
        } else {
            model.addAttribute("result", currency.getId()+" already exists");
        }
        return "adminResult";
    }*/

}