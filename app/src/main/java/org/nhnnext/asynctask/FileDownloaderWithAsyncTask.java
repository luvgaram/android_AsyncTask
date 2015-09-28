package org.nhnnext.asynctask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.ImageView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by eunjooim on 15. 9. 24.
 */
public class FileDownloaderWithAsyncTask {
    public void downFile(String url, ImageView imageView) {
        int api = android.os.Build.VERSION.SDK_INT;

        ImageDownTask task = new ImageDownTask(imageView);
        task.setUrl(url);

        if (api >= Build.VERSION_CODES.HONEYCOMB)
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else
            task.execute();

    }

    private class ImageDownTask extends AsyncTask<String, Void, Bitmap> {
        private String url;
        private ImageView imageView;

        public ImageDownTask(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = downloadBitmap(url);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }

        private Bitmap downloadBitmap(String url) {
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
//                        EntityUtils.consume(entity);
                    }
                }
            } catch (IOException | IllegalStateException e) {
                getRequest.abort();
            }

            return null;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
