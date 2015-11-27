package kr.ac.cau.lumin.algomoa.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import kr.ac.cau.lumin.algomoa.R;
import kr.ac.cau.lumin.algomoa.SQLite.AlgomoaSQLHelper;
import kr.ac.cau.lumin.algomoa.Util.Language.LanguageList;
import kr.ac.cau.lumin.algomoa.Util.Language.LanguageRefer;
import kr.ac.cau.lumin.algomoa.Util.Language.Ruby;
import kr.ac.cau.lumin.algomoa.Util.ReferenceAdapter;
import kr.ac.cau.lumin.algomoa.Util.User;

/**
 * Created by Lumin on 2015-11-26.
 */
public class ReferenceSearchActivity extends AppCompatActivity implements View.OnClickListener{
    private Toolbar toolbar;
    private AppCompatImageButton searchButton;
    private RecyclerView referFavRecyclerView;
    private RecyclerView referFavnRecyclerView;
    private List<LanguageRefer> refers;
    private ReferenceAdapter referenceAdapter;
    private EditText searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);

        this.refers = new ArrayList<>();
        this.initializeToolBar();

        this.searchText = (EditText) findViewById(R.id.toolbar_edit_text);
        this.referenceAdapter = new ReferenceAdapter(ReferenceSearchActivity.this, refers, null, R.layout.refer_itemview);
        this.referFavRecyclerView = (RecyclerView) findViewById(R.id.fav_lang_search_recycler);
        this.referFavnRecyclerView = (RecyclerView) findViewById(R.id.favn_lang_search_recycler);
        this.referFavRecyclerView.setHasFixedSize(true);
        this.referFavnRecyclerView.setHasFixedSize(true);
        this.referFavRecyclerView.setLayoutManager(new LinearLayoutManager(ReferenceSearchActivity.this));
        this.referFavnRecyclerView.setLayoutManager(new LinearLayoutManager(ReferenceSearchActivity.this));
        this.referFavRecyclerView.setAdapter(referenceAdapter);
    }

    @Override
    public void onClick(View view) {
        ArrayList<ArrayList<LanguageRefer>> searchRefers = new ArrayList<>();
        ArrayList<LanguageList> favoriteLanguageList = User.getInstance().copyFavoriteLanguage();
        searchRefers.add(AlgomoaSQLHelper.getInstance(this).getAllReferences(LanguageList.Ruby));
        searchRefers.add(AlgomoaSQLHelper.getInstance(this).getAllReferences(LanguageList.Java));

        for (ArrayList<LanguageRefer> referList : searchRefers) {
            for (int i = 0; i < referList.size(); i++) {
                LanguageRefer languageRefer = referList.get(i);

                if (favoriteLanguageList.indexOf(languageRefer.getLanguage().toString()) == -1 &&
                        languageRefer.getReferenceName().startsWith(this.searchText.getText().toString())) {
                    this.refers.add(referList.get(i));
                }
            }

            this.referenceAdapter.notifyDataSetChanged();
        }

    }

    @Override
    protected void onPause() {
        this.referenceAdapter.clearData();
        super.onPause();
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
