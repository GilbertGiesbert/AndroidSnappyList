package com.example.joern.snappylist;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by joern on 16.06.2016.
 */

/**
 *
 * AsyncTask<INPUT, PROGRESS, RESULT>
 *
 *     doInBackground( INPUT input... ){
 *
 *      PROGRESS p
 *         publishProgress( p )
 *
 *      RESULT r
 *         return r
 *     }
 *
 *     onProgressUpdate( PROGRESS p...)
 *
 *      onPostExecute( RESULT r)
 *
 *
 *
 */

public class AsyncImageDownloadTask extends AsyncTask<String, Void, ImageLoad>{

    private final WeakReference<ImageView> imageViewReference;

    public AsyncImageDownloadTask(ImageView imageView) {
        imageViewReference = new WeakReference<>(imageView);
    }


    @Override
    protected ImageLoad doInBackground(String... params) {

        return downloadBitmap(params[0]);
    }


    private ImageLoad downloadBitmap(String imageUrl) {

        HttpURLConnection urlConnection = null;
        try {
            URL uri = new URL(imageUrl);
            urlConnection = (HttpURLConnection) uri.openConnection();
            int statusCode = urlConnection.getResponseCode();
            if (statusCode != HttpURLConnection.HTTP_OK) {
                return null;
            }

            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream != null) {

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                ImageLoad imageLoad = new ImageLoad();
                imageLoad.setImageUrl(imageUrl);
                imageLoad.setImage(bitmap);
                return imageLoad;
            }
        } catch (Exception e) {

            Log.w("", "Error downloading image from " + imageUrl);

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(ImageLoad imageLoad) {

        if (isCancelled() || imageLoad == null) {
            imageLoad = new ImageLoad();
        }

        if (imageViewReference != null) {

            ImageView imageView = imageViewReference.get();
            if (imageView != null) {

                String imageViewUrl = (String) imageView.getTag();
                boolean wantedUrl = imageViewUrl != null && imageViewUrl.equals(imageLoad.getImageUrl());

                if (wantedUrl && imageLoad.getImage() != null) {
                    imageView.setImageBitmap(imageLoad.getImage());
                } else {

                    Drawable placeholder = ResourcesCompat.getDrawable(imageView.getContext().getResources(), R.mipmap.ic_launcher, null);
                    imageView.setImageDrawable(placeholder);
                }
            }
            else{
                Log.w("", "referent of imageViewReference == null");
            }
        }
        else{
            Log.w("", "imageViewReference == null");
        }
    }
}
