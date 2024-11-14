package com.jhordan.Entity;

import java.util.Optional;
import java.util.Random;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "devices")
public class Device {
	@Id
	  private Long id;
	  private String uuid;
	  private String nombre;
	  private String tipo;
	  private String descripcion;
	  private String status;
	  private OcupadoDisponible ocupadoDisponible;
	  
	  
	public Device() {
		 this.id = new Random().nextLong();
	}

	public Device(String uuid, String nombre, String tipo, String descripcion, String status, OcupadoDisponible ocupadoDisponible) {
		 this.id = new Random().nextLong();
		this.uuid = uuid;
		this.nombre = nombre;
		this.tipo = tipo;
		this.descripcion = descripcion;
		this.status = status;
		this.ocupadoDisponible = ocupadoDisponible;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getUuid() {
		return uuid;
	}



	public void setUuid(String uuid) {
		this.uuid = uuid;
	}



	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public String getTipo() {
		return tipo;
	}



	public void setTipo(String tipo) {
		this.tipo = tipo;
	}



	public String getDescripcion() {
		return descripcion;
	}



	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}
	
	
	  
	 public OcupadoDisponible getOcupadoDisponible() {
		return ocupadoDisponible;
	}

	public void setOcupadoDisponible(OcupadoDisponible ocupadoDisponible) {
		this.ocupadoDisponible = ocupadoDisponible;
	}

	public Device merge(Device updateRequest) {
	      Optional.ofNullable(updateRequest.getUuid()).ifPresent(this::setUuid);
	      Optional.ofNullable(updateRequest.getNombre()).ifPresent(this::setNombre);
	      Optional.ofNullable(updateRequest.getDescripcion()).ifPresent(this::setDescripcion);
	      Optional.ofNullable(updateRequest.getTipo()).ifPresent(this::setTipo);
	      Optional.ofNullable(updateRequest.getStatus()).ifPresent(this::setStatus);
	      Optional.ofNullable(updateRequest.getOcupadoDisponible()).ifPresent(this::setOcupadoDisponible);

	      return this;
	  }
	 
	public enum OcupadoDisponible {
	    DISPONIBLE, OCUPADO
	}
}
