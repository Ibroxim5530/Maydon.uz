package uz.arena.stadium.fragment_top_menu;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import uz.arena.stadium.OrderAdapter;
import uz.arena.stadium.OrderItem;
import uz.arena.stadium.R;

public class TennisFragment extends Fragment implements OnMapReadyCallback {
    private DatabaseReference RootRef;
    SupportMapFragment mapFragment;
    private GoogleMap map;
    Button btn_football, btn_tennis, btn_bowling, btn_add;
    RecyclerView recyclerView;
    OrderAdapter stadiumAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_tennis, container, false);
        mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map_tennis);
        mapFragment.getMapAsync(this);
        recyclerView = view.findViewById(R.id.recycler_tennis);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true));
        RootRef = FirebaseDatabase.getInstance().getReference();

        btn_football = view.findViewById(R.id.btn_football_home2);
        btn_tennis = view.findViewById(R.id.btn_tennis_home2);
        btn_bowling = view.findViewById(R.id.btn_bowling_home2);
        btn_add = view.findViewById(R.id.btn_add_home2);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("order_arena").child("Tennis");

        FirebaseRecyclerOptions<OrderItem> options =
                new FirebaseRecyclerOptions.Builder<OrderItem>()
                        .setQuery(reference, OrderItem.class)
                        .build();
        stadiumAdapter = new OrderAdapter(options);
        recyclerView.setAdapter(stadiumAdapter);

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

    @Override
    public void onStart() {
        super.onStart();
        stadiumAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        stadiumAdapter.stopListening();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;

        map.setMinZoomPreference(11.0f);
        LatLng oddiyy = new LatLng(41.2976238, 69.3559075);

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(oddiyy, 11.0f));

        RootRef.child("order_arena").child("Tennis")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot dsp : snapshot.getChildren()) {
                            double Lat = Double.parseDouble(snapshot.child(dsp.getKey()).child("latitude").getValue().toString());
                            double Long = Double.parseDouble(snapshot.child(dsp.getKey()).child("longitude").getValue().toString());
                            String name_stadium = snapshot.child(dsp.getKey()).child("arena_name").getValue().toString();
                            LatLng position = new LatLng(Lat, Long);

                            BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.icon_cart);
                            Bitmap b = bitmapdraw.getBitmap();
                            int height = 75;
                            int width = 130;
                            Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

                            Marker marker = map.addMarker(new MarkerOptions()
                                    .position(position)
                                    .title(name_stadium)
                                    .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
                            marker.setTag(position);

                            map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                @Override
                                public boolean onMarkerClick(Marker mark) {
                                    LatLng posit = (LatLng) mark.getTag();
                                    Toast.makeText(getContext(), posit.toString(), Toast.LENGTH_SHORT).show();

                                    return false;
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}