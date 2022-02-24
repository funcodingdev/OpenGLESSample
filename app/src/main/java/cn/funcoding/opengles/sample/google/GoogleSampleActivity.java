package cn.funcoding.opengles.sample.google;

import android.os.Bundle;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import cn.funcoding.opengles.sample.databinding.ActivityGoogleSampleBinding;
import cn.funcoding.opengles.sample.google.square1.SquareActivity1;
import cn.funcoding.opengles.sample.google.square2.SquareActivity2;
import cn.funcoding.opengles.sample.google.square3.SquareActivity3;
import cn.funcoding.opengles.sample.google.square4.SquareActivity4;
import cn.funcoding.opengles.sample.google.triangle1.TriangleActivity1;
import cn.funcoding.opengles.sample.google.triangle2.TriangleActivity2;
import cn.funcoding.opengles.sample.google.triangle3.TriangleActivity3;
import cn.funcoding.opengles.sample.google.triangle4.TriangleActivity4;
import cn.funcoding.opengles.sample.internal.SampleActivity;
import cn.funcoding.opengles.sample.internal.SampleItemModel;

/**
 * Google官方示例
 * https://developer.android.google.cn/training/graphics/opengl
 */
public class GoogleSampleActivity extends SampleActivity {
    private ActivityGoogleSampleBinding binding;
    private final List<SampleItemModel> sampleItemModelList = new ArrayList<SampleItemModel>() {
        {
            add(new SampleItemModel("三角形-绘制三角形", TriangleActivity1.class));
            add(new SampleItemModel("三角形-应用投影和相机视图", TriangleActivity2.class));
            add(new SampleItemModel("三角形-添加动画", TriangleActivity3.class));
            add(new SampleItemModel("三角形-响应触摸事件", TriangleActivity4.class));

            add(new SampleItemModel("矩形-绘制矩形", SquareActivity1.class));
            add(new SampleItemModel("矩形-应用投影和相机视图", SquareActivity2.class));
            add(new SampleItemModel("矩形-添加动画", SquareActivity3.class));
            add(new SampleItemModel("矩形-响应触摸事件", SquareActivity4.class));
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGoogleSampleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.displayView.submitData(sampleItemModelList);
    }
}
