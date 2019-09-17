package com.example.flashlight;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity
{
    private ImageView imageButton;
    private Camera camera;
    private Camera.Parameters parameters;
    private boolean isFlash = false;
    private boolean isOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageButton = findViewById(R.id.image_button);

        if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH))
        {
            camera = Camera.open();
            parameters = camera.getParameters();
            isFlash = true;
        }

        imageButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (isFlash)
                {
                    if (!isOn)
                    {
                        imageButton.setImageResource(R.drawable.ic_power_on);
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                        camera.setParameters(parameters);
                        camera.startPreview();
                        isOn = true;
                    }
                    else
                    {
                        imageButton.setImageResource(R.drawable.ic_power_off);
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                        camera.setParameters(parameters);
                        camera.stopPreview();
                        isOn = false;
                    }

                }
                else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Error...");
                    builder.setMessage("Flash Light is not available for this device...");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int i)
                        {
                            dialog.dismiss();
                            finish();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        if (camera != null)
        {
            camera.release();
            camera = null;
        }
    }
}
