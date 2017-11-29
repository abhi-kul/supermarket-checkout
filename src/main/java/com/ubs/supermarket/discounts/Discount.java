package com.ubs.supermarket.discounts;

import com.ubs.supermarket.Item;

import java.util.Collection;

public interface Discount {

    String getName();

    Collection<Item> apply(Collection<Item> items);
}
