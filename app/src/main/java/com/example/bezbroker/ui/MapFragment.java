package com.example.bezbroker.ui;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bezbroker.ApiClient;
import com.example.bezbroker.R;
import com.example.bezbroker.SessionManager;
import com.example.bezbroker.model.Estate;

import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.List;

public class MapFragment extends Fragment {

    private MapView map;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Configuration.getInstance().load(
                requireContext().getApplicationContext(),
                PreferenceManager.getDefaultSharedPreferences(
                        requireContext().getApplicationContext())
                );

        Configuration.getInstance().setUserAgentValue(requireContext().getPackageName());

        View root = inflater.inflate(R.layout.fragment_map, container,false);

        map = root.findViewById(R.id.mapView);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setMultiTouchControls(true);
        map.getController().setZoom(9);
        map.getController().setCenter(new GeoPoint(42.7339, 25.4858));

        loadEstates();

        return root;
    }

    @Override
    public void onPause() {
        super.onPause();
        if(map != null){
            map.onPause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(map != null){
            map.onResume();
        }
    }

    private void loadEstates() {

        String token = new SessionManager(requireContext()).getToken();

        ApiClient.get("/api/estates", token, new ApiClient.Callback() {
            @Override
            public void onSuccess(JSONObject body) {
                try {
                    List<Estate> estates = Estate.listFromJson(body.optJSONArray("estates"));

                    for(Estate e : estates){

                        Marker m = new Marker(map);
                        m.setPosition(new GeoPoint(e.lat, e.lont));
                        m.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                        m.setTitle(e.title);
                        m.setSnippet(e.city + ":" + e.area + ":" + e.price + "euro");

                        m.setOnMarkerClickListener((marker, map) ->{
                            //TODO: add detailed page view
                            return true;
                        });

                        map.getOverlays().add(m);
                    }

                    map.invalidate();

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onError(int httpCode, String message) {

            }
        });
    }
}
