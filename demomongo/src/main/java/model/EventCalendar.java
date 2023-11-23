package model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "events")
public class EventCalendar {

	@Id
	private ObjectId id;

	@Field("eventId")
	public String eventId;

	@Field("title")
	public String title;

	@Field("start")
	public String start;

	@Field("end")
	public String end;

	@Field("description")
	public String description;

	@Field("backgroundColor")
	public String backgroundColor;

	public EventCalendar() {

	}

	public EventCalendar(String eventId, String title, String start, String end, String description,
			String backgroundColor) {
		this.eventId = eventId;
		this.title = title;
		this.start = start;
		this.end = end;
		this.description = description;
		this.backgroundColor = backgroundColor;
	}

	/**
	 * @return the id
	 */
	public String getEventId() {
		return eventId;
	}

	/**
	 * @param id the id to set
	 */
	public void setEventId(String id) {
		this.eventId = id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the start
	 */
	public String getStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(String start) {
		this.start = start;
	}

	/**
	 * @return the end
	 */
	public String getEnd() {
		return end;
	}

	/**
	 * @param end the end to set
	 */
	public void setEnd(String end) {
		this.end = end;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the backgroundColor
	 */
	public String getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * @param backgroundColor the backgroundColor to set
	 */
	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

}
