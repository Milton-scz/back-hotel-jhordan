package com.jhordan.Entity;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

@Document(collection = "users")
public class User {
  @Id
  private Long id;
  private String name;
  private String cedula;
  private String telefono;
  private String direccion;
  private String email;
  private String password;
  private Role role;
  public User() {
	  this.id = new Random().nextLong();
  }

  public User(String name,String cedula, String email, String telefono, String direccion, String password,Role role) {
	this.id = new Random().nextLong();
    this.name = name;
    this.cedula = cedula;
    this.telefono = telefono;
    this.direccion = direccion;
    this.email = email;
    this.password = password;
    this.role = role;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

public String getCedula() {
	return cedula;
}

public void setCedula(String cedula) {
	this.cedula = cedula;
}

public String getTelefono() {
	return telefono;
}

public void setTelefono(String telefono) {
	this.telefono = telefono;
}

public String getDireccion() {
	return direccion;
}

public void setDireccion(String direccion) {
	this.direccion = direccion;
}



public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }
  @Override
  public String toString() {
      return new ToStringBuilder(this)
              .append("id", id)
              .append("email", email)
              .append("role", role)
              .toString();
  }
  public User merge(User updateRequest) {
	  Optional.ofNullable(updateRequest.getName()).ifPresent(this::setName);
	  Optional.ofNullable(updateRequest.getCedula()).ifPresent(this::setCedula);
	  Optional.ofNullable(updateRequest.getTelefono()).ifPresent(this::setTelefono);
	  Optional.ofNullable(updateRequest.getDireccion()).ifPresent(this::setDireccion);
      Optional.ofNullable(updateRequest.getEmail()).ifPresent(this::setEmail);
      Optional.ofNullable(updateRequest.getPassword()).ifPresent(this::setPassword);
      Optional.ofNullable(updateRequest.getRole()).ifPresent(this::setRole);

      return this;
  }
  
  public enum Role implements GrantedAuthority {
      CLIENTE,
      EMPLEADO,
      ADMINISTRADOR;

      @Override
      public String getAuthority() {
          return "ROLE_" + this.name();
      }
  }


}
