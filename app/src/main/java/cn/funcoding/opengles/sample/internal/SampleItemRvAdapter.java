package cn.funcoding.opengles.sample.internal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.funcoding.opengles.sample.databinding.ViewSampleRvItemBinding;

public class SampleItemRvAdapter extends ListAdapter<SampleItemModel, SampleItemRvAdapter.ViewHolder> {

    protected SampleItemRvAdapter() {
        super(new DiffUtil.ItemCallback<SampleItemModel>() {
            @Override
            public boolean areItemsTheSame(@NonNull SampleItemModel oldItem, @NonNull SampleItemModel newItem) {
                return oldItem == newItem;
            }

            @Override
            public boolean areContentsTheSame(@NonNull SampleItemModel oldItem, @NonNull SampleItemModel newItem) {
                return oldItem.clazz.getName().equals(newItem.clazz.getName());
            }
        });
    }

    @NonNull
    @Override
    public SampleItemRvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewSampleRvItemBinding binding = ViewSampleRvItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SampleItemModel mainItemModel = getItem(position);
        if (mainItemModel != null) {
            holder.bind(mainItemModel);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ViewSampleRvItemBinding binding;
        SampleItemModel mainItemModel;

        public ViewHolder(@NonNull ViewSampleRvItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(@NonNull SampleItemModel mainItemModel) {
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
