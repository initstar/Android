package com.example.player;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import java.io.File;

public class VideoFragment extends Fragment implements View.OnClickListener {

    private VideoView videoView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view= LayoutInflater.from(getContext()).inflate(R.layout.video_layout,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        videoView = (VideoView) getActivity().findViewById(R.id.video_view);
        Button back = (Button) getActivity().findViewById(R.id.video_back);
        Button pause = (Button) getActivity().findViewById(R.id.video_play_pause);
        Button fast = (Button) getActivity().findViewById(R.id.video_fast);
        back.setOnClickListener(this);
        pause.setOnClickListener(this);
        fast.setOnClickListener(this);
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{ Manifest.permission. WRITE_EXTERNAL_STORAGE }, 1);
        } else {
            initVideoPath(); // 初始化MediaPlayer
        }
    }
    private void initVideoPath() {
        File file = new File("/sdcard/Download/movie.mp4");
        videoView.setVideoPath(file.getPath()); // 指定视频文件的路径
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initVideoPath();
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
            case R.id.video_back:
                videoView.seekTo(videoView.getCurrentPosition() - 5000); // 视频快退
                Log.e("视频快退: ", "当前时间"+videoView.getCurrentPosition());
                break;
            case R.id.video_play_pause:
                if (videoView.isPlaying()) {
                    videoView.pause(); // 暂停/播放
                    Log.e("暂停: ", "当前时间"+videoView.getCurrentPosition());
                }else {
                    videoView.start();
                    Log.e("开始: ", "当前时间"+videoView.getCurrentPosition());
                }
                break;
            case R.id.video_fast:
                videoView.seekTo(videoView.getCurrentPosition() + 5000);// 视频快进
                Log.e("视频快进: ", "当前时间"+videoView.getCurrentPosition());
                break;
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (videoView != null) {
            videoView.suspend();
        }
    }
}
