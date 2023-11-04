package demomongo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.junit.After;
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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EventControllerTest {

	@LocalServerPort
	private int port;

	private String baseUrl;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Before
	public void setUp() {
		baseUrl = "http://localhost:" + port + "/api/events";
		mongoTemplate.dropCollection("events");
	}

	@After
	public void tearDown() {
		// mongoTemplate.dropCollection("events");
	}

	@Test
	public void fillDbWithEvents() {

		// First event
		String startDate = this.convertDate(new Date().toString());

		Date endDate = new Date();
		// Ajoutez 2 heures (2 * 60 * 60 * 1000 millisecondes)
		endDate.setTime(endDate.getTime() + (2 * 60 * 60 * 1000));
		String endDateStr = this.convertDate(endDate.toString());

		EventCalendar eventCalendar = new EventCalendar();
		eventCalendar.setId("1");
		eventCalendar.setTitle("TEST 1");
		eventCalendar.setDescription("Bla blablabla blabla bla bla");
		eventCalendar.setStart(startDate);
		eventCalendar.setEnd(endDateStr);

		ResponseEntity<EventCalendar> response = restTemplate.postForEntity(baseUrl + "/addEvent", eventCalendar,
				EventCalendar.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		EventCalendar savedEvent = response.getBody();
		assertNotNull(savedEvent.getTitle());
		assertEquals(eventCalendar.getTitle(), savedEvent.getTitle());

		// Second sevent
		String startDate_2 = this.convertDate(new Date().toString());

		Date endDate_2 = new Date();
		endDate_2.setTime(endDate_2.getTime() + (24 * 60 * 60 * 1000));
		String endDateStr_2 = this.convertDate(endDate_2.toString());

		EventCalendar eventCalendar_2 = new EventCalendar();
		eventCalendar_2.setId("2");
		eventCalendar_2.setTitle("TEST 2");
		eventCalendar_2.setDescription("Bla blablabla blabla bla bla");
		eventCalendar_2.setStart(startDate_2);
		eventCalendar_2.setEnd(endDateStr_2);

		ResponseEntity<EventCalendar> response_2 = restTemplate.postForEntity(baseUrl + "/addEvent", eventCalendar_2,
				EventCalendar.class);

		assertEquals(HttpStatus.CREATED, response_2.getStatusCode());
		EventCalendar savedEvent_2 = response_2.getBody();
		assertNotNull(savedEvent_2.getTitle());
		assertEquals(eventCalendar_2.getTitle(), savedEvent_2.getTitle());

		// Third
		// Second sevent
		Date startDate_3 = new Date();
		startDate_3.setTime(startDate_3.getTime() + (24 * 60 * 60 * 1000));
		String startDateStr_3 = this.convertDate(new Date().toString());

		Date endDate_3 = new Date();
		// Ajoutez 2 heures (2 * 60 * 60 * 1000 millisecondes)
		endDate_3.setTime(endDate_3.getTime() + (2 * 60 * 60 * 1000));
		String endDateStr_3 = this.convertDate(endDate_3.toString());

		EventCalendar eventCalendar_3 = new EventCalendar();
		eventCalendar_3.setId("3");
		eventCalendar_3.setTitle("TEST 3");
		eventCalendar_3.setDescription("Bla blablabla blabla bla bla");
		eventCalendar_3.setStart(startDateStr_3);
		eventCalendar_3.setEnd(endDateStr_3);

		ResponseEntity<EventCalendar> response_3 = restTemplate.postForEntity(baseUrl + "/addEvent", eventCalendar_3,
				EventCalendar.class);

		assertEquals(HttpStatus.CREATED, response_3.getStatusCode());
		EventCalendar savedEvent_3 = response_3.getBody();
		assertNotNull(savedEvent_3.getTitle());
		assertEquals(eventCalendar_3.getTitle(), savedEvent_3.getTitle());
	}

	public String convertDate(String dateToConvert) {
		String formattedDate = null;
		SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
		inputFormat.setTimeZone(TimeZone.getTimeZone("CET"));

		try {
			Date date = inputFormat.parse(dateToConvert);
			SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			formattedDate = outputFormat.format(date);
			System.out.println(formattedDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return formattedDate;
	}
}
