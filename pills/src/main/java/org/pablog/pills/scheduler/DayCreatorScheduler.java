package org.pablog.pills.scheduler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.pablog.pills.domain.Day;
import org.pablog.pills.domain.Product;
import org.pablog.pills.domain.ProductTaken;
import org.pablog.pills.domain.User;
import org.pablog.pills.repositories.DayRepository;
import org.pablog.pills.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class DayCreatorScheduler {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired DayRepository dayRepository;
	@Autowired UserRepository userRepository;
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	
	@Scheduled(cron="0 0 0 * * *")
	public void scheduleCreation() {
		logger.info("Start creating new treatment day time: " + new Date());
		for(User u : userRepository.findAll()) {
			logger.info("	Creating program for user: " + u.getUsername());
			Day day = new Day();
			day.setTheDate(formatter.format(new Date()));
			day.setProductTaken(new ArrayList<ProductTaken>());
			day.setUser(u);
			
			for(Product p : u.getProducts()) {
				logger.info("		Adding product: " + p.getName() + " for user: " + u.getUsername());
				ProductTaken pt = new ProductTaken();
				pt.setProduct(p);
				day.getProductTaken().add(pt);
				logger.info("		Product: " + pt.getProduct().getName() + "added.");
			}
			
			dayRepository.save(day);
		}
	}
	
}
