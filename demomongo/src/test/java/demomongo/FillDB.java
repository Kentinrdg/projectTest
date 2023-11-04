package demomongo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FillDB {

	@LocalServerPort
	private int port;

	private String baseUrl;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Before
	public void setUp() {
		baseUrl = "http://localhost:" + port + "/api/users";
	}

	@Test
	public void testCreateUser() {
		// Créer un utilisateur avec un nom d'utilisateur et un mot de passe valides
		User user = new User();
		user.setName("a");
		user.setPassword("a");
		user.setEmail("a");

		// Envoyer une requête POST pour créer l'utilisateur
		ResponseEntity<User> response = restTemplate.postForEntity(baseUrl + "/createUser", user, User.class);

		// Vérifier que la réponse est OK et que le corps de la réponse contient
		// l'utilisateur créé
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		User savedUser = response.getBody();
		assertNotNull(savedUser.getName());
		assertEquals(user.getName(), savedUser.getName());
		assertTrue(new BCryptPasswordEncoder().matches(user.getPassword(), savedUser.getPassword()));
	}
}
