package demomongo;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "users")
public class User {

	@Field("lastname")
	public String lastname;

	@Field("name")
	public String name;

	@Field("password")
	public String password;

	@Field("email")
	public String email;

	@Field("role")
	public ERole role;

	public User() {

	}

	public User(String lastname, String name, String password, String email, ERole role) {
		this.lastname = lastname;
		this.name = name;
		this.password = password;
		this.email = email;
		this.role = role;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ERole getRole() {
		return role;
	}

	public void setRole(ERole role) {
		this.role = role;
	}

}
