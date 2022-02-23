package cn.funcoding.opengles.sample.internal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SampleItemModel {
    String name;
    Class<? extends AppCompatActivity> clazz;
    List<String> permissionList;

    public SampleItemModel(@NonNull String name, @NonNull Class<? extends AppCompatActivity> clazz, @NonNull List<String> permissionList) {
        this.name = name;
        this.clazz = clazz;
        this.permissionList = permissionList;
    }

    public SampleItemModel(@NonNull String name, @NonNull Class<? extends AppCompatActivity> clazz) {
        this(name, clazz, new ArrayList<>());
    }
}
