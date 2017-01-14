package miage.istic.com.asianmarketfinder.database.store;

import android.content.Context;

import miage.istic.com.asianmarketfinder.database.DAOBase;

/**
 * Created by Rom on 11/21/2016.
 */
public class StoreDAO extends DAOBase {
    public static final String TAG = "StoreDAO.java";
    public static final String TABLE_NAME = "store";
    public static final String KEY = "sto_id";
    public static final String NAME = "sto_name";
    public static final String LONGITUDE = "sto_lon";
    public static final String LATITUDE = "sto_lat";
    public static final String OPENING = "sto_opening";

    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
            KEY + " VARCHAR(45) NOT NULL PRIMARY KEY AUTOINCREMENT, " +
            NAME + " VARCHAR(45) NOT NULL, " +
            LONGITUDE + " REAL NOT NULL, " +
            LATITUDE + " REAL NOT NULL, " +
            OPENING + " TEXT DEFAULT NULL" +
            ");";

    public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";


    public StoreDAO(Context pContext) {
        super(pContext);
    }
}
