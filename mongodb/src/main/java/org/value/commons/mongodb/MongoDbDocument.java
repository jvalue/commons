package org.value.commons.mongodb;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.Document;
import org.jvalue.commons.utils.Log;

import java.io.IOException;

public class MongoDbDocument<V> extends Document {

	private final Class<V> type;
	private ObjectMapper mapper = new ObjectMapper();

	public V value;
	private String _id;


	protected MongoDbDocument(V value, Class<V> type) {
		this.type = type;
		setValue(value);
	}


	protected MongoDbDocument(Document document, Class<V> type) {
		putAll(document);
		this.type = type;
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
		clear();
		try {
			String objectAsJsonString = mapper.writeValueAsString(value);
			Document parse = Document.parse(objectAsJsonString);
			put("value", parse);
		} catch (JsonProcessingException e) {

		}

		this.value = value;
	}


	public void setId(String id) {
		_id = id;
		put("_id", id);
	}


	public String getId() {
		return _id;
	}
}
