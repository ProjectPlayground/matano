package net.apkode.matano.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import net.apkode.matano.model.Actualite;

import java.util.ArrayList;
import java.util.List;

public class DBActualite extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Matano.db";
    private static final Integer DATBASE_VERSION = 1;
    private static final String TABLE_NAME = "actualites";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_JOUR = "jour";
    private static final String COLUMN_ACTUALITE = "actualite";

    private static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
    private static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY , " + COLUMN_JOUR + " TEXT, " + COLUMN_ACTUALITE + " TEXT );";


    List<Actualite> actualites = new ArrayList<>();

    public DBActualite(Context context) {
        super(context, DATABASE_NAME, null, DATBASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TABLE_DROP);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TABLE_DROP);
        onCreate(db);
    }

    public void createData(Integer id, String jour, String actualite) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("jour", jour);
        contentValues.put("actualite", actualite);

        db.insert(TABLE_NAME, null, contentValues);
    }

    public List<Actualite> getDatas() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + "", null);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            actualites.add(new Actualite(
                    cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_JOUR)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_ACTUALITE))
            ));
        }
        cursor.close();

        return actualites;
    }

    public Cursor getDatasCursor() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from " + TABLE_NAME + "", null);
    }

    public Integer delete(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "id = ? ", new String[]{Integer.toString(id)});
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Delete from " + TABLE_NAME + "");
    }

}
