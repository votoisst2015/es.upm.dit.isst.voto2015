package es.upm.dit.isst.voto2015.model;
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue; 
import javax.persistence.GenerationType; 
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;

@Entity
public class User implements Serializable {
	private static final long serialVersionUID = 1L; 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Key id;
	
	private int dni;
	private String nombre;
	private String apellidos; 
	private int sector; 
	private int ponderacion; 
	boolean votacionElectronica;
	
	public User(int dni, String nombre, String apellidos, int sector, int ponderacion, boolean votacionElectronica) {
		this.dni = dni;
		this.nombre = nombre;
		this.apellidos = apellidos; 
		this.sector = sector; 
		this.ponderacion = ponderacion; 
		this.votacionElectronica = votacionElectronica;
	}
	public int getDni() { 
		return dni;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getApellidos() {
		return apellidos;
	}
	
	public void setApellidos(String apellidos) {
			this.apellidos = apellidos; 
	}
	
	public int getSector() {
		return sector;
	}
	
	public void setSector(int sector) {
		this.sector = sector; 
	}
	
	public int getPonderacion() {
		return ponderacion;
	}
	
	public void setPonderacion(int ponderacion) {
		this.ponderacion = ponderacion;
	}
	
	public boolean votacionElectronica() {
		return votacionElectronica;
	}
	
	public void setVotacionElectronica(boolean votacionElectronica) { 
		this.votacionElectronica = votacionElectronica;
	} 
}