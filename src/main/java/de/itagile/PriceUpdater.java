package de.itagile;

public interface PriceUpdater {

   Double priceForProduct(String productId);
   
   /**
    * Use carefully, avoid call with numberOfChanges == 0
    */
   void pricesChangedStats(int numberOfChanges);

}
