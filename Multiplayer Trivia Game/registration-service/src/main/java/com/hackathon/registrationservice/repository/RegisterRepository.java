package com.hackathon.registrationservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hackathon.registrationservice.entity.User;



@Repository
public interface RegisterRepository extends JpaRepository<User, String>{

	Optional<User> findByUserName(String username);

	Optional<User> findByEmail(String mail);

}