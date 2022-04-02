package com.virtuslab.internship.discount;

import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.receipt.Receipt;

import java.math.BigDecimal;
import java.util.IntSummaryStatistics;
import java.util.stream.Collectors;

public class FifteenPercentDiscount extends DiscountDecorator{

    public static String NAME = "FifteenPercentDiscount";

    public FifteenPercentDiscount(Discount discount) {
        super(discount);
    }
    public Receipt apply(Receipt receipt) {
        receipt =  this.discount.apply(receipt);
        if (shouldApply(receipt)) {
            var totalPrice = receipt.totalPrice().multiply(BigDecimal.valueOf(0.85));
            var discounts = receipt.discounts();
            discounts.add(NAME);
            return new Receipt(receipt.entries(), discounts, totalPrice);
        }
        return receipt;
    }

    private boolean shouldApply(Receipt receipt) {
        return receipt.entries().stream()
                .filter(e->e.product().type().equals(Product.Type.GRAINS))
                .mapToInt(e->e.quantity()).sum() >= 3;
    }
}
