package com.hitechwebdesign.yeasin.recipesqlite.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.hitechwebdesign.yeasin.recipesqlite.R;
import com.hitechwebdesign.yeasin.recipesqlite.adpter.RecipeAdapter;
import com.hitechwebdesign.yeasin.recipesqlite.database.RecipeDbSource;
import com.hitechwebdesign.yeasin.recipesqlite.model.RecipeModel;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeFragment extends Fragment{
    private StaggeredGridLayoutManager GridLayoutManager;
    RecipeAdapter mAdapter;
    private RecipeDbSource source;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    private Context context;

    List<RecipeModel> recipelist;

    private ProgressDialog pDialog;
    View rootView;

    public static RecipeFragment newInstance() {
        RecipeFragment recipefeag = new RecipeFragment();
        return recipefeag;
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_recipe, null);
        source = new RecipeDbSource(getContext());
        GridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayouts);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

        getAllrecipe();
        return rootView;
    }



    private void getAllrecipe() {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.lsv_recipe);
        GridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        GridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(GridLayoutManager);
        recipelist = source.getAllRecipes();
        recipelist.size();
        mAdapter = new RecipeAdapter(getActivity(),recipelist);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }


    private void refresh() {
        swipeRefreshLayout.setRefreshing(true);

        //refresh long-time task in background thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //dummy delay for 2 second
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //update ui on UI thread
                (getActivity()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        swipeRefreshLayout.setRefreshing(false);
                    }
                });

            }
        }).start();
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

}
