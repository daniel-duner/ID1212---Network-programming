package se.kth.id1212.hw4.Homework_4.model;

import javax.persistence.*;

@Entity
public class Counter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int count;
    public Counter(){
        count = 0;
    }

    public void increment(){
        this. count++;
    }

    public long getId() {
        return id;
    }

    public int getCount() {
        return count;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
