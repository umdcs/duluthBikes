package com.example.sam.duluthbikes;

import android.app.Activity;
import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;

import java.util.List;

/**
<<<<<<< HEAD
 * Created by Sam on 3/26/2017.
 */

public class Presenter implements ModelViewPresenterComponents.PresenterContract {

    private ModelViewPresenterComponents.Model mModel;
    private ModelViewPresenterComponents.View mView;
    private Context mContext;
    private Activity mActivity;

    public Presenter(Context context, Activity activity,ModelViewPresenterComponents.View view){
        mView = view;
        mContext = context;
        mActivity = activity;
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
    public void returnLogin(String result){mView.userResults(result);}
}
