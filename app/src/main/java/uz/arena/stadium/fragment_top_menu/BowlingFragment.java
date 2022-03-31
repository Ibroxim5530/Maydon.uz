package uz.arena.stadium.fragment_top_menu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import uz.arena.stadium.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BowlingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BowlingFragment extends Fragment {
    Button btn_football, btn_tennis, btn_bowling, btn_add;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BowlingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BowlingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BowlingFragment newInstance(String param1, String param2) {
        BowlingFragment fragment = new BowlingFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bowling, container, false);

        btn_football = view.findViewById(R.id.btn_football_home3);
        btn_tennis = view.findViewById(R.id.btn_tennis_home3);
        btn_bowling = view.findViewById(R.id.btn_bowling_home3);
        btn_add = view.findViewById(R.id.btn_add_home3);

        btn_football.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_home, new HomeFragment())
                        .commit();
            }
        });

        btn_tennis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_home, new TennisFragment())
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