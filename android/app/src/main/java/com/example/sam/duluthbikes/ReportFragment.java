package com.example.sam.duluthbikes;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
    EditText editDescription;
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
    String imageLocation = "Unable to find location";
    String imageDescription = "No Description Given.";
    String picLoc;
    Location pictureLocation;
    TextView locTV;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View myView = inflater.inflate(R.layout.activity_report, container, false);
        mPresenter = new Presenter();
        imageView = (ImageView) myView.findViewById(R.id.image);
        editDescription = (EditText) myView.findViewById(R.id.imageDescription);
        takePictureButton = (Button) myView.findViewById(R.id.camera);
        submitPictureButton = (Button) myView.findViewById(R.id.camera2);
        submitPictureButton.setVisibility(View.INVISIBLE);
        //locTV = (TextView) myView.findViewById(R.id.locTV);
        //locTV.setText("");

        requestCameraPermission();

        //Overrides return key to close IME (keyboard) rather than create a new line.
        editDescription.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent keyevent) {
                //If the keyevent is a key-down event on the "enter" button
                if ((keyevent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    if (myView != null) {
                        InputMethodManager imm = (InputMethodManager)getContext().getSystemService(getContext().INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    return true;
                }
                return false;
            }
        });

        //Take picture button - gets your location at this time.
        takePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePictureButton.setVisibility(View.INVISIBLE);
                if ((ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)&&
                        (ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
                    LocationManager lm = (LocationManager)getContext().getSystemService(getContext().LOCATION_SERVICE);
                    Criteria criteria = new Criteria();
                    criteria.setAccuracy(Criteria.ACCURACY_FINE);
                    lm.getBestProvider(criteria,true);
                    Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    double longitude = location.getLongitude();
                    double latitude = location.getLatitude();
                    picLoc = (String.valueOf(latitude) + "," + String.valueOf(longitude));
                    // TO DEBUG. SHOWS LAT LONG IN TEXT VIEW
                    //locTV.setText(String.valueOf(latitude) + String.valueOf(longitude));
                    //Location loc = mPresenter.getLocationForCamera(); //WILL BE FIXING -sam
                    startCamera();
                } else {
                    CharSequence text = "You need to grant camera and storage permissions";
                    Toast toast = Toast.makeText(getContext(), text, Toast.LENGTH_SHORT);
                    toast.show();
                    requestCameraPermission();
                    takePictureButton.setVisibility(View.VISIBLE);
                }
            }
        });

        submitPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                sendPictureToServer(imageLocation, imageDescription, encodedImage);
                submitPictureButton.setVisibility(View.INVISIBLE);
                takePictureButton.setVisibility(View.VISIBLE);
                editDescription.setText("");
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
                //Log.d("Encoded Image:", encodedImage);
                imageView.setImageBitmap(decodedByte);
                imageView.setRotation(90);
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
        if (ContextCompat.checkSelfPermission(this.getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this.getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);
        }
    }

    public void compressImage() throws FileNotFoundException {
        //Bitmap bm = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath());
        bm = BitmapFactory.decodeFile(path);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,33,baos);
        byte[] b = baos.toByteArray();
        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
    }

    public void decompressImage() {
        byte[] decodedString = Base64.decode(encodedImage,Base64.DEFAULT);
        decodedByte = BitmapFactory.decodeByteArray(decodedString,0,decodedString.length);
    }

    public void sendPictureToServer(String location, String description, String picture) {

        //pictureLocation = LocationData.getOurInstance(this.getContext()).getLastLocation();
        //imageLocation = pictureLocation.toString();

        imageLocation = picLoc;
        if(editDescription.getText().toString().equals("")){
            mPresenter.sendPictureToServer(imageLocation,imageDescription,picture);
        }else {
            imageDescription = editDescription.getText().toString();
            mPresenter.sendPictureToServer(imageLocation, imageDescription, picture);
        }

    }
}




