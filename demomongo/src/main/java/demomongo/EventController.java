package demomongo;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowCredentials = "false", allowedHeaders = "*")
@RestController
@RequestMapping("/api/events")
public class EventController {

	private final MongoTemplate mongoTemplate;

	public EventController(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@RequestMapping(value = "/addEvent", headers = {
			"content-type=application/json" }, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<EventCalendar> createUser(@RequestBody EventCalendar eventCalendar) {
		// Sauvegarde l'event dans la DB
		mongoTemplate.save(eventCalendar, "events");

		// Retourne l'utilisateur sauvegardé avec le code de statut 201 (Created)
		ResponseEntity<EventCalendar> responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(eventCalendar);
		return responseEntity;
	}

	@GetMapping("/getAllEvents")
	public List<EventCalendar> getAllEvents() {
		List<EventCalendar> events = mongoTemplate.findAll(EventCalendar.class, "events");
		return events;
	}
}
