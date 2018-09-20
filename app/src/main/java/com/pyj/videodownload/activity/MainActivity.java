package com.pyj.videodownload.activity;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.jaeger.library.StatusBarUtil;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.pyj.videodownload.R;
import com.pyj.videodownload.fragment.BaseFragment;
import com.pyj.videodownload.fragment.VideoDownLoadFragment;
import com.pyj.videodownload.util.AppUtils;
import com.pyj.videodownload.util.Constans;
import com.pyj.videodownload.util.StringUtil;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private MaterialSearchView searchView;

    private VideoDownLoadFragment videoDownLoadFragment;

    private long firstTime = 0;

    private BaseFragment curFragment;

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(Constans.source_zzs);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        StatusBarUtil.setColorNoTranslucentForDrawerLayout(this, drawer, getResources().getColor(R.color.colorPrimary));

        initSearchView();

        setDefaultFrag();
    }

    private void initSearchView() {
        searchView = findViewById(R.id.searchView);
        searchView.setVoiceSearch(false);
        searchView.setCursorDrawable(R.drawable.custom_cursor);
        searchView.setEllipsize(true);
        searchView.setHintTextColor(R.color.colorGray);

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!StringUtil.isEmpty(query)) {
                    toolbar.setTitle(query);

                    VideoDownLoadFragment fragment = (VideoDownLoadFragment) curFragment;
                    fragment.setSourceAndKeyword("", query,true);
                    searchView.closeSearch();
                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                searchView.setHint(toolbar.getTitle().toString());
            }

            @Override
            public void onSearchViewClosed() {
            }
        });
    }

    @Override
    public void initData() {
    }

    private void setDefaultFrag() {
        if (videoDownLoadFragment == null) {
            videoDownLoadFragment = new VideoDownLoadFragment();
        }
        addFrag(videoDownLoadFragment);

        getSupportFragmentManager().beginTransaction().show(videoDownLoadFragment).commit();
    }

    private void addFrag(BaseFragment frag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if (frag != null && !frag.isAdded()) {
            ft.add(R.id.content, frag, frag.getTag());
        }
        ft.commit();

        curFragment = frag;
    }

    private void hideAllFrag() {
        hideFrag(videoDownLoadFragment);
    }

    /*隐藏frag*/
    private void hideFrag(Fragment frag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (frag != null && frag.isAdded()) {
            ft.hide(frag);
        }
        ft.commit();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        //hideAllFrag();

        if (id == R.id.nav_zzs) {
            //setDefaultFrag();
            setSource(Constans.source_zzs);
        } else if (id == R.id.nav_clb) {
            setSource(Constans.source_clb);
        } else if (id == R.id.nav_bttz) {
            setSource(Constans.source_bttz);
        } else if (id == R.id.nav_btdb) {
            setSource(Constans.source_btdb);
        } else if (id == R.id.nav_bt4g) {
            setSource(Constans.source_bt4g);
        } else if (id == R.id.nav_dss) {
            setSource(Constans.source_dss);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setSource(String source){
        toolbar.setTitle(source);

        VideoDownLoadFragment fragment = (VideoDownLoadFragment) curFragment;
        fragment.setSourceAndKeyword(source, "",false);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return false;
        }
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
            return false;
        } else {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                if (System.currentTimeMillis() - firstTime > 2000) {

                    AppUtils.showSnackBar(drawer, "再按一次退出", getResources().getColor(R.color.colorPrimary));
                    firstTime = System.currentTimeMillis();
                } else {
                    finish();
                }
                return true;
            }
        }

        return super.onKeyDown(keyCode, event);
    }
}
