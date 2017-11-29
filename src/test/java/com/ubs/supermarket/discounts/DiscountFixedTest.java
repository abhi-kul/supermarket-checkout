package com.ubs.supermarket.discounts;

import com.ubs.supermarket.Item;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;

import static com.ubs.supermarket.TestFactory.aItem;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.assertThatThrownBy;

public class DiscountFixedTest {
    @Test
    public void shouldThrowNPEWhenLabelIsNull() {
        assertThatThrownBy(() -> new DiscountFixed(null, new BigDecimal(1))).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldThrowNPEWhenPriceIsNull() {
        assertThatThrownBy(() -> new DiscountFixed("test", null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldThrowIAEWhenNIsNegative() {
        assertThatThrownBy(() -> new DiscountFixed("test", -1, new BigDecimal(1.0))).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldReturnDiscountedItemsWhenCanApplyDiscount() {
        // given
        Discount discount = new DiscountFixed("Bread", new BigDecimal(1.5));

        final Item item1 = aItem("Bread", 2.0);

        // when
        Collection<Item> newItems = discount.apply(Collections.singletonList(item1));

        // then
        final Item discountedItem1 = aItem("Bread", 2.0, discount, 1.5);

        assertThat(newItems).containsExactly(discountedItem1);
    }

    @Test
    public void shouldReturnTwoDiscountedItemsWhenCanApplyDiscountAndItemsContainsMoreThanNumberOfRequiredItemsAndNumberIsOne() {
        // given
        Discount discount = new DiscountFixed("Bread", new BigDecimal(1.5));

        final Item item1 = aItem("Bread", 2.0);
        final Item item2 = aItem("Bread", 2.0);

        // when
        Collection<Item> newItems = discount.apply(asList(item1, item2));

        // then
        final Item discountedItem1 = aItem("Bread", 2.0, discount, 1.5);
        final Item discountedItem2 = aItem("Bread", 2.0, discount, 1.5);

        assertThat(newItems).containsExactly(discountedItem1, discountedItem2);
    }

    @Test
    public void shouldReturnTwoDiscountedItemsWhenCanApplyDiscountAndItemsContainsMoreThanNumberOfRequiredItemsAndNumberIsTwo() {
        // given
        Discount discount = new DiscountFixed("Bread", 2, new BigDecimal(1.5));

        final Item item1 = aItem("Bread", 2.0);
        final Item item2 = aItem("Bread", 2.0);
        final Item item3 = aItem("Bread", 2.0);

        // when
        Collection<Item> newItems = discount.apply(asList(item1, item2, item3));

        // then
        final Item discountedItem1 = aItem("Bread", 2.0, discount, 0.75);
        final Item discountedItem2 = aItem("Bread", 2.0, discount, 0.75);

        assertThat(newItems).containsExactly(discountedItem1, discountedItem2, item3);
    }
}
