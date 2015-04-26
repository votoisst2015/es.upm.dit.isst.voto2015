package es.upm.dit.isst.voto2015.dao;
import java.util.List;

import es.upm.dit.isst.voto2015.model.*;

public interface VotoDAO {
	public List<User> listUsers();
	public void add (int dni, String nombre, String apellidos, int sector, int ponderacion);
	public List<User> getUsers(int dni); 
	public void remove (int dni);
}