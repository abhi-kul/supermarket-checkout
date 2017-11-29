package com.ubs.supermarket;

import com.ubs.supermarket.util.Unit;
import org.junit.Test;

import java.math.BigDecimal;

import static com.ubs.supermarket.TestFactory.aItem;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Java6Assertions.assertThat;

public class ItemTest {
    @Test
    public void shouldThrowNPEWhenLabelIsMissing() {
        assertThatThrownBy(() -> Item.builder().withPrice(1).withQuantity(1).withUnit(Unit.KG).build()).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldThrowNPEWhenLabelIsNull() {
        assertThatThrownBy(() -> Item.builder().withLabel(null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldThrowNPEWhenPriceIsMissing() {
        assertThatThrownBy(() -> Item.builder().withLabel("test").withQuantity(1).withUnit(Unit.KG).build()).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldThrowNPEWhenPriceIsNull() {
        assertThatThrownBy(() -> Item.builder().withPrice(null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldThrowNPEWhenQuantityIsMissing() {
        assertThatThrownBy(() -> Item.builder().withLabel("test").withPrice(1).withUnit(Unit.KG).build()).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldThrowNPEWhenQuantityIsNull() {
        assertThatThrownBy(() -> Item.builder().withQuantity(null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldThrowNPEWhenUnitIsMissing() {
        assertThatThrownBy(() -> Item.builder().withLabel("test").withPrice(1).withQuantity(2).build()).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldThrowNPEWhenUnitIsNull() {
        assertThatThrownBy(() -> Item.builder().withUnit(null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void shouldThrowIAEWhenUnitIsITEMAndQuantityIsNotOne() {
        assertThatThrownBy(() -> Item.builder().withUnit(Unit.ITEM).withQuantity(2).withPrice(1).withLabel("test").build()).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldThrowIAEWhenActualPriceIsGreaterThanPrice() {
        assertThatThrownBy(() -> Item.builder().withUnit(Unit.ITEM).withQuantity(1).withPrice(1).withLabel("test").withActualPrice(2).build()).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldReturnLabel() {
        final Item item = aItem("item", (double) 2, 3, Unit.KG);

        assertThat(item.getLabel()).isEqualTo("item");
    }

    @Test
    public void shouldReturnPrice() {
        final Item item = aItem("item", (double) 2, 3, Unit.KG);

        assertThat(item.getPrice()).isEqualTo(new BigDecimal(2));
    }

    @Test
    public void shouldReturnQuantity() {
        final Item item = aItem("item", (double) 2, 3, Unit.KG);

        assertThat(item.getQuantity()).isEqualTo(new BigDecimal(3));
    }

    @Test
    public void shouldReturnUnit() {
        final Item item = aItem("item", (double) 2, 3, Unit.KG);

        assertThat(item.getUnit()).isEqualTo(Unit.KG);
    }

}
