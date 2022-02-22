package com.johnjacksonryan.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.johnjacksonryan.R;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.ViewHolder> {

    private String[] names;
    private String[] ids;
    private String[] subNames;
    private String[] info;
    private OnNoteListener mOnNoteListener;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView nameTextView;
        private final TextView idTextView;
        private final TextView subNameTextView;
        private String exerciseInfo = "";
        private String name = "";
        OnNoteListener onNoteListener;

        public ViewHolder(View view, OnNoteListener onNoteListener) {
            super(view);
            // Define click listener for the ViewHolder's View

            nameTextView = (TextView) view.findViewById(R.id.nameTextView);
            idTextView = (TextView) view.findViewById(R.id.idTextView);
            subNameTextView = (TextView) view.findViewById(R.id.subNameTextView);
            this.onNoteListener = onNoteListener;
            itemView.setOnClickListener(this);
        }

        public TextView getNameTextView() {
            return nameTextView;
        }
        public TextView getIdTextView() {
            return idTextView;
        }
        public TextView getSubNameTextView() {
            return subNameTextView;
        }

        public void setInfo(String info) {
            if (info != null) {
                this.exerciseInfo = info;
            }
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public void onClick(View view) {
            onNoteListener.OnNoteClick(getAdapterPosition(), exerciseInfo);
        }
    }

    public interface OnNoteListener {
        void OnNoteClick(int position, String exerciseInfo);
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     */
    public WorkoutAdapter(String[] n, String[] i, String[] sn, String[] in, OnNoteListener onNoteListener) {
        names = n;
        ids = i;
        subNames = sn;
        info = in;
        this.mOnNoteListener = onNoteListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.workout_layout, viewGroup, false);

        return new ViewHolder(view, mOnNoteListener);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getNameTextView().setText(names[position]);
        viewHolder.getIdTextView().setText(ids[position]);
        viewHolder.getSubNameTextView().setText(subNames[position]);
        viewHolder.setInfo(info[position]);
        viewHolder.setName(names[position]);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return names.length;
    }
}