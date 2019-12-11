package se.kth.id1212.hw4.Homework_4.model;

import org.springframework.stereotype.Component;

@Component
public class Converter {
    public  Converter(){

    }
    public double conversion(Currency from,Currency to, double amount){
        if(from.equals(to)){
            return amount;
        }
        System.out.println(from.getRate() +" "+to.getRate());
        return (amount*from.getRate()*to.getRate());
    }
}
