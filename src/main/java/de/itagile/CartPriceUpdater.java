package de.itagile;

import java.util.HashMap;
import java.util.Map;

public class CartPriceUpdater {

    private CartRepository archive;
    private PriceUpdater priceUpdater;

    public CartPriceUpdater(CartRepository archive, PriceUpdater priceUpdater)
    {
        this.archive = archive;
        this.priceUpdater = priceUpdater;
    }

    /**
     * Loads a Cart and updates all prices.
     * If a price has changed, price-stats are updated.
     * Creates a new cart if it does not exists.
     */
    public Cart recalculateCart(int cartId)
    {
    	var cartToCheck = archive.byId(cartId);
        Map<String, Double> changedPrices = new HashMap<String, Double>();

        if(cartToCheck == null)
        {
        	// What to do?
        }

        for (String idOfProduct : cartToCheck.items.keySet()) {
        	// Some Updates?
        };
        
        // Price-Stats Update, where is it called?
        

        return cartToCheck;
    }

}
