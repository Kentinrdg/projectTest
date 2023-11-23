package model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "eventtype")
public class EventTypeModel {

	@Id
	private ObjectId id;

	@Field("typeName")
	private String typeName;

	@Field("color")
	private String color;

	public EventTypeModel() {

	}

	public EventTypeModel(String typeName, String color) {
		this.typeName = typeName;
		this.color = color;
	}

	/**
	 * @return the typeName
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * @param typeName the typeName to set
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id.toHexString(); // Convertir l'ObjectId en une représentation hexadécimale de la chaîne
	}

}
