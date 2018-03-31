package team4.sdp.uconn.sdp2018_team4.localdata;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import team4.sdp.uconn.sdp2018_team4.R;

/**
 * Created by ameen on 31-Mar-18.
 */

public class Local_Music extends AppCompatActivity{

    private static final String TAG = "Local_Music";
    
    ListView local_music;

    MediaPlayer mp;

    Button mPlay, mPause, mStop;

    boolean mMusicPermission = false;
    final int mPermissionRequestCode = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.local_music);

        local_music = findViewById(R.id.local_music_list);

        gettingPermission();


        mPlay = findViewById(R.id.btn_play);
        mPause = findViewById(R.id.btn_pause);
        mStop = findViewById(R.id.btn_stop);


        mPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();
            }
        });

        mPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.pause();
            }
        });

        mStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();
            }
        });

        mp = new MediaPlayer();
        local_music.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                try {
                    mp.stop();
                    mp = new MediaPlayer();
                    mp.setDataSource(mMusic.get(position).path);
                    mp.prepare();
                    mp.start();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

    }


    //To get all music from the phone
    Cursor mCursor;
    ArrayList<Music_info> mMusic = new ArrayList<Music_info>();
    public void getMusic() {

        Uri mUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String mSelection = MediaStore.Audio.Media.IS_MUSIC + "!=0";

        mCursor = managedQuery(mUri, null, mSelection, null, null);

        if (mCursor != null) {
            if (mCursor.moveToFirst()) {
                do {
                    String musicName = mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                    String musicPath = mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Media.DATA));

                    mMusic.add(new Music_info(musicPath, musicName));

                } while (mCursor.moveToNext());
            }
            mBuildListView();
            mCursor.close();
        }
    }


    //build list view
    public void mBuildListView(){

        Log.i(TAG, "mBuildListView: " + mMusic.size());
        
        if(mMusic.size() > 0){
            //final ArrayAdapter mAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mMusic);
            local_music.setAdapter(new myAdapter());
        }

    }

    private class myAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mMusic.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.list_item, null);
            Music_info mInfo = mMusic.get(position);
            TextView mText = view.findViewById(R.id.list_item_data);
            mText.setText(mInfo.music_name);

            return view;
        }
    }

    //Getting permission
    public void gettingPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED){
            //Permission Granted
            mMusicPermission = true;
            getMusic();
        }else{
            // Permission not granted
            Toast.makeText(this, "You need to allow access", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, mPermissionRequestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        mMusicPermission = false;
        switch (requestCode){
            case mPermissionRequestCode:{

                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    mMusicPermission = true;
                    getMusic();
                }
            }
        }

    }
}
