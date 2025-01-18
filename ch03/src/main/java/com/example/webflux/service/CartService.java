package com.example.webflux.service;

import com.example.webflux.model.entity.Cart;
import com.example.webflux.model.entity.CartItem;
import com.example.webflux.repository.CartRepository;
import com.example.webflux.repository.ItemRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CartService {
    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;

    public CartService(ItemRepository itemRepository, CartRepository cartRepository) {
        this.itemRepository = itemRepository;
        this.cartRepository = cartRepository;
    }

    public Mono<Cart> addToCart(String cartId, String itemId) {
        return this.cartRepository.findById(cartId)
                .log("found cart")
                .defaultIfEmpty(new Cart("My Cart"))
                .log("empty cart")
                .flatMap(cart -> cart.getCartItems().stream()
                        .filter(cartItem -> cartItem.getItem().getId().equals(itemId))
                        .findAny()
                        .map(cartItem -> {
                            cartItem.setQuantity(cartItem.getQuantity() + 1);
                            return Mono.just(cart);
                        })
                        .orElseGet(() -> this.itemRepository.findById(itemId)
                                    .log("fetchedItem")
                                    .map(CartItem::new)
                                    .log("cartItem")
                                    .map(cartItem -> {
                                        cart.getCartItems().add(cartItem);
                                        return cart;
                                    }).log("addCartItem")
                        ))
                .log("cartWithAnotherItem")
                .flatMap(this.cartRepository::save)
                .log("savedCart");
    }
}
