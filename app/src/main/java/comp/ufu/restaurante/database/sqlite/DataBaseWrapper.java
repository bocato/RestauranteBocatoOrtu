package comp.ufu.restaurante.database.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by bocato on 10/06/15.
 */
public class DataBaseWrapper extends SQLiteOpenHelper {

    public static final String ORDERS = "Orders";
    public static final String ORDER_ID = "_id";
    public static final String ORDER_TABLE = "_table";
    public static final String ORDER_DATA = "_data";

    private static final String DATABASE_NAME = "orders.db";
    private static final int DATABASE_VERSION = 1;

    // creation SQLite statement
    private static final String DATABASE_CREATE = "CREATE TABLE " + ORDERS
            + "(" + ORDER_ID + " " +
            "INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ORDER_TABLE + " TEXT NOT NULL, "
            + ORDER_DATA + " TEXT NOT NULL)";

    public DataBaseWrapper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ORDERS);
        onCreate(db);
    }

}
