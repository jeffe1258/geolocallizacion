package com.facci.gps_jjad;
        import android.content.pm.PackageManager;
        import android.support.v4.content.ContextCompat;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;

        import java.util.List;
        import android.location.Location;
        import android.location.LocationListener;
        import android.location.LocationManager;
        import android.location.LocationProvider;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.Toast;

public class MainActivityJJAD extends AppCompatActivity {

    LocationManager locationM;
    private Double latitud;
    private Double longitud;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_jjad);
        locationM = (LocationManager)getSystemService(LOCATION_SERVICE);
        List<String> Providers = locationM.getAllProviders();
        LocationProvider Provider = locationM.getProvider(Providers.get(0));

        int precision = Provider.getAccuracy();
        boolean getAlt = Provider.supportsAltitude();
        int consumo = Provider.getPowerRequirement();

        Toast.makeText(MainActivityJJAD.this,
                String.format("Precision : %s \n Altitud : %s \n Consumo Recursos : %s",
                        String.valueOf(precision),String.valueOf(getAlt),String.valueOf(consumo)),
                Toast.LENGTH_LONG).show();
    }


    public void ActualizarCoordenadasClick(View v){

        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
        }

        locationM.requestLocationUpdates(LocationManager.GPS_PROVIDER,2*60*1000,10,locListenerGPS);

    }

    private final LocationListener locListenerGPS = new LocationListener() {
        public void onLocationChanged(Location location) {
            longitud = location.getLongitude();
            latitud = location.getLatitude();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    EditText txtLongitud = (EditText) findViewById(R.id.txtLongitud);
                    EditText txtLatitud = (EditText) findViewById(R.id.txtLatitud);
                    txtLongitud.setText(longitud + "");
                    txtLatitud.setText(latitud + "");
                    Toast.makeText(MainActivityJJAD.this, "Coordenadas GPS Capturadas", Toast.LENGTH_SHORT).show();
                }
            });

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }

    };
}
