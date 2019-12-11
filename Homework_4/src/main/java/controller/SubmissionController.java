package se.kth.id1212.hw4.Homework_4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import se.kth.id1212.hw4.Homework_4.model.Conversion;
import se.kth.id1212.hw4.Homework_4.service.CurrencyService;

import javax.validation.Valid;

@Controller
public class SubmissionController implements WebMvcConfigurer {
    private final CurrencyService currencyService;

    @Autowired
    public SubmissionController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/")
    public String converterForm(Model model) {
        model.addAttribute("currencies", currencyService.getAllCurrencies());
        model.addAttribute("conversion", new Conversion());
        return "index";
    }

    @PostMapping("/")
    public String convertSubmit(@ModelAttribute @Valid Conversion conversion, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            model.addAttribute("currencies", currencyService.getAllCurrencies());
            model.addAttribute("conversion", new Conversion());
            model.addAttribute("fail", true);
            return "index";
        } else {
            currencyService.addExchange();
            model.addAttribute("result", currencyService.conversion(conversion.getFrom(), conversion.getTo(), conversion.getAmount()));
            model.addAttribute("to", conversion.getTo());
            model.addAttribute("from", conversion.getFrom());
            model.addAttribute("amount", conversion.getAmount());
            return "result";
        }
    }

}