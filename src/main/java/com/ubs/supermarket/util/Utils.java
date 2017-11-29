package com.ubs.supermarket.util;

import com.ubs.supermarket.Item;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedList;

import static java.math.BigDecimal.ROUND_HALF_DOWN;

public class Utils {

    private Utils() {}

    public static Collection<Item> copyList(Collection<Item> list) {
        Collection<Item> copy = new LinkedList<>();
        copy.addAll(list);
        return copy;
    }

    public static BigDecimal applyScale(BigDecimal decimal, int scale) {
        return decimal.setScale(scale, ROUND_HALF_DOWN);
    }
}
