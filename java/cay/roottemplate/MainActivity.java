package cay.roottemplate;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import eu.chainfire.libsuperuser.Shell;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        (new RootCheck()).execute();
    }
    private class RootCheck extends AsyncTask<Void, Boolean, Boolean> {
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Checking for Root...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            // Shell.SU.available is a blocking call and thus should be run on a async task
            return Shell.SU.available();
        }
        protected void onPostExecute(Boolean hasRoot) {
            if (hasRoot){
                progressDialog.dismiss();
            } else {
                progressDialog.dismiss();
                alertDialog.setTitle("Root Check Failed");
                alertDialog.setMessage("This application requires root in order to function.");
                alertDialog.setCancelable(false);
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Close",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                System.exit(0);
                            }
                        });
                alertDialog.show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
