package com.jhordan.Resolver;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import com.jhordan.Entity.Device;
import com.jhordan.Entity.PageEntity.EntityPage;
import com.jhordan.Entity.PageEntity.PageInfo;
import com.jhordan.Repository.DeviceRepository;

import graphql.kickstart.tools.GraphQLQueryResolver;

@Component
@RestController
public class DeviceQueryResolver implements GraphQLQueryResolver {

    @Autowired
    private DeviceRepository deviceRepository;

    @QueryMapping 
    public EntityPage<Device> getDevices(@Argument int page, @Argument int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Device> entityPage = deviceRepository.findAll(pageable);
        PageInfo pageInfo = new PageInfo(entityPage.getTotalPages(), entityPage.getTotalElements(), page, size);
        return new EntityPage<Device>(pageInfo, entityPage.getContent());
    }
    
    @QueryMapping 
    public Optional<Device> getDevice(@Argument Long Id) {
        return deviceRepository.findById(Id);
    }
    
    @QueryMapping 
    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }
}
