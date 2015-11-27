package kr.ac.cau.lumin.algomoa.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import kr.ac.cau.lumin.algomoa.R;

/**
 * Created by Lumin on 2015-11-26.
 */
public class ProblemSelectActivity extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String siteName = getIntent().getExtras().getString("SiteName");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_view);

        this.initializeToolBar();
        this.initializeRecyclerView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private void initializeToolBar() {
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (this.toolbar != null) {
            this.setSupportActionBar(this.toolbar);
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initializeRecyclerView() {
        // TODO : This
    }
}