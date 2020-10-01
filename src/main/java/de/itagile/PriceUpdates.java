package de.itagile;

public interface PriceUpdates {

   Double PriceForProduct(String productId);
   void PricesChangedStats(int numberOfChanges);

}
