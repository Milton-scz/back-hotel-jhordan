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

import com.jhordan.Entity.Registro;
import com.jhordan.Exception.DuplicateEntryException;
import com.jhordan.Exception.EntityNotFoundException;
import com.jhordan.Repository.RegistroRepository;

@Component
@RestController
@Secured("ROLE_ADMINISTRADOR")
public class RegistroMutationResolver {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RegistroRepository registroRepository;

    @MutationMapping
    public Registro createRegistro(@Argument Registro registro) {
        try {
        
            return registroRepository.save(registro);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEntryException("Error, Registro ya existe!");
        }
    }

    @MutationMapping
    public Registro updateRegistro(@Argument Registro registro) {
        if (registro.getId() == null) {
            throw new IllegalArgumentException("The update request must include an ID");
        }

        LOGGER.info("Update request received: {}", registro);

        Registro currentRegistro = registroRepository.findById(registro.getId())
                .orElseThrow(() -> new EntityNotFoundException("Registro not found"));

        // Actualiza los campos que pueden ser nulos
        if (registro.getMetodo() != null) {
            currentRegistro.setMetodo(registro.getMetodo());
        }
        if (registro.getTrxHash() != null) {
            currentRegistro.setTrxHash(registro.getTrxHash());
        }

        return registroRepository.save(currentRegistro);
    }

    @MutationMapping
    public boolean deleteRegistro(@Argument Long id) {
        LOGGER.info("Received request to delete registro with id: {}", id);
        registroRepository.deleteById(id);
        return true;
    }
}
