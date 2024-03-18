package com.example.lab4memopaddcp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab4memopaddcp.databinding.ActivityMainBinding;

import java.beans.PropertyChangeEvent;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AbstractView{

    private ActivityMainBinding binding;
    private DefaultController controller;
    private List<Memo> memoData;
    private int selectedMemo = -1;
    MemoPadItemClickHandler itemClick = new MemoPadItemClickHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ClickHandler click = new ClickHandler();
        binding.button.setOnClickListener(click);
        binding.button2.setOnClickListener(click);


        DefaultModel model = new DefaultModel(new DatabaseHandler(this,null,null,1));
        controller = new DefaultController(model);

        controller.addView(this);

        model.initDefault();

        controller.getAllMemos();
    }


    @Override
    public void modelPropertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        String propertyValue = evt.getNewValue().toString();

        if ( propertyName.equals(DefaultController.ELEMENT_GETALLMEMOS_PROPERTY) ) {

            String oldPropertyValue = evt.getOldValue().toString();

            if ( !oldPropertyValue.equals(propertyValue) ) {
                memoData = (List<Memo>) evt.getNewValue();
                updateRecyclerView();
            }
        }
    }

    private void updateRecyclerView() {
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, memoData);
        binding.output.setHasFixedSize(true);
        binding.output.setLayoutManager(new LinearLayoutManager(this));
        binding.output.setAdapter(adapter);
    }


    private class ClickHandler implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            String tag = v.getTag().toString();

            if (tag.equals("addMemo")) {
                String newText = binding.editTextText.getText().toString();
                controller.bAdd(newText);
            }
            else if (tag.equals("deleteMemo")) {
                controller.bDelete(selectedMemo);
                selectedMemo = -1;
            }
        }
    }
    private class MemoPadItemClickHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int position = binding.output.getChildLayoutPosition(v);
            RecyclerViewAdapter adapter = (RecyclerViewAdapter)binding.output.getAdapter();
            if (adapter != null) {
                Memo memo = adapter.getMemo(position);
                int id = memo.getId();
                //Toast.makeText(v.getContext(), String.valueOf(id), Toast.LENGTH_SHORT).show();
                selectedMemo = id;
            }
        }
    }

    public MemoPadItemClickHandler getItemClick() { return itemClick; }

}