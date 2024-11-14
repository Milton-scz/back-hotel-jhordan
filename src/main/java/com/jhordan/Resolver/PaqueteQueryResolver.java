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
import com.jhordan.Entity.Paquete;
import com.jhordan.Entity.PageEntity.EntityPage;
import com.jhordan.Entity.PageEntity.PageInfo;

import com.jhordan.Repository.PaqueteRepository;
import graphql.kickstart.tools.GraphQLQueryResolver;

@Component
@RestController
public class PaqueteQueryResolver implements GraphQLQueryResolver {

    @Autowired
    private PaqueteRepository paqueteRepository;

    @QueryMapping
    public EntityPage<Paquete> getPaquetes(@Argument int page, @Argument int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Paquete> entityPage = paqueteRepository.findAll(pageable);
        PageInfo pageInfo = new PageInfo(entityPage.getTotalPages(), entityPage.getTotalElements(), page, size);
        return new EntityPage<>(pageInfo, entityPage.getContent());
    }

    @QueryMapping
    public Paquete getPaquete(@Argument Long paqueteId) {
        return paqueteRepository.findByPaqueteId(paqueteId);
               
    }
    
    @QueryMapping
    public List<Paquete> getAllPaquetes() {
        return paqueteRepository.findAll();
               
    }
}
