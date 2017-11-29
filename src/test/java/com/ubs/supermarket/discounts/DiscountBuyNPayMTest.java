package com.ubs.supermarket.discounts;

import com.ubs.supermarket.Item;
import org.junit.Test;

import java.util.Collection;

import static com.ubs.supermarket.TestFactory.aItem;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Java6Assertions.assertThat;

public class DiscountBuyNPayMTest {
    @Test
    public void shouldThrowNPEWhenLabelIsNull() {
        assertThatThrownBy(() -> new DiscountNforM(null, 2, 1)).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldThrowIAEWhenNIsLessThanM() {
        assertThatThrownBy(() -> new DiscountNforM("test", 2, 3)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldThrowIAEWhenNIsNegastive() {
        assertThatThrownBy(() -> new DiscountNforM("test", -2, 3)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldThrowIAEWhenMIsNegastive() {
        assertThatThrownBy(() -> new DiscountNforM("test", 2, -3)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldReturnDiscountedItemsWhenCanApply2X1DiscountOnTwoItems() {
        // given
        Discount discount = new DiscountNforM("  Bread", 2, 1);

        final Item item1 = aItem("  Bread", 2.0);
        final Item item2 = aItem("  Bread", 2.0);

        // when
        Collection<Item> newItems = discount.apply(asList(item1, item2));

        // then
        final Item discountedItem1 = aItem("  Bread", 2.0, discount, 2.0);
        final Item discountedItem2 = aItem("  Bread", 2.0, discount, 0.0);

        assertThat(newItems).containsExactly(discountedItem1, discountedItem2);
    }

    @Test
    public void shouldReturnDiscountedItemsWhenCanApply2X1DiscountOnThreeItems() {
        // given
        Discount discount = new DiscountNforM("  Bread", 2, 1);

        final Item item1 = aItem("  Bread", 2.0);
        final Item item2 = aItem("  Bread", 2.0);
        final Item item3 = aItem("  Bread", 2.0);

        // when
        Collection<Item> newItems = discount.apply(asList(item1, item2, item3));

        // then
        final Item discountedItem1 = aItem("  Bread", 2.0, discount, 2.0);
        final Item discountedItem2 = aItem("  Bread", 2.0, discount, 0.0);

        assertThat(newItems).containsExactly(discountedItem1, discountedItem2, item3);
    }

    @Test
    public void shouldReturnFourDiscountedItemsWhenCanApply2X1DiscountOnFourItems() {
        // given
        Discount discount = new DiscountNforM("  Bread", 2, 1);

        final Item item1 = aItem("  Bread", 2.0);
        final Item item2 = aItem("  Bread", 2.0);
        final Item item3 = aItem("  Bread", 2.0);
        final Item item4 = aItem("  Bread", 2.0);

        // when
        Collection<Item> newItems = discount.apply(asList(item1, item2, item3, item4));

        // then
        final Item discountedItem1 = aItem("  Bread", 2.0, discount, 2.0);
        final Item discountedItem2 = aItem("  Bread", 2.0, discount, 0.0);
        final Item discountedItem3 = aItem("  Bread", 2.0, discount, 2.0);
        final Item discountedItem4 = aItem("  Bread", 2.0, discount, 0.0);

        assertThat(newItems).containsExactly(discountedItem1, discountedItem2, discountedItem3, discountedItem4);
    }

    @Test
    public void shouldReturnDiscountedItemsWhenCanApply3X2DiscountOnFourItems() {
        // given
        Discount discount = new DiscountNforM("  Bread", 3, 2);

        final Item item1 = aItem("  Bread", 2.0);
        final Item item2 = aItem("  Bread", 2.0);
        final Item item3 = aItem("  Bread", 2.0);
        final Item item4 = aItem("  Bread", 2.0);

        // when
        Collection<Item> newItems = discount.apply(asList(item1, item2, item3, item4));

        // then
        final Item discountedItem1 = aItem("  Bread", 2.0, discount, 2.0);
        final Item discountedItem2 = aItem("  Bread", 2.0, discount, 2.0);
        final Item discountedItem3 = aItem("  Bread", 2.0, discount, 0.0);

        assertThat(newItems).containsExactly(discountedItem1, discountedItem2, discountedItem3, item4);
    }
}
