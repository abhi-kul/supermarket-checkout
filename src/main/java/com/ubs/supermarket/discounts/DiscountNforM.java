package com.ubs.supermarket.discounts;

import com.ubs.supermarket.Item;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Class representing N For M Discount type
 */
public class DiscountNforM implements Discount {
    private final String label;
    private final int buyN;
    private final int payM;

    public DiscountNforM(String label, int buyN, int payM) {
        this.label = Objects.requireNonNull(label);
        this.buyN = buyN;
        this.payM = payM;

        if (buyN < 0) {
            throw new IllegalArgumentException("Buy N must be > 0");
        }

        if (payM < 0) {
            throw new IllegalArgumentException("Pay M must be > 0");
        }

        if (buyN < payM) {
            throw new IllegalArgumentException("Buy N must be > Pay M");
        }
    }


    /**
     * Returns name or Label of N for M Discount type.
     * @return String
     */
    @Override
    public String getName() {
        return "Buy " + buyN + " pay " + payM;
    }


    /**
     * Returns accumulated items of same type
     * @param items Collection of Items
     * @return Collection of Items
     */
    @Override
    public Collection<Item> apply(Collection<Item> items) {
        int totalCount = countItems(items);
        if (totalCount >= buyN) {
            return items.stream().collect(() -> new Accumulator(totalCount), this::accumulate, this::combine).items;
        } else {
            return items;
        }
    }


    /**
     * Returns count of items matching label
     * @param items
     * @return number of items with given label
     */
    private int countItems(Collection<Item> items) {
        return items.stream().filter(item -> !item.getDiscount().isPresent()).filter(item -> item.getLabel().equals(label)).mapToInt(item -> 1).sum();
    }


    /**
     * Returns combined results of 2 accumulated objects
     * @param accumulator
     * @param accumulator1
     * @return 2 different combined Accumulator objects
     */
    private Accumulator combine(Accumulator accumulator, Accumulator accumulator1) {
        accumulator.items.addAll(accumulator1.items);
        return accumulator;
    }


    /**
     * @param accumulator
     * @param item
     * @return Accumulated Items ready for checkout
     */
    private Accumulator accumulate(Accumulator accumulator, Item item) {
        if (accumulator.itemsCount < (accumulator.totalCount / buyN) * buyN && !item.getDiscount().isPresent() && item.getLabel().equals(label)) {
            if (accumulator.itemsCount % buyN < payM) {
                accumulator.items.add(Item.builder(item).withDiscount(this).build());
            } else {
                accumulator.items.add(Item.builder(item).withDiscount(this).withActualPrice(0).build());
            }
            accumulator.itemsCount += 1;
        } else {
            accumulator.items.add(Item.builder(item).build());
        }
        return accumulator;
    }


    /**
     * Accumulator Class
     */
    private class Accumulator {
        List<Item> items = new LinkedList<>();
        int totalCount;
        int itemsCount;

        public Accumulator(int totalCount) {
            this.totalCount = totalCount;
        }
    }
}
