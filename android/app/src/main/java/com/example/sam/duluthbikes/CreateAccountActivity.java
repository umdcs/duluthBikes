package com.example.sam.duluthbikes;

import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by kevin on 3/23/2017.
 */

public class CreateAccountActivity extends AppCompatActivity {
    public String userName = null;
    public int duplicateCode;

    /**
     * Called when the app is created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_create);
        duplicateCode = 200;
    }
    private void storeUser(String userName, Context context) {
        try {
            OutputStreamWriter outputStreamWriter =
                    new OutputStreamWriter(context.openFileOutput("account.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(userName);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
    /**
     * Creates a new user account.
     * @param view The activity view
     */
    public void register(View view) throws JSONException {
        EditText userNameText = (EditText) findViewById(R.id.editTextUsername);
        userName = userNameText.getText().toString();
        if (userName.trim().length() != 0) {
            Log.d("username on register", userName);
            JSONObject user = new JSONObject();
            user.put("Username", userName);
            new CreateAccountActivity.HTTPAsyncTask().execute(MainActivity.ACCOUNT_SERVICE + "/users", "POST", user.toString());
        }
        else {
            Context context = getApplicationContext();
            CharSequence text = "Please enter a username";
            Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            toast.show();
            Intent restart = new Intent(this, CreateAccountActivity.class);
            startActivity(restart);
            finish();
        }
    }

    /**
     * Checks if username already exists on the server.
     */
    public void checkDuplicates() {
        if (duplicateCode == 500) {
            Log.d("on checkDuplicates()", "duplicate name detected!");
            File file = this.getFileStreamPath("account.txt");
            if (file.exists()) {
                file.delete();
            }
            Context context = getApplicationContext();
            CharSequence text = "Username already exists";
            Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            toast.show();
        }
        else {
            userAdded();
        }
    }
    /**
     * Confirms that a user account has been created.
     */
    private void userAdded(){
        storeUser(userName, this);
        Context context = getApplicationContext();
        CharSequence text = "Account created";
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.show();

        Intent backToMap = new Intent(this, MainActivity.class);
        startActivity(backToMap);
        finish();
    }

    /**
     * Private class that extends AsyncTask which then allows the use of the asynchronous methods which
     * are needed in collaboration with the restGET, restPUT, restPOST, and restDELETE methods.
     */
    private class HTTPAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {

            //Initialize the server connection and input stream to null.
            HttpURLConnection serverConnection = null;
            InputStream is = null;

            try {
                URL url = new URL(params[0]);
                serverConnection = (HttpURLConnection) url.openConnection();
                serverConnection.setRequestMethod(params[1]);
                //If the request is either POST PUT or DELETE, set the server connection to allow output.
                if (params[1].equals("POST") ||
                        params[1].equals("PUT") ||
                        params[1].equals("DELETE")) {
                    serverConnection.setDoOutput(true);
                    serverConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");

                    //params[2] contains the JSON String to send, make sure we send the
                    //content length to be the json string length.
                    serverConnection.setRequestProperty("Content-Length", "" +
                            Integer.toString(params[2].toString().getBytes().length));

                    //Send POST data that was provided.
                    DataOutputStream out = new DataOutputStream(serverConnection.getOutputStream());
                    out.writeBytes(params[2].toString());
                    out.flush();
                    out.close();
                }
                //Retrieves the response code from the server.
                duplicateCode = serverConnection.getResponseCode();
                Log.d("Debug:", "\nSending " + params[1] + " request to URL : " + params[0]);
                Log.d("Debug: ", "Response Code : " + duplicateCode);

                //Sets the input stream to the input stream of the Server.
                if (duplicateCode != 500)
                    is = serverConnection.getInputStream();

                //If the request is restGET, create a JSON Array which is used to collect all
                //of the JSON Objects which were went from the Node Server.
                if (params[1] == "GET") {
                    StringBuilder sb = new StringBuilder();
                    String line;
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }

                    try {
                        JSONObject jsonData = new JSONObject(sb.toString());
                        return jsonData.toString();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                //Else the request with anything other than restGET so we return test to notify
                //the onPostExecute method to not try to access the elements in the JSON Array.
                else if (params[1] == "POST" || params[1] == "PUT" || params[1] == "DELETE"){
                    return "test";
                }

                //Catches any exception that is thrown and handles it accordingly.
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                serverConnection.disconnect();
            }

            return "Should not get to this if the data has been sent/received correctly!";
        }

        /**
         * Method called when a RESTful method is used like restGET and restPOST.
         * @param result The result from the query
         */
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            checkDuplicates();
        }
    }
}
