package com.example.poshell.cli;

import com.example.poshell.biz.PosService;
import com.example.poshell.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class PosCommand {

    private PosService posService;

    @Autowired
    public void setPosService(PosService posService) {
        this.posService = posService;
    }

    @ShellMethod(value = "List Products", key = "p")
    public String products() {
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        for (Product product : posService.products()) {
            stringBuilder.append("\t").append(++i).append("\t").append(product).append("\n");
        }
        return stringBuilder.toString();
    }

    @ShellMethod(value = "New Cart", key = "n")
    public String newCart() {
        return posService.newCart() + " OK";
    }

    @ShellMethod(value = "Add a Product to Cart", key = "a")
    public String addToCart(String productId, int amount) {
        if (posService.add(productId, amount)) {
            return posService.getCart().toString();
        }
        return "ERROR";
    }

    @ShellMethod(value = "List Merchandise in Cart", key = "l")
    public String printCart() {
        return posService.getCart().toString();
    }

    @ShellMethod(value = "Delete Purchase in Cart", key = "d") 
    public String deleteProduct(String productId) {
        String info = null;
        if(posService.delete(productId)) {
            info = "delete success!\n";
        }
        else {
            info = "You didn't order that product!\n";
        }
        return info + posService.getCart().toString();
    }

    @ShellMethod(value = "Modify Purchase Amount in Cart", key = "m") 
    public String modifyProduct(String productId, int amount) {
        if(amount <= 0) {
            return "ERROR";
        }
        String info = null;
        if(posService.modify(productId, amount)) {
            info = "modify success!\n";
        }
        else {
            info = "You didn't order that product!\n";
        }
        return info + posService.getCart().toString();
    }

    @ShellMethod(value = "Clear Cart", key = "c") 
    public String clearCart() {
        double total = posService.clear();
        return "total is " + total + "\n" + posService.getCart().toString();
    }
    
}
