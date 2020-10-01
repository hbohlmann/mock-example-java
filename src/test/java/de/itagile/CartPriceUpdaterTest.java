package de.itagile;

import static org.junit.Assert.assertSame;
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
}
