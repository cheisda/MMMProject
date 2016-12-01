package miage.istic.com.asianmarketfinder.database.sto_tag;

import android.content.Context;

import miage.istic.com.asianmarketfinder.database.DAOBase;
import miage.istic.com.asianmarketfinder.database.store.StoreDAO;
import miage.istic.com.asianmarketfinder.database.tag.TagDAO;

/**
 * Created by Rom on 12/1/2016.
 */
public class Sto_tagDAO extends DAOBase {
    public static final String TAG = "Sto_tagDAO.java";
    public static final String TABLE_NAME = "sto_tag";
    public static final String KEY = "sto_tag_id";
    public static final String REF_ID_STORE = "sto_id";
    public static final String REF_ID_TAG = "tag_id";

    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
            KEY + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
            REF_ID_STORE + " INTEGER NOT NULL, " +
            REF_ID_TAG + " INTEGER NOT NULL, " +
            "FOREIGN KEY(" + REF_ID_STORE + ") REFERENCES " + StoreDAO.TABLE_NAME + "(" + StoreDAO.KEY + "), " +
            "FOREIGN KEY(" + REF_ID_TAG + ") REFERENCES " + TagDAO.TABLE_NAME + "(" + TagDAO.KEY + ")" +
            ");";

    public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public Sto_tagDAO(Context pContext) {
        super(pContext);
    }
}
