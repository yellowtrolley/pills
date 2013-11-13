package org.pablog.pills.service;

import java.util.Date;

import org.pablog.pills.domain.Day;
import org.pablog.pills.domain.Product;
import org.pablog.pills.domain.User;

public interface DayService {

	Day createDay(User user);
	Day findByTheDateAndUser(Date date, User user);
	void addProductToDay(Day day, Product product);
}