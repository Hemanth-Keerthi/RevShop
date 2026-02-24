// BuyerService.java
package main.java.service;

import main.java.dao.BuyerDAO;
import main.java.model.Buyer;

public class BuyerService {
    private BuyerDAO buyerDAO = new BuyerDAO();

    public String registerBuyer(Buyer buyer) {
        boolean success = buyerDAO.addBuyer(buyer);
        return success ? "Buyer registered successfully" : "Buyer registration failed";
    }
}
