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

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NameCompatibility extends AppCompatActivity {

    private Button b_name_compatibility_execute;
    private EditText eT_name_1;
    private EditText eT_name_2;
    private TextView tV_name_compatibility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_compatibility);

        b_name_compatibility_execute = (Button) findViewById(R.id.button_execute_name_compatibility);
        eT_name_1 = (EditText) findViewById(R.id.editText_name_1);
        eT_name_2 = (EditText) findViewById(R.id.editText_name_2);
        tV_name_compatibility = (TextView) findViewById(R.id.textView_name_compatibility_result);

        b_name_compatibility_execute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name_1 = eT_name_1.getText().toString();
                String name_2 = eT_name_2.getText().toString();
                RetrieveNameCompatibility yodaTask = new RetrieveNameCompatibility();
                yodaTask.execute(name_1,name_2);
            }
        });
    }

    class RetrieveNameCompatibility extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... names) {
            final String LOG_TAG = YodaWrite.class.getSimpleName();

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String str = "";

            try {
                final String BASE_URL =
                        "https://love-calculator.p.mashape.com/getPercentage?";
                final String NAME_1_PARAM = "fname";
                final String NAME_2_PARAM = "sname";
                String key = "CBDliJ1HOwmshmQPBUeH8F8Y3baxp1Hz4KXjsneIn0lFPYCuS6";

                Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                        .appendQueryParameter(NAME_1_PARAM, names[0])
                        .appendQueryParameter(NAME_2_PARAM, names[1])
                        .build();

                URL url = new URL(builtUri.toString());

                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestProperty("X-Mashape-Key", key);
                urlConnection.setRequestProperty("Accept", "text/plain");

                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }
                str = buffer.toString();

                JSONObject nameCompatibilityJson = new JSONObject(str);

                String percentage = (String) nameCompatibilityJson.get("percentage");
                String result = (String) nameCompatibilityJson.get("result");

                return "The compatibility of your names are: " + percentage + "%. " + result;
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
            tV_name_compatibility.setText(str);
        }
    }
}
