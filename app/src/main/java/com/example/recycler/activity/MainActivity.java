package com.example.recycler.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.recycler.R;
import com.example.recycler.adapter.PagerAdapter;
import com.example.recycler.api.ApiTheLoai;
import com.example.recycler.fragment.*;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ApiTheLoai.ApiTheLoaiData, NavigationView.OnNavigationItemSelectedListener {
    private ArrayList<Fragment> listFragment;
    private ArrayList<String> listTitle;
    private FragmentManager fragmentManager;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ApiTheLoai apiTheLoai;
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_main);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
        test();
        apiTheLoai = new ApiTheLoai(this);
        apiTheLoai.theloai();
        if(navigationView!=null){
            navigationView.setNavigationItemSelectedListener(this);
        }
    }

    public void test(){
        listFragment = new ArrayList<>();
        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tablayout);
        fragmentManager = getSupportFragmentManager();

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public void setTheLoaiData(ArrayList<String> listName, ArrayList<String> listLink) {
        for (String link :listLink){
            listFragment.add(new FagmentTrangChu(link));
            Log.d("link", link);
        }
        PagerAdapter pagerAdapter = new PagerAdapter(fragmentManager,listFragment,listName);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if(id==R.id.nav_history){
            Intent intent = new Intent(MainActivity.this,HistoryActivity.class);
            startActivity(intent);
        }
        return true;
    }
}
