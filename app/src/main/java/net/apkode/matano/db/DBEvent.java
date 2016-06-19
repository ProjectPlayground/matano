package net.apkode.matano.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import net.apkode.matano.model.Event;

import java.util.ArrayList;
import java.util.List;


public class DBEvent extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Matano.db";
    private static final Integer DATBASE_VERSION = 2;
    private static final String TABLE_NAME = "events";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_CATEGORIE = "categorie";
    private static final String COLUMN_RUBRIQUE = "rubrique";
    private static final String COLUMN_TITRE = "titre";
    private static final String COLUMN_TARIF = "tarif";
    private static final String COLUMN_LIEU = "lieu";
    private static final String COLUMN_PRESENTATION = "presentation";
    private static final String COLUMN_IMAGE = "image";
    private static final String COLUMN_HORAIRE = "horaire";
    private static final String COLUMN_LIEN = "lien";
    private static final String COLUMN_JOUR = "jour";
    private static final String COLUMN_VIDEO = "video";
    private static final String COLUMN_IMAGEFULL = "imagefull";

    private static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
    private static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY , " + COLUMN_CATEGORIE + " TEXT, " + COLUMN_RUBRIQUE + " TEXT, " + COLUMN_TITRE + " TEXT, " + COLUMN_TARIF + " TEXT, " + COLUMN_LIEU + " TEXT, " + COLUMN_PRESENTATION + " TEXT, " + COLUMN_IMAGE + " TEXT, " + COLUMN_HORAIRE + " TEXT, " + COLUMN_LIEN + " TEXT, " + COLUMN_JOUR + " TEXT, " + COLUMN_VIDEO + " TEXT, " + COLUMN_IMAGEFULL + " TEXT );";


    private List<Event> events = new ArrayList<>();

    public DBEvent(Context context) {
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

    /**
     * Enregistrement d'un event
     *
     * @param id
     * @param categorie
     * @param rubrique
     * @param titre
     * @param tarif
     * @param lieu
     * @param presentation
     * @param image
     * @param horaire
     * @param lien
     * @param jour
     * @param video
     * @param imagefull
     */
    public void createData(Integer id, String categorie, String rubrique, String titre, String tarif, String lieu, String presentation, String image, String horaire, String lien, String jour, String video, String imagefull) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("categorie", categorie);
        contentValues.put("rubrique", rubrique);
        contentValues.put("titre", titre);
        contentValues.put("tarif", tarif);
        contentValues.put("lieu", lieu);
        contentValues.put("presentation", presentation);
        contentValues.put("image", image);
        contentValues.put("horaire", horaire);
        contentValues.put("lien", lien);
        contentValues.put("jour", jour);
        contentValues.put("video", video);
        contentValues.put("imagefull", imagefull);

        db.insert(TABLE_NAME, null, contentValues);
    }

    /**
     * Liste de tous les events
     *
     * @return
     */
    public List<Event> getDatas() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + "", null);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            events.add(new Event(
                    cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORIE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_RUBRIQUE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TITRE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TARIF)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_LIEU)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_PRESENTATION)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_HORAIRE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_LIEN)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_JOUR)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_VIDEO)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_IMAGEFULL))
            ));
        }
        cursor.close();

        return events;
    }

    /**
     * Liste de tous les events cursor
     *
     * @return
     */
    public Cursor getDatasCursor() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from " + TABLE_NAME + "", null);
    }

    /**
     * Liste de tous les events
     *
     * @return
     */
    public List<Event> getDatasByCategorie(String categorie) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " WHERE categorie='" + categorie + "' order by id Desc", null);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            events.add(new Event(
                    cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORIE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_RUBRIQUE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TITRE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TARIF)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_LIEU)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_PRESENTATION)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_HORAIRE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_LIEN)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_JOUR)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_VIDEO)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_IMAGEFULL))
            ));
        }
        cursor.close();

        return events;
    }

    /**
     * Suppression d'un event
     *
     * @param id
     * @return
     */
    public Integer delete(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "id = ? ", new String[]{Integer.toString(id)});
    }

    /**
     * Mise à jour d'un event
     *
     * @param id
     * @param categorie
     * @param rubrique
     * @param titre
     * @param tarif
     * @param lieu
     * @param presentation
     * @param image
     * @param horaire
     * @param lien
     * @param jour
     * @param video
     * @param imagefull
     * @return
     */
    public boolean update(Integer id, String categorie, String rubrique, String titre, String tarif, String lieu, String presentation, String image, String horaire, String lien, String jour, String video, String imagefull) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("categorie", categorie);
        contentValues.put("rubrique", rubrique);
        contentValues.put("titre", titre);
        contentValues.put("tarif", tarif);
        contentValues.put("lieu", lieu);
        contentValues.put("presentation", presentation);
        contentValues.put("image", image);
        contentValues.put("horaire", horaire);
        contentValues.put("lien", lien);
        contentValues.put("jour", jour);
        contentValues.put("video", video);
        contentValues.put("imagefull", imagefull);

        db.update(TABLE_NAME, contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

}
