package com.jhordan.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;


import com.jhordan.Entity.Recepcion;
import com.jhordan.Entity.User;

public interface RecepcionRepository extends MongoRepository<Recepcion, Long> {
	 Recepcion findTopByUserOrderByIdDesc(User user);
}