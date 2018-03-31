package team4.sdp.uconn.sdp2018_team4;

import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import team4.sdp.uconn.sdp2018_team4.database.Database;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

public class Favourite extends AppCompatActivity {

    private static final String TAG = "Favourite";
    
    ListView favListView;

    ArrayList<String> mName = new ArrayList<String>();
    ArrayList<String> mUrl = new ArrayList<String>();

    //ArrayAdapter<String> mAdapterList;

    Database mDataBaseFav;
    SQLiteDatabase mData;

    //List<String> test = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fav_list);

        favListView = findViewById(R.id.list_view_music);

        mBuildListView();

        //To Update or delete the Music
        favListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final String oldName = (String) parent.getItemAtPosition(position);
                showDialog(oldName);

            }
        });
    }


    //build list view
    public void mBuildListView(){

        mDataBaseFav = new Database(this);
        mDataBaseFav.getData();

        mName = mDataBaseFav.getmMusicName();
        mUrl = mDataBaseFav.getmMusicUrl();

        final arrayAdapter mAdapter = new arrayAdapter(this, android.R.layout.simple_list_item_1, mName);
        favListView.setAdapter(mAdapter);
    }

    //Adapter for the array list -List View-
    private class arrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public arrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

    //Dialog to update or delete
    public void showDialog(final String oldName){

        final EditText mEditText = new EditText(this);
        mEditText.setText(oldName);
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Update -Music-");
        //builder.setMessage("Upate or delete");
        builder.setView(mEditText);


        builder.setPositiveButton("Update",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Update
                        mDataBaseFav.updateData(oldName, mEditText.getText().toString());
                        mBuildListView();
                        makeText(getApplicationContext(),mEditText.getText().toString() + "\n is updated" , LENGTH_LONG).show();
                    }
                });

        /*builder.setPositiveButton("Delete",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Delete
                        mDataBaseFav.deleteData(mEditText.getText().toString());
                        makeText(getApplicationContext(),mEditText.getText().toString() + "\n is deleted" , LENGTH_LONG).show();
                    }
                });*/

        builder.setNegativeButton("Delete",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Delete
                        mDataBaseFav.deleteData(mEditText.getText().toString());
                        mBuildListView();
                        makeText(getApplicationContext(),mEditText.getText().toString() + "\n is deleted" , LENGTH_LONG).show();
                    }
                });

        builder.show();

    }
}
