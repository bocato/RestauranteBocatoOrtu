package comp.ufu.restaurante.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import comp.ufu.restaurante.database.FoodDatabase;

/**
 * Created by bocato on 5/26/15.
 */
public class Order {

    private int id;
    private String table;
    private int foodOrdered[]; // list[product_id] = quantity>
    private float totalSpent;
    private FoodDatabase foodDatabase = FoodDatabase.getInstance();

    public Order(){}

    public Order(String table){
        this.table = table;
        this.foodOrdered = new int[foodDatabase.getCardapio().size()+1];
        this.totalSpent = 0;
    }

    public Order(String table, int[] foodOrdered){
        this.table = table;
        this.foodOrdered = foodOrdered;
    }

    public Order(int id, String table, int[] foodOrdered){
        this.id = id;
        this.table = table;
        this.foodOrdered = foodOrdered;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int[] getFoodOrdered() {
        return foodOrdered;
    }

    public void setFoodOrdered(int[] foodOrdered) {
        this.foodOrdered = foodOrdered;
    }

    public String getOrders() {
        String orders = "";

        for (int i = 0; i < foodOrdered.length-1; i++){
            orders += i+"#"+foodOrdered[i]+"-";
        }

        String allOrders = orders.substring(0, (orders.lastIndexOf("-")));

        return allOrders;
    }

    public double getTotalSpent() {
        float totalSpent = 0;

        for (int i = 0; i < foodOrdered.length; i++){
            int quantity = foodOrdered[i];
            totalSpent += foodDatabase.getCardapio().get(i).getPrice() * quantity;
        }

        return totalSpent;
    }

    public void setTotalSpent(float totalSpent) {
        this.totalSpent = totalSpent;
    }
}
