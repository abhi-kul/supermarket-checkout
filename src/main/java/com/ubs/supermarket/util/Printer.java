package com.ubs.supermarket.util;

import com.ubs.supermarket.Item;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Objects;

import static com.ubs.supermarket.util.Utils.applyScale;

public class Printer {

    private PrintStream stream;

    public Printer(PrintStream stream) {
        this.stream = Objects.requireNonNull(stream);
    }

    public void print(Receipt receipt) {

        receipt.getItems().forEach(this::printItem);

        stream.println((String.format("%30s", "---")));

        stream.println(String.format("%-20s %9.2f", "Sub-total", convertToDouble(receipt.getSubTotal())));

        stream.println(String.format("%-20s %9.2f", "Total savings", convertToDouble(receipt.getTotal().subtract(receipt.getSubTotal()))));

        stream.println((String.format("%30s", "---")));

        stream.println(String.format("%-20s %9.2f", "Total to Pay", convertToDouble(receipt.getTotal())));
    }

    private void printItem(Item item) {
        if (item.getUnit().equals(Unit.ITEM)) {
            final double nominalCost = convertToDouble(item.getNominalCost());

            stream.println(String.format("%-20s %9.2f", item.getLabel(), nominalCost));

            if (item.getDiscount().isPresent() && item.getActualCost().compareTo(item.getNominalCost()) < 0) {
                stream.println(String.format("  %-18s %9.2f", item.getDiscount().get().getName(), convertToDouble(item.getActualCost().subtract(item.getNominalCost()))));
            }
        } else {
            final double nominalCost = convertToDouble(item.getNominalCost());

            final double quantity = convertToDouble(item.getQuantity());

            final double price = convertToDouble(item.getPrice());

            final String unit = item.getUnit().toString().toLowerCase();

            final String label = String.format("%.3f %s @ zł%.2f/%s", quantity, unit, price, unit);

            stream.println(String.format("%-20s", item.getLabel()));

            stream.println(String.format("%-20s %9.2f", label, nominalCost));

            if (item.getDiscount().isPresent() && item.getActualCost().compareTo(item.getNominalCost()) < 0) {
                stream.println(String.format("  %-18s %9.2f", item.getDiscount().get().getName(), convertToDouble(item.getActualCost().subtract(item.getNominalCost()))));
            }
        }
    }

    private double convertToDouble(BigDecimal decimal) {
        return applyScale(decimal, 2).doubleValue();
    }
}
