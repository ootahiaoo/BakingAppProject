package com.example.android.bakingappproject.ui.fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingappproject.R;
import com.example.android.bakingappproject.data.model.Steps;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepFragmentDirectionsAdapter extends RecyclerView
        .Adapter<StepFragmentDirectionsAdapter.StepFragmentDirectionsAdapterViewHolder> {

    @BindView(R.id.textview_short_description)
    TextView shortDescriptionTextView;

    private Context context;
    private final List<Steps> stepsList;


    private static ClickListener clickListener;

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        StepFragmentDirectionsAdapter.clickListener = clickListener;
    }

    public StepFragmentDirectionsAdapter(Context context, List<Steps> stepsList) {
        this.context = context;
        this.stepsList = stepsList;
    }


    @Override
    public StepFragmentDirectionsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        context = parent.getContext();
        int layoutIdForListItem = R.layout.details_recyclerview_directions_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);
        ButterKnife.bind(this, view);
        StepFragmentDirectionsAdapterViewHolder viewHolder = new
                StepFragmentDirectionsAdapterViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(StepFragmentDirectionsAdapterViewHolder viewHolder, int i) {
        final Steps currentStep = stepsList.get(i);
        shortDescriptionTextView.setText(currentStep.getStepShortDescription());
    }

    @Override
    public int getItemCount() {
        if (null == stepsList) return 0;
        return stepsList.size();
    }

    public class StepFragmentDirectionsAdapterViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {


        private StepFragmentDirectionsAdapterViewHolder(View view) {
            super(view);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(final View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }
}
