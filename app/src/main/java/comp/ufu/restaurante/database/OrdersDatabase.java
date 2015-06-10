package comp.ufu.restaurante.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import comp.ufu.restaurante.model.Order;

/**
 * Created by bocato on 5/26/15.
 */
public class OrdersDatabase extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "restaurant_orders";

    // table name
    private static final String TABLE_ORDERS = "orders";

    // Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TABLE = "table";
    private static final String KEY_PRODUCT_QUANTITY = "product_quantity"; // represented by 1#2.1#3.1#4... being product_1+quantity, product_2+quantity
    private final ArrayList<Order> orders_list = new ArrayList<Order>();

    public OrdersDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_ORDERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TABLE + " TEXT," + KEY_PRODUCT_QUANTITY + " TEXT)";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding
    public void addOrder(Order order) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TABLE, order.getTable());
        values.put(KEY_PRODUCT_QUANTITY, order.getOrders());
        // Inserting Row
        db.insert(TABLE_ORDERS, null, values);
        db.close(); // Closing database connection
    }

    // Getting single
    Order getOrder(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ORDERS, new String[] { KEY_ID,
                        KEY_TABLE, KEY_PRODUCT_QUANTITY}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Order order = parseOrder(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));

        cursor.close();
        db.close();

        return order;
    }

    private Order parseOrder(int id, String table, String orders){
        Order myOrder = new Order();
        myOrder.setId(id);
        myOrder.setTable(table);
        int foodOrdered[];

        String[] split1 = orders.split("-");
        foodOrdered = new int[split1.length];
        for(String split: split1){
            String[] split2 = split.split("#");
            int key = Integer.parseInt(split2[0]);
            int value = Integer.parseInt(split2[1]);
            foodOrdered[key] = value;
        }

        myOrder.setFoodOrdered(foodOrdered);
        myOrder.setTotalSpent(myOrder.getTotalSpent());

        return myOrder;
    }

    // Getting All
    public ArrayList<Order> getAllOrders() {
        try {
            orders_list.clear();

            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_ORDERS;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Order order = parseOrder(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));
                    // Adding user to list
                    orders_list.add(order);
                } while (cursor.moveToNext());
            }

            // return contact list
            cursor.close();
            db.close();
            return orders_list;
        } catch (Exception e) {
            Log.e("all_orders", "" + e);
        }

        return orders_list;
    }

    public int updateOrder(Order order) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TABLE, order.getTable());
        values.put(KEY_PRODUCT_QUANTITY, order.getOrders());

        // updating row
        return db.update(TABLE_ORDERS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(order.getId()) });
    }
    
    public void deleteFood(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ORDERS, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    // Getting count
    public int getTotalOrders() {
        String countQuery = "SELECT  * FROM " + TABLE_ORDERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
}
