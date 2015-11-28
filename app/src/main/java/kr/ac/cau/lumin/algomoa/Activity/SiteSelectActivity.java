package kr.ac.cau.lumin.algomoa.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;

import kr.ac.cau.lumin.algomoa.R;
import kr.ac.cau.lumin.algomoa.Util.Adapter.SiteAdapter;
import kr.ac.cau.lumin.algomoa.Util.Algorithm.SiteList;
import kr.ac.cau.lumin.algomoa.Util.Language.LanguageList;
import kr.ac.cau.lumin.algomoa.Util.User;

/**
 * Created by Lumin on 2015-11-26.
 */
public class SiteSelectActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView siteRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_view);

        this.siteRecyclerView = (RecyclerView) findViewById(R.id.fav_site_recycler);
        this.siteRecyclerView.setHasFixedSize(true);
        this.siteRecyclerView.setLayoutManager(new LinearLayoutManager(SiteSelectActivity.this));
        this.siteRecyclerView.setAdapter(new SiteAdapter(SiteSelectActivity.this, this.getOrderedLanguageList(), null, R.layout.refer_itemview));

        this.initializeToolBar();
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

    private ArrayList<SiteList> getOrderedLanguageList() {
        ArrayList<SiteList> userSiteList = User.getInstance().copyFavoriteAlgorithmSite();
        ArrayList<SiteList> favSiteList = new ArrayList<>();
        ArrayList<SiteList> unFavSiteList = new ArrayList<>();
        ArrayList<SiteList> resultList = new ArrayList<>();


        for (SiteList site : SiteList.fetchAllSiteList()) {
            if (userSiteList.indexOf(site) == -1) {
                favSiteList.add(site);
            } else {
                unFavSiteList.add(site);
            }
        }

        for (SiteList site : favSiteList) {
            resultList.add(site);
        }

        for (SiteList site : unFavSiteList) {
            resultList.add(site);
        }

        return resultList;
    }
}
