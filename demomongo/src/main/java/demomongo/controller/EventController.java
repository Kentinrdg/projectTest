package demomongo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import model.EventCalendar;

@CrossOrigin(origins = "*", allowCredentials = "false", allowedHeaders = "*")
@RestController
@RequestMapping("/api/events")
public class EventController {

	private static final Logger logger = LoggerFactory.getLogger(EventController.class);

	private final MongoTemplate mongoTemplate;

	public EventController(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	// Ajoutez cette méthode pour gérer les requêtes de pré-vérification
	@RequestMapping(value = "/getAllEvents", method = RequestMethod.OPTIONS)
	public ResponseEntity<?> handleOptions() {
		return ResponseEntity.ok().build();
	}

	@RequestMapping(value = "/addEvent", headers = {
			"content-type=application/json" }, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<EventCalendar> createUser(@RequestBody EventCalendar eventCalendar) {
		logger.info("Request receive for create event");
		// Sauvegarde l'event dans la DB
		mongoTemplate.save(eventCalendar, "events");
		logger.info("Event is saved !");
		// Retourne l'utilisateur sauvegardé avec le code de statut 201 (Created)
		ResponseEntity<EventCalendar> responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(eventCalendar);
		return responseEntity;
	}

	@GetMapping("/getAllEvents")
	public List<EventCalendar> getAllEvents() {
		logger.info("Request receive to get all events exsting in db");
		List<EventCalendar> events = mongoTemplate.findAll(EventCalendar.class, "events");
		return events;
	}

	@RequestMapping(value = "/deleteEventById", headers = {
			"content-type=application/json" }, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<String> deleteEventById(@RequestBody String eventId) {
		// Recherche de l'événement par son ID
		// Query query = new Query(Criteria.where("eventId").is(eventId));

		// EventCalendar eventToDelete = mongoTemplate.findOne(query,
		// EventCalendar.class);

		AggregationOperation match = Aggregation.match(Criteria.where("eventId").is(eventId));
		AggregationOperation limit = Aggregation.limit(1);

		Aggregation aggregation = Aggregation.newAggregation(match, limit);

		EventCalendar eventToDelete = mongoTemplate.aggregate(aggregation, "events", EventCalendar.class)
				.getUniqueMappedResult();

		if (eventToDelete != null) {
			// Suppression de l'événement
			mongoTemplate.remove(eventToDelete, "events");
			return ResponseEntity.ok("Événement supprimé avec succès.");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Événement introuvable.");
		}
	}
}
