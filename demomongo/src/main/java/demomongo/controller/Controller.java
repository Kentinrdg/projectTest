package demomongo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*", allowCredentials = "false", allowedHeaders = "*")
@RestController
@Api
public class Controller {

	@Autowired
	public Controller() {

	}

	@ApiOperation(value = "Dit bonjour")
	@GetMapping("/hello")
	public String sayHello() {
		return "Hello World!";
	}

	@RequestMapping(value = "/custom", method = RequestMethod.POST)
	public String custom() {
		return "custom";
	}

}
