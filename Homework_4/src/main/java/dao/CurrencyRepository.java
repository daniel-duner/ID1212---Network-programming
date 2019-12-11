package se.kth.id1212.hw4.Homework_4.dao;

import org.springframework.data.repository.CrudRepository;
import se.kth.id1212.hw4.Homework_4.model.Currency;

public interface CurrencyRepository extends CrudRepository<Currency, String>
{

}
