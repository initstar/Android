package com.example.player;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.io.File;

public class AudioFragment extends Fragment implements View.OnClickListener{
    private MediaPlayer mediaPlayer = new MediaPlayer();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view= LayoutInflater.from(getContext()).inflate(R.layout.audio_layout,container,false);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button back = (Button) getActivity().findViewById(R.id.audio_back);
        Button start = (Button) getActivity().findViewById(R.id.audio_play_pause);
        Button fast = (Button) getActivity().findViewById(R.id.audio_fast);
        back.setOnClickListener(this);
        start.setOnClickListener(this);
        fast.setOnClickListener(this);
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{ Manifest.permission. WRITE_EXTERNAL_STORAGE }, 1);
        } else {
            initMediaPlayer(); // 初始化MediaPlayer
        }
    }
    private void initMediaPlayer() {
        try {
            File file = new File("/sdcard/Download/music.mp3");
            mediaPlayer.setDataSource(file.getPath()); // 指定音频文件的路径
            mediaPlayer.prepare(); // 让MediaPlayer进入到准备状态
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initMediaPlayer();
                } else {
                    Toast.makeText(getActivity(), "拒绝权限将无法使用程序", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                }
                break;
            default:
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.audio_back:
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() - 5000); // 视频快退
                Log.e("音乐快退: ", "当前时间"+mediaPlayer.getCurrentPosition());
                break;
            case R.id.audio_play_pause:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause(); // 暂停/播放
                    Log.e("暂停: ", "当前时间"+mediaPlayer.getCurrentPosition());
                }else {
                    mediaPlayer.start();
                    Log.e("开始: ", "当前时间"+mediaPlayer.getCurrentPosition());
                }
                break;
            case R.id.audio_fast:
                //initMediaPlayer();
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + 5000); // 视频快退
                Log.e("音乐快进: ", "当前时间"+mediaPlayer.getCurrentPosition());
                break;
            default:
                break;
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }
}
