package org.pablog.pills.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;
import org.pablog.pills.domain.Day;
import org.pablog.pills.domain.Product;
import org.pablog.pills.domain.ProductTaken;
import org.pablog.pills.domain.User;
import org.pablog.pills.repositories.DayRepository;
import org.pablog.pills.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DayServiceImpl implements DayService {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired DayRepository dayRepository;
	@Autowired UserRepository userRepository;
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	
	/* (non-Javadoc)
	 * @see org.pablog.pills.service.DayService#createDay(org.pablog.pills.domain.User)
	 */
	@Override
	public Day createDay(User user) {
		logger.info("Start creating new treatment day time: " + new Date());
		logger.info("	Creating program for user: " + user.getUsername());
		Day day = new Day();
		day.setTheDate(formatter.format(new Date()));
		day.setProductTaken(new ArrayList<ProductTaken>());
		day.setUser(user);
		
		for(Product p : user.getProducts()) {
			logger.info("		Adding product: " + p.getName() + " for user: " + user.getUsername());
			ProductTaken pt = new ProductTaken();
			pt.setProduct(p);
			day.getProductTaken().add(pt);
			logger.info("		Product: " + pt.getProduct().getName() + "added.");
		}
		
		return dayRepository.save(day);
	}

	@Override
	public Day findByTheDateAndUser(Date date, User user) {
		return dayRepository.findByTheDateAndUser(formatter.format(date), userRepository.findByUsername(user.getUsername()));
	}

}
