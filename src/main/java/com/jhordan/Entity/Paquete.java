package com.jhordan.Entity;

import java.util.Optional;
import java.util.Random;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "paquetes")
public class Paquete {
@Id
private  Long paqueteId;
private String nombre;
private Float precio;
private String detalle;


public Paquete() {
	 this.paqueteId = new Random().nextLong();
}


public Paquete(String nombre, Float precio, String detalle) {
	 this.paqueteId = new Random().nextLong();
	 this.nombre = nombre;
	this.precio = precio;
	this.detalle = detalle;
}


public Long getPaqueteId() {
	return paqueteId;
}


public void setPaqueteId(Long paqueteId) {
	this.paqueteId = paqueteId;
}


public Float getPrecio() {
	return precio;
}


public void setPrecio(Float precio) {
	this.precio = precio;
}


public String getDetalle() {
	return detalle;
}


public void setDetalle(String detalle) {
	this.detalle = detalle;
}


public String getNombre() {
	return nombre;
}


public void setNombre(String nombre) {
	this.nombre = nombre;
}


public Paquete merge(Paquete updateRequest) {
    Optional.ofNullable(updateRequest.getPaqueteId()).ifPresent(this::setPaqueteId);
    Optional.ofNullable(updateRequest.getPrecio()).ifPresent(this::setPrecio);
    Optional.ofNullable(updateRequest.getDetalle()).ifPresent(this::setDetalle);
    Optional.ofNullable(updateRequest.getNombre()).ifPresent(this::setNombre);
    return this;
}

}
