package org.pablog.pills.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.pablog.pills.domain.Day;
import org.pablog.pills.domain.Product;
import org.pablog.pills.domain.ProductTaken;
import org.pablog.pills.domain.User;
import org.pablog.pills.repositories.DayRepository;
import org.pablog.pills.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DayServiceImpl implements DayService {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired DayRepository dayRepository;
	@Autowired UserRepository userRepository;
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	
	@Override
	public Day createDay(User user) {
		logger.info("Start creating new treatment day time: " + new Date());
		logger.info("	Creating program for user: " + user.getUsername());
		Day day = new Day(user, formatter.format(new Date()), new ArrayList<ProductTaken>());
		day.setUser(user);
		
		for(Product p : user.getProducts()) {
			logger.info("		Adding product: " + p.getName() + " for user: " + user.getUsername());
			ProductTaken pt = new ProductTaken(p, false, false, false);
			day.getProductTaken().add(pt);
			logger.info("		Product: " + pt.getProduct().getName() + "added.");
		}
		
		return dayRepository.save(day);
	}

	@Override
	public void addProductToDay(Day day, Product product, User user) {
		day.getProductTaken().add(new ProductTaken(product, false, false, false));
		dayRepository.save(day);
	}
	

	@Override
	public Day save(Day day) {
		return dayRepository.save(day);
	}

	@Override
	public void delete(Day day) {
		dayRepository.delete(day);
	}

	@Override
	public Day findByTheDateAndUser(Date date, User user) {
		return dayRepository.findByTheDateAndUser(formatter.format(date), userRepository.findByUsername(user.getUsername()));
	}
	
	@Override
	public Day findById(ObjectId id) {
		return dayRepository.findById(id);
	}
	
	@Override
	public List<Day> findByUser(User user) {
		return dayRepository.findByUser(user);
	}
	
	@Override
	public List<Day> findByUser(User user, Pageable pageable) {
		return dayRepository.findByUser(user, pageable);
	}
}
