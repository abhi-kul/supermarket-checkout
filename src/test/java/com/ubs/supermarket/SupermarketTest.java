package com.ubs.supermarket;

import com.ubs.supermarket.util.Unit;
import com.ubs.supermarket.discounts.Discount;
import com.ubs.supermarket.discounts.DiscountFixed;
import com.ubs.supermarket.discounts.DiscountNforM;
import com.ubs.supermarket.util.Receipt;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.IntStream;

import static com.ubs.supermarket.TestFactory.aItem;
import static com.ubs.supermarket.util.Utils.applyScale;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Java6Assertions.assertThat;

public class SupermarketTest {

    @Test
    public void shouldThrowNPEWhenBasketIsNull() {
        assertThatThrownBy(() -> new Supermarket().checkoutBasket(null)).isInstanceOf(NullPointerException.class);
    }
    @Test
    public void shouldProduceReceiptWithOfZeroForAnEmptyBasket() {
        final Supermarket supermarket = new Supermarket();
        final Basket basket = Basket.builder().build();

        final Receipt receipt = supermarket.checkoutBasket(basket);

        assertThat(receipt.getTotal()).isEqualTo(new BigDecimal(0));
    }

    @Test
    public void shouldProduceReceiptWithCorrectTotalWhenBasketContainsOneItem() {
        // given
        final Supermarket supermarket = new Supermarket();

        final Item item = TestFactory.aItem("bread", 1.0, 1, Unit.ITEM);

        final Basket basket = Basket.builder()
            .addItem(item)
            .build();

        // when
        final Receipt receipt = supermarket.checkoutBasket(basket);

        // then
        assertThat(applyScale(receipt.getTotal(), 4)).isEqualTo(applyScale(new BigDecimal(1.0), 4));
    }

    @Test
    public void shouldProduceAReceiptWithCorrectTotalWhenBasketContainsTwoItems() {
        // given
        final Supermarket supermarket = new Supermarket();

        final Item item1 = aItem("onions", 1.0, 1, Unit.KG);
        final Item item2 = aItem("bread", 0.9, 1, Unit.ITEM);

        final Basket basket = Basket.builder()
            .addItem(item1)
            .addItem(item2)
            .build();

        // when
        final Receipt receipt = supermarket.checkoutBasket(basket);

        // then
        assertThat(applyScale(receipt.getTotal(), 4)).isEqualTo(applyScale(new BigDecimal(1.9), 4));
    }

    @Test
    public void shouldProduceAReceiptWithCorrectTotalWhenBasketContainsSameTwoItemsAndCanApplyDiscount2X1() {
        // given
        final Collection<Discount> discounts = Collections.singletonList(new DiscountNforM("Bread", 2, 1));

        final Supermarket supermarket = new Supermarket(discounts);

        final Item item1 = aItem("Bread", 2.0);
        final Item item2 = aItem("Bread", 2.0);

        final Basket basket = Basket.builder()
            .addItem(item1)
            .addItem(item2)
            .build();

        // when
        final Receipt receipt = supermarket.checkoutBasket(basket);

        // then
        assertThat(applyScale(receipt.getTotal(), 4)).isEqualTo(applyScale(new BigDecimal(2.0), 4));
    }
    @Test
    public void shouldProduceAReceiptWithCorrectTotalWhenBasketContainsSameThreeItemsAndCanApplyDiscount2X1() {
        // given
        final Collection<Discount> discounts = Collections.singletonList(new DiscountNforM("Bread", 2, 1));

        final Supermarket supermarket = new Supermarket(discounts);

        final Item item1 = aItem("Bread", 2.0);
        final Item item2 = aItem("Bread", 2.0);
        final Item item3 = aItem("Bread", 2.0);

        final Basket basket = Basket.builder()
            .addItem(item1)
            .addItem(item2)
            .addItem(item3)
            .build();

        // when
        final Receipt receipt = supermarket.checkoutBasket(basket);

        // then
        assertThat(applyScale(receipt.getTotal(), 4)).isEqualTo(applyScale(new BigDecimal(4.0), 4));
    }

    @Test
    public void shouldProduceAReceiptWithCorrectTotalWhenBasketContainsSameFourItemsAndCanApplyDiscount2X1() {
        // given
        final Collection<Discount> discounts = Collections.singletonList(new DiscountNforM("Bread", 2, 1));

        final Supermarket supermarket = new Supermarket(discounts);

        final Item item1 = aItem("Bread", 2.0);
        final Item item2 = aItem("Bread", 2.0);
        final Item item3 = aItem("Bread", 2.0);
        final Item item4 = aItem("Bread", 2.0);

        final Basket basket = Basket.builder().addItem(item1)
            .addItem(item2)
            .addItem(item3)
            .addItem(item4)
            .build();

        // when
        final Receipt receipt = supermarket.checkoutBasket(basket);

        // then
        assertThat(applyScale(receipt.getTotal(), 4)).isEqualTo(applyScale(new BigDecimal(4.0), 4));
    }

    @Test
    public void shouldProduceAReceiptWithCorrectTotalWhenBasketContainsFourItemsAndCanApplyTwoDiscounts()
    {
        // given
        final Collection<Discount> discounts = asList(new DiscountNforM("Bread", 3, 2), new DiscountFixed("Butter", new BigDecimal(1.7)));

        final Supermarket supermarket = new Supermarket(discounts);

        final Item item1 = aItem("Bread", 2.0);
        final Item item2 = aItem("Bread", 2.0);
        final Item item3 = aItem("Bread", 2.0);
        final Item item4 = aItem("Butter", 2.0);

        final Basket basket = Basket.builder()
            .addItem(item1)
            .addItem(item2)
            .addItem(item3)
            .addItem(item4)
            .build();

        // when
        final Receipt receipt = supermarket.checkoutBasket(basket);

        // then
        assertThat(applyScale(receipt.getTotal(), 4)).isEqualTo(applyScale(new BigDecimal(5.7), 4));
    }

    @Test
    public void shouldProduceAReceiptWithCorrectTotalWhenBasketContainsFourItemsAndCanApplyTwoDiscountsOnSameItems() {
        // given
        final Collection<Discount> discounts = asList(new DiscountNforM("Bread", 3, 2), new DiscountFixed("Bread", new BigDecimal(1.7)));

        final Supermarket supermarket = new Supermarket(discounts);

        final Item item1 = aItem("Bread", 2.0);
        final Item item2 = aItem("Bread", 2.0);
        final Item item3 = aItem("Bread", 2.0);
        final Item item4 = aItem("Bread", 2.0);

        final Basket basket = Basket.builder()
            .addItem(item1)
            .addItem(item2)
            .addItem(item3)
            .addItem(item4)
            .build();

        // when
        final Receipt receipt = supermarket.checkoutBasket(basket);

        // then
        assertThat(applyScale(receipt.getTotal(), 4)).isEqualTo(applyScale(new BigDecimal(5.7), 4));
    }
        @Test
    public void shouldProduceAReceiptWithCorrectTotalWhenBasketContainsManyItems() {
        // given
        final Collection<Discount> discounts = asList(new DiscountNforM("Bread", 2, 1), new DiscountFixed("Bread", new BigDecimal(0.9)));

        final Supermarket supermarket = new Supermarket(discounts);

        final Basket.Builder builder = Basket.builder();

        IntStream.range(0, 200).forEach(i -> builder.addItem(aItem("Bread", 2.0)));

        final Basket basket = builder.build();

        // when
        final Receipt receipt = supermarket.checkoutBasket(basket);

        // then
        assertThat(applyScale(receipt.getTotal(), 4)).isEqualTo(applyScale(new BigDecimal(180.0), 4));
    }
}
