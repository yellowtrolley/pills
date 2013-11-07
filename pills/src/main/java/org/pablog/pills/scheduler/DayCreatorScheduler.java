package org.pablog.pills.scheduler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.pablog.pills.domain.Day;
import org.pablog.pills.domain.Product;
import org.pablog.pills.domain.ProductTaken;
import org.pablog.pills.repositories.DayRepository;
import org.pablog.pills.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class DayCreatorScheduler {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired DayRepository dayRepository;
	@Autowired ProductRepository productRepository;
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	
	@Scheduled(cron="0 0 0 * * *")
	public void scheduleCreation() {
		Day day = new Day();
		List<Product> products = productRepository.findAll();
		day.setTheDay(formatter.format(new Date()));
		day.setProductTaken(new ArrayList<ProductTaken>());
		
		for(Product p : products) {
			ProductTaken pt = new ProductTaken();
			pt.setProduct(p);
			day.getProductTaken().add(pt);
		}
		
		dayRepository.save(day);
	}
	
}
