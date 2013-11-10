package org.pablog.pills.domain;
import java.util.Iterator;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pablog.pills.repositories.DayRepository;
import org.pablog.pills.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//@Configurable
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath*:/META-INF/spring/applicationContext*.xml")
public class DayIntegrationTest {
	/*
    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    DayDataOnDemand dod;

	@Autowired DayRepository dayRepository;
	@Autowired UserRepository userRepository;

	@Test
    public void testCount() {
        Assert.assertNotNull("Data on demand for 'Day' failed to initialize correctly", dod.getRandomDay());
        long count = dayRepository.count();
        Assert.assertTrue("Counter for 'Day' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFind() {
        Day obj = dod.getRandomDay();
        Assert.assertNotNull("Data on demand for 'Day' failed to initialize correctly", obj);
        ObjectId id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Day' failed to provide an identifier", id);
        obj = dayRepository.findById(id);
        Assert.assertNotNull("Find method for 'Day' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Day' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindByUser() {
        Assert.assertNotNull("Data on demand for 'Day' failed to initialize correctly", dod.getRandomDay());
        long count = dayRepository.count();
        User user = userRepository.findByUsername("salva");
        Assert.assertTrue("Too expensive to perform a find all test for 'Day', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Day> result = dayRepository.findByUser(user);
        Assert.assertNotNull("Find all method for 'Day' illegally returned null", result);
//        Assert.assertTrue("Find all method for 'Day' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindEntries() {
        Assert.assertNotNull("Data on demand for 'Day' failed to initialize correctly", dod.getRandomDay());
        long count = dayRepository.count();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Day> result = dayRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
        Assert.assertNotNull("Find entries method for 'Day' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Day' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testSave() {
        Assert.assertNotNull("Data on demand for 'Day' failed to initialize correctly", dod.getRandomDay());
        Day obj = dod.getNewTransientDay(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Day' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Day' identifier to be null", obj.getId());
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
        Assert.assertNotNull("Expected 'Day' identifier to no longer be null", obj.getId());
    }
	@Test
    public void testDelete() {
        Day obj = dod.getRandomDay();
        Assert.assertNotNull("Data on demand for 'Day' failed to initialize correctly", obj);
        ObjectId id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Day' failed to provide an identifier", id);
        obj = dayRepository.findById(id);
        dayRepository.delete(obj);
        Assert.assertNull("Failed to remove 'Day' with identifier '" + id + "'", dayRepository.findById(id));
    }
	 */
}
