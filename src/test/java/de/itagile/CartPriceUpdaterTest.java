package de.itagile;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

public class CartPriceUpdaterTest {

	private CartRepository archiveMock;
	private PriceUpdater priceMock;
	private CartPriceUpdater objectUnderTest;

	@Before
	public void setUp() {
		archiveMock = mock(CartRepository.class);
		priceMock = mock(PriceUpdater.class);

		objectUnderTest = new CartPriceUpdater(archiveMock, priceMock);
	}

	@Test
	public void testCreateNewCartIfNotExists() throws Exception {
        objectUnderTest = new CartPriceUpdater(archiveMock, null);
        Cart newCart = new Cart();

        // Stubs?

        Cart returnedCart = objectUnderTest.recalculateCart(815);
        assertSame(newCart, returnedCart);
	}
	
	@Test
	public void testEmptyCartNeverCallsPriceForProduct() throws Exception {
        Cart cart = new Cart().withId(1);

        // Stubs?

        Cart returnedCart = objectUnderTest.recalculateCart(1);
        assertEquals(cart, returnedCart);

        // Verify-Step?
	}
	
	@Test
	public void testCartWithOneItemIsUpdated() throws Exception {
        Cart cart = new Cart().withId(1).addProduct("Brot", 1.69);

        // Stubs?

        Cart returnedCart = objectUnderTest.recalculateCart(1);
        assertEquals((Double)1.79, returnedCart.items.get("Brot"));
	}
	
	@Test
	public void testCartWithoutChangeDoesNotCallStats() throws Exception {
        Cart cart = new Cart().withId(1).addProduct("Brot", 1.69);

        // Stubs?

        objectUnderTest.recalculateCart(1);

        // Verify-Step?
	}

	@Test
	public void testCartWithChangesCallsStats() throws Exception {
        Cart cart = new Cart().withId(1).addProduct("Brot", 1.69);

        // Stubs?

        objectUnderTest.recalculateCart(1);

        // Verify-Step?
	}
	
	@Test
	public void testCartCanHandleMultipleEntries() throws Exception {
        Cart cart = new Cart().withId(1)
                .addProduct("Brot", 1.69)
                .addProduct("Butter", 1.19)
                .addProduct("Marmelade", 2.69);
		
        // Many Stubs?
        
        Cart returnedCart = objectUnderTest.recalculateCart(1);

        assertEquals((Double)1.69, returnedCart.items.get("Brot"));
        assertEquals((Double)1.29, returnedCart.items.get("Butter"));
        assertEquals((Double)2.99, returnedCart.items.get("Marmelade"));
        
	}
	
	@Test
	public void testProductWithoutPrice() throws Exception {
        Cart cart = new Cart().withId(1).addProduct("UNKNOWN", 9.99);
		
        // Many Stubs?

        Cart returnedCart = objectUnderTest.recalculateCart(1);

        assertEquals((Double)0.00, returnedCart.items.get("UNKNOWN"));
        
        // Verify? Check if Price Update is not called.
	}
}
