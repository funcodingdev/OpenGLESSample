package cn.funcoding.opengles.sample.internal;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SampleDisplayView extends FrameLayout {
    private final SampleItemRvAdapter itemRvAdapter;

    public SampleDisplayView(@NonNull Context context) {
        this(context, null);
    }

    public SampleDisplayView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SampleDisplayView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        RecyclerView sampleRv = new RecyclerView(context, attrs, defStyleAttr);
        addView(sampleRv, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        sampleRv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        sampleRv.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        itemRvAdapter = new SampleItemRvAdapter();
        sampleRv.setAdapter(itemRvAdapter);
    }

    public void submitData(List<SampleItemModel> itemModelList) {
        itemRvAdapter.submitList(itemModelList);
    }
}
