package org.pablog.pills.service;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.pablog.pills.domain.Day;
import org.pablog.pills.domain.Product;
import org.pablog.pills.domain.User;
import org.springframework.data.domain.Pageable;

public interface DayService {

	Day save(Day day);

	void delete(Day day);
	
	Day createDay(User user);

	void addProductToDay(Day day, Product product, User user);
	
	Day findById(ObjectId id);
	
	Day findByTheDateAndUser(Date date, User user);
	
	List<Day> findByUser(User user);
	
	List<Day> findByUser(User user, Pageable pageable);
}