package com.example.gamedb.util;

import android.net.Uri;
import android.util.Log;

import com.example.gamedb.BuildConfig;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

public class Utilities {
    private static final String LOG_TAG = Utilities.class.getName();

    public static Object httpRequest(String requestType, String body, Uri uri) {
        HttpsURLConnection httpsURLConnection = null;
        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            URL requestUrl = new URL(uri.toString());

            // Open URL connection
            httpsURLConnection = (HttpsURLConnection) requestUrl.openConnection();
            httpsURLConnection.setRequestMethod(requestType);
            httpsURLConnection.setRequestProperty("user-key", BuildConfig.USER_KEY);
            httpsURLConnection.setRequestProperty("Accept", "application/json");
            httpsURLConnection.setDoInput(true);
            httpsURLConnection.setDoOutput(true);
            httpsURLConnection.connect();

            if (requestType.equals("POST")) {
                // OutputStream POST body
                OutputStream outputStream = httpsURLConnection.getOutputStream();
                byte[] bytes = body.getBytes();
                outputStream.write(bytes);
            }

            // InputStream get response
            InputStream inputStream = httpsURLConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }

            // Builder is empty
            if (stringBuilder.length() == 0) {
                return null;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpsURLConnection != null) {
                httpsURLConnection.disconnect();
            }

            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            return new JSONArray(stringBuilder.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static int convertDoubleToInteger(String number) {
        double num = Double.parseDouble(number);
        return (int) num;
    }

    public static String convertTimestampToDate(String timestamp) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM, dd yyy");
        return simpleDateFormat.format(new Date(Long.parseLong(timestamp) * 1000));
    }
}
