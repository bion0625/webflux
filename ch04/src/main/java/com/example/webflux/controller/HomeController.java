package com.example.webflux.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.result.view.Rendering;

import com.example.webflux.model.entity.Cart;
import com.example.webflux.repository.CartRepository;
import com.example.webflux.service.CartService;
import com.example.webflux.service.InventoryService;

import reactor.core.publisher.Mono;

@Controller
public class HomeController {

    private CartService cartService;
    private InventoryService inventoryService;

    public HomeController(CartService cartService, InventoryService inventoryService) {
        this.cartService = cartService;
        this.inventoryService = inventoryService;
    }

    @GetMapping
    Mono<Rendering> home() {
        return Mono.just(Rendering.view("home.html")
                        .modelAttribute("items", this.inventoryService.getInventory())
                        .modelAttribute("cart", this.cartService.getCart("My Cart")
                                .defaultIfEmpty(new Cart("My Cart")))
                .build());
    }

    @PostMapping("/add/{id}")
    Mono<String> addToCart(@PathVariable String id) {
        return this.cartService.addToCart("My Cart", id)
                .thenReturn("redirect:/");
    }

    @GetMapping("/search")
    Mono<Rendering> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam boolean useAnd
    ) {
        return Mono.just(Rendering.view("home.html")
                        .modelAttribute("items", this.inventoryService.searchByExample(name, description, useAnd))
                        .modelAttribute("cart", this.cartService.getCart("My Cart").defaultIfEmpty(new Cart("My Cart")))
                .build());
    }
}
