package miage.istic.com.asianmarketfinder.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import miage.istic.com.asianmarketfinder.database.favoris.FavorisDAO;
import miage.istic.com.asianmarketfinder.database.sto_tag.Sto_tagDAO;
import miage.istic.com.asianmarketfinder.database.store.StoreDAO;
import miage.istic.com.asianmarketfinder.database.tag.TagDAO;
import miage.istic.com.asianmarketfinder.database.user.UserDAO;

/**
 * Created by Rom on 11/21/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserDAO.TABLE_CREATE);
        db.execSQL(StoreDAO.TABLE_CREATE);
        db.execSQL(TagDAO.TABLE_CREATE);
        db.execSQL(FavorisDAO.TABLE_CREATE);
        db.execSQL(Sto_tagDAO.TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(UserDAO.TABLE_DROP);
        db.execSQL(StoreDAO.TABLE_DROP);
        db.execSQL(TagDAO.TABLE_DROP);
        db.execSQL(FavorisDAO.TABLE_DROP);
        db.execSQL(Sto_tagDAO.TABLE_DROP);
        onCreate(db);
    }
}
