package cn.funcoding.opengles.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import cn.funcoding.opengles.sample.databinding.ActivityMainBinding;
import cn.funcoding.opengles.sample.google.GoogleSampleActivity;
import cn.funcoding.opengles.sample.internal.SampleItemModel;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private final List<SampleItemModel> sampleItemModelList = new ArrayList<SampleItemModel>() {
        {
            add(new SampleItemModel("Google官方示例", GoogleSampleActivity.class));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.displayView.submitData(sampleItemModelList);
    }
}