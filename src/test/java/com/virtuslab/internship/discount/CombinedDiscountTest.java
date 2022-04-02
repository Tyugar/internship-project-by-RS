package com.virtuslab.internship.discount;

import com.virtuslab.internship.product.ProductDb;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptEntry;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CombinedDiscountTest {

    @Test
    void shouldApply15PercentDiscountAndApply10PercentDiscountWhenGrainNumberIsAbove2AndPriceAfterIsAbove50(){
        var productDb = new ProductDb();
        var bread = productDb.getProduct("Bread");
        var cereals = productDb.getProduct("Cereals");
        var steak = productDb.getProduct("Steak");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(bread, 1));
        receiptEntries.add(new ReceiptEntry(cereals, 3));
        receiptEntries.add(new ReceiptEntry(steak, 1));

        var receipt = new Receipt(receiptEntries);
        Discount discount = new TenPercentDiscount(new FifteenPercentDiscount(new DiscountImpl()));
        var expectedTotalPrice = bread.price().add(cereals.price().multiply(BigDecimal.valueOf(3)))
                .add(steak.price()).multiply(BigDecimal.valueOf(0.85)).multiply(BigDecimal.valueOf(0.9));

        // When
        var receiptAfterDiscount = discount.apply(receipt);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(2, receiptAfterDiscount.discounts().size());
    }

    @Test
    void shouldNotApply15PercentDiscountAndApply10PercentDiscountWhenGrainNumberIsBelow3AndPriceAfterIsAbove50(){

        var productDb = new ProductDb();
        var bread = productDb.getProduct("Bread");
        var cereals = productDb.getProduct("Cereals");
        var steak = productDb.getProduct("Steak");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(bread, 1));
        receiptEntries.add(new ReceiptEntry(cereals, 1));
        receiptEntries.add(new ReceiptEntry(steak, 1));

        var receipt = new Receipt(receiptEntries);
        Discount discount = new TenPercentDiscount(new FifteenPercentDiscount(new DiscountImpl()));
        var expectedTotalPrice = bread.price().add(cereals.price())
                .add(steak.price()).multiply(BigDecimal.valueOf(0.9));

        // When
        var receiptAfterDiscount = discount.apply(receipt);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(1, receiptAfterDiscount.discounts().size());

    }
    @Test
    void shouldApply15PercentDiscountAndNotApply10PercentDiscountWhenGrainNumberIsAbove2AndPriceAfterIsBelow50(){
        var productDb = new ProductDb();
        var bread = productDb.getProduct("Bread");
        var cereals = productDb.getProduct("Cereals");
        var tomato = productDb.getProduct("Tomato");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(bread, 1));
        receiptEntries.add(new ReceiptEntry(cereals, 2));
        receiptEntries.add(new ReceiptEntry(tomato, 1));

        var receipt = new Receipt(receiptEntries);
        Discount discount = new TenPercentDiscount(new FifteenPercentDiscount(new DiscountImpl()));
        var expectedTotalPrice = bread.price().add(cereals.price().multiply(BigDecimal.valueOf(2)))
                .add(tomato.price()).multiply(BigDecimal.valueOf(0.85));

        // When
        var receiptAfterDiscount = discount.apply(receipt);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(1, receiptAfterDiscount.discounts().size());
    }

    @Test
    void shouldNotApply15PercentDiscountAndNotApply10PercentDiscountWhenGrainNumberIsBelow3AndPriceAfterIsBelow50(){
        var productDb = new ProductDb();
        var bread = productDb.getProduct("Bread");
        var cereals = productDb.getProduct("Cereals");
        var tomato = productDb.getProduct("Tomato");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(bread, 1));
        receiptEntries.add(new ReceiptEntry(cereals, 1));
        receiptEntries.add(new ReceiptEntry(tomato, 1));

        var receipt = new Receipt(receiptEntries);
        Discount discount = new TenPercentDiscount(new FifteenPercentDiscount(new DiscountImpl()));
        var expectedTotalPrice = bread.price().add(cereals.price())
                .add(tomato.price());

        // When
        var receiptAfterDiscount = discount.apply(receipt);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(0, receiptAfterDiscount.discounts().size());
    }
}
