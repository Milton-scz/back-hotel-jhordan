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

import com.jhordan.Entity.Habitacion;
import com.jhordan.Entity.PageEntity.EntityPage;
import com.jhordan.Entity.PageEntity.PageInfo;
import com.jhordan.Repository.HabitacionRepository;
import graphql.kickstart.tools.GraphQLQueryResolver;

@Component  @RestController
public class HabitacionQueryResolver implements GraphQLQueryResolver {

    @Autowired
    private HabitacionRepository habitacionRepository;

    @QueryMapping
    public EntityPage<Habitacion> getHabitaciones(@Argument int page, @Argument int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Habitacion> entityPage = habitacionRepository.findAll(pageable);
        PageInfo pageInfo = new PageInfo(entityPage.getTotalPages(), entityPage.getTotalElements(), page, size);
        return new EntityPage<>(pageInfo, entityPage.getContent());
    }

    @QueryMapping
    public Habitacion getHabitacion(@Argument Long id) {
        return habitacionRepository.findById(id).orElseThrow(() -> new RuntimeException("Habitacion no encontrada"));
    }

    @QueryMapping
    public List<Habitacion> getAllHabitaciones() {
        return habitacionRepository.findAll();
    }

}
