package com.cerezaconsulting.cocosapp.presentation.main;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.cerezaconsulting.cocosapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kath on 1/03/18.
 */

public class PermisosActivity extends AppCompatActivity {

    Map<String, Integer> perms = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear);

        if (Build.VERSION_CODES.M >= 23) {
            if (checkAndRequestPermissions()) {

                startActivity(new Intent(getBaseContext(), MainActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                finish();

                // llamarparse();
            }
        } else {

            startActivity(new Intent(getBaseContext(), MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();

        }

    }


    @Override
    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    private boolean checkAndRequestPermissions() {

        int locationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int location1Permission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int internet = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        int accessnetworkstates = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE);

        List<String> listPermissionsNeeded = new ArrayList<>();
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (location1Permission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (internet != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.INTERNET);
        }
        if (accessnetworkstates != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_NETWORK_STATE);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
            return false;
        }
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.ACCESS_COARSE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.INTERNET, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.ACCESS_NETWORK_STATE, PackageManager.PERMISSION_GRANTED);

                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    if (perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                            perms.get(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                            perms.get(Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED
                            &&
                            perms.get(Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED) {
                        // Intent serviceIntentx = new Intent(this, LocationService.class);
                        // startService(serviceIntentx);

                      /*  Intent serviceIntenta = new Intent(this, TMAsociadosBroadcastReceiver.class);
                        startService(serviceIntenta);*/

                        startActivity(new Intent(getBaseContext(), MainActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                        finish();


                    } else {
                        Log.d("PERMISO DENEGADO", "Some permissions are not granted ask again ");

                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION) ||
                                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION) ||

                                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET) ||
                                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_NETWORK_STATE)
                                ) {
                            showDialogOK("La aplicación necesita todos los permisos para su correcto funcionamiento", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case DialogInterface.BUTTON_POSITIVE:
                                            checkAndRequestPermissions();
                                            break;
                                        case DialogInterface.BUTTON_NEGATIVE:
                                            finish();
                                            break;
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(this, "La aplicación necesita todos los permisos para su correcto funcionamiento", Toast.LENGTH_LONG)
                                    .show();
                            finish();
                        }
                    }
                }
            }
        }
    }

    public boolean verificarPermisos(){

        if(perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                perms.get(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                perms.get(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED &&
                perms.get(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED &&
                perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

                &&
                perms.get(Manifest.permission.WAKE_LOCK) == PackageManager.PERMISSION_GRANTED
                &&
                perms.get(Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED
                &&
                perms.get(Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED
                &&
                perms.get(Manifest.permission.VIBRATE) == PackageManager.PERMISSION_GRANTED
                &&
                perms.get(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED
                &&
                perms.get(Manifest.permission.GET_ACCOUNTS) == PackageManager.PERMISSION_GRANTED){
            return true;
        }

        return false;
    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("CANCEL", okListener)
                .setCancelable(false)
                .create()
                .show();
    }

}
