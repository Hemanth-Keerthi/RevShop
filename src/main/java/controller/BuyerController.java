// BuyerController.java
package main.java.controller;

import main.java.model.Buyer;
import main.java.service.BuyerService;

public class BuyerController {
    private BuyerService buyerService = new BuyerService();

    public void registerBuyer(int id, String name, String email, String password) {
        Buyer buyer = new Buyer(id, name, email, password);
        String result = buyerService.registerBuyer(buyer);
        System.out.println(result);
    }
}
