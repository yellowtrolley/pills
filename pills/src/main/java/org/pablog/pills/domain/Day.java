package org.pablog.pills.domain;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.bson.types.ObjectId;
import org.pablog.pills.mongo.CascadeSave;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Document
public class Day {

	@Id
    private ObjectId id;

	@DBRef
	private User user;

	private String theDate;
    
    @DBRef
    @CascadeSave
    private List<ProductTaken> productTaken = new ArrayList<ProductTaken>();
    
	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public String toJson(String[] fields) {
        return new JSONSerializer().include(fields).exclude("*.class").serialize(this);
    }

	public static Day fromJsonToDay(String json) {
        return new JSONDeserializer<Day>().use(null, Day.class).deserialize(json);
    }

	public static String toJsonArray(Collection<Day> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static String toJsonArray(Collection<Day> collection, String[] fields) {
        return new JSONSerializer().include(fields).exclude("*.class").serialize(collection);
    }

	public static Collection<Day> fromJsonArrayToDays(String json) {
        return new JSONDeserializer<List<Day>>().use(null, ArrayList.class).use("values", Day.class).deserialize(json);
    }

	public ObjectId getId() {
    	return id;
    }
    
    public void setId(ObjectId id) {
    	this.id = id;
    }

	public List<ProductTaken> getProductTaken() {
        return this.productTaken;
    }

	public void setProductTaken(List<ProductTaken> productTaken) {
        this.productTaken = productTaken;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getTheDate() {
		return theDate;
	}

	public void setTheDate(String theDate) {
		this.theDate = theDate;
	}
}
