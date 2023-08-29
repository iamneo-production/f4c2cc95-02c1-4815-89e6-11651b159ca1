package com.hackathon.registrationservice.serviceimpl;

import java.util.List;
import java.util.Optional;

import com.hackathon.registrationservice.entity.Role;
import com.hackathon.registrationservice.exception.EmailDuplicateException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hackathon.registrationservice.entity.LoginRequest;
import com.hackathon.registrationservice.entity.User;
import com.hackathon.registrationservice.exception.UserNameNotValidException;
import com.hackathon.registrationservice.exception.UserAlreadyExistsException;
import com.hackathon.registrationservice.repository.RegisterRepository;
import com.hackathon.registrationservice.services.RegisterService;

@Service
public class RegisterServiceImpl implements RegisterService{

	private org.slf4j.Logger log=LoggerFactory.getLogger(RegisterServiceImpl.class);
	
	@Autowired
	RegisterRepository registerRepository;
	
	@Override
	public User saveUser(User user) {


		if(getUser(user.getUserName()))
			throw new UserAlreadyExistsException();
		else
			if(getUserByEmail(user.getEmail()))
				throw new EmailDuplicateException();
			else {
				if (user.getUserName().matches("^[a-zA-Z0-9_]{3,20}$")) {

					log.info("User saved : "+ user.getEmail() );
					user.setRole(Role.PLAYER);
					return registerRepository.save(user);
				}
				else throw new UserNameNotValidException();
			}
	}

	@Override
	public List<User> getAllUsers() {
		
		return registerRepository.findAll();
		
	}

	@Override
	public boolean getUser(String userName) {

		Optional<User> u=registerRepository.findByUserName(userName);
		if(u.isPresent()) {
			return  true;
		}
		 return false;
		
	}

	public boolean getUserByEmail(String email) {

		Optional<User> u=registerRepository.findByEmail(email);
		if(u.isPresent()) {
			return  true;
		}
		return false;

	}

	@Override
	public User userLogin(LoginRequest userCredentials) {

		try{
			User user = registerRepository.findByUserName(userCredentials.getUserName()).get();
			if(user !=null)
			{
				if(user.getPassword().equals(userCredentials.getPassword()))
					return  user;
			}
			else  return  new User();

		}
		catch (Exception e)
		{
			throw new UserNameNotValidException();

 		}

		return  new User();

	}
		
}
