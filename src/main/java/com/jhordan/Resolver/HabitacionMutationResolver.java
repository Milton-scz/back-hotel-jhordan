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
import com.jhordan.Entity.Habitacion;
import com.jhordan.Entity.Device.OcupadoDisponible;

import com.jhordan.Exception.DuplicateEntryException;
import com.jhordan.Exception.EntityNotFoundException;
import com.jhordan.Repository.DeviceRepository;
import com.jhordan.Repository.HabitacionRepository;
import graphql.kickstart.tools.GraphQLMutationResolver;

@Component
@RestController

public class HabitacionMutationResolver implements GraphQLMutationResolver {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private HabitacionRepository habitacionRepository;
	@Autowired
	private DeviceRepository deviceRepository;

	@MutationMapping
	public Habitacion createHabitacion(@Argument Habitacion habitacion) {
		if (!habitacionRepository.existsByNumeroHabitacion(habitacion.getNumeroHabitacion())) {
			try {
				Device device = deviceRepository.findByUuid(habitacion.getDevice().getUuid());
				device.setOcupadoDisponible(OcupadoDisponible.OCUPADO);
				deviceRepository.save(device);
				return habitacionRepository.save(habitacion);
			} catch (DataIntegrityViolationException e) {
				throw new DuplicateEntryException("Error, la habitación ya existe!");
			}
		} else {
			throw new DuplicateEntryException("Error, la habitación ya existe!");
		}
	}

	@MutationMapping
	public Habitacion updateHabitacion(@Argument Habitacion habitacion) {
		if (habitacion.getId() == null) {
			throw new IllegalArgumentException("La solicitud de actualización debe incluir un ID");
		}
		if (habitacion.getNumeroHabitacion() == 0 && habitacion.getDetalles() == null) {
			throw new IllegalArgumentException(
					"La solicitud de actualización debe incluir valores para el numero o la descripción de la habitación");
		}

		LOGGER.info("Solicitud de actualización recibida: {}", habitacion);
		Device device = deviceRepository.findByUuid(habitacion.getDevice().getUuid());
		Habitacion currentHabitacion = habitacionRepository.findById(habitacion.getId())
				.orElseThrow(() -> new EntityNotFoundException("Habitación no encontrada"));
			habitacion.setDevice(device);
		// Actualiza solo los campos que han sido proporcionados
		currentHabitacion.merge(habitacion);

		return habitacionRepository.save(currentHabitacion);
	}

	@MutationMapping
	public boolean deleteHabitacion(@Argument Long id) {
		LOGGER.info("Recibida solicitud para eliminar habitación con id: {}", id);
		habitacionRepository.deleteById(id);
		return true;
	}
}
