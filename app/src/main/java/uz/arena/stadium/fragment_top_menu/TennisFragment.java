package uz.arena.stadium.fragment_top_menu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import uz.arena.stadium.R;

public class TennisFragment extends Fragment {
    Button btn_football, btn_tennis, btn_bowling, btn_add;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public TennisFragment() {

    }

    public static TennisFragment newInstance(String param1, String param2) {
        TennisFragment fragment = new TennisFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_tennis, container, false);

        btn_football = view.findViewById(R.id.btn_football_home2);
        btn_tennis = view.findViewById(R.id.btn_tennis_home2);
        btn_bowling = view.findViewById(R.id.btn_bowling_home2);
        btn_add = view.findViewById(R.id.btn_add_home2);

        btn_football.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_home, new HomeFragment())
                        .commit();
            }
        });

        btn_bowling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_home, new BowlingFragment())
                        .commit();
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_home, new AddFragment())
                        .commit();
            }
        });

        return view;
    }
}