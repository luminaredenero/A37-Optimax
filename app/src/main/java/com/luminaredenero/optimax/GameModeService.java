package com.luminaredenero.optimax;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class GameModeService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String currentMode = getCurrentGameMode();
        Toast.makeText(this, "Current Mode: " + currentMode, Toast.LENGTH_SHORT).show();
        return START_NOT_STICKY;
    }

    private String getCurrentGameMode() {
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"su", "-c", "getprop persist.optimax.mode"});
            java.io.BufferedReader reader = new java.io.BufferedReader(
                new java.io.InputStreamReader(process.getInputStream()));
            return reader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
            return "unknown";
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}