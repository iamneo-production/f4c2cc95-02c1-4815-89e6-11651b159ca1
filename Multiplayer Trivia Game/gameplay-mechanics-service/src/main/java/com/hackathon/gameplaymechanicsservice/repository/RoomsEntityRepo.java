package com.hackathon.gameplaymechanicsservice.repository;

import com.hackathon.gameplaymechanicsservice.entity.RoomsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomsEntityRepo extends JpaRepository<RoomsEntity,String> {
}
