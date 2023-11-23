package demomongo;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import demomongo.controller.EventTypeController;
import model.EventTypeModel;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EventTypeControllerTest {
	@LocalServerPort
	private int port;

	private String baseUrl;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private MongoTemplate mongoTemplate;

	public EventTypeController eventController = new EventTypeController(mongoTemplate);

	@Before
	public void setUp() {
		baseUrl = "http://localhost:" + port + "/api/eventtypes";
		// mongoTemplate.dropCollection("events");
	}

	@Test
	public void fillDbWithEvents() {

		EventTypeModel type1 = new EventTypeModel("TYPE 1", "#FF5733");

		ResponseEntity<EventTypeModel> response = restTemplate.postForEntity(baseUrl + "/addEventType", type1,
				EventTypeModel.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		EventTypeModel savedEventType = response.getBody();
		assertNotNull(savedEventType.getTypeName());
		assertEquals(savedEventType.getTypeName(), type1.getTypeName());

		EventTypeModel type2 = new EventTypeModel("TYPE 2", "#33FFA3");

		ResponseEntity<EventTypeModel> response_2 = restTemplate.postForEntity(baseUrl + "/addEventType", type2,
				EventTypeModel.class);

		assertEquals(HttpStatus.CREATED, response_2.getStatusCode());
		EventTypeModel savedEventType_2 = response_2.getBody();
		assertNotNull(savedEventType_2.getTypeName());
		assertEquals(savedEventType_2.getTypeName(), type2.getTypeName());

		EventTypeModel type3 = new EventTypeModel("TYPE 3", "#336BFF");
		ResponseEntity<EventTypeModel> response_3 = restTemplate.postForEntity(baseUrl + "/addEventType", type3,
				EventTypeModel.class);

		assertEquals(HttpStatus.CREATED, response_3.getStatusCode());
		EventTypeModel savedEventType_3 = response_3.getBody();
		assertNotNull(savedEventType_3.getTypeName());
		assertEquals(savedEventType_3.getTypeName(), type3.getTypeName());

	}
}
