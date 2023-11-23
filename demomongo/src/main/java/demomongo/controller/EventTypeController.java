package demomongo.controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import model.EventTypeModel;

@CrossOrigin(origins = "*", allowCredentials = "false", allowedHeaders = "*")
@RestController
@RequestMapping("/api/eventtypes")
public class EventTypeController {

	private final String COLLECTION_NAME = "eventtypes";
	private final MongoTemplate mongoTemplate;

	private static final Logger logger = LoggerFactory.getLogger(EventTypeController.class);

	public EventTypeController(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@PostMapping("/addEventType")
	public ResponseEntity<EventTypeModel> addEventType(@RequestBody EventTypeModel eventType) {
		mongoTemplate.save(eventType, COLLECTION_NAME);
		ResponseEntity<EventTypeModel> responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(eventType);
		return responseEntity;
	}

	@GetMapping("/getAllEventTypes")
	public List<EventTypeModel> getAllEventsTypes() {
		logger.info("Request receive to get all events types exsting in db");
		List<EventTypeModel> allEventType = mongoTemplate.findAll(EventTypeModel.class, COLLECTION_NAME);
		return allEventType;
	}

	/**
	 * @CrossOrigin(origins = "*", methods = { RequestMethod.DELETE,
	 *                      RequestMethod.OPTIONS })
	 * @RequestMapping(value = "/deleteEventById", headers = {
	 *                       "content-type=application/json" }, consumes =
	 *                       MediaType.APPLICATION_JSON_VALUE, method =
	 *                       RequestMethod.DELETE) public ResponseEntity<String>
	 *                       deleteEventTypeById(@RequestBody String eventTypeId) {
	 *                       // Recherche de l'événement par son ID // Query query =
	 *                       new Query(Criteria.where("eventId").is(eventId));
	 * 
	 *                       // EventCalendar eventToDelete =
	 *                       mongoTemplate.findOne(query, // EventCalendar.class);
	 * 
	 *                       AggregationOperation match =
	 *                       Aggregation.match(Criteria.where("eventId").is(eventTypeId));
	 *                       AggregationOperation limit = Aggregation.limit(1);
	 * 
	 *                       Aggregation aggregation =
	 *                       Aggregation.newAggregation(match, limit);
	 * 
	 *                       EventTypeModel EventTypeToDelete =
	 *                       mongoTemplate.aggregate(aggregation, COLLECTION_NAME,
	 *                       EventTypeModel.class) .getUniqueMappedResult();
	 * 
	 *                       if (EventTypeToDelete != null) { // Suppression de
	 *                       l'événement mongoTemplate.remove(EventTypeToDelete,
	 *                       COLLECTION_NAME); return ResponseEntity.ok("Événement
	 *                       supprimé avec succès."); } else { return
	 *                       ResponseEntity.status(HttpStatus.NOT_FOUND).body("Événement
	 *                       introuvable."); } }
	 **/

	@DeleteMapping("/deleteEventById/{id}")
	public ResponseEntity<Void> deleteResource(@PathVariable String id) {
		try {
			ObjectId objectId = new ObjectId(id);
			Query query = new Query(Criteria.where("_id").is(objectId));
			EventTypeModel eventTypeToDelete = mongoTemplate.findOne(query, EventTypeModel.class, COLLECTION_NAME);

			if (eventTypeToDelete != null) {
				mongoTemplate.remove(eventTypeToDelete, COLLECTION_NAME);
				return ResponseEntity.ok().build();
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (IllegalArgumentException e) {
			// Gérer l'exception si la conversion de l'ID échoue
			return ResponseEntity.badRequest().build();
		} catch (Exception e) {
			// Gérer d'autres exceptions
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
