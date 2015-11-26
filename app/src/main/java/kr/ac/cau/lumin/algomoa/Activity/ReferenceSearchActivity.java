package kr.ac.cau.lumin.algomoa.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import kr.ac.cau.lumin.algomoa.R;

/**
 * Created by CAUCSE on 2015-11-26.
 */
public class ReferenceSearchActivity extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);

        this.initializeToolBar();
    }

    private void initializeToolBar() {
        this.toolbar = (Toolbar) findViewById(R.id.toolbar_search);

        if (this.toolbar != null) {
            this.setSupportActionBar(this.toolbar);
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
