package org.pablog.pills.domain;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Document
public class ProductTaken {

	@Id
    private ObjectId id;
	@DBRef
    private Product product;
	private boolean morning;
    private boolean midday;
    private boolean night;

    
    public ProductTaken() {}
	
	public ProductTaken(Product product, boolean morning, boolean midday, boolean night) {
		this.product = product;
		this.morning = morning;
		this.midday = midday;
		this.night = night;
	}

	
	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public String toJson(String[] fields) {
        return new JSONSerializer().include(fields).exclude("*.class").serialize(this);
    }

	public static ProductTaken fromJsonToProductTaken(String json) {
        return new JSONDeserializer<ProductTaken>().use(null, ProductTaken.class).deserialize(json);
    }

	public static String toJsonArray(Collection<ProductTaken> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static String toJsonArray(Collection<ProductTaken> collection, String[] fields) {
        return new JSONSerializer().include(fields).exclude("*.class").serialize(collection);
    }

	public static Collection<ProductTaken> fromJsonArrayToProductTakens(String json) {
        return new JSONDeserializer<List<ProductTaken>>().use(null, ArrayList.class).use("values", ProductTaken.class).deserialize(json);
    }
	
	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}
	public Product getProduct() {
        return this.product;
    }

	public void setProduct(Product product) {
        this.product = product;
    }

	public boolean isMorning() {
        return this.morning;
    }

	public void setMorning(boolean morning) {
        this.morning = morning;
    }

	public boolean isMidday() {
        return this.midday;
    }

	public void setMidday(boolean midday) {
        this.midday = midday;
    }

	public boolean isNight() {
        return this.night;
    }

	public void setNight(boolean night) {
        this.night = night;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductTaken other = (ProductTaken) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.toString().equals(other.id.toString()))
			return false;
		return true;
	}
	
	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
