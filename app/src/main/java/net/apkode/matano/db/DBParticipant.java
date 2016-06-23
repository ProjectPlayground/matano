package net.apkode.matano.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import net.apkode.matano.model.Participant;

import java.util.ArrayList;
import java.util.List;

public class DBParticipant extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Matano.db";
    private static final Integer DATBASE_VERSION = 1;
    private static final String TABLE_NAME = "participants";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NOM = "nom";
    private static final String COLUMN_PRENOM = "prenom";
    private static final String COLUMN_TELEPHONE = "telephone";
    private static final String COLUMN_IMAGE = "image";
    private static final String COLUMN_PRESENTATION = "presentation";

    private static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
    private static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY , " + COLUMN_NOM + " TEXT, " + COLUMN_PRENOM + " TEXT, " + COLUMN_TELEPHONE + " TEXT, " + COLUMN_IMAGE + " TEXT, " + COLUMN_PRESENTATION + " TEXT );";

    private List<Participant> participants = new ArrayList<>();

    public DBParticipant(Context context) {
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

    public void createData(Integer id, String nom, String prenom, String telephone, String image, String commentaire) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("nom", nom);
        contentValues.put("prenom", prenom);
        contentValues.put("telephone", telephone);
        contentValues.put("image", image);
        contentValues.put("commentaire", commentaire);

        db.insert(TABLE_NAME, null, contentValues);
    }

    public List<Participant> getDatas() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + "", null);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            participants.add(new Participant(
                    cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_NOM)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_PRENOM)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TELEPHONE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_PRESENTATION))
            ));
        }
        cursor.close();

        return participants;
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
