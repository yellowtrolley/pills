package org.pablog.pills.web;

import org.bson.types.ObjectId;
import org.pablog.pills.domain.Day;
import org.pablog.pills.domain.Product;
import org.pablog.pills.domain.ProductTaken;
import org.pablog.pills.repositories.DayRepository;
import org.pablog.pills.repositories.ProductRepository;
import org.pablog.pills.repositories.ProductTakenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;

@Configurable
/**
 * A central place to register application converters and formatters. 
 */
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {

	@Override
	protected void installFormatters(FormatterRegistry registry) {
		super.installFormatters(registry);
		// Register application converters and formatters
	}

	@Autowired
    DayRepository dayRepository;

	@Autowired
    ProductRepository productRepository;

	@Autowired
    ProductTakenRepository productTakenRepository;

	public Converter<Day, String> getDayToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<org.pablog.pills.domain.Day, java.lang.String>() {
            public String convert(Day day) {
                return new StringBuilder().append(day.getTheDate()).toString();
            }
        };
    }

	public Converter<ObjectId, Day> getIdToDayConverter() {
        return new org.springframework.core.convert.converter.Converter<ObjectId, org.pablog.pills.domain.Day>() {
            public org.pablog.pills.domain.Day convert(ObjectId id) {
                return dayRepository.findById(id);
            }
        };
    }

	public Converter<String, Day> getStringToDayConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, org.pablog.pills.domain.Day>() {
            public org.pablog.pills.domain.Day convert(String id) {
                return getObject().convert(getObject().convert(id, ObjectId.class), Day.class);
            }
        };
    }

	public Converter<Product, String> getProductToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<org.pablog.pills.domain.Product, java.lang.String>() {
            public String convert(Product product) {
                return new StringBuilder().append(product.getName()).append(' ').append(product.getMorningDose()).append(' ').append(product.getMiddayDose()).append(' ').append(product.getNightDose()).toString();
            }
        };
    }

	public Converter<ObjectId, Product> getIdToProductConverter() {
        return new org.springframework.core.convert.converter.Converter<ObjectId, org.pablog.pills.domain.Product>() {
            public org.pablog.pills.domain.Product convert(ObjectId id) {
                return productRepository.findById(id);
            }
        };
    }

	public Converter<String, Product> getStringToProductConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, org.pablog.pills.domain.Product>() {
            public org.pablog.pills.domain.Product convert(String id) {
                return getObject().convert(getObject().convert(id, ObjectId.class), Product.class);
            }
        };
    }

	public Converter<ProductTaken, String> getProductTakenToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<org.pablog.pills.domain.ProductTaken, java.lang.String>() {
            public String convert(ProductTaken productTaken) {
                return "(no displayable fields)";
            }
        };
    }

	public Converter<ObjectId, ProductTaken> getIdToProductTakenConverter() {
        return new org.springframework.core.convert.converter.Converter<ObjectId, org.pablog.pills.domain.ProductTaken>() {
            public org.pablog.pills.domain.ProductTaken convert(ObjectId id) {
                return productTakenRepository.findById(id);
            }
        };
    }

	public Converter<String, ProductTaken> getStringToProductTakenConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, org.pablog.pills.domain.ProductTaken>() {
            public org.pablog.pills.domain.ProductTaken convert(String id) {
                return getObject().convert(getObject().convert(id, ObjectId.class), ProductTaken.class);
            }
        };
    }

	public void installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(getDayToStringConverter());
        registry.addConverter(getIdToDayConverter());
        registry.addConverter(getStringToDayConverter());
        registry.addConverter(getProductToStringConverter());
        registry.addConverter(getIdToProductConverter());
        registry.addConverter(getStringToProductConverter());
        registry.addConverter(getProductTakenToStringConverter());
        registry.addConverter(getIdToProductTakenConverter());
        registry.addConverter(getStringToProductTakenConverter());
    }

	public void afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }
}
