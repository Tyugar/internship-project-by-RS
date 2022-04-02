package com.virtuslab.internship.service;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.discount.DiscountImpl;
import com.virtuslab.internship.discount.FifteenPercentDiscount;
import com.virtuslab.internship.discount.TenPercentDiscount;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptGenerator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class CalculateReceiptService {

    public Receipt calculateReceipt(Basket basket){

        var receiptGenerator = new ReceiptGenerator();
        var receipt = receiptGenerator.generate(basket);
        var discount = new TenPercentDiscount(new FifteenPercentDiscount(new DiscountImpl()));
        return discount.apply(receipt);
    }
}
