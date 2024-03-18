package com.example.lab4memopaddcp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab4memopaddcp.databinding.MemoItemBinding;

import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<Memo> data;
    private MainActivity activity;
    public RecyclerViewAdapter(MainActivity activity, List<Memo> data) {
        Log.i("MainActivity", data + "  init as data array");
        this.data = data;
        this.activity = activity;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MemoItemBinding binding = MemoItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        binding.getRoot().setOnClickListener(activity.getItemClick()); // the click handler
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setMemo(data.get(position));
        holder.bindData();
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    public Memo getMemo(int position){
        Memo memo = data.get(position);
        return memo;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private Memo memo;
        private TextView memoHolderView;
        public ViewHolder(View itemView) {
            super(itemView);
        }
        public Memo getMemo() {
            return memo;
        }
        public void setMemo(Memo memo) {
            this.memo = memo;
        }
        public void bindData() {
            if (memoHolderView == null) {
                memoHolderView = (TextView) itemView.findViewById(R.id.memoHolderView);
            }
            Log.i("MainActivity", memo.getMemo() + "  memo");
            memoHolderView.setText(memo.getMemo());

        }
    }
}
