package com.virtuslab.internship.discount;

import com.virtuslab.internship.product.ProductDb;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptEntry;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FifteenPercentDiscountTest {



    @Test
    void shouldApply15PercentDiscountWhenGrainNumberIsAbove2(){
        var productDb = new ProductDb();
        var bread = productDb.getProduct("Bread");
        var cereals = productDb.getProduct("Cereals");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(bread, 1));
        receiptEntries.add(new ReceiptEntry(cereals, 3));

        var receipt = new Receipt(receiptEntries);
        Discount discount = new FifteenPercentDiscount(new DiscountImpl());
        var expectedTotalPrice = bread.price().add(cereals.price().multiply(BigDecimal.valueOf(3)))
                        .multiply(BigDecimal.valueOf(0.85));

        // When
        var receiptAfterDiscount = discount.apply(receipt);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(1, receiptAfterDiscount.discounts().size());
    }

    @Test
    void shouldApply15PercentDiscountWhenGrainNumberIsBelow3(){
        var productDb = new ProductDb();
        var steak = productDb.getProduct("Steak");
        var tomato = productDb.getProduct("Tomato");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(steak, 1));
        receiptEntries.add(new ReceiptEntry(tomato, 2));

        var receipt = new Receipt(receiptEntries);
        Discount discount = new FifteenPercentDiscount(new DiscountImpl());
        var expectedTotalPrice = steak.price().add(tomato.price().multiply(BigDecimal.valueOf(2)));

        // When
        var receiptAfterDiscount = discount.apply(receipt);

        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(0, receiptAfterDiscount.discounts().size());
    }
}
