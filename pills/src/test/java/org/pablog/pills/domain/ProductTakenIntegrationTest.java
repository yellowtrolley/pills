package org.pablog.pills.domain;
import java.util.Iterator;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pablog.pills.repositories.ProductTakenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Configurable
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/META-INF/spring/applicationContext*.xml")
public class ProductTakenIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    ProductTakenDataOnDemand dod;

	@Autowired
    ProductTakenRepository productTakenRepository;

	@Test
    public void testCount() {
        Assert.assertNotNull("Data on demand for 'ProductTaken' failed to initialize correctly", dod.getRandomProductTaken());
        long count = productTakenRepository.count();
        Assert.assertTrue("Counter for 'ProductTaken' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFind() {
        ProductTaken obj = dod.getRandomProductTaken();
        Assert.assertNotNull("Data on demand for 'ProductTaken' failed to initialize correctly", obj);
        ObjectId id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ProductTaken' failed to provide an identifier", id);
        obj = productTakenRepository.findById(id);
        Assert.assertNotNull("Find method for 'ProductTaken' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'ProductTaken' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAll() {
        Assert.assertNotNull("Data on demand for 'ProductTaken' failed to initialize correctly", dod.getRandomProductTaken());
        long count = productTakenRepository.count();
        Assert.assertTrue("Too expensive to perform a find all test for 'ProductTaken', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<ProductTaken> result = productTakenRepository.findAll();
        Assert.assertNotNull("Find all method for 'ProductTaken' illegally returned null", result);
        Assert.assertTrue("Find all method for 'ProductTaken' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindEntries() {
        Assert.assertNotNull("Data on demand for 'ProductTaken' failed to initialize correctly", dod.getRandomProductTaken());
        long count = productTakenRepository.count();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<ProductTaken> result = productTakenRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
        Assert.assertNotNull("Find entries method for 'ProductTaken' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'ProductTaken' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testSave() {
        Assert.assertNotNull("Data on demand for 'ProductTaken' failed to initialize correctly", dod.getRandomProductTaken());
        ProductTaken obj = dod.getNewTransientProductTaken(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'ProductTaken' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'ProductTaken' identifier to be null", obj.getId());
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
        Assert.assertNotNull("Expected 'ProductTaken' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDelete() {
        ProductTaken obj = dod.getRandomProductTaken();
        Assert.assertNotNull("Data on demand for 'ProductTaken' failed to initialize correctly", obj);
        ObjectId id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ProductTaken' failed to provide an identifier", id);
        obj = productTakenRepository.findById(id);
        productTakenRepository.delete(obj);
        Assert.assertNull("Failed to remove 'ProductTaken' with identifier '" + id + "'", productTakenRepository.findById(id));
    }
}
