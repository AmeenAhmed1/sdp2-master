package team4.sdp.uconn.sdp2018_team4;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import team4.sdp.uconn.sdp2018_team4.localdata.Local_Video;


public class VideoListFragment extends Fragment {
    private RecyclerView mVideoListView;
    private Button mScanVideoBtn, mLocalVideo;

    public static final String CUR_VIDEO = "CurrentVideo";
    public static final String VIDEO_LIST= "VideoList";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_video_list, container, false);

        ImageView search_bt = rootView.findViewById(R.id.Video_Button_Search);

        search_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), VideoService.class);
                startActivity(intent);
            }
        });



        ImageView local_video = rootView.findViewById(R.id.video_folder);

        local_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), Local_Video.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
}
