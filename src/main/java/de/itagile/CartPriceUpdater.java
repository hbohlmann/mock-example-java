package de.itagile;

import java.util.HashMap;
import java.util.Map;

public class CartPriceUpdater {

    private CartArchive ArchiveService;
    private PriceUpdates PriceService;

    public CartPriceUpdater(CartArchive Archive, PriceUpdates Price)
    {
        ArchiveService = Archive;
        PriceService = Price;
    }

    public Cart RecalculateCart(int CartId)
    {
    	var cartToCheck = ArchiveService.ById(CartId);
        Map<String, Double> changedPrices = new HashMap<String, Double>();

        if(cartToCheck == null)
        {
            return ArchiveService.CreateNewCart();
        }

        int changedPriceCount = 0;
        
        for (String idOfProduct : cartToCheck.Items.keySet()) {
            try
            {
                var newPrice = PriceService.PriceForProduct(idOfProduct);
                if (!newPrice.equals(cartToCheck.Items.get(idOfProduct)))
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
            PriceService.PricesChangedStats(changedPrices.size());
        }
        changedPrices.forEach((var idOfProduct,var priceOfProduct) -> {
            cartToCheck.Items.remove(idOfProduct);
            cartToCheck.Items.put(idOfProduct, priceOfProduct);
        });

        return cartToCheck;
    }

}
