package org.pablog.pills.domain;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.bson.types.ObjectId;
import org.pablog.pills.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

@Configurable
@Component
public class ProductDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<Product> data;

	@Autowired
    ProductRepository productRepository;

	public Product getNewTransientProduct(int index) {
        Product obj = new Product();
        setMiddayDose(obj, index);
        setMorningDose(obj, index);
        setName(obj, index);
        setNightDose(obj, index);
        return obj;
    }

	public void setMiddayDose(Product obj, int index) {
        double middayDose = new Integer(index).doubleValue();
        obj.setMiddayDose(middayDose);
    }

	public void setMorningDose(Product obj, int index) {
        double morningDose = new Integer(index).doubleValue();
        obj.setMorningDose(morningDose);
    }

	public void setName(Product obj, int index) {
        String name = "name_" + index;
        obj.setName(name);
    }

	public void setNightDose(Product obj, int index) {
        double nightDose = new Integer(index).doubleValue();
        obj.setNightDose(nightDose);
    }

	public Product getSpecificProduct(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Product obj = data.get(index);
        ObjectId id = obj.getId();
        return productRepository.findById(id);
    }

	public Product getRandomProduct() {
        init();
        Product obj = data.get(rnd.nextInt(data.size()));
        ObjectId id = obj.getId();
        return productRepository.findById(id);
    }

	public boolean modifyProduct(Product obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = productRepository.findAll(new org.springframework.data.domain.PageRequest(from / to, to)).getContent();
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Product' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Product>();
        for (int i = 0; i < 10; i++) {
            Product obj = getNewTransientProduct(i);
            try {
                productRepository.save(obj);
            } catch (final ConstraintViolationException e) {
                final StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    final ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
                }
                throw new IllegalStateException(msg.toString(), e);
            }
            data.add(obj);
        }
    }
}
