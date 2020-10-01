package de.itagile;

import java.util.List;

public interface CartRepository {
	
    Cart byId(int Id);
    
    List<Cart> byName(String username);
    
    Cart createNewCart();

}
