package com.jhordan.Entity;

import java.util.Optional;
import java.util.Random;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "habitaciones")
public class Habitacion {

	@Id
	private Long id;
	private int numeroHabitacion;
	private int capacidad;
	private String detalles;
	private double precioPorNoche;
	private Status estado;
	private Tipo tipo;
	@DBRef
	private Device device;

	public Habitacion() {
		this.id = new Random().nextLong();
	}

	public Habitacion(int numeroHabitacion, int capacidad, String detalles, double precioPorNoche, Status estado,
			Tipo tipo, Device device) {
		this.id = new Random().nextLong();
		this.numeroHabitacion = numeroHabitacion;
		this.tipo = tipo;
		this.capacidad = capacidad;
		this.detalles = detalles;
		this.precioPorNoche = precioPorNoche;
		this.estado = estado;
	}

	// Getters y Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getNumeroHabitacion() {
		return numeroHabitacion;
	}

	public void setNumeroHabitacion(int numeroHabitacion) {
		this.numeroHabitacion = numeroHabitacion;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public String getDetalles() {
		return detalles;
	}

	public void setDetalles(String detalles) {
		this.detalles = detalles;
	}

	public double getPrecioPorNoche() {
		return precioPorNoche;
	}

	public void setPrecioPorNoche(double precioPorNoche) {
		this.precioPorNoche = precioPorNoche;
	}

	public Status getEstado() {
		return estado;
	}

	public void setEstado(Status estado) {
		this.estado = estado;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public Habitacion merge(Habitacion updateRequest) {
		Optional.ofNullable(updateRequest.getNumeroHabitacion()).ifPresent(this::setNumeroHabitacion);
		Optional.ofNullable(updateRequest.getTipo()).ifPresent(this::setTipo);
		Optional.ofNullable(updateRequest.getCapacidad()).ifPresent(this::setCapacidad);
		Optional.ofNullable(updateRequest.getDetalles()).ifPresent(this::setDetalles);
		Optional.ofNullable(updateRequest.getPrecioPorNoche()).ifPresent(this::setPrecioPorNoche);
		Optional.ofNullable(updateRequest.getEstado()).ifPresent(this::setEstado);
		Optional.ofNullable(updateRequest.getDevice()).ifPresent(this::setDevice);
		return this;
	}

	public enum Tipo {
		SIMPLE, DOBLE, SUITE;
	}

	public enum Status {
		MANTENIMIENTO, DISPONIBLE, RESERVADO, OCUPADO;
	}
}
