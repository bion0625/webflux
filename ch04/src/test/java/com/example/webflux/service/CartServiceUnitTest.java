package com.example.webflux.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.webflux.model.entity.Cart;
import com.example.webflux.model.entity.CartItem;
import com.example.webflux.model.entity.Item;
import com.example.webflux.repository.CartRepository;
import com.example.webflux.repository.ItemRepository;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
class CartServiceUnitTest {

	CartService cartService;

	@MockBean
	private ItemRepository itemRepository;
	@MockBean
	private CartRepository cartRepository;

	@BeforeEach
	void setUp() {
		Item sampleItem = new Item("item1", "TV tray", "Alf TV tray", 19.99);
		CartItem sampleCartItem = new CartItem(sampleItem);
		Cart sampleCart = new Cart("My Cart", Collections.singletonList(sampleCartItem));

		when(cartRepository.findById(anyString())).thenReturn(Mono.empty());
		when(itemRepository.findById(anyString())).thenReturn(Mono.just(sampleItem));
		when(cartRepository.save(any(Cart.class))).thenReturn(Mono.just(sampleCart));

		cartService = new CartService(itemRepository, cartRepository);
	}

	@Test
	void addItemToEmptyCartShouldProduceOneCartItem() {
		cartService.addToCart("My Cart", "item1").as(StepVerifier::create).expectNextMatches(cart -> {
			assertThat(cart.getCartItems()).extracting(CartItem::getQuantity).containsExactlyInAnyOrder(1);

			assertThat(cart.getCartItems()).extracting(CartItem::getItem)
					.containsExactly(new Item("item1", "TV tray", "Alf TV tray", 19.99));

			return true;
		}).verifyComplete();
	}

	@Test
	void alternativeWayToTest() {
		StepVerifier.create(cartService.addToCart("My Cart", "item1")).expectNextMatches(cart -> {
			assertThat(cart.getCartItems()).extracting(CartItem::getQuantity).containsExactlyInAnyOrder(1);

			assertThat(cart.getCartItems()).extracting(CartItem::getItem)
					.containsExactly(new Item("item1", "TV tray", "Alf TV tray", 19.99));

			return true;
		}).verifyComplete();
	}

}
