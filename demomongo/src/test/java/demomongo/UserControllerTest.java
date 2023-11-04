package demomongo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

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
		mongoTemplate.dropCollection("users");
	}

	@After
	public void tearDown() {
		mongoTemplate.dropCollection("users");
	}

	@Test
	public void testCreateUser() {
		// Créer un utilisateur avec un nom d'utilisateur et un mot de passe valides
		User user = new User();
		user.setName("testuser");
		user.setPassword("testpassword");

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

	@Test
	public void testCreateUserWithExistingName() {
		// Créer un utilisateur avec un nom d'utilisateur existant
		User existingUser = new User();
		existingUser.setName("existinguser");
		existingUser.setPassword("existingpassword");
		mongoTemplate.save(existingUser, "users");

		// Créer un nouvel utilisateur avec le même nom d'utilisateur
		User user = new User();
		user.setName(existingUser.getName());
		user.setPassword("testpassword");

		// Envoyer une requête POST pour créer l'utilisateur
		ResponseEntity<User> response = restTemplate.postForEntity(baseUrl + "/createUser", user, User.class);

		// Vérifier que la réponse est une erreur HTTP 400 (Bad Request)
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	public void testLogin() {
		// Créer un utilisateur avec un email et un mot de passe valides
		String email = "test@test.com";
		String password = "testPassword";
		User user = new User();
		user.setEmail(email);
		user.setPassword(new BCryptPasswordEncoder().encode(password));
		mongoTemplate.save(user);

		// Créer un objet LoginRequest avec les mêmes informations de connexion
		LoginRequest loginRequest = new LoginRequest(email, password);

		// Envoyer une requête POST pour se connecter avec les informations de connexion
		// valides
		ResponseEntity<String> response = restTemplate.postForEntity(baseUrl + "/login", loginRequest, String.class);

		// Vérifier que la réponse est OK et que le corps de la réponse contient le
		// texte "Connexion réussie !"
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
}
