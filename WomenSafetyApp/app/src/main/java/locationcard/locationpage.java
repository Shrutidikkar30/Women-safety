package locationcard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.womensafetyapp.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class locationpage extends AppCompatActivity {
    TextView txtLat, txtLon, txtAdd, txtLoc, txtCon;
    Button btnLoc;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locationpage);
        txtLat = (TextView) findViewById(R.id.lat);
        txtLon = (TextView) findViewById(R.id.lon);
        txtAdd = (TextView) findViewById(R.id.addr);
        txtLoc = (TextView) findViewById(R.id.loc);
        txtCon = (TextView) findViewById(R.id.con);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        btnLoc = (Button) findViewById(R.id.btnGet);
        btnLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(locationpage.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    showLocation();
                } else {
                    ActivityCompat.requestPermissions(locationpage.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
                }
            }
        });

    }

    private void showLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    Geocoder geocoder = new Geocoder(locationpage.this, Locale.getDefault());
                    try {
                        List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        txtLat.setText("Latitude :- " + addressList.get(0).getLatitude());
                        txtLon.setText("Longitude :- " + addressList.get(0).getLongitude());
                        txtAdd.setText("Address Line :- "+addressList.get(0).getAddressLine(0));
                        txtLoc.setText("Locality :-"+addressList.get(0).getLocality());
                        txtCon.setText("Country  :- "+addressList.get(0).getCountryName());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(locationpage.this, "Location null error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}