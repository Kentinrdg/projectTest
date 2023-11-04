package demomongo;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowCredentials = "false", allowedHeaders = "*")
@RestController
@RequestMapping("/api/users")
public class UserController {

	private final MongoTemplate mongoTemplate;

	public UserController(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@RequestMapping(value = "/createUser", headers = {
			"content-type=application/json" }, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<User> createUser(@RequestBody User user) {
		// Vérifie si un utilisateur avec le même nom d'utilisateur existe déjà dans la
		// base de données

		User existingUser = mongoTemplate.findOne(Query.query(Criteria.where("name").is(user.getName())), User.class);
		if (existingUser != null) {
			return ResponseEntity.badRequest().build();
		}

		PasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword()));

		// Sauvegarde l'utilisateur dans la base de données
		mongoTemplate.save(user, "users");

		// Retourne l'utilisateur sauvegardé avec le code de statut 201 (Created)
		ResponseEntity<User> responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(user);
		return responseEntity;
	}

	@RequestMapping(value = "/login", headers = {
			"content-type=application/json" }, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<User> login(@RequestBody LoginRequest loginRequest) {
		// Vérifie si un utilisateur avec le même nom d'utilisateur existe déjà dans la
		// base de données
		User existingUser = mongoTemplate.findOne(Query.query(Criteria.where("email").is(loginRequest.getEmail())),
				User.class);
		if (existingUser == null) {
			return ResponseEntity.notFound().build();
		}

		PasswordEncoder encoder = new BCryptPasswordEncoder();
		if (!encoder.matches(loginRequest.getPassword(), existingUser.getPassword())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		// Retourne l'utilisateur connecté avec le code de statut 200 (OK)
		return ResponseEntity.ok(existingUser);
	}

}
