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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import team4.sdp.uconn.sdp2018_team4.localdata.Local_Music;


public class MusicListFragment extends Fragment {
    private RecyclerView mMusicListView;
    private Button mScanMusicButton;
    private EditText mKeywordsEt;
    private Button mSearchBtn;
    private LinearLayout mSearchBar;
    public static final String CUR_MUSIC = "CurrentMusic";
    public static final String MUSIC_LIST= "MusicList";



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_music_list, container, false);
        mMusicListView = rootView.findViewById(R.id.rv_music_list);

        ImageView search_bt = rootView.findViewById(R.id.Music_Button_Search);
        search_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity().getApplicationContext(), MusicService.class);
                startActivity(intent);
            }
        });


        ImageView favBTN = rootView.findViewById(R.id.FavFolder);
        favBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity().getApplicationContext(), Favourite.class);
                startActivity(intent);

            }
        });

        ImageView musicLocal = rootView.findViewById(R.id.music_folder);
        musicLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity().getApplicationContext(), Local_Music.class);
                startActivity(intent);

            }
        });


        return rootView;
    }

}

