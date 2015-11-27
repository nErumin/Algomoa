package kr.ac.cau.lumin.algomoa.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;

import kr.ac.cau.lumin.algomoa.R;
import kr.ac.cau.lumin.algomoa.SQLite.AlgomoaSQLHelper;
import kr.ac.cau.lumin.algomoa.Util.Language.LanguageList;
import kr.ac.cau.lumin.algomoa.Util.Language.LanguageRefer;

/**
 * Created by Lumin on 2015-11-26.
 */
public class ReferenceSearchActivity extends AppCompatActivity implements View.OnClickListener{
    private Toolbar toolbar;
    private AppCompatImageButton searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);

        this.initializeToolBar();
    }

    @Override
    public void onClick(View view) {
        ArrayList<ArrayList<LanguageRefer>> searchRefers = new ArrayList<>();
        searchRefers.add(AlgomoaSQLHelper.getInstance(this).getAllReferences(LanguageList.Ruby));
        searchRefers.add(AlgomoaSQLHelper.getInstance(this).getAllReferences(LanguageList.Java));

        for (ArrayList<LanguageRefer> referList : searchRefers) {
            
        }

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
        this.toolbar = (Toolbar) findViewById(R.id.toolbar_search);

        if (this.toolbar != null) {
            this.setSupportActionBar(this.toolbar);
            this.getSupportActionBar().setDisplayUseLogoEnabled(false);
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        this.searchButton = (AppCompatImageButton) findViewById(R.id.toolbar_search_button);
        this.searchButton.setOnClickListener(this);
    }
}
