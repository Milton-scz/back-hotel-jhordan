package com.jhordan.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Document(collection = "registros")
public class Registro {

    @Id
    private Long id;              
    private String metodo;        
    private String trxHash;          
    private LocalDateTime fechaCreacion;    
    private LocalDateTime fechaModificacion; 

  
    public Registro() {
    	 this.id = new Random().nextLong();
        this.fechaCreacion = LocalDateTime.now();
    }


    public Registro(String metodo, String trxHash) {
    	 this.id = new Random().nextLong();
        this.metodo = metodo;
        this.trxHash = trxHash;
        this.fechaCreacion = LocalDateTime.now();
    }

    // Método merge
    public Registro merge(Registro updateRequest) {
        Optional.ofNullable(updateRequest.getMetodo()).ifPresent(this::setMetodo);
        Optional.ofNullable(updateRequest.getTrxHash()).ifPresent(this::setTrxHash);
        this.fechaModificacion = LocalDateTime.now(); 
        return this;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public String getTrxHash() {
        return trxHash;
    }

    public void setTrxHash(String trxHash) {
        this.trxHash = trxHash;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(LocalDateTime fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
}
