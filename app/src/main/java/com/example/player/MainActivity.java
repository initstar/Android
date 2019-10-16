package com.example.player;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioGroup;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Fragment> mlist= new ArrayList<Fragment>();
    private RadioGroup mRadioGroup;
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRadioGroup = findViewById(R.id.radioGroup);
        setRadioGroupLinster();
        initFragment();
        init();
    }
    private void setRadioGroupLinster() {
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.auido:
                        addContentFragment(mlist.get(index),mlist.get(0));
                        index = 0;
                        Log.d("auido", "onClick: 音乐");
                        break;
                    case R.id.video:
                        addContentFragment(mlist.get(index),mlist.get(1));
                        index = 1;
                        Log.d("video", "onClick: 视频");
                        break;
                }
            }
        });
    }
    private void addContentFragment(Fragment from,Fragment to){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if(!to.isAdded()){
            ft.add(R.id.see,to).show(to);
        }else {
            ft.hide(from).show(to);
        }
        ft.commit();
    }
    private void initFragment() {
        AudioFragment audioFragment = new AudioFragment();
        VideoFragment videoFragment = new VideoFragment();
        mlist.add(audioFragment);
        mlist.add(videoFragment);
    }
    private void init() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.see,mlist.get(index));
        ft.commit();
    }
}
