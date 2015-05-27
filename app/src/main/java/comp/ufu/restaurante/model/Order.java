package comp.ufu.restaurante.model;

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
    private HashMap<Integer, Integer> foodOrdered; // <product_id , quantity>
    private double totalSpent;
    private FoodDatabase foodDatabase = FoodDatabase.getInstance();

    public Order(){}

    public Order(String table){
        this.table = table;
        this.foodOrdered = new HashMap<>();
        this.totalSpent = 0.0;
    }

    public Order(String table, HashMap<Integer, Integer> foodOrdered){
        this.table = table;
        this.foodOrdered = foodOrdered;
    }

    public Order(int id, String table, HashMap<Integer, Integer> foodOrdered){
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

    public HashMap<Integer, Integer> getFoodOrdered() {
        return foodOrdered;
    }

    public void setFoodOrdered(HashMap<Integer, Integer> foodOrdered) {
        this.foodOrdered = foodOrdered;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getOrders() {
        String orders = "";
        Set<HashMap.Entry<Integer, Integer>> entrySet = foodOrdered.entrySet();
        Iterator<HashMap.Entry<Integer, Integer>> entrySetIterator = entrySet.iterator();
        while (entrySetIterator.hasNext()) {

            HashMap.Entry entry = entrySetIterator.next();
            int id = (int) entry.getKey();
            int quantity = (int) entry.getValue();
            orders += id+"#"+quantity+"-";
        }

        String allOrders = orders.substring(0,(orders.lastIndexOf(".")-1));

        return allOrders;
    }

    public double getTotalSpent() {
        float totalSpent = 0;

        Set<HashMap.Entry<Integer, Integer>> entrySet = foodOrdered.entrySet();
        Iterator<HashMap.Entry<Integer, Integer>> entrySetIterator = entrySet.iterator();
        while (entrySetIterator.hasNext()) {

            HashMap.Entry entry = entrySetIterator.next();
            int id = (int) entry.getKey();
            int quantity = (int) entry.getValue();

            totalSpent += foodDatabase.getPriceById(id) * quantity;
        }

        return totalSpent;
    }

    public void setTotalSpent(double totalSpent) {
        this.totalSpent = totalSpent;
    }
}
