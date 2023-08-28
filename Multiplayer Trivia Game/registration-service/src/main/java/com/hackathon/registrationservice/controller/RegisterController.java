package com.hackathon.registrationservice.controller;

import java.util.List;
import java.util.Optional;

import com.hackathon.registrationservice.exception.UserNameNotValidException;
import com.hackathon.registrationservice.repository.RegisterRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hackathon.registrationservice.entity.LoginRequest;
import com.hackathon.registrationservice.entity.Role;
import com.hackathon.registrationservice.entity.User;
import com.hackathon.registrationservice.services.RegisterService;



@RestController
@RequestMapping("/registration")
public class RegisterController {
	
	private org.slf4j.Logger log=LoggerFactory.getLogger(RegisterController.class);
	
	@Autowired
	private RegisterService registerService;

	@Autowired
	private RegisterRepository registerRepository;
	
	@PostMapping("/register")
	public ResponseEntity<User> createUser(@RequestBody User user){
		User u=registerService.saveUser(user);
		log.info("Created");
		return ResponseEntity.status(HttpStatus.CREATED).body(u);
	}
	
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers(){
		List<User> allUsers= registerService.getAllUsers();
		return ResponseEntity.ok(allUsers);
	}
	
	
	@PostMapping("/login")
	public Role userLogin(@RequestBody LoginRequest userCredentials){
		
		User user=registerService.userLogin(userCredentials);
		
		
		return user.getRole();
	}

	@GetMapping("/getUserID/{email}")
	public int getUserIdByEmail(@PathVariable("email") String email)
	{
		Optional<User> user = registerRepository.findByUserName(email);
		if(user.isPresent())

			return  user.get().getUserId();
		else throw new UserNameNotValidException();
	}
}
