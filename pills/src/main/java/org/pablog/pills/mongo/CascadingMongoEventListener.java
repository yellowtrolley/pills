package org.pablog.pills.mongo;

import java.lang.reflect.Field;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.util.ReflectionUtils;

/**
 * Performs cascade operations on child objects annotated with {@link DBRef}
 *
 * @author Maciej Walkowiak
 */
public class CascadingMongoEventListener extends AbstractMongoEventListener {
    @Autowired
    private MongoTemplate mongoTemplate;
    Logger logger = Logger.getLogger(this.getClass());
    
    @Override
    public void onBeforeConvert(final Object source) {
        ReflectionUtils.doWithFields(source.getClass(), new ReflectionUtils.FieldCallback() {
            @Override
            public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                ReflectionUtils.makeAccessible(field);

                if (field.isAnnotationPresent(DBRef.class) && field.isAnnotationPresent(CascadeSave.class)) {
                    final Object fieldValue = field.get(source);
                    logger.debug("field value: " + fieldValue);
                    if (fieldValue != null) {
                        Class fieldClass = fieldValue.getClass();
                        logger.debug("field Class: " + fieldClass);
//                        if (Collection.class.isAssignableFrom(field.getType())) {
//                            fieldClass = getParameterType(field);
//                        }

                        DbRefFieldCallback callback = new DbRefFieldCallback();

                        ReflectionUtils.doWithFields(fieldClass, callback);

//                        if (!callback.isIdFound()) {
//                            throw new MappingException("Cannot perform cascade save on child object without id set");
//                        }

                        if (Collection.class.isAssignableFrom(field.getType())) {
                        	logger.debug("field isAssignableFrom: Collection.class" + field.getType());
                            @SuppressWarnings("unchecked")
                            Collection<Object> models = (Collection<Object>) fieldValue;
                            for (Object model : models) {
                                mongoTemplate.save(model);
                                logger.debug("model saved: " + model);
                            }
                        } else {
                            mongoTemplate.save(fieldValue);
                            logger.debug("fieldValue saved: " + fieldValue);
                        }
                    }

                }
            }
        });
    }
    
    /**
     * *
     * if (field.isAnnotationPresent(DBRef.class) &&
     * field.isAnnotationPresent(CascadeSave.class)) { final Object fieldValue =
     * field.get(source); if (fieldValue != null) { Class fieldClass =
     * fieldValue.getClass(); if
     * (Collection.class.isAssignableFrom(field.getType())) { fieldClass =
     * getParameterType(field); }
     *
     * DbRefFieldCallback callback = new DbRefFieldCallback();
     *
     * ReflectionUtils.doWithFields(fieldClass, callback);
     *
     * if (!callback.isIdFound()) { throw new MappingException("Cannot perform
     * cascade save on child object without id set"); }
     *
     * if (Collection.class.isAssignableFrom(field.getType())) {
     *
     * @SuppressWarnings("unchecked") Collection<object> models =
     * (Collection<object>) fieldValue; for (Object model : models) {
     * mongoOperations.save(model); } } else { mongoOperations.save(fieldValue);
     * } } }
     */
    private static class DbRefFieldCallback implements ReflectionUtils.FieldCallback {

        private boolean idFound;

        @Override
        public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
            ReflectionUtils.makeAccessible(field);

            if (field.isAnnotationPresent(Id.class)) {
                idFound = true;
            }
        }

        public boolean isIdFound() {
            return idFound;
        }
    }
}