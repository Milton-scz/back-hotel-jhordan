package com.jhordan.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.jhordan.Entity.Registro;

public interface RegistroRepository extends MongoRepository<Registro, Long> {
    Registro findByTrxHash(String trxHash);
}
