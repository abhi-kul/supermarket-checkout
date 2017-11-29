package com.ubs.supermarket.discounts;

import com.ubs.supermarket.Item;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class DiscountFixed implements Discount {
    private final String label;
    private final int buyN;
    private final BigDecimal price;

    public DiscountFixed(String label, int buyN, BigDecimal price) {
        this.label = Objects.requireNonNull(label);
        this.price = Objects.requireNonNull(price);
        this.buyN = buyN;

        if (buyN < 0) {
            throw new IllegalArgumentException("Buy N must be > 0");
        }
    }

    public DiscountFixed(String label, BigDecimal price) {
        this(label,1, price);
    }

    @Override
    public String getName() {
        return "Buy " + buyN + " for zł" + price;
    }

    @Override
    public Collection<Item> apply(Collection<Item> items) {
        int totalCount = countItems(items);
        if (totalCount >= buyN) {
            return items.stream().collect(() -> new Accumulator(totalCount), this::accumulate, this::combine).items;
        } else {
            return items;
        }
    }

    private int countItems(Collection<Item> items) {
        return items.stream().filter(item -> !item.getDiscount().isPresent()).filter(item -> item.getLabel().equals(label)).mapToInt(item -> 1).sum();
    }

    private Accumulator combine(Accumulator accumulator, Accumulator accumulator1) {
        accumulator.items.addAll(accumulator1.items);
        return accumulator;
    }

    private Accumulator accumulate(Accumulator accumulator, Item item) {
        if (accumulator.itemsCount < (accumulator.totalCount / buyN) * buyN && !item.getDiscount().isPresent() && item.getLabel().equals(label)) {
            accumulator.items.add(Item.builder(item).withDiscount(this).withActualPrice(price.divide(new BigDecimal(buyN))).build());
            accumulator.itemsCount += 1;
        } else {
            accumulator.items.add(Item.builder(item).build());
        }
        return accumulator;
    }

    private class Accumulator {
        List<Item> items = new LinkedList<>();
        int totalCount;
        int itemsCount;

        public Accumulator(int totalCount) {
            this.totalCount = totalCount;
        }
    }
}
