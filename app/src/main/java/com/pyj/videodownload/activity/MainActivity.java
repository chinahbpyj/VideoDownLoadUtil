package com.pyj.videodownload.activity;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.jaeger.library.StatusBarUtil;
import com.pyj.videodownload.R;
import com.pyj.videodownload.fragment.VideoDownLoadFragment;
import com.pyj.videodownload.util.AppUtils;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;

    private VideoDownLoadFragment videoDownLoadFragment;

    private long firstTime = 0;

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        StatusBarUtil.setColorNoTranslucentForDrawerLayout(this, drawer, getResources().getColor(R.color.colorPrimary));

        setDefaultFrag();
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

    private void addFrag(Fragment frag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if (frag != null && !frag.isAdded()) {
            ft.add(R.id.content, frag);
        }
        ft.commit();
    }

    private void hideAllFrag() {
        hideFrag(videoDownLoadFragment);
       /* hideFrag(px);
        hideFrag(user);
        hideFrag(me);*/
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

        hideAllFrag();

        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
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
