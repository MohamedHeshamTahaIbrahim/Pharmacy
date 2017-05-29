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

public class DAOdbCapture {

    private SQLiteDatabase database;
    private DBHelperImage dbHelper;

    public DAOdbCapture(Context context) {
        dbHelper = new DBHelperImage(context);
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
        cv.put(DBHelperImage.COLUMN_PATH, image.getPath());
        cv.put(DBHelperImage.COLUMN_TITLE, image.getTitle());
        cv.put(DBHelperImage.COLUMN_DESCRIPTION, image.getDescription());
        cv.put(DBHelperImage.COLUMN_DATETIME, System.currentTimeMillis());
        return database.insert(DBHelperImage.TABLE_NAME, null, cv);
    }

    /**
     * delete the given image from database
     *
     * @param image
     */
    public void deleteImage(MyImage image) {
        String whereClause =
                DBHelperImage.COLUMN_TITLE + "=? AND " + DBHelperImage.COLUMN_DATETIME +
                        "=?";
        String[] whereArgs = new String[]{image.getTitle(),
                String.valueOf(image.getDatetimeLong())};
        database.delete(DBHelperImage.TABLE_NAME, whereClause, whereArgs);
    }

    /**
     * @return all image as a List
     */
    public List<MyImage> getImages() {
        List<MyImage> MyImages = new ArrayList<>();
        Cursor cursor =
                database.query(DBHelperImage.TABLE_NAME, null, null, null, null,
                        null, DBHelperImage.COLUMN_DATETIME + " DESC");
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
                cursor.getString(cursor.getColumnIndex(DBHelperImage.COLUMN_PATH)));
        image.setTitle(
                cursor.getString(cursor.getColumnIndex(DBHelperImage.COLUMN_TITLE)));
        image.setDatetime(cursor.getLong(
                cursor.getColumnIndex(DBHelperImage.COLUMN_DATETIME)));
        image.setDescription(cursor.getString(
                cursor.getColumnIndex(DBHelperImage.COLUMN_DESCRIPTION)));
        return image;
    }
}
