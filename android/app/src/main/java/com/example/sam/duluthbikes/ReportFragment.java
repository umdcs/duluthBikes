package com.example.sam.duluthbikes;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Sam on 3/22/2017.
 */

public class ReportFragment extends Fragment {

    int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 2;
    int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE;
    Presenter mPresenter;
    int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1;
    Button button;
    ImageView imageView;
    public String imageFileName;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 1;
    public static final int RequestPermissionCode = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View myView = inflater.inflate(R.layout.activity_report, container, false);
        button = (Button) myView.findViewById(R.id.camera);
        imageView = (ImageView) myView.findViewById(R.id.image);

        requestCameraPermission();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    startCamera();
                } else {
                    CharSequence text = "You need to grant camera permissions";
                    Toast toast = Toast.makeText(getContext(), text, Toast.LENGTH_SHORT);
                    toast.show();
                    requestCameraPermission();
                }
            }
        });
        return myView;
    }

    //Method to start the camera activity.
    public void startCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = getFile();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    //Pulls the picture taken and places it into the image preview.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String path = "sdcard/Duluth Bikes Pictures/" + imageFileName;
        imageView.setImageDrawable(Drawable.createFromPath(path));
    }

    //Method to get a file. Stores Pictures in Device storage/Duluth Bikes Pictures
    private File getFile() {
        File file = new File("sdcard/Duluth Bikes Pictures");
        if (!file.exists()) {
            file.mkdir();
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        imageFileName = "JPEG_" + timeStamp + ".jpg";
        File image_file = new File(file, imageFileName);
        return image_file;
    }


    //Method that requests Camera Permission
    private void requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(this.getContext(),
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this.getActivity(),
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);

        }
    }
}




