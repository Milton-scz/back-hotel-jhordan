package com.jhordan.Resolver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import com.jhordan.Entity.Habitacion;
import com.jhordan.Entity.Habitacion.Status;
import com.jhordan.Entity.Paquete;
import com.jhordan.Entity.Recepcion;
import com.jhordan.Entity.Recepcion.TIPO_REGISTRO;
import com.jhordan.Entity.User;
import com.jhordan.Exception.DuplicateEntryException;
import com.jhordan.Exception.EntityNotFoundException;
import com.jhordan.Repository.HabitacionRepository;
import com.jhordan.Repository.PaqueteRepository;
import com.jhordan.Repository.RecepcionRepository;
import com.jhordan.Repository.UserRepository;
import graphql.kickstart.tools.GraphQLMutationResolver;

@Component @RestController
public class RecepcionMutationResolver implements GraphQLMutationResolver {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RecepcionRepository recepcionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HabitacionRepository habitacionRepository;
    @Autowired
    private PaqueteRepository paqueteRepository;

    @MutationMapping
    public Recepcion createRecepcion(@Argument int numeroHabitacion,
    		@Argument String cedula,
    		@Argument Long paqueteId,
    		@Argument String fechaEntrada,
    		@Argument String fechaSalida,
    		@Argument float adelanto,
    		@Argument float descuento,
    		@Argument float totalAPagar,
    		@Argument TIPO_REGISTRO tipo,
    		@Argument String preferencias,
    		@Argument String observaciones,
    		@Argument String trxHash
    		) {
    	Habitacion habitacion = habitacionRepository.findByNumeroHabitacion(numeroHabitacion);
    	if(tipo == TIPO_REGISTRO.RESERVA) {
    		habitacion.setEstado(Status.RESERVADO);
    	}else if (tipo == TIPO_REGISTRO.HOSPEDAJE) {
    		habitacion.setEstado(Status.OCUPADO);
		}
    	habitacionRepository.save(habitacion);
    	 User user = userRepository.findByCedula(cedula);
    	 Paquete paquete = paqueteRepository.findByPaqueteId(paqueteId);
    	 Recepcion recepcion =  new Recepcion();
    	 recepcion.setHabitacion(habitacion);
    	 recepcion.setUser(user);
    	 recepcion.setPaquete(paquete);
    	 recepcion.setFechaEntrada(fechaEntrada);
    	 recepcion.setFechaSalida(fechaSalida);
    	 recepcion.setAdelanto(adelanto);
    	 recepcion.setDescuento(descuento);
    	 recepcion.setTotalAPagar(totalAPagar);
    	 recepcion.setTipo(tipo);
    	 recepcion.setTerminado(false);
    	 if( preferencias!=null) {
    		 recepcion.setPreferencias(preferencias);
    	 }
    	 recepcion.setObservaciones(observaciones);
    	 recepcion.setTrxHash(trxHash);
    	
        try {
            return recepcionRepository.save(recepcion);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEntryException("Error, ya existe una recepción con esta información!");
        }
    }

    @MutationMapping 
    public Recepcion updateRecepcion(@Argument Long id) {
        // Find the current 'Recepcion'
        Recepcion currentRecepcion = recepcionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Recepcion not found"));
        
        // Find the associated 'Habitacion' by room number
        Habitacion habitacion = habitacionRepository.findByNumeroHabitacion(currentRecepcion.getHabitacion().getNumeroHabitacion());

        // Check room type and update its status accordingly
        if(habitacion.getEstado() == Habitacion.Status.RESERVADO) {
            habitacion.setEstado(Status.OCUPADO);
            habitacionRepository.save(habitacion);
            
            currentRecepcion.setHabitacion(habitacion);
            currentRecepcion.setTerminado(false);
        } else {
            habitacion.setEstado(Status.DISPONIBLE);
            habitacionRepository.save(habitacion);
            
            currentRecepcion.setHabitacion(habitacion);
            currentRecepcion.setTerminado(true);
        }

        // Save and return the updated 'Recepcion'
        return recepcionRepository.save(currentRecepcion);
    }


    @MutationMapping
    public boolean deleteRecepcion(@Argument Long id) {
        LOGGER.info("Solicitud recibida para eliminar recepción con id: {}", id);
        recepcionRepository.deleteById(id);
        return true;
    }
}
