package se.kth.id1212.hw4.Homework_4.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Currency {
    public Currency(){ }
    @Id
    private String id;
    private double rate;
    public Currency(@JsonProperty("id") String id,@JsonProperty("rate") double rate ){
        super();
        this.id = id;
        this.rate = rate;
    }

    public double getRate() {
        return rate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id){
        this.id = id;
    }
    public void setRate(double rate){
        this.rate = rate;
    }
}
