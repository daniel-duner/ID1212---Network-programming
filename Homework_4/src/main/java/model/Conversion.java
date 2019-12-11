package se.kth.id1212.hw4.Homework_4.model;

import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class Conversion {
    @Size(min=3, max=3)
    private String from;
    @Size(min=3, max=3)
    private String to;
    @Min(0)
    @Digits(integer = 6, fraction = 19, message = "only digits are allowed")
    private double amount;
    public Conversion(){}

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public double getAmount() {
        return amount;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }
}
