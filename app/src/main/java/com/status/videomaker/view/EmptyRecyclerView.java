package com.status.videomaker.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class EmptyRecyclerView extends RecyclerView {
    private AdapterDataObserver dataObserver;
    private View emptyView;

    public EmptyRecyclerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public EmptyRecyclerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.dataObserver = new AdapterDataObserver() {
            public void onChanged() {
                Adapter adapter = EmptyRecyclerView.this.getAdapter();
                if (!(adapter == null || EmptyRecyclerView.this.emptyView == null)) {
                    if (adapter.getItemCount() == 0) {
                        EmptyRecyclerView.this.emptyView.setVisibility(View.VISIBLE);
                        EmptyRecyclerView.this.setVisibility(View.GONE);
                        return;
                    }
                    EmptyRecyclerView.this.emptyView.setVisibility(View.GONE);
                    EmptyRecyclerView.this.setVisibility(View.VISIBLE);
                }
            }
        };
    }

    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(this.dataObserver);
        }
        this.dataObserver.onChanged();
    }

    public void setEmptyView(View view) {
        this.emptyView = view;
    }
}
