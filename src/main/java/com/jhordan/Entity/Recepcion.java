package com.jhordan.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Optional;
import java.util.Random;

@Document(collection = "recepciones")
public class Recepcion {

    @Id
    private Long id;     
    @DBRef
    private Habitacion habitacion;
    @DBRef
    private User user;
    @DBRef
    private Paquete paquete;
    private String fechaEntrada;
    private String fechaSalida;
    private float adelanto;
    private float descuento;
    private float totalAPagar;
    private String preferencias;
    private String observaciones;
    private String trxHash;
    private  TIPO_REGISTRO tipo;
    private boolean terminado;

    // Constructor vacío (requerido)
    public Recepcion() {
    	 this.id = new Random().nextLong();
    }

    // Constructor con parámetros
    public Recepcion(Habitacion habitacion, User user, Paquete paquete,String fechaEntrada,  String fechaSalida, float adelanto, float descuento,  float totalAPagar, String preferencias, String observaciones, String trxHash, TIPO_REGISTRO tipo, boolean terminado) {
    	 this.id = new Random().nextLong();
        this.habitacion = habitacion;
        this.user = user;
        this.paquete = paquete;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.adelanto = adelanto;
        this.descuento = descuento;
        this.totalAPagar = totalAPagar;
        this.preferencias = preferencias;
        this.observaciones = observaciones;
        this.trxHash = trxHash;
    this.tipo = tipo;
    this.terminado = terminado;
    }

    // Método merge
    public Recepcion merge(Recepcion updateRequest) {
        Optional.ofNullable(updateRequest.getHabitacion()).ifPresent(this::setHabitacion);
        Optional.ofNullable(updateRequest.getUser()).ifPresent(this::setUser);
        Optional.ofNullable(updateRequest.getPaquete()).ifPresent(this::setPaquete);
        Optional.ofNullable(updateRequest.getFechaEntrada()).ifPresent(this::setFechaEntrada);
        Optional.ofNullable(updateRequest.getFechaSalida()).ifPresent(this::setFechaSalida);
        Optional.ofNullable(updateRequest.getAdelanto()).ifPresent(this::setAdelanto);
        Optional.ofNullable(updateRequest.getDescuento()).ifPresent(this::setDescuento);
        Optional.ofNullable(updateRequest.getTotalAPagar()).ifPresent(this::setTotalAPagar);
        Optional.ofNullable(updateRequest.getPreferencias()).ifPresent(this::setPreferencias);
        Optional.ofNullable(updateRequest.getObservaciones()).ifPresent(this::setObservaciones);
        Optional.ofNullable(updateRequest.getTrxHash()).ifPresent(this::setTrxHash);
        Optional.ofNullable(updateRequest.getTipo()).ifPresent(this::setTipo);
        Optional.ofNullable(updateRequest.isTerminado()).ifPresent(this::setTerminado);
        return this;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    
    public Paquete getPaquete() {
		return paquete;
	}

	public void setPaquete(Paquete paquete) {
		this.paquete = paquete;
	}

	public String getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(String fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public String getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(String fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public float getAdelanto() {
        return adelanto;
    }

    public void setAdelanto(float adelanto) {
        this.adelanto = adelanto;
    }

    public float getDescuento() {
        return descuento;
    }

    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }

    public float getTotalAPagar() {
        return totalAPagar;
    }

    public void setTotalAPagar(float totalAPagar) {
        this.totalAPagar = totalAPagar;
    }

    public String getPreferencias() {
    	return preferencias;
    }

    public void setPreferencias(String preferencias) {
    	this.preferencias = preferencias;
    }
    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getTrxHash() {
        return trxHash;
    }

    public void setTrxHash(String trxHash) {
        this.trxHash = trxHash;
    }
    
    
    public TIPO_REGISTRO getTipo() {
		return tipo;
	}

	public void setTipo(TIPO_REGISTRO tipo) {
		this.tipo = tipo;
	}


	public boolean isTerminado() {
		return terminado;
	}

	public void setTerminado(boolean terminado) {
		this.terminado = terminado;
	}


	public enum TIPO_REGISTRO {
		HOSPEDAJE, RESERVA;
	}
}

