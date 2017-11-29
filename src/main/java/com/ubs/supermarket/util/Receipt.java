package com.ubs.supermarket.util;

import com.ubs.supermarket.Item;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;

public class Receipt {
    private final Collection<Item> items;

    public Receipt(Collection<Item> items) {
        this.items = Objects.requireNonNull(items);
    }

    public Collection<Item> getItems() {
        return Utils.copyList(items);
    }

    public BigDecimal getTotal() {
        return computeTotal(items);
    }

    public BigDecimal getSubTotal() {
        return computeSubTotal(items);
    }

    private BigDecimal computeTotal(Collection<Item> items) {
        return items.stream()
            .parallel()
            .map(Item::getActualCost)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal computeSubTotal(Collection<Item> items) {
        return items.stream()
            .parallel()
            .map(Item::getNominalCost)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
