package com.ubs.supermarket;

import com.ubs.supermarket.util.Unit;
import org.assertj.core.api.Java6Assertions;
import org.junit.Test;

import static com.ubs.supermarket.TestFactory.aItem;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Java6Assertions.assertThat;

public class BasketTest {
    @Test
    public void shouldNotThrowNPEWhenItemsAreMissing() {
        Java6Assertions.assertThat(Basket.builder().build()).isNotNull();
    }

    @Test
    public void shouldThrowNPEWhenItemsIsNull() {
        assertThatThrownBy(() -> Basket.builder().addItem(null).build()).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldReturnOneItem() {
        final Item actualItem = TestFactory.aItem("item", (double) 1, 1, Unit.KG);
        final Item expectedItem = aItem("item", (double) 1, 1, Unit.KG);

        assertThat(Basket.builder().addItem(actualItem).build().getItems()).containsExactly(expectedItem);
    }

}
