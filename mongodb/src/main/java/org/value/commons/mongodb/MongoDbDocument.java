package org.value.commons.mongodb;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.Document;
import org.jvalue.commons.EntityBase;
import org.jvalue.commons.utils.Log;

import java.io.IOException;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MongoDbDocument<V extends EntityBase> extends Document {

	private final Class<V> type;
	private ObjectMapper mapper = new ObjectMapper();


	@JsonProperty("_id")
	public String _id;


	@JsonProperty("value")
	public V value;
	protected MongoDbDocument(V value, Class<V> type) {
		this.type = type;
		setValue(value);
	}

	protected MongoDbDocument(Document document, Class<V> type){
		putAll(document);
		this.type = type;
	}


	public String get_id() {
		return _id;
	}


	public V getValue() {
		V entity = null;
		try {
			String s = this.toJson();
			JsonNode node = mapper.readTree(s);
			JsonNode object = node.get("value");
			entity = mapper.readValue(object.toString(), type);
		} catch (IOException e) {
			Log.info("Could not deserialize json:" + this.toJson());
		}
		return entity;
	}


	public void setValue(V value) {
		try {
			String objectAsJsonString = mapper.writeValueAsString(value);
			Document parse = Document.parse(objectAsJsonString);
			put("value", parse);
		}catch (JsonProcessingException e){

		}
	}

	public String getId(){
		return getValue().getId();
	}
}
