package com.example.poshell.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
public class Cart {

    private List<Item> items = new ArrayList<>();

    public boolean addItem(Item item) {
        Iterator<Item> iter = items.iterator();
        while(iter.hasNext()) {
            Item tmp = iter.next();
            if(tmp.getProduct().getId() == item.getProduct().getId()) {
                int newAmount = tmp.getAmount() + item.getAmount();
                if(newAmount <= 0) {
                    iter.remove();
                }
                else {
                    tmp.setAmount(newAmount);
                }
                return true;
            }
        }
        return items.add(item);
    }

    public boolean deleteItem(String productId) {
        Iterator<Item> iter = items.iterator();
        while(iter.hasNext()) {
            Item tmp = iter.next();
            if(tmp.getProduct().getId().equals(productId)) {
                iter.remove();
                return true;
            }
        }
        return false;
    }

    public boolean modifyItem(String productId, int amount) {
        Iterator<Item> iter = items.iterator();
        while(iter.hasNext()) {
            Item tmp = iter.next();
            if(tmp.getProduct().getId().equals(productId)) {
                tmp.setAmount(amount);
                return true;
            }
        }
        return false;
    }

    public double clear() {
        double total = 0;
        for(Item item : items) {
            total += item.getAmount() * item.getProduct().getPrice();
        }
        items.clear();
        return total;
    }

    @Override
    public String toString() {
        if (items.size() ==0){
            return "Empty Cart";
        }
        double total = 0;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Cart -----------------\n"  );

        for (int i = 0; i < items.size(); i++) {
            stringBuilder.append(items.get(i).toString()).append("\n");
            total += items.get(i).getAmount() * items.get(i).getProduct().getPrice();
        }
        stringBuilder.append("----------------------\n"  );

        stringBuilder.append("Total...\t\t\t" + total );

        return stringBuilder.toString();
    }
}
