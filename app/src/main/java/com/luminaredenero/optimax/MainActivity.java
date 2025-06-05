package com.luminaredenero.optimax;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private RadioGroup modeGroup;
    private Switch langSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        modeGroup = findViewById(R.id.modeGroup);
        langSwitch = findViewById(R.id.langSwitch);

        modeGroup.setOnCheckedChangeListener((group, checkedId) -> {
            String mode = "balanced";
            switch (checkedId) {
                case R.id.radioUltra: mode = "ultra"; break;
                case R.id.radioBalanced: mode = "balanced"; break;
                case R.id.radioPerf: mode = "performance"; break;
                case R.id.radioBattery: mode = "battery"; break;
            }
            setProp("persist.optimax.mode", mode);
        });

        langSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String lang = isChecked ? "id" : "en";
            LocaleHelper.setLocale(this, lang);
            recreate();
        });
    }

    private void setProp(String key, String value) {
        try {
            Process p = Runtime.getRuntime().exec(new String[]{"su", "-c", "setprop " + key + " " + value});
            p.waitFor();
            Toast.makeText(this, "Mode: " + value, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to setprop", Toast.LENGTH_SHORT).show();
        }
    }
}