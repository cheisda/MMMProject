package miage.istic.com.asianmarketfinder.database.user;

import android.content.Context;

import miage.istic.com.asianmarketfinder.database.DAOBase;

/**
 * Created by Rom on 11/21/2016.
 */
public class UserDAO extends DAOBase {
    public static final String TAG = "UserDAO.java";
    public static final String TABLE_NAME = "user";
    public static final String KEY = "usr_id";
    public static final String USERNAME = "usr_username";
    public static final String PASSWORD = "usr_password";
    public static final String NAME = "usr_name";
    public static final String FIRSTNAME = "usr_firstname";

    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
            KEY + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
            FIRSTNAME + " VARCHAR(45) NOT NULL, " +
            PASSWORD + " VARCHAR(45) DEFAULT NULL, " +
            NAME + " VARCHAR(45) NOT NULL, " +
            FIRSTNAME + " VARCHAR(45) NOT NULL" +
            ");";

    public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public UserDAO(Context pContext) {
        super(pContext);
    }
}
