package com.ubs.supermarket;

import com.ubs.supermarket.util.Utils;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 */
public class Basket {

    private final List<Item> items;

    private Basket(List<Item> items) {
        this.items = Objects.requireNonNull(items);
    }

    public static Builder builder() {
        return new Builder();
    }

    public Collection<Item> getItems() {
        return Utils.copyList(items);
    }

    public static class Builder {
        private List<Item> items = new LinkedList<>();

        public Builder addItem(Item item) {
            items.add(Objects.requireNonNull(item));
            return this;
        }

        public Basket build() {
            return new Basket(items);
        }
    }

}
