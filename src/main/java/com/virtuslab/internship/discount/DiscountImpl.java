package com.virtuslab.internship.discount;

import com.virtuslab.internship.receipt.Receipt;

public class DiscountImpl implements Discount {

    @Override
    public Receipt apply(Receipt receipt) {
        return receipt;
    }
}
