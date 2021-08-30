package com.java.main.course.coursemanagementapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        // Load the default Fragment i.e List Terms
        this.loadFragmentById(R.id.nav_terms);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // Load the Fragment that is selected from the Top Action Bar
        this.loadFragmentById(id);

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        // Call function to load the selected Fragment from Navigation Drawer
        this.loadFragmentById(id);

        return true;
    }

    /**
     * Custom function to load a selected UI Fragment into the Main Activity.
     * The Entire project revolves around UI Fragment Screen that loads into
     * Main Activity from selection of Menu Actions or Navigation Drawer Menu
     * Options or other Buttons being clicked.
     *
     * Overall this functions loads a fragment into the MainActivity identified
     * by the Fragment Id passed to the function.
     *
     * @param itemId id of the fragment to load
     */
    private void loadFragmentById(int itemId) {

        // creating fragment object that will be used to referenced the selected
        // fragment
        Fragment fragment = null;

        // Create Fragment based on Id
        switch (itemId) {
            case R.id.nav_terms:
            case R.id.action_terms:
                fragment = new ListTerms();
                break;
            case R.id.nav_courses:
            case R.id.action_courses:
                fragment = new ListCourses();
                break;
            case R.id.nav_assessments:
            case R.id.action_assessments:
                fragment = new ListAssessments();
                break;
            case R.id.nav_notes:
            case R.id.action_notes:
                fragment = new ListNotes();
                break;
            case R.id.nav_notifications:
            case R.id.action_notifications:
                fragment = new Notifications();
                break;
        }

        // If the fragment has been created previously, load it
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_content, fragment);
            fragmentTransaction.commit();
        }

        // Adjust the drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
}
