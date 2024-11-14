package com.jhordan.Resolver;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import com.jhordan.Entity.Recepcion;
import com.jhordan.Entity.User;
import com.jhordan.Entity.PageEntity.EntityPage;
import com.jhordan.Entity.PageEntity.PageInfo;
import com.jhordan.Exception.EntityNotFoundException;
import com.jhordan.Repository.RecepcionRepository;
import com.jhordan.Repository.UserRepository;

import graphql.kickstart.tools.GraphQLQueryResolver;

@Component  @RestController
public class RecepcionQueryResolver implements GraphQLQueryResolver {

    @Autowired
    private RecepcionRepository recepcionRepository;
    @Autowired
    private UserRepository userRepository;
    @QueryMapping
    public EntityPage<Recepcion> getRecepciones(@Argument int page, @Argument int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Recepcion> entityPage = recepcionRepository.findAll(pageable);
        PageInfo pageInfo = new PageInfo(entityPage.getTotalPages(), entityPage.getTotalElements(), page, size);
        return new EntityPage<>(pageInfo, entityPage.getContent());
    }

    @QueryMapping
    public Recepcion getRecepcion(@Argument Long id) {
        return recepcionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recepción no encontrada"));
    }
    
    @QueryMapping
    public Recepcion getRecepcionByUser(@Argument String cedula) {
    	User user = userRepository.findByCedula(cedula);
        return recepcionRepository.findTopByUserOrderByIdDesc(user);
                
    }
    
    @QueryMapping
    public List<Recepcion> getAllRecepciones() {
        return recepcionRepository.findAll();
                
    }
}
