package com.virtuslab.internship.discount;

import com.virtuslab.internship.receipt.Receipt;

public abstract class DiscountDecorator implements Discount {
    Discount discount;

    public DiscountDecorator(Discount discount) {
        this.discount = discount;
    }
    @Override
    public Receipt apply(Receipt receipt){
        return this.discount.apply(receipt);
    }

}
