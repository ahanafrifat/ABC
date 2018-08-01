package com.appinionbd.abc.view.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.appinionbd.abc.R;
import com.appinionbd.abc.model.dataHolder.UserInfo;
import com.appinionbd.abc.model.dataModel.User;
import com.appinionbd.abc.presenter.HomePresenter;
import com.appinionbd.abc.view.home.fragment.HomeFragment;
import com.appinionbd.abc.view.home.fragment.HomeMonitorFragment;
import com.appinionbd.abc.view.home.fragment.MyInfoFragment;
import com.appinionbd.abc.view.home.fragment.TrackMeFragment;
import com.appinionbd.abc.view.patientCode.PatientCodeActivity;
import com.appinionbd.abc.view.signIn.SignInActivity;

import io.realm.Realm;
import io.realm.RealmResults;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Fragment fragment;

    final int HOME_FRAGMENT_FOR_PATIENT = 1;
    final int TRACK_ME = 2;
    final int MY_INFO = 3;

    final int HOME_FRAGMENT_FOR_Monitor = 11;

    private String choose;

    private HomePresenter homePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(this);
        setContentView(R.layout.activity_home);
        Realm.init(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        Intent intent = getIntent();
        choose = intent.getStringExtra("patientOrMonitor");


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);;
        if (choose.equals("patient")) {
            navigationView.inflateMenu(R.menu.activity_home_drawer);
        }
        else {
            navigationView.inflateMenu(R.menu.activity_home_monitor_drawer);
        }

        navigationView.setNavigationItemSelectedListener(this);


        fragment = null;
        if (choose.equals("patient"))
            startFragment(HOME_FRAGMENT_FOR_PATIENT);
        else if(choose.equals("monitor"))
            startFragment(HOME_FRAGMENT_FOR_Monitor);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home_patient) {
                startFragment(HOME_FRAGMENT_FOR_PATIENT);
        }
        else if (id == R.id.nav_track_me) {
                startFragment(TRACK_ME);
        }
        else if (id == R.id.nav_my_info) {
            startFragment(MY_INFO);
        }
        else if (id == R.id.nav_home_monitor) {
            startFragment(MY_INFO);
        }
        else if (id == R.id.nav_track_patient) {
            startPatientCodeActivity();
        }

        else if (id == R.id.nav_change_password) {

        }
        else if (id == R.id.nav_log_out) {
            gotoLogOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void startFragment(int fragmentCheck) {
        if (fragmentCheck == HOME_FRAGMENT_FOR_PATIENT)
        {
            fragment = new HomeFragment();
        }
        else if (fragmentCheck == TRACK_ME)
        {
            fragment = new TrackMeFragment();
        }
        else if (fragmentCheck == MY_INFO)
        {
            fragment = new MyInfoFragment();
        }
        else if(fragmentCheck == HOME_FRAGMENT_FOR_Monitor){
            fragment = new HomeMonitorFragment();
        }

        if(fragment != null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout_home, fragment);
            fragmentTransaction.commit();
        }
    }

    private void gotoLogOut() {
        try(Realm realm = Realm.getDefaultInstance()){
            realm.executeTransaction(realm1 -> {
                RealmResults<User> users = realm1.where(User.class).findAll();
                users.deleteAllFromRealm();
                RealmResults<UserInfo> userInfos = realm1.where(UserInfo.class).findAll();
                userInfos.deleteAllFromRealm();
            });
            Intent intent = new Intent(this , SignInActivity.class);
            startActivity(intent);
        }
    }

    private void startPatientCodeActivity() {
        Intent intent = new Intent(this , PatientCodeActivity.class);
        startActivity(intent);
    }
}
