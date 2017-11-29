package com.ubs.supermarket.util;

import com.ubs.supermarket.Item;
import com.ubs.supermarket.TestFactory;
import com.ubs.supermarket.discounts.Discount;
import com.ubs.supermarket.discounts.DiscountFixed;
import com.ubs.supermarket.discounts.DiscountNforM;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedList;

import static com.ubs.supermarket.TestFactory.aItem;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Java6Assertions.assertThat;

public class PrinterTest {
    @Test
    public void shouldPrintReceiptTotal() {
        // given
        final Receipt receipt = new Receipt(new LinkedList<>());
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final Printer printer = new Printer(new PrintStream(out));

        // when
        printer.print(receipt);

        System.out.println("???"+out.toString()+"???");

        // then
        assertThat(out.toString()).isEqualTo(
            "                           ---\n" +
                "Sub-total                 0.00\n" +
                "Total savings             0.00\n" +
                "                           ---\n" +
                "Total to Pay              0.00\n");
    }

    @Test
    public void shouldPrintReceiptItems() {
        // given
        final Item item1 = TestFactory.aItem("Onions", 1, 1, Unit.KG);
        final Item item2 = aItem("Rice", 0.9, 2, Unit.KG);
        final Item item3 = aItem("Bread", 2);

        final Collection<Item> items = asList(item1, item2, item3);

        final Receipt receipt = new Receipt(items);

        final ByteArrayOutputStream out = new ByteArrayOutputStream();

        final Printer printer = new Printer(new PrintStream(out));

        // when
        printer.print(receipt);

        System.out.println(out.toString());

        // then
        assertThat(out.toString()).isEqualTo(
                "Onions              \n" +
                "1.000 kg @ zł1.00/kg      1.00\n" +
                "Rice             \n" +
                "2.000 kg @ zł0.90/kg      1.80\n" +
                "Bread                      2.00\n" +
                "                           ---\n" +
                "Sub-total                 4.80\n" +
                "Total savings             0.00\n" +
                "                           ---\n" +
                "Total to Pay              4.80\n");
    }

    @Test
    public void shouldPrintReceiptItemsWithDiscount() {
        // given
        final Discount discount1 = new DiscountNforM("Bread", 2, 1);
        final Discount discount2 = new DiscountFixed("Butter", new BigDecimal(2.5));

        final Item item1 = aItem("Bread", 2.0, discount1,2.0);
        final Item item2 = aItem("Bread", 2.0, discount1,0.0);
        final Item item3 = aItem("Butter", 3.0, discount2, 2.5);

        // when
        final ByteArrayOutputStream out = new ByteArrayOutputStream();

        final Printer printer = new Printer(new PrintStream(out));

        Receipt receipt = new Receipt(asList(item1, item2, item3));

        printer.print(receipt);

        System.out.println(out.toString());

        // then
        Assertions.assertThat(out.toString()).isEqualTo(
                "Bread                     2.00\n" +
                "Bread                     2.00\n" +
                "  Buy 2 pay 1            -2.00\n" +
                "Butter                    3.00\n" +
                "  Buy 1 for zł2.5        -0.50\n" +
                "                           ---\n" +
                "Sub-total                 7.00\n" +
                "Total savings            -2.50\n" +
                "                           ---\n" +
                "Total to Pay              4.50\n");
    }

}
