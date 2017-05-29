package com.pharmacy.pharmacy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.pharmacy.pharmacy.Model.MyImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohamed Hesham on 2017-05-27.
 */

public class DAOdbUpload {

    private SQLiteDatabase database;
    private DBHelperUpload dbHelper;

    public DAOdbUpload(Context context) {
        dbHelper = new DBHelperUpload(context);
        database = dbHelper.getWritableDatabase();
    }

    /**
     * close any database object
     */
    public void close() {
        dbHelper.close();
    }

    /**
     * insert a text report item to the location database table
     *
     * @param image
     * @return the row ID of the newly inserted row, or -1 if an error occurred
     */
    public long addImage(MyImage image) {
        ContentValues cv = new ContentValues();
        cv.put(DBHelperUpload.COLUMN_PATH, image.getPath());
        cv.put(DBHelperUpload.COLUMN_TITLE, image.getTitle());
        cv.put(DBHelperUpload.COLUMN_DESCRIPTION, image.getDescription());
        cv.put(DBHelperUpload.COLUMN_DATETIME, System.currentTimeMillis());
        return database.insert(DBHelperUpload.TABLE_NAME, null, cv);
    }

    /**
     * delete the given image from database
     *
     * @param image
     */
    public void deleteImage(MyImage image) {
        String whereClause =
                DBHelperUpload.COLUMN_TITLE + "=? AND " + DBHelperUpload.COLUMN_DATETIME +
                        "=?";
        String[] whereArgs = new String[]{image.getTitle(),
                String.valueOf(image.getDatetimeLong())};
        database.delete(DBHelperUpload.TABLE_NAME, whereClause, whereArgs);
    }

    /**
     * @return all image as a List
     */
    public List<MyImage> getImages() {
        List<MyImage> MyImages = new ArrayList<>();
        Cursor cursor =
                database.query(DBHelperUpload.TABLE_NAME, null, null, null, null,
                        null, DBHelperUpload.COLUMN_DATETIME + " DESC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            MyImage MyImage = cursorToMyImage(cursor);
            MyImages.add(MyImage);
            cursor.moveToNext();
        }
        cursor.close();
        return MyImages;
    }

    /**
     * read the cursor row and convert the row to a MyImage object
     *
     * @param cursor
     * @return MyImage object
     */
    private MyImage cursorToMyImage(Cursor cursor) {
        MyImage image = new MyImage();
        image.setPath(
                cursor.getString(cursor.getColumnIndex(DBHelperUpload.COLUMN_PATH)));
        image.setTitle(
                cursor.getString(cursor.getColumnIndex(DBHelperUpload.COLUMN_TITLE)));
        image.setDatetime(cursor.getLong(
                cursor.getColumnIndex(DBHelperUpload.COLUMN_DATETIME)));
        image.setDescription(cursor.getString(
                cursor.getColumnIndex(DBHelperUpload.COLUMN_DESCRIPTION)));
        return image;
    }
}
