package cn.funcoding.opengles.sample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.funcoding.opengles.sample.databinding.ActivityMainBinding;
import cn.funcoding.opengles.sample.databinding.ViewMainRvItemBinding;
import cn.funcoding.opengles.sample.google.GoogleSampleActivity;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private final List<MainItemModel> mainItemModelList = new ArrayList<MainItemModel>() {
        {
            add(new MainItemModel("Google官方例子", GoogleSampleActivity.class));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.mainItemRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.mainItemRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        MainItemRvAdapter mainItemRvAdapter = new MainItemRvAdapter();
        binding.mainItemRv.setAdapter(mainItemRvAdapter);
        mainItemRvAdapter.submitList(mainItemModelList);
    }

    static class MainItemRvAdapter extends ListAdapter<MainItemModel, MainItemRvAdapter.ViewHolder> {

        protected MainItemRvAdapter() {
            super(new DiffUtil.ItemCallback<MainItemModel>() {
                @Override
                public boolean areItemsTheSame(@NonNull MainItemModel oldItem, @NonNull MainItemModel newItem) {
                    return oldItem == newItem;
                }

                @Override
                public boolean areContentsTheSame(@NonNull MainItemModel oldItem, @NonNull MainItemModel newItem) {
                    return oldItem.clazz.getName().equals(newItem.clazz.getName());
                }
            });
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ViewMainRvItemBinding binding = ViewMainRvItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            MainItemModel mainItemModel = getItem(position);
            if (mainItemModel != null) {
                holder.bind(mainItemModel);
            }
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            ViewMainRvItemBinding binding;
            MainItemModel mainItemModel;

            public ViewHolder(@NonNull ViewMainRvItemBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

            public void bind(@NonNull MainItemModel mainItemModel) {
                this.mainItemModel = mainItemModel;
                binding.titleTv.setText(mainItemModel.name);
                binding.titleTv.setOnClickListener(v -> openTargetActivity());
            }

            private void openTargetActivity() {
                Activity activity = (Activity) binding.getRoot().getContext();
                if (checkSelfPermission(binding.getRoot().getContext(), mainItemModel.permissionList)
                        != PackageManager.PERMISSION_GRANTED) {
                    String[] permissionStringArr = new String[mainItemModel.permissionList.size()];
                    for (int i = 0; i < mainItemModel.permissionList.size(); i++) {
                        permissionStringArr[i] = mainItemModel.permissionList.get(i);
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        activity.requestPermissions(permissionStringArr, 1);
                    }
                }
                Intent intent = new Intent(activity, mainItemModel.clazz);
                intent.putExtra("title", mainItemModel.name);
                activity.startActivity(intent);
            }

            private int checkSelfPermission(Context context, @NonNull List<String> permissionList) {
                for (int i = 0; i < permissionList.size(); i++) {
                    if (ContextCompat.checkSelfPermission(context, permissionList.get(i)) != PackageManager.PERMISSION_GRANTED) {
                        return PackageManager.PERMISSION_DENIED;
                    }
                }
                return PackageManager.PERMISSION_GRANTED;
            }
        }

    }

    static class MainItemModel {
        String name;
        Class<? extends AppCompatActivity> clazz;
        List<String> permissionList;

        public MainItemModel(@NonNull String name, @NonNull Class<? extends AppCompatActivity> clazz, @NonNull List<String> permissionList) {
            this.name = name;
            this.clazz = clazz;
            this.permissionList = permissionList;
        }

        public MainItemModel(@NonNull String name, @NonNull Class<? extends AppCompatActivity> clazz) {
            this(name, clazz, new ArrayList<>());
        }
    }
}