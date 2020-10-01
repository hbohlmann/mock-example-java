package de.itagile;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

public class CartPriceUpdaterTest {

	CartArchive archiveMock;
	PriceUpdates priceMock;
	CartPriceUpdater objectUnderTest;

	@Before
	public void setUp() {
		archiveMock = mock(CartArchive.class);
		priceMock = mock(PriceUpdates.class);

		objectUnderTest = new CartPriceUpdater(archiveMock, priceMock);
	}

	@Test
	public void TestCreateNewCartIfNotExists() throws Exception {
        objectUnderTest = new CartPriceUpdater(archiveMock, null);
        Cart newCart = new Cart();

        when(archiveMock.ById(815)).thenReturn(null);
        when(archiveMock.CreateNewCart()).thenReturn(newCart);

        Cart returnedCart = objectUnderTest.RecalculateCart(815);
        assertSame(newCart, returnedCart);
	}
	
	@Test
	public void TestEmptyCartNeverCallsPriceForProduct() throws Exception {
        Cart cart = new Cart().withId(1);

        when(archiveMock.ById(1)).thenReturn(cart);
        Cart returnedCart = objectUnderTest.RecalculateCart(1);
        assertEquals(cart, returnedCart);

        verify(priceMock, times(0)).PriceForProduct(anyString());		
	}
	
	@Test
	public void TestCartWithOneItemIsUpdated() throws Exception {
        Cart cart = new Cart().withId(1).addProduct("Brot", 1.69);

        when(archiveMock.ById(1)).thenReturn(cart);
        when(priceMock.PriceForProduct("Brot")).thenReturn(1.79);
        Cart returnedCart = objectUnderTest.RecalculateCart(1);

        assertEquals((Double)1.79, returnedCart.Items.get("Brot"));
	}
	
	@Test
	public void TestCartWithoutChangeDoesNotCallStats() throws Exception {
        Cart cart = new Cart().withId(1).addProduct("Brot", 1.69);

        when(archiveMock.ById(1)).thenReturn(cart);
        when(priceMock.PriceForProduct("Brot")).thenReturn(1.69);
        objectUnderTest.RecalculateCart(1);

        verify(priceMock, times(0)).PricesChangedStats(anyInt());		
	}

	@Test
	public void TestCartWithChangesCallsStats() throws Exception {
        Cart cart = new Cart().withId(1).addProduct("Brot", 1.69);

        when(archiveMock.ById(1)).thenReturn(cart);
        when(priceMock.PriceForProduct("Brot")).thenReturn(9.61);
        objectUnderTest.RecalculateCart(1);

        verify(priceMock, times(1)).PricesChangedStats(1);		
	}
	
	@Test
	public void TestCartCanHandleMultipleEntries() throws Exception {
        Cart cart = new Cart().withId(1)
                .addProduct("Brot", 1.69)
                .addProduct("Butter", 1.19)
                .addProduct("Marmelade", 2.69);
		
        when(archiveMock.ById(1)).thenReturn(cart);
        when(priceMock.PriceForProduct("Brot")).thenReturn(1.69);
        when(priceMock.PriceForProduct("Butter")).thenReturn(1.29);
        when(priceMock.PriceForProduct("Marmelade")).thenReturn(2.99);
        
        Cart returnedCart = objectUnderTest.RecalculateCart(1);

        assertEquals((Double)1.69, returnedCart.Items.get("Brot"));
        assertEquals((Double)1.29, returnedCart.Items.get("Butter"));
        assertEquals((Double)2.99, returnedCart.Items.get("Marmelade"));
        
	}
	
	@Test
	public void TestProductWithoutPrice() throws Exception {
        Cart cart = new Cart().withId(1).addProduct("UNKNOWN", 9.99);
		
        when(archiveMock.ById(1)).thenReturn(cart);
        when(priceMock.PriceForProduct(anyString())).thenThrow(PriceNotFound.class);
        Cart returnedCart = objectUnderTest.RecalculateCart(1);

        assertEquals((Double)0.00, returnedCart.Items.get("UNKNOWN"));
        verify(priceMock, times(0)).PricesChangedStats(anyInt());		
	}
}
