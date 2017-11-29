package com.ubs.supermarket;

import com.ubs.supermarket.discounts.Discount;
import com.ubs.supermarket.discounts.DiscountFixed;
import com.ubs.supermarket.discounts.DiscountNforM;
import com.ubs.supermarket.util.Printer;
import com.ubs.supermarket.util.Receipt;
import com.ubs.supermarket.util.Unit;

import java.io.PrintStream;
import java.math.BigDecimal;

import static java.util.Arrays.asList;

public class SupermarketDemo {
    public static void main(String[] args) {
        Discount discount1 = new DiscountNforM("Eggs", 2, 1);
        Discount discount2 = new DiscountFixed("Sensonite", new BigDecimal(1.5));

        Supermarket supermarket = new Supermarket(asList(discount1, discount2));

        Item item1 = Item.builder()
            .withLabel("Eggs")
            .withPrice(2.0)
            .withQuantity(1)
            .withUnit(Unit.ITEM)
            .build();

        Item item2 = Item.builder()
            .withLabel("Eggs")
            .withPrice(2.0)
            .withQuantity(1)
            .withUnit(Unit.ITEM)
            .build();

        Item item3 = Item.builder()
            .withLabel("Sensonite")
            .withPrice(2.4)
            .withQuantity(1)
            .withUnit(Unit.ITEM)
            .build();

        Item item4 = Item.builder()
            .withLabel("Oranges")
            .withPrice(1.7)
            .withQuantity(1)
            .withUnit(Unit.KG)
            .build();

        Basket basket = Basket.builder()
            .addItem(item1)
            .addItem(item2)
            .addItem(item3)
            .addItem(item4)
            .build();

        Receipt receipt = supermarket.checkoutBasket(basket);

        Printer printer = new Printer(new PrintStream(System.out));

        printer.print(receipt);
    }
}
