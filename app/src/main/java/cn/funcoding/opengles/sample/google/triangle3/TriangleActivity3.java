package cn.funcoding.opengles.sample.google.triangle3;

import android.os.Bundle;

import androidx.annotation.Nullable;

import cn.funcoding.opengles.sample.internal.SampleActivity;

public class TriangleActivity3 extends SampleActivity {
    private MyGLSurfaceView gLView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gLView = new MyGLSurfaceView(this);
        setContentView(gLView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gLView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gLView.onPause();
    }
}
