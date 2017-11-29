package com.ubs.supermarket;

import com.ubs.supermarket.Basket;
import com.ubs.supermarket.Item;
import com.ubs.supermarket.discounts.Discount;
import com.ubs.supermarket.util.Receipt;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;

import static java.util.Collections.emptyList;

public class Supermarket {
    private Collection<Discount> discounts = new LinkedList<>();

    public Supermarket() {
        this(emptyList());
    }

    public Supermarket(Collection<Discount> discounts) {
        this.discounts.addAll(Objects.requireNonNull(discounts));
    }

    public Receipt checkoutBasket(Basket basket) {
        return new Receipt(computeBestDiscountedPrice(Objects.requireNonNull(basket).getItems()));
    }

    private Collection<Item> computeBestDiscountedPrice(Collection<Item> items) {
        for (;;) {
            Collection<Item> currentItems = items;

            PairItems pair = discounts.stream()
                .map(discount -> discount.apply(currentItems))
                .map(Items -> new PairItems(Items, calculateTotal(Items)))
                .reduce(new PairItems(currentItems, calculateTotal(currentItems)), this::reducePairs);

            if (currentItems.equals(pair.items)) {
                break;
            }

            items = pair.items;
        }

        return items;
    }

    private PairItems reducePairs(PairItems a, PairItems p) {
        if (p.total.compareTo(a.total) < 0) {
            return p;
        }
        return a;
    }

    private BigDecimal calculateTotal(Collection<Item> items) {
        return items.stream()
            .parallel()
            .map(Item::getActualCost)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private class PairItems {
        Collection<Item> items;
        BigDecimal total;

        public PairItems(Collection<Item> items, BigDecimal total) {
            this.items = items;
            this.total = total;
        }
    }
}
