package de.itagile;

import java.util.HashMap;
import java.util.Map;

public class Cart {
	
	
    public Integer cartId;
    public Integer userId;
    public String userName;
    public Map<String,Double> items;

    public Cart()
    {
        items = new HashMap<String, Double>();
    }

    public Cart withId(int cartId)
    {
        this.cartId = cartId;
        return this;
    }

    public Cart withUser(int userId, String name)
    {
        this.userId = userId;
        this.userName = name;
        return this;
    }

    public Cart addProduct(String id, Double price)
    {
        this.items.put(id, price);
        return this;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cartId == null) ? 0 : cartId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cart other = (Cart) obj;
		if (cartId == null) {
			if (other.cartId != null)
				return false;
		} else if (!cartId.equals(other.cartId))
			return false;
		return true;
	}
    
    

}
