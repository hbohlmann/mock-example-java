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
            return archive.createNewCart();
        }

        int changedPriceCount = 0;
        
        for (String idOfProduct : cartToCheck.items.keySet()) {
            try
            {
                var newPrice = priceUpdater.priceForProduct(idOfProduct);
                if (!newPrice.equals(cartToCheck.items.get(idOfProduct)))
                {
                    changedPrices.put(idOfProduct, newPrice);
                    changedPriceCount++;
                }
            }
            catch (PriceNotFound ex)
            {
               changedPrices.put(idOfProduct, 0.0);
            }
        };
        
        if(changedPriceCount > 0)
        {
            priceUpdater.pricesChangedStats(changedPrices.size());
        }

        changedPrices.forEach((var idOfProduct,var priceOfProduct) -> {
            cartToCheck.items.remove(idOfProduct);
            cartToCheck.items.put(idOfProduct, priceOfProduct);
        });

        return cartToCheck;
    }

}
