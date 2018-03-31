package team4.sdp.uconn.sdp2018_team4.localdata;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
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
import android.widget.VideoView;

import java.util.ArrayList;

import team4.sdp.uconn.sdp2018_team4.R;

public class Local_Video extends AppCompatActivity {

    private static final String TAG = "Local_Video";

    boolean mVideoPermission = false;
    final int mPermissionRequestCode = 1;

    ListView local_video;

    VideoView mVideoView;

    Button mPause, mResume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local__video);

        local_video = findViewById(R.id.video_list);
        mVideoView = findViewById(R.id.videoView);
        mPause = findViewById(R.id.btn_video_pause);
        mResume = findViewById(R.id.btn_video_resume);


        mPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVideoView.pause();
            }
        });

        mResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVideoView.resume();
            }
        });

        gettingPermission();


        local_video.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                mVideoView.setVideoPath(mVideo.get(position).path);
                mVideoView.start();
            }
        });
    }



    //build list view
    public void mBuildListView(){

        Log.i(TAG, "mBuildListView: " + mVideo.size());

        if(mVideo.size() > 0){
            //final ArrayAdapter mAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mVideo);
            local_video.setAdapter(new myAdapter());
        }

    }

    //To get all videos from the phone
    Cursor mCursor;
    ArrayList<Music_info> mVideo = new ArrayList<Music_info>();
    public void getVideo() {

        Uri mUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String mSelection = MediaStore.Video.Media.DATA + "!=0";

        mCursor = managedQuery(mUri, null, mSelection, null, null);

        if (mCursor != null) {
            if (mCursor.moveToFirst()) {
                do {
                    String videoName = mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                    String videoPath = mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Media.DATA));

                    mVideo.add(new Music_info(videoPath, videoName));

                } while (mCursor.moveToNext());
            }
            mBuildListView();
            mCursor.close();
        }
    }


    private class myAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mVideo.size();
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
            Music_info mInfo = mVideo.get(position);
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
            mVideoPermission = true;
            getVideo();
        }else{
            // Permission not granted
            Toast.makeText(this, "You need to allow access", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, mPermissionRequestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        mVideoPermission = false;
        switch (requestCode){
            case mPermissionRequestCode:{

                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    mVideoPermission = true;
                    getVideo();
                }
            }
        }

    }
}
