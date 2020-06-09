package com.example.mycontentprovider2;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class MyContentProvider extends ContentProvider {
    MyDbHelper dbHelper;

    public static String DB_TABLE="mydbtable";
    public static final String AUTHORITY="com.example.mycontentprovider.provider";
    public static final Uri CONTENT_URI=Uri.parse("content://"+AUTHORITY+"/"+DB_TABLE);

    public static int EMP=1;
    public static int NAME=2;

    static UriMatcher matcher=new UriMatcher(UriMatcher.NO_MATCH);

    static {
        matcher.addURI(AUTHORITY,""+DB_TABLE,EMP);
        matcher.addURI(AUTHORITY,""+DB_TABLE+"/#",NAME);
    }

    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long row=dbHelper.getWritableDatabase().insert(DB_TABLE,null,values);
        if(row>0) {
            uri = ContentUris.withAppendedId(CONTENT_URI, row);
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return uri;
    }

    @Override
    public boolean onCreate() {
        dbHelper=new MyDbHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder builder=new SQLiteQueryBuilder();
        builder.setTables(DB_TABLE);
        Cursor c=builder.query(dbHelper.getWritableDatabase(),null,null,null,null,null,"_id");
        c.setNotificationUri(getContext().getContentResolver(),uri);
        return c;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
