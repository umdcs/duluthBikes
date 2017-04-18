package com.example.sam.duluthbikes;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
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
    Button takePictureButton;
    Button submitPictureButton;
    ImageView imageView;
    public String imageFileName;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 1;
    public static final int RequestPermissionCode = 1;
    public String encodedImage;
    public String path;
    Bitmap bm;
    Bitmap decodedByte;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View myView = inflater.inflate(R.layout.activity_report, container, false);
        mPresenter = new Presenter();
        takePictureButton = (Button) myView.findViewById(R.id.camera);
        submitPictureButton = (Button) myView.findViewById(R.id.camera2);
        submitPictureButton.setVisibility(View.INVISIBLE);
        imageView = (ImageView) myView.findViewById(R.id.image);

        requestCameraPermission();

        takePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePictureButton.setVisibility(View.INVISIBLE);
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

        submitPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                sendPictureToServer(encodedImage);
                submitPictureButton.setVisibility(View.INVISIBLE);
                takePictureButton.setVisibility(View.VISIBLE);
                CharSequence text = "Thank you!";
                Toast toast = Toast.makeText(getContext(), text, Toast.LENGTH_LONG);
                toast.show();
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
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_CANCELED) {
            // if back button pressed, do nothing and go back to ReportFragment.
            submitPictureButton.setVisibility(View.INVISIBLE);
            takePictureButton.setVisibility(View.VISIBLE);

        } else {
            path = "sdcard/Duluth Bikes Pictures/" + imageFileName;
            //imageView.setImageDrawable(Drawable.createFromPath(path));
            try {
                compressImage();
                decompressImage();
                Log.d("Encoded Image:", encodedImage);
                imageView.setImageBitmap(decodedByte);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            submitPictureButton.setVisibility(View.VISIBLE);
            takePictureButton.setVisibility(View.INVISIBLE);
        }
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

    public void compressImage() throws FileNotFoundException {
        //Bitmap bm = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath());
        bm = BitmapFactory.decodeFile(path);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
    }

    public void decompressImage() {
        byte[] decodedString = Base64.decode(encodedImage,Base64.DEFAULT);
        decodedByte = BitmapFactory.decodeByteArray(decodedString,0,decodedString.length);
    }

    public void sendPictureToServer(String picture) {
        mPresenter.sendPictureToServer(picture);
    }
}




