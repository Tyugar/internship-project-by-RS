package com.virtuslab.internship.receipt;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.product.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class ReceiptGenerator {

    public Receipt generate(Basket basket) {
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        Map<Product,Long> productQuantity = basket.getProducts().stream()
                .collect(groupingBy(e->e, Collectors.counting()));
        for (var entry : productQuantity.entrySet()) {
            receiptEntries.add(new ReceiptEntry(entry.getKey(),(entry.getValue().intValue())));
        }
        return new Receipt (receiptEntries);
    }
}
