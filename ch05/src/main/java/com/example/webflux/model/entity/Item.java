package com.example.webflux.model.entity;

import java.util.Objects;

import org.springframework.data.annotation.Id;

public class Item {
    private @Id String id;
    private String name;
    private double price;
    private String description;

    private Item() {}

    public Item(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public Item(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }
    
    public Item(String id, String name, String description, double price) {
    	this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", price=" + price + ", description=" + description + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, id, name, price);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		return Objects.equals(description, other.description) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name)
				&& Double.doubleToLongBits(price) == Double.doubleToLongBits(other.price);
	}
	
	
}
