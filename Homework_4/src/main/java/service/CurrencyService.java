package se.kth.id1212.hw4.Homework_4.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import se.kth.id1212.hw4.Homework_4.dao.CurrencyRepository;
import se.kth.id1212.hw4.Homework_4.dao.ExchangeRepository;
import se.kth.id1212.hw4.Homework_4.model.*;

import java.util.ArrayList;
import java.util.List;

@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
@Service
public class CurrencyService {
    @Autowired
    private CurrencyRepository currencyRepository;
    @Autowired
    private ExchangeRepository exchangeRepository;
    @Autowired
    private Converter converter;
    public CurrencyService() {
    }

    public Currency getCurrency(String id){
        return currencyRepository.findById(id).orElseGet(Currency::new);
    }

    public Counter getCounter(){
        List<Counter> c = new ArrayList<>();
        exchangeRepository.findAll().forEach(c::add);
        return c.get(0);
    }

    @Transactional
    public void addExchange(){
        if(exchangeRepository.count()<1){
            exchangeRepository.save(new Counter());
        } else {
            List<Counter> c = new ArrayList<>();
            exchangeRepository.findAll().forEach(c::add);
            c.get(0).increment();
        }
    }

    @Transactional
    public void updateRate(Currency currency){
        Currency curr = currencyRepository.findById(currency.getId()).orElseGet(Currency::new);
        curr.setRate(currency.getRate());
    }

    @Transactional
    public boolean addCurrency (Currency currency){
        if(currencyRepository.existsById(currency.getId())){
            return false;
        }
        currencyRepository.save(currency);
        return true;
    }

    public double conversion(String from, String to, double amount){
        Currency currencyFrom = getCurrency(from);
        Currency currencyTo = getCurrency(to);
        if(currencyFrom != null && currencyTo != null){
            return converter.conversion(currencyFrom, currencyTo, amount);
        }
        return amount;
    }

    public List<Currency> getAllCurrencies(){
        List<Currency> currencies = new ArrayList<>();
        currencyRepository.findAll().forEach(currencies::add);
        return currencies;
    }
}
