package org.pablog.pills.domain;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.bson.types.ObjectId;
import org.pablog.pills.repositories.DayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

@Component
@Configurable
public class DayDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<Day> data;

	@Autowired
    DayRepository dayRepository;

	public Day getNewTransientDay(int index) {
        Day obj = new Day();
        setTheDay(obj, index);
        return obj;
    }

	public void setTheDay(Day obj, int index) {
        String theDay = "theDay_" + index;
        obj.setTheDay(theDay);
    }

	public Day getSpecificDay(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Day obj = data.get(index);
        ObjectId id = obj.getId();
        return dayRepository.findById(id);
    }

	public Day getRandomDay() {
        init();
        Day obj = data.get(rnd.nextInt(data.size()));
        ObjectId id = obj.getId();
        return dayRepository.findById(id);
    }

	public boolean modifyDay(Day obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = dayRepository.findAll(new org.springframework.data.domain.PageRequest(from / to, to)).getContent();
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Day' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Day>();
        for (int i = 0; i < 10; i++) {
            Day obj = getNewTransientDay(i);
            try {
                dayRepository.save(obj);
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
