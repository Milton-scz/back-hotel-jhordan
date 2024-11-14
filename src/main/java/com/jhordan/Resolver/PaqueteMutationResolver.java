package com.jhordan.Resolver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import com.jhordan.Entity.Paquete;
import com.jhordan.Exception.DuplicateEntryException;
import com.jhordan.Repository.PaqueteRepository;


@Component
@RestController

public class PaqueteMutationResolver {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PaqueteRepository paqueteRepository;

    @MutationMapping
    public Paquete createPaquete(@Argument Paquete paquete) {
        try {
        
            return paqueteRepository.save(paquete);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEntryException("Error, paquete ya existe!");
        }
    }

    @MutationMapping
    public Paquete updatePaquete(@Argument Paquete paquete) {
        if (paquete.getPaqueteId() == null) {
            throw new IllegalArgumentException("The update request must include an ID");
        }

        LOGGER.info("Update request received: {}", paquete);

        Paquete currentPaquete = paqueteRepository.findByPaqueteId(paquete.getPaqueteId());
               

        return paqueteRepository.save(currentPaquete);
    }

    @MutationMapping
    public boolean deletePaquete(@Argument Long paqueteId) {
        LOGGER.info("Received request to delete paquete with id: {}", paqueteId);
        paqueteRepository.deleteById(paqueteId);
        return true;
    }
}
