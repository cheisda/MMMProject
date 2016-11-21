package miage.istic.com.asianmarketfinder.database.tag;

import android.content.Context;

import miage.istic.com.asianmarketfinder.database.DAOBase;

/**
 * Created by Rom on 11/21/2016.
 */
public class TagDAO extends DAOBase {
    public static final String TAG = "TagDAO.java";
    public static final String TABLE_NAME = "tag";
    public static final String KEY = "tag_id";
    public static final String NAME = "tag_name";

    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
            KEY + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
            NAME + " VARCHAR(45) NOT NULL, " +
            ");";

    public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public TagDAO(Context pContext) {
        super(pContext);
    }
}
