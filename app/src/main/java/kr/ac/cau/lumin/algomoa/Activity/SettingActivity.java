package kr.ac.cau.lumin.algomoa.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.CompoundButton;

import java.io.InvalidObjectException;
import java.util.ArrayList;

import kr.ac.cau.lumin.algomoa.R;
import kr.ac.cau.lumin.algomoa.Util.Adapter.SiteAdapter;
import kr.ac.cau.lumin.algomoa.Util.Algorithm.SiteList;
import kr.ac.cau.lumin.algomoa.Util.Language.LanguageList;
import kr.ac.cau.lumin.algomoa.Util.User;

/**
 * Created by Lumin on 2015-11-28.
 */
public class SettingActivity extends AppCompatActivity {
    private Toolbar toolbar;

    private AppCompatCheckBox[] langCheckBoxs;
    private AppCompatCheckBox[] siteCheckBoxs;
    private AppCompatTextView[] langTextViews;
    private AppCompatTextView[] siteTextViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        this.initializeToolBar();
        this.langCheckBoxs = new AppCompatCheckBox[2];
        this.siteCheckBoxs = new AppCompatCheckBox[3];
        this.langTextViews = new AppCompatTextView[2];
        this.siteTextViews = new AppCompatTextView[3];

        this.langCheckBoxs[0] = (AppCompatCheckBox) findViewById(R.id.ruby_checkbox);
        this.langCheckBoxs[1] = (AppCompatCheckBox) findViewById(R.id.java_checkbox);
        this.langTextViews[0] = (AppCompatTextView) findViewById(R.id.ruby_textview);
        this.langTextViews[1] = (AppCompatTextView) findViewById(R.id.java_textview);

        this.siteCheckBoxs[0] = (AppCompatCheckBox) findViewById(R.id.codeforce_checkbox);
        this.siteCheckBoxs[1] = (AppCompatCheckBox) findViewById(R.id.baekjoon_checkbox);
        this.siteCheckBoxs[2] = (AppCompatCheckBox) findViewById(R.id.algospot_checkbox);
        this.siteTextViews[0] = (AppCompatTextView) findViewById(R.id.codeforce_textview);
        this.siteTextViews[1] = (AppCompatTextView) findViewById(R.id.baekjoon_textview);
        this.siteTextViews[2] = (AppCompatTextView) findViewById(R.id.algospot_textview);

        this.initializeCheckboxListener(langTextViews, langCheckBoxs, siteTextViews, siteCheckBoxs);
    }

    private void initializeCheckboxListener(final AppCompatTextView[] langTextViews, AppCompatCheckBox[] langCheckBoxs, final AppCompatTextView[] siteTextViews, AppCompatCheckBox[] siteCheckBoxs) {
        final ArrayList<LanguageList> favorLanguageList = User.getInstance().copyFavoriteLanguage();
        final ArrayList<SiteList> favorSiteList = User.getInstance().copyFavoriteAlgorithmSite();



        for (int i = 0; i < langTextViews.length; i++) {
            final int index = i;
            langCheckBoxs[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (!compoundButton.isChecked()) {
                        try {
                            int i = favorLanguageList.indexOf(LanguageList.valueOf(langTextViews[index].getText().toString()));
                            if (i >= 0) {
                                User.getInstance().deleteFavoriteLanguage(favorLanguageList.get(i));
                            }
                        } catch (InvalidObjectException e) {

                        }
                    } else {
                        int i = favorLanguageList.indexOf(LanguageList.valueOf(langTextViews[index].getText().toString()));

                        if (i < 0) {
                            User.getInstance().addNewFavoriteLanguage(LanguageList.valueOf(langTextViews[index].getText().toString()));
                        }
                    }
                }
            });

            if (favorLanguageList.indexOf(LanguageList.valueOf(langTextViews[i].getText().toString())) >= 0) {
                langCheckBoxs[i].setChecked(true);
            }
        }

        for (int i = 0; i < siteCheckBoxs.length; i++) {
            final int index = i;
            siteCheckBoxs[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (!compoundButton.isChecked()) {
                        try {
                            int i = favorSiteList.indexOf(SiteList.valueOf(siteTextViews[index].getText().toString()));
                            if (i >= 0) {
                                User.getInstance().deleteFavoriteSite(favorSiteList.get(i));
                            }
                        } catch (InvalidObjectException e) {

                        }
                    } else {
                        int i = favorSiteList.indexOf(SiteList.valueOf(siteTextViews[index].getText().toString()));

                        if (i < 0) {
                            User.getInstance().addNewFavoriteSite(SiteList.valueOf(siteTextViews[index].getText().toString()));
                        }
                    }
                }
            });

            if (favorSiteList.indexOf(SiteList.valueOf(siteTextViews[i].getText().toString())) >= 0) {
                siteCheckBoxs[i].setChecked(true);
            }
        }
    }

    private void initializeToolBar() {
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (this.toolbar != null) {
            this.setSupportActionBar(this.toolbar);
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

}
