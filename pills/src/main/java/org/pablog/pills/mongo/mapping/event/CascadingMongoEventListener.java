package org.pablog.pills.mongo.mapping.event;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.pablog.pills.mongo.mapping.Cascade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.mapping.Association;
import org.springframework.data.mapping.AssociationHandler;
import org.springframework.data.mapping.model.BeanWrapper;
import org.springframework.data.mapping.model.MappingException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.core.mapping.MongoPersistentProperty;

/*
**
* Performs cascade operations on child objects annotated with {@link DBRef}
*
* @author Maciej Walkowiak
*/
public class CascadingMongoEventListener extends AbstractMongoEventListener {
	Logger log = Logger.getLogger(this.getClass());
	@Autowired private MongoTemplate mongoTemplate;

	/**
	 * Executes {@link CascadeSaveAssociationHandler} on each property annotated with {@link DBRef}
	 * with cascadeType delete
	 *
	 * @param source object that is going to be converted
	 */
	@Override
	public void onBeforeConvert(final Object source) {
		log.debug("before convert: {}" + source);

		final MongoPersistentEntity<?> persistentEntity = mongoTemplate.getConverter().getMappingContext().getPersistentEntity(source.getClass());

		persistentEntity.doWithAssociations(new CascadeSaveAssociationHandler(source));
	}

	/**
	 * Executes {@link CascadeDeleteAssociationHandler} on each property annotated with {@link DBRef}
	 * with cascadeType save
	 *
	 * @param source object that has just been deleted
	 */
	@Override
	public void onAfterDelete(Object source) {
		log.debug("before delete: {}" + source);
//		final MongoPersistentEntity<?> persistentEntity = mongoTemplate.getConverter().getMappingContext().getPersistentEntity(source.getClass());
//		persistentEntity.doWithAssociations(new CascadeDeleteAssociationHandler(source));
	}

	/**
	 * Executes {@link MongoOperations#save(Object)} on referred objects
	 */
	private class CascadeSaveAssociationHandler extends AbstractCascadeAssociationHandler {
		private CascadeSaveAssociationHandler(Object source) {
			super(source);
		}

		@Override
		boolean isAppliable(Cascade cascade) {
			return cascade != null ? cascade.cascadeType().isSave() : false;
		}

		@Override
		void handleObject(Object referencedObject) {
			final MongoPersistentEntity<?> referencedObjectEntity = mongoTemplate.getConverter().getMappingContext().getPersistentEntity(referencedObject.getClass());

			if (referencedObjectEntity.getIdProperty() == null) {
				throw new MappingException("Cannot perform cascade save on child object without id set");
			}
			mongoTemplate.save(referencedObject);
		}

		@Override
		void handleCollection(Collection collection) {
			for (Object objectToSave : collection) {
				mongoTemplate.save(objectToSave);
			}
		}
	}

	/**
	 * Executes {@link MongoOperations#remove(Object)} on referred objects
	 */
	private class CascadeDeleteAssociationHandler extends AbstractCascadeAssociationHandler {
		public CascadeDeleteAssociationHandler(Object source) {
			super(source);
		}

		@Override
		boolean isAppliable(Cascade cascade) {
			return cascade != null ? cascade.cascadeType().isDelete() : false;
		}

		@Override
		void handleObject(Object referencedObject) {
			mongoTemplate.remove(referencedObject);
		}

		@Override
		void handleCollection(Collection collection) {
			for (Object objectToDelete : collection) {
				mongoTemplate.remove(objectToDelete);
			}
		}
	}

	private abstract class AbstractCascadeAssociationHandler implements AssociationHandler<MongoPersistentProperty> {
		protected Object source;

		protected AbstractCascadeAssociationHandler(Object source) {
			this.source = source;
		}

		public void doWithAssociation(Association<MongoPersistentProperty> mongoPersistentPropertyAssociation) {
			MongoPersistentProperty persistentProperty = mongoPersistentPropertyAssociation.getInverse();

			if (persistentProperty != null) {
//				DBRef dbRef = persistentProperty.getDBRef();
//				Annotations cannot be extended :( We have to use another annotation
				Cascade cascade = persistentProperty.getField().getAnnotation(Cascade.class);

				if (isAppliable(cascade)) {
					Object referencedObject = getReferredObject(persistentProperty);

					if (referencedObject != null) {
						handle(referencedObject);
					}
				}
			}
		}

		private Object getReferredObject(MongoPersistentProperty mongoPersistentProperty) {
			ConversionService service = mongoTemplate.getConverter().getConversionService();
			return BeanWrapper.create(source, service).getProperty(mongoPersistentProperty, Object.class, true);
		}

		abstract boolean isAppliable(Cascade cascade);

		void handle(Object referencedObject) {
			if (referencedObject instanceof Collection) {
				handleCollection((Collection) referencedObject);
			} else {
				handleObject(referencedObject);
			}
		}

		abstract void handleObject(Object referencedObject);

		abstract void handleCollection(Collection referencedObject);
	}
}