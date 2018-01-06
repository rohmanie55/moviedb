package com.mr.rohmani.moviezamannow;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.mr.rohmani.moviezamannow.fragment.Now;
import com.mr.rohmani.moviezamannow.fragment.Popular;
import com.mr.rohmani.moviezamannow.fragment.Top;
import com.mr.rohmani.moviezamannow.fragment.Upcoming;
import com.mr.rohmani.moviezamannow.models.MovieList;

import java.util.List;

public class Main extends AppCompatActivity {

    private Fragment fragment = null;
    private Class fragmentClass;
    public dbHandler db;
    private SliderLayout sliderLayout;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_popular:
                    fragmentClass = Popular.class;
                    callFragment();
                    setTitle(R.string.title_popular);
                    return true;
                case R.id.nav_top:
                    fragmentClass = Top.class;
                    callFragment();
                    setTitle(R.string.title_top);
                    return true;
                case R.id.nav_now:
                    fragmentClass = Now.class;
                    callFragment();
                    setTitle(R.string.title_now);
                    return true;
                case R.id.nav_upcoming:
                    fragmentClass = Upcoming.class;
                    callFragment();
                    setTitle(R.string.title_upcoming);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        db = new dbHandler(Main.this);

        if (savedInstanceState == null) {
            fragmentClass = Popular.class;
            callFragment();
        }
        List<MovieList> banner = db.getBanner();
        sliderLayout = (SliderLayout) findViewById(R.id.slider);
        for (int counter = 0; counter < banner.size(); counter++) {
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(banner.get(counter).title)
                    .image("http://image.tmdb.org/t/p/w185/" + banner.get(counter).url)
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop);
            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", banner.get(counter).mid);
            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(4000);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.toolbarsize);
        collapsingToolbarLayout.setExpandedTitleGravity(Gravity.CENTER_HORIZONTAL);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    private void callFragment() {
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.mylayout, fragment).commit();
    }
}
