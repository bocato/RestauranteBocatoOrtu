package comp.ufu.restaurante.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import comp.ufu.restaurante.model.Food;
import comp.ufu.restaurante.model.User;

/**
 * Created by bocato on 5/26/15.
 */
public class FoodDatabase extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "restaurant_menu";

    // table name
    private static final String TABLE_MENU = "menu";

    // Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TYPE = "type";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_PRICE = "price";
    private final ArrayList<Food> food_list = new ArrayList<Food>();

    public FoodDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_MENU + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TYPE + " TEXT," + KEY_DESCRIPTION + " TEXT,"
                + KEY_IMAGE + " TEXT," + KEY_PRICE + " FLOAT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MENU);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding
    public void addFood(Food food) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TYPE, food.getType());
        values.put(KEY_DESCRIPTION, food.getDescription());
        values.put(KEY_IMAGE, food.getImage());
        values.put(KEY_PRICE, food.getPrice());
        // Inserting Row
                db.insert(TABLE_MENU, null, values);
        db.close(); // Closing database connection
    }

    // Getting single
    Food getFood(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_MENU, new String[] { KEY_ID,
                        KEY_TYPE, KEY_DESCRIPTION, KEY_IMAGE, KEY_PRICE}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Food food = new Food(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), Float.parseFloat(cursor.getString(4)));

        cursor.close();
        db.close();

        return food;
    }

    // Getting All
    public ArrayList<Food> getAllFood() {
        try {
            food_list.clear();

            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_MENU;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Food food = new Food();
                    food.setId(Integer.parseInt(cursor.getString(0)));
                    food.setType(cursor.getString(1));
                    food.setDescription(cursor.getString(2));
                    food.setImage(cursor.getString(3));
                    food.setPrice(Float.parseFloat(cursor.getString(4)));
                    // Adding user to list
                    food_list.add(food);
                } while (cursor.moveToNext());
            }

            // return contact list
            cursor.close();
            db.close();
            return food_list;
        } catch (Exception e) {
            Log.e("all_food", "" + e);
        }

        return food_list;
    }

    public int updateFood(Food food) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TYPE, food.getType());
        values.put(KEY_DESCRIPTION, food.getDescription());
        values.put(KEY_IMAGE, food.getImage());
        values.put(KEY_PRICE, food.getPrice());

        // updating row
        return db.update(TABLE_MENU, values, KEY_ID + " = ?",
                new String[] { String.valueOf(food.getId()) });
    }
    
    public void deleteFood(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MENU, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }

    // Getting count
    public int getTotalFoodOnMenu() {
        String countQuery = "SELECT  * FROM " + TABLE_MENU;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    public void populateFoodDatabase(){
        // TODO: Implement Method
    }
}
