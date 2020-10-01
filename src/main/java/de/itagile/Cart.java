package de.itagile;

import java.util.HashMap;
import java.util.Map;

public class Cart {
	
	
    public Integer CartId;
    public Integer UserId;
    public String UserName;
    public Map<String,Double> Items;

    public Cart()
    {
        Items = new HashMap<String, Double>();
    }

    public Cart withId(int CartId)
    {
        this.CartId = CartId;
        return this;
    }

    public Cart withUser(int UserId, String Name)
    {
        this.UserId = UserId;
        this.UserName = Name;
        return this;
    }

    public Cart addProduct(String id, Double price)
    {
        this.Items.put(id, price);
        return this;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((CartId == null) ? 0 : CartId.hashCode());
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
		if (CartId == null) {
			if (other.CartId != null)
				return false;
		} else if (!CartId.equals(other.CartId))
			return false;
		return true;
	}
    
    

}
