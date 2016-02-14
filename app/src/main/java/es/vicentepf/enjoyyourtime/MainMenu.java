package es.vicentepf.enjoyyourtime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class MainMenu extends AppCompatActivity {

    private ImageButton b_name_compatibility;
    private ImageButton b_yoda_write;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        b_name_compatibility = (ImageButton) findViewById(R.id.name_affinity_button);
        b_name_compatibility.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(MainMenu.this, NameCompatibility.class);
                startActivity(i);
            }
        });

        b_yoda_write = (ImageButton) findViewById(R.id.yoda_button);
        b_yoda_write.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(MainMenu.this, YodaWrite.class);
                startActivity(i);
            }
        });
    }

    public void launchPreferences (View view) {
        Intent i = new Intent(this, Preferences.class);
        startActivity(i);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            launchPreferences(null);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
