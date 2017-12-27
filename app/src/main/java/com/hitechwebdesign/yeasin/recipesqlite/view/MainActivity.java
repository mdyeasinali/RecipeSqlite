package com.hitechwebdesign.yeasin.recipesqlite.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.hitechwebdesign.yeasin.recipesqlite.R;
import com.hitechwebdesign.yeasin.recipesqlite.authentication.SignIn;
import com.hitechwebdesign.yeasin.recipesqlite.fragment.RecipeFragment;
import com.hitechwebdesign.yeasin.recipesqlite.session.MyPreferencesHelperSinglaton;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Recipe");
        //setSupportActionBar(toolbar);
        email = MyPreferencesHelperSinglaton.getInstance(getApplicationContext()).getString(MyPreferencesHelperSinglaton.Key.KEY_USER_EMAIL,"");
        MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, RecipeFragment.newInstance()).commit();
        if (email.equals("admin@gmail.com")) {
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), NewRecipes.class);
                    startActivity(intent);
                }
            });
        }else {
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.hide();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (email.equals("admin@gmail.com")) {
            navigationView.getMenu().findItem(R.id.add_recipes).setVisible(true);
            navigationView.getMenu().findItem(R.id.my_recipes).setVisible(true);
            navigationView.getMenu().findItem(R.id.fevarit).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_rate).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_share).setVisible(false);

        }else{
            navigationView.getMenu().findItem(R.id.add_recipes).setVisible(false);
            navigationView.getMenu().findItem(R.id.my_recipes).setVisible(false);
        }




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
        adminmenu(item.getItemId());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void adminmenu(int item) {
        Fragment fragment = null;
        switch (item) {
            case R.id.nav_search:
                Intent inent = new Intent(MainActivity.this, SignIn.class);
                startActivity(inent);
                break;
            case R.id.my_recipes:
                setTitle("My Recipe");
                MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, RecipeFragment.newInstance()).commit();
                break;
            case R.id.add_recipes:
//                Intent inents = new Intent(MainActivity.this, NewRecipes.class);
//                setTitle("New recipe");
//                startActivity(inents);
                break;
            case R.id.fevarit:
                setTitle("Fevarit");
                // MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, Favorite.newInstance()).commit();
                break;
            case R.id.nav_about:
                //MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, Aboutus.newInstance()).commit();

                break;
            case R.id.nav_share:
                //shareApp();
                break;
            case R.id.nav_rate:
                //goToMyApp(true);
                break;

            case R.id.nav_logout:
                MyPreferencesHelperSinglaton.getInstance(MainActivity.this).put(MyPreferencesHelperSinglaton.Key.KEY_USER_EMAIL, "");
                Toast.makeText(this, "logut", Toast.LENGTH_SHORT).show();
                 startActivity(new Intent(this, SignIn.class));

                break;
            case R.id.nav_exit:
                new AlertDialog.Builder(this)
                        .setTitle("Close App?")
                        .setMessage("Do you really want to close this app?")
                        .setPositiveButton("YES",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        android.os.Process.killProcess(android.os.Process.myPid());
                                    }
                                })
                        .setNegativeButton("NO",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                    }
                                }).show();
                break;

            default:
//                Intent inente = new Intent(MainActivity.this, NewRecipes.class);
//                startActivity(inente);
                break;
        }
    }




}
