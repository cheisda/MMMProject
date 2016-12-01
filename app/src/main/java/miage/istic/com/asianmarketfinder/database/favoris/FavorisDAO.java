package miage.istic.com.asianmarketfinder.database.favoris;

import android.content.Context;

import miage.istic.com.asianmarketfinder.database.DAOBase;
import miage.istic.com.asianmarketfinder.database.store.StoreDAO;
import miage.istic.com.asianmarketfinder.database.user.UserDAO;

/**
 * Created by Rom on 12/1/2016.
 */
public class FavorisDAO extends DAOBase {
    public static final String TAG = "FavorisDAO.java";
    public static final String TABLE_NAME = "favoris";
    public static final String KEY = "fav_id";
    public static final String REF_ID_USER = "usr_id";
    public static final String REF_ID_STORE = "sto_id";

    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
            KEY + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
            REF_ID_USER + " INTEGER NOT NULL, " +
            REF_ID_STORE + " INTEGER NOT NULL, " +
            "FOREIGN KEY(" + REF_ID_USER + ") REFERENCES " + UserDAO.TABLE_NAME + "(" + UserDAO.KEY + "), " +
            "FOREIGN KEY(" + REF_ID_STORE + ") REFERENCES " + StoreDAO.TABLE_NAME + "(" + StoreDAO.KEY + ")" +
            ");";

    public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";


    public FavorisDAO(Context pContext) {
        super(pContext);
    }
}
