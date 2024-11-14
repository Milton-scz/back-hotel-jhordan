package com.jhordan.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.jhordan.Entity.Paquete;


public interface PaqueteRepository extends MongoRepository<Paquete, Long> {
 Paquete findByPaqueteId(Long paqueteId);
}
