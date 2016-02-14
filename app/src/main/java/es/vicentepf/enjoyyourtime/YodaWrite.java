package es.vicentepf.enjoyyourtime;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class YodaWrite extends AppCompatActivity {

    private Button b_yoda_write_execute;
    private EditText eT_yoda_write;
    private TextView tV_yoda_write;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yoda_write);

        b_yoda_write_execute = (Button) findViewById(R.id.button_execute_yoda_write);
        eT_yoda_write = (EditText) findViewById(R.id.editText_your_phrase_yoda);
        tV_yoda_write = (TextView) findViewById(R.id.textView_yoda_write_result);

        b_yoda_write_execute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String yourPhrase = eT_yoda_write.getText().toString();
                RetrieveYodaPhrase yodaTask = new RetrieveYodaPhrase();
                yodaTask.execute(yourPhrase);
            }
        });
    }

    class RetrieveYodaPhrase extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... yourPhrase) {
            final String LOG_TAG = YodaWrite.class.getSimpleName();

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String str = "";

            try {
                final String BASE_URL =
                        "https://yoda.p.mashape.com/yoda?";
                final String SENTENCE_PARAM = "sentence";
                String key = "CBDliJ1HOwmshmQPBUeH8F8Y3baxp1Hz4KXjsneIn0lFPYCuS6";

                Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                        .appendQueryParameter(SENTENCE_PARAM, yourPhrase[0])
                        .build();

                URL url = new URL(builtUri.toString());

                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestProperty("X-Mashape-Key", key);
                urlConnection.setRequestProperty("Accept", "text/plain");

                urlConnection.setRequestMethod("GET");
                urlConnection.connect();


                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                byte[] contents = new byte[1024];

                int bytesRead=0;
                while( (bytesRead = in.read(contents)) != -1){
                    str = new String(contents, 0, bytesRead);
                }

                Log.d(this.getClass().getName(), "YODAAAAAAAAAAAA FRASE " + str);
                return str;
            } catch (Exception e) {
                Log.e(LOG_TAG, "Error ", e);

                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final Exception e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
        }

        protected void onPostExecute(String str) {
            // TODO: check this.exception
            // TODO: do something with the feed
            tV_yoda_write.setText(str);
        }
    }
}
