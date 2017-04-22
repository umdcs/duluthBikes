package com.example.sam.duluthbikes;

import android.content.Context;
import android.location.Location;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;

/**

 * Created by Sam on 3/26/2017.
 */

public class Presenter implements ModelViewPresenterComponents.PresenterContract {

    private ModelViewPresenterComponents.Model mModel;
    private ModelViewPresenterComponents.View mView;
    private Context mContext;
    private FragmentActivity mActivity;

    public Presenter(){mModel = new Model();}

    public Presenter(Context context, FragmentActivity activity,ModelViewPresenterComponents.View view){
        mView = view;
        mContext = context;
        mActivity = activity;
    }

    @Override
    public Location getLocationForCamera() {

        // NEED TO START GOOOGLE API CLIENT TO GET LOCATION
        ////// DOES NOT WORK BECAUSE MODEL HAS NOT STARTED. NO MAPS HACE STARTEd.
        //mModel = new Model(mContext,mActivity,this);
        Location loc = mModel.getLocation();
        return  loc;
    }

    @Override
    public void updateMapLocation(){
        mView.locationChanged(mModel.getLocation());
    }

    @Override
    public void clickStart(){
        mModel = new Model(mContext,mActivity,this);
    }

    @Override
    public void pauseRideButton() { mModel.stopLocationUpdates(); }

    @Override
    public void finishRideButton() { mModel.disconnectApiOnFinish(); }

    @Override
    public void connectApi() { mModel.connectApiOnResume(); }

    @Override
    public void notifyRoute(JSONArray route,JSONArray l){mModel.notifyFinishRoute(route,l);}

    @Override
    public void loginUser(String userName, String passWord) {
        mModel= new Model(mContext,mActivity,this);
        mModel.loginAttempt(userName,passWord);
    }

    @Override
    public void sendPictureToServer(String location, String description, String encodedImage) {
        //mModel = new Model(mContext);
        mModel.sendPicture(location, description, encodedImage);
    }

    @Override
    public void returnLogin(String result){mView.userResults(result);}

    @Override
    public void setOurClient(GoogleApiClient googleApiClient) {
        mView.setClient(googleApiClient);
    }

    @Override
    public GoogleApiClient getOurClient() {
        return mView.getClient();
    }
}
