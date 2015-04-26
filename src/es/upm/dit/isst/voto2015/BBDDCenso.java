package es.upm.dit.isst.voto2015;
import java.util.ArrayList;
import java.util.List;

import es.upm.dit.isst.voto2015.model.User;

public final class BBDDCenso {
	
	public static List<User> users = new ArrayList<User>();
	private static User user1 = new User(50619917, "Mikel", "Domaica Valiente", 1, 1, false);
	private static User user2 = new User(5457726, "Francisco", "Gonzalez Muriel", 2, 1, false);
	private static User user3 = new User(14308043, "Alejandro", "Vicente Hernando", 3, 1, false);
	
	private BBDDCenso(){
		
		
	}
	public static List<User> getUsersCenso() 
	{
		users.clear();
		users.add(user1);
        users.add(user2);
        users.add(user3);
        return users;
    }

}
