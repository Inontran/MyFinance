package com.vk.id194177937.myfinance.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.vk.id194177937.myfinance.R;
import com.vk.id194177937.myfinance.core.database.Initializer;
import com.vk.id194177937.myfinance.core.interfaces.TreeNode;
import com.vk.id194177937.myfinance.fragments.HandbookListFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, HandbookListFragment.OnListFragmentInteractionListener {

    private ImageView backIcon;
    private Toolbar toolbar;
    private TextView toolbarTitle;

    private TreeNode selectedNode;

    private HandbookListFragment handbookListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();

//        initFloatingButton();

        initNavigationDrawer(toolbar);

        initFragment();
    }

    private void initFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        handbookListFragment = new HandbookListFragment();
        fragmentTransaction.replace(R.id.handbook_list_fragment, handbookListFragment);

        // fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void initNavigationDrawer(Toolbar toolbar) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initFloatingButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void initToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        backIcon = (ImageView) findViewById(R.id.back_icon);

        backIcon.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if (selectedNode.getParent() == null){
                    handbookListFragment.updateData(Initializer.getSourceSync().getAll());
                    toolbarTitle.setText(R.string.sources);
                }else{
                    handbookListFragment.updateData(selectedNode.getParent().getChildren());
                    selectedNode = selectedNode.getParent();
                    toolbarTitle.setText(selectedNode.getName());
                }
            }
        });
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
        getMenuInflater().inflate(R.menu.main, menu);
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

        if (id == R.id.nav_operations) {
            // Handle the camera action
        } else if (id == R.id.nav_reports) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_accounts) {

        } else if (id == R.id.nav_sources) {

        } else if (id == R.id.nav_help) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onListFragmentInteraction(TreeNode item) {
        this.selectedNode = item;
        if (selectedNode.hasChildren()) {
            toolbarTitle.setText(selectedNode.getName());// показывает выбранную категорию
        }
    }
}
