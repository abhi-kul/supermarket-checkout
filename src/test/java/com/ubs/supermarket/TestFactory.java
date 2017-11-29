package com.ubs.supermarket;

import com.ubs.supermarket.discounts.Discount;
import com.ubs.supermarket.util.Unit;

public class TestFactory {
    private TestFactory() {}

    public static Item aItem(String label, double price) {
        return Item.builder().withLabel(label).withPrice(price).withQuantity(1).withUnit(Unit.ITEM).build();
    }

    public static Item aItem(String label, double price, Discount discount, double actualPrice) {
        return Item.builder().withLabel(label).withPrice(price).withQuantity(1).withUnit(Unit.ITEM).withDiscount(discount).withActualPrice(actualPrice).build();
    }

    public static Item aItem(String label, double price, int quantity, Unit unit) {
        return Item.builder().withLabel(label).withPrice(price).withQuantity(quantity).withUnit(unit).build();
    }

    public static Item aItem(String label, double price, int quantity, Unit unit, Discount discount, double actualPrice) {
        return Item.builder().withLabel(label).withPrice(price).withQuantity(quantity).withUnit(unit).withDiscount(discount).withActualPrice(actualPrice).build();
    }

}
