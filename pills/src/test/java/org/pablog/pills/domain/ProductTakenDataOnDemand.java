package org.pablog.pills.domain;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.bson.types.ObjectId;
import org.pablog.pills.repositories.ProductTakenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

@Component
@Configurable
public class ProductTakenDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<ProductTaken> data;

	@Autowired
    ProductTakenRepository productTakenRepository;

	public ProductTaken getNewTransientProductTaken(int index) {
        ProductTaken obj = new ProductTaken();
        setMidday(obj, index);
        setMorning(obj, index);
        setNight(obj, index);
        setProduct(obj, index);
        return obj;
    }

	public void setMidday(ProductTaken obj, int index) {
        Boolean midday = true;
        obj.setMidday(midday);
    }

	public void setMorning(ProductTaken obj, int index) {
        Boolean morning = true;
        obj.setMorning(morning);
    }

	public void setNight(ProductTaken obj, int index) {
        Boolean night = true;
        obj.setNight(night);
    }

	public void setProduct(ProductTaken obj, int index) {
        Product product = null;
        obj.setProduct(product);
    }

	public ProductTaken getSpecificProductTaken(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        ProductTaken obj = data.get(index);
        ObjectId id = obj.getId();
        return productTakenRepository.findById(id);
    }

	public ProductTaken getRandomProductTaken() {
        init();
        ProductTaken obj = data.get(rnd.nextInt(data.size()));
        ObjectId id = obj.getId();
        return productTakenRepository.findById(id);
    }

	public boolean modifyProductTaken(ProductTaken obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = productTakenRepository.findAll(new org.springframework.data.domain.PageRequest(from / to, to)).getContent();
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'ProductTaken' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<ProductTaken>();
        for (int i = 0; i < 10; i++) {
            ProductTaken obj = getNewTransientProductTaken(i);
            try {
                productTakenRepository.save(obj);
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
