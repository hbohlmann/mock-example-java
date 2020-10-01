package de.itagile;

import java.util.List;

public interface CartArchive {
    Cart ById(int Id);
    List<Cart> ByName(String UserName);
    Cart CreateNewCart();

}
