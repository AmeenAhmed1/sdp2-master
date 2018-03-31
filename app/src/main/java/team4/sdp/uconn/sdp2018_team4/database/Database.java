package team4.sdp.uconn.sdp2018_team4.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by ameen on 29-Mar-18.
 */

public class Database extends DatabaseSQLite {

    Cursor mCursor;

    ArrayList<String> mMusicName = new ArrayList<String>();
    ArrayList<String> mMusicUrl = new ArrayList<String>();

    SQLiteDatabase db;

    private static final String TAG = "Database";

    public Database(Context context) {
        super(context);
    }


    //insert new data
    public boolean insertData(String name, String Url){

        Log.i(TAG, "insertData: ");

        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME", name);
        contentValues.put("FAVORITEID", Url);

        long result = db.insert("usersinfo_table", null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    //Updating data
    public void updateData(String oldName, String newName){

        Log.i(TAG, "updateData: ");

        db = this.getWritableDatabase();
        String mQuery = "UPDATE usersinfo_table " +
                "SET NAME = '" + newName +"'" +
                " WHERE NAME = '" + oldName + "'";

        try{
            db.execSQL(mQuery);
        }catch (SQLiteException ex){
            Log.i(TAG, "updateData: " + ex);
        }

    }

    //Deleting data
    public void deleteData(String mName){

        db = this.getWritableDatabase();
        String mQuery = "DELETE FROM usersinfo_table "+
                " WHERE NAME = '" + mName + "'";

        db.execSQL(mQuery);
    }


    //Getting the data from SQLite
    public void getData(){

        Log.i(TAG, "getData: ");

        SQLiteDatabase db = this.getWritableDatabase();
        mCursor = db.rawQuery("SELECT * FROM usersinfo_table", null);

        mMusicName.clear();
        mMusicUrl.clear();

        if(mCursor.moveToFirst()){
            do{
                mMusicName.add(mCursor.getString(mCursor.getColumnIndex("NAME")));
                mMusicUrl.add(mCursor.getString(mCursor.getColumnIndex("FAVORITEID")));
            }while (mCursor.moveToNext());
        }
    }

    public ArrayList<String> getmMusicName(){
        return mMusicName;
    }
    public ArrayList<String> getmMusicUrl(){
        return mMusicUrl;
    }

}
