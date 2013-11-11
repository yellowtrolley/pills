package org.pablog.pills.domain;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Document
public class Product {

	@Id
    private ObjectId id;

    private String name;

    private double morningDose;

    private double middayDose;

    private double nightDose;

    

	public Product() {}

	public Product(String name, double morningDose, double middayDose, double nightDose) {
		super();
		this.name = name;
		this.morningDose = morningDose;
		this.middayDose = middayDose;
		this.nightDose = nightDose;
	}

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public String toJson(String[] fields) {
        return new JSONSerializer().include(fields).exclude("*.class").serialize(this);
    }

	public static Product fromJsonToProduct(String json) {
        return new JSONDeserializer<Product>().use(null, Product.class).deserialize(json);
    }

	public static String toJsonArray(Collection<Product> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static String toJsonArray(Collection<Product> collection, String[] fields) {
        return new JSONSerializer().include(fields).exclude("*.class").serialize(collection);
    }

	public static Collection<Product> fromJsonArrayToProducts(String json) {
        return new JSONDeserializer<List<Product>>().use(null, ArrayList.class).use("values", Product.class).deserialize(json);
    }

	public String getName() {
        return this.name;
    }

	public void setName(String name) {
        this.name = name;
    }

	public double getMorningDose() {
        return this.morningDose;
    }

	public void setMorningDose(double morningDose) {
        this.morningDose = morningDose;
    }

	public double getMiddayDose() {
        return this.middayDose;
    }

	public void setMiddayDose(double middayDose) {
        this.middayDose = middayDose;
    }

	public double getNightDose() {
        return this.nightDose;
    }

	public void setNightDose(double nightDose) {
        this.nightDose = nightDose;
    }

	public ObjectId getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Product other = (Product) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}
	
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
