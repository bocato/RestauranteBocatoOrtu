package comp.ufu.restaurante.database.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import comp.ufu.restaurante.database.FoodDatabase;
import comp.ufu.restaurante.model.Order;

/**
 * Created by bocato on 10/06/15.
 */
public class OrderOperations {

    // Database fields
    private DataBaseWrapper dbHelper;
    private String[] ORDERS_TABLE_COLUMNS = { DataBaseWrapper.ORDER_ID, DataBaseWrapper.ORDER_TABLE, DataBaseWrapper.ORDER_DATA };
    private SQLiteDatabase database;

    public OrderOperations(Context context) {
        dbHelper = new DataBaseWrapper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Order addOrder(Order order) {

        ContentValues values = new ContentValues();

        values.put(DataBaseWrapper.ORDER_TABLE, order.getTable());
        values.put(DataBaseWrapper.ORDER_DATA, order.getOrders());

        long orderID = database.insert(DataBaseWrapper.ORDERS, null, values);

        // now that the student is created return it ...
        Cursor cursor = database.query(DataBaseWrapper.ORDERS,
                ORDERS_TABLE_COLUMNS, DataBaseWrapper.ORDER_ID + " = "
                        + orderID, null, null, null, null);

        cursor.moveToFirst();

        Order newComment = parseOrder(cursor);
        cursor.close();
        return newComment;
    }

    public void deleteOrder(Order order) {
        long id = order.getId();
        System.out.println("Order deleted with id: " + id);
        database.delete(DataBaseWrapper.ORDERS, DataBaseWrapper.ORDER_ID
                + " = " + id, null);
    }

    public List getAllOrders() {
        List orders = new ArrayList();

        Cursor cursor = database.query(DataBaseWrapper.ORDERS,
                ORDERS_TABLE_COLUMNS, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Order order = parseOrder(cursor);
            orders.add(order);
            cursor.moveToNext();
        }

        cursor.close();
        return orders;
    }

    private Order parseOrder(Cursor cursor) {
        Order order = new Order();
        order.setId((cursor.getInt(0)));
        order.setTable(cursor.getString(1));
        int[] foodOrdered = parseFoodOrdered(cursor.getString(2));
        order.setFoodOrdered(foodOrdered);
        return order;
    }

    private int[] parseFoodOrdered(String orders){
        int[] foodOrdered = new int[FoodDatabase.getInstance().getCardapio().size()];

        String[] split1 = orders.split("-");
        for (int i = 0; i < split1.length; i++){
            String[] split2 = split1[i].split("#");
            int id = Integer.parseInt(split2[0]);
            int quantity = Integer.parseInt(split2[1]);
            foodOrdered[id] = quantity;
            //System.out.println(i+" ~ foodOrdered["+id+"] = "+quantity);
        }
        return foodOrdered;
    }

}
