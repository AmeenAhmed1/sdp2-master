package team4.sdp.uconn.sdp2018_team4.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ameen on 29-Mar-18.
 */

public class DatabaseSQLite extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseSQLite";

    private static final String DATABASE_NAME = "UserManager.db";
    private static final String TABLE_NAME = "usersinfo_table";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "NAME";
    private static final String COL_3 = "TIME";
    private static final String COL_4 = "FAVORITEID";

    private static final String DROP_TABLE = "DROP TABLE IF EXIST" + TABLE_NAME;
    private static final String CREAT_TABLE = "CREATE TABLE " + TABLE_NAME +
            " (" + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_2 + " TEXT, " +
            COL_3 + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, "+
            COL_4 + " TEXT );";

    public DatabaseSQLite(Context context) {
        super(context, DATABASE_NAME, null, 1);

        Log.i(TAG, "DatabaseSQLite: Construcotor");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.i(TAG, "onCreate: Database");

        try {
            //Create the table
            db.execSQL(CREAT_TABLE);
        }catch (SQLException ex){
            Log.i(TAG, "onCreate: Exeption " + ex);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.i(TAG, "onUpgrade: Database");

        try{
            db.execSQL(DROP_TABLE);
            onCreate(db);
        }catch (SQLException ex){
            Log.i(TAG, "onUpgrade: Exception " + ex);
        }
    }
}
