package com.hitechwebdesign.yeasin.recipesqlite.view;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;


import com.hitechwebdesign.yeasin.recipesqlite.R;
import com.hitechwebdesign.yeasin.recipesqlite.database.RecipeDbSource;
import com.hitechwebdesign.yeasin.recipesqlite.model.RecipeModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import at.markushi.ui.CircleButton;

public class NewRecipes extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    ImageView imageView;
    Button btnUpload, btnPicks;
    String imagePath;

    private Toolbar toolbar;
    private EditText et_name;
    private Spinner difficultySpinner;
    private EditText et_des;
    private Spinner sp_cat;
    private EditText et_preTime;
    private EditText et_cookTime;
    private EditText et_ing;
    private EditText et_dir;

    public static int SELECT_IMAGE;
    private ImageView img_recView;
    private ImageView img_cancleView;

    private String id;



    private String imageURL = null;
    private CircleButton cb_save;
    private CircleButton cb_image;
    private CircleButton recipeImageCircularButton;

    private boolean recipeAdded;
    private RecipeDbSource source;


    private LinearLayout recipeImageLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipes);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        verifyStoragePermissions(NewRecipes.this);
//        setSupportActionBar(toolbar);


        source = new RecipeDbSource(getApplicationContext());



        setTitle(getIntent().getBooleanExtra("editRecipe", false) ? getIntent().getStringExtra("recipeName") : getResources().getString(R.string.new_recipe_name));
        //setTitle(R.string.new_recipe_name);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        initView();
        initListeners();

    }

    private void initView() {
        et_name = (EditText) findViewById(R.id.et_name);
        difficultySpinner = (Spinner) findViewById(R.id.difficultySpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.difficulty_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultySpinner.setAdapter(adapter);
        et_preTime = (EditText) findViewById(R.id.et_preTime);
        et_ing = (EditText) findViewById(R.id.et_ing);
        et_dir = (EditText) findViewById(R.id.et_dir);
        img_recView = (ImageView) findViewById(R.id.img_recView);
        img_cancleView = (ImageView) findViewById(R.id.img_cancleView);
        cb_save = (CircleButton) findViewById(R.id.cb_save);
        cb_image = (CircleButton) findViewById(R.id.cb_image);
        recipeImageLayout = (LinearLayout) findViewById(R.id.recipeImageLayout);


         //id = MyPreferencesHelperSinglaton.getInstance(getApplicationContext()).getString(MyPreferencesHelperSinglaton.Key.KEY_USER_ID);
    }

    private void initListeners() {
        cb_save.setOnClickListener(this);
        img_cancleView.setOnClickListener(this);
        cb_image.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cb_image:
              showImagePopup(v);
                break;
            case R.id.img_cancleView:
                recipeImageLayout.setVisibility(View.GONE);
                imageURL = null;
                break;
            case R.id.cb_save:
                String eName =  et_name.getText().toString().trim();
                String cat = difficultySpinner.getSelectedItem().toString();
                final String times = et_preTime.getText().toString();
                String eIng = et_ing.getText().toString().trim();
                String eDir = et_dir.getText().toString().trim();
                String image = "{local:" + Environment.getExternalStorageDirectory().toString() + "/DCIM/BitmRecipe/" + imageURL + "}";
                if(imageURL!=null) {
                    RecipeModel recipeModel = new RecipeModel(eName,eIng,eDir,times,cat,image);
                    final boolean recipeadd = source.insertRecipe(recipeModel);
                    if (recipeadd){
                        Toast.makeText(NewRecipes.this, R.string.recipe_added_success, Toast.LENGTH_LONG).show();
                        onBackPressed();
                    }else {
                        Toast.makeText(NewRecipes.this, R.string.recipe_added_failure, Toast.LENGTH_LONG).show();
                    }
                 }


                break;
        }


    }

    public void showImagePopup(View view) {
        final Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_PICK);
        final Intent chooserIntent = Intent.createChooser(galleryIntent, "Choose image");
        startActivityForResult(chooserIntent, 100);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


            if (resultCode == Activity.RESULT_OK && requestCode == 100) {
                if (data != null) {
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());

                        recipeImageLayout.setVisibility(View.VISIBLE);
                        float ratio = (float) bitmap.getWidth() / bitmap.getHeight();
                        Log.d("Height", String.valueOf(ratio));
                        //TODO Check whether the image actually needs more compression (hint: if height is already 500 then no compression is needed)
                        img_recView.setImageBitmap(Bitmap.createScaledBitmap(bitmap, (int) (500 * ratio), 500, false));
                         saveImage(Bitmap.createScaledBitmap(bitmap, (int) (500 * ratio), 500, false));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //Toast.makeText(NewRecipeActivity.this, "Image selected", Toast.LENGTH_SHORT).show();
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(NewRecipes.this, R.string.couldnt_retrieve_image, Toast.LENGTH_SHORT).show();
                img_recView.setVisibility(View.GONE);
            }

    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }
    }

    public String saveImage(Bitmap image) {
        String storedImageName;
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/DCIM/BitmRecipe");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        storedImageName = n + ".jpg";
        File file = new File(myDir, storedImageName);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.JPEG, 100, out);
            //Toast.makeText(NewRecipeActivity.this, "Image Saved", Toast.LENGTH_SHORT).show();
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            storedImageName = null;
        }
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(file);
        mediaScanIntent.setData(contentUri);
        getApplicationContext().sendBroadcast(mediaScanIntent);
        //final String storedImageName1 = storedImageName;
        imageURL = storedImageName;
        return storedImageName;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:

                //Log.d("Back pressed", "from menu");
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        // For good practice, this will be called either automatically on 2.0 or later, or from onOptionsItemSelected the code above on earlier versions.
        Intent returnIntent = new Intent();
        if(recipeAdded) {
            returnIntent.putExtra("result", true);
        }
        else{
            returnIntent.putExtra("result", false);
            //TODO Delete the saved images in external memory (hint: deletev image named imageURL from external memory)
        }
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
        overridePendingTransition(R.anim.hold, R.anim.bottom_down);
    }

}
