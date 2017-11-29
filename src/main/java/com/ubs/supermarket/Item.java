package com.ubs.supermarket;

import com.ubs.supermarket.util.Unit;
import com.ubs.supermarket.discounts.Discount;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

public class Item {
    private final String label;
    private final Unit unit;
    private final BigDecimal price;
    private final BigDecimal quantity;
    private final Optional<Discount> discount;
    private final BigDecimal actualPrice;

    private Item(String label, Unit unit, BigDecimal price, BigDecimal quantity, Discount discount, BigDecimal actualPrice) {
        this.label = Objects.requireNonNull(label);
        this.unit = Objects.requireNonNull(unit);
        this.price = Objects.requireNonNull(price);
        this.quantity = Objects.requireNonNull(quantity);
        this.discount = Optional.ofNullable(discount);
        this.actualPrice = actualPrice != null ? actualPrice : this.price;

        if (this.unit.equals(Unit.ITEM) && !this.quantity.equals(new BigDecimal(1))) {
            throw new IllegalArgumentException("Quantity must be 1 for unit ITEM");
        }

        if (this.actualPrice.compareTo(this.price) > 0) {
            throw new IllegalArgumentException("Actual price must not be greater than price");
        }
    }

    public String getLabel() {
        return label;
    }

    public Unit getUnit() {
        return unit;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getNominalCost() {
        return price.multiply(quantity);
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public Optional<Discount> getDiscount() {
        return discount;
    }

    public BigDecimal getActualPrice() {
        return actualPrice;
    }

    public BigDecimal getActualCost() {
        return actualPrice.multiply(quantity);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(Item item) {
        final Builder builder = new Builder();
        builder.withLabel(item.getLabel());
        builder.withUnit(item.getUnit());
        builder.withPrice(item.getPrice());
        builder.withQuantity(item.getQuantity());
        builder.withDiscount(item.getDiscount().orElse(null));
        builder.withActualPrice(item.getActualPrice());
        return builder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Item item = (Item) o;

        return (label != null ? label.equals(item.label) : item.label == null) && (unit != null ? unit.equals(item.unit) : item.unit == null) && (price != null ? price.equals(item.price) : item.price == null) && (quantity != null ? quantity
            .equals(item.quantity) : item.quantity == null) && (discount != null ? discount.equals(item.discount) : item.discount == null) && (actualPrice != null ? actualPrice.equals(
            item.actualPrice) : item.actualPrice == null);
    }

    @Override
    public int hashCode() {
        int result = label != null ? label.hashCode() : 0;
        result = 31 * result + (unit != null ? unit.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (discount != null ? discount.hashCode() : 0);
        result = 31 * result + (actualPrice != null ? actualPrice.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Item{" +
            "label='" + label + '\'' +
            ", unit='" + unit + '\'' +
            ", price=" + price +
            ", quantity=" + quantity +
            ", discount=" + discount.map(Discount::getName).orElse("") +
            ", actualPrice=" + actualPrice +
            '}';
    }

    public static class Builder {
        private String label;
        private Unit unit;
        private BigDecimal price;
        private BigDecimal quantity;
        private Discount discount;
        private BigDecimal actualPrice;

        public Builder withLabel(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder withUnit(Unit unit) {
            this.unit = Objects.requireNonNull(unit);
            return this;
        }

        public Builder withPrice(BigDecimal price) {
            this.price = Objects.requireNonNull(price);
            return this;
        }

        public Builder withPrice(double price) {
            this.price = new BigDecimal(price);
            return this;
        }

        public Builder withQuantity(BigDecimal quantity) {
            this.quantity = Objects.requireNonNull(quantity);
            return this;
        }

        public Builder withQuantity(int quantity) {
            this.quantity = new BigDecimal(quantity);
            return this;
        }

        public Builder withDiscount(Discount discount) {
            this.discount = discount;
            return this;
        }

        public Builder withActualPrice(BigDecimal actualPrice) {
            this.actualPrice = Objects.requireNonNull(actualPrice);
            return this;
        }

        public Builder withActualPrice(double actualPrice) {
            this.actualPrice = new BigDecimal(actualPrice);
            return this;
        }

        public Item build() {
            return new Item(label, unit, price, quantity, discount, actualPrice);
        }
    }
}
