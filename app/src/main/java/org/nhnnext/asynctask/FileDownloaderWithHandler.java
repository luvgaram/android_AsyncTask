package org.nhnnext.asynctask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.widget.ImageView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

/**
 * Created by eunjooim on 15. 9. 28.
 */
public class FileDownloaderWithHandler {
    public void downFile(final String url, final ImageView imageView) {

        HandlerThread handlerThread = new HandlerThread("HandlerThread");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                final Bitmap bitmap = downloadImage(url);
                Handler handler = new Handler(Looper.getMainLooper()) {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        imageView.setImageBitmap(bitmap);
                    }
                };
                handler.sendMessage(new Message());
            }
        };
        handler.sendMessage(new Message());
    }

    private Bitmap downloadImage(String url) {
        final DefaultHttpClient client = new DefaultHttpClient();
        final HttpGet getRequest = new HttpGet(url);

        try {
            HttpResponse response = client.execute(getRequest);
            final int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) return null;

            final HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream inputStream = null;

                try {
                    inputStream = entity.getContent();
                    return BitmapFactory.decodeStream(inputStream);
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    entity.consumeContent();
                }
            }
        } catch (IOException | IllegalStateException e) {
            getRequest.abort();
        }

        return null;
    }
}
