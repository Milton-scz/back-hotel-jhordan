package com.jhordan.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.jhordan.Entity.Device;

public interface DeviceRepository extends MongoRepository<Device, Long> {
    Device findByUuid(String uuid);
    Boolean existsByUuid(String uuid);
}