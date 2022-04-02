package com.virtuslab.internship.webapp.controller;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.discount.Discount;
import com.virtuslab.internship.discount.DiscountImpl;
import com.virtuslab.internship.discount.FifteenPercentDiscount;
import com.virtuslab.internship.discount.TenPercentDiscount;
import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.product.ProductDb;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptGenerator;
import com.virtuslab.internship.service.CalculateReceiptService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.*;

@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})

@RestController
@RequestMapping("/api/")
public class SpringRestController {

    @GetMapping("/get")
    public Basket connect(){
        Basket basket = new Basket();
        var productDb = new ProductDb();
        basket.addProduct(productDb.getProduct("Bread"));
        basket.addProduct( productDb.getProduct("Cereals"));
        basket.addProduct( productDb.getProduct("Steak"));
        basket.addProduct( productDb.getProduct("Potato"));
        System.out.println("Spring Boot Application Connected.");
        return basket;
    }

    @PostMapping("/calculateReceipt")
    public Receipt calculateReceipt(@RequestBody Basket basket){
        CalculateReceiptService calculateReceiptService = new CalculateReceiptService();
        return calculateReceiptService.calculateReceipt(basket);
    }
}