package com.jhordan.Resolver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import com.jhordan.Entity.Device;
import com.jhordan.Exception.DuplicateEntryException;
import com.jhordan.Exception.EntityNotFoundException;
import com.jhordan.Repository.DeviceRepository;
import graphql.kickstart.tools.GraphQLMutationResolver;

@Component
@RestController
public class DeviceMutationResolver implements GraphQLMutationResolver {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DeviceRepository deviceRepository;

    @MutationMapping 
    public Device createDevice(@Argument Device device) {
        if (!deviceRepository.existsByUuid(device.getUuid())) {
            try {
            	if (device.getStatus() == null) {
                    device.setStatus("ON");
                }
                return deviceRepository.save(device);
            } catch (DataIntegrityViolationException e) {
                throw new DuplicateEntryException("Error, Device ID already exists!");
            }
        } else {
            throw new DuplicateEntryException("Error, Device ID already exists!");
        }
    }

    @MutationMapping 
    public Device updateDevice(@Argument Device device) {
        if (device.getId() == null) {
            throw new IllegalArgumentException("The update request must include an ID");
        }
        if (device.getNombre() == null && device.getStatus() == null) {
            throw new IllegalArgumentException("The update request must include values for either the device name or status");
        }

        LOGGER.info("Update request received: {}", device);

        Device currentDevice = deviceRepository.findById(device.getId())
                .orElseThrow(() -> new EntityNotFoundException("Device not found"));

        currentDevice.merge(device);

        return deviceRepository.save(currentDevice);
    }

    @MutationMapping 
    public boolean deleteDevice(@Argument Long id) {
        LOGGER.info("Received request to delete device with id: {}",id);
        deviceRepository.deleteById(id);
        return true;
    }
}
