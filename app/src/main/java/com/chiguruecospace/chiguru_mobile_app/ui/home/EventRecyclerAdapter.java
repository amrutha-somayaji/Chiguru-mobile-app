package com.chiguruecospace.chiguru_mobile_app.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chiguruecospace.chiguru_mobile_app.R;

import java.util.List;

public class EventRecyclerAdapter extends RecyclerView.Adapter<EventRecyclerAdapter.ViewHolder> {

    public List<HomeViewModel> eventlist;

    public EventRecyclerAdapter(List<HomeViewModel> eventlist){

        this.eventlist = eventlist;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_event, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String descdata = eventlist.get(position).getDesc();
        holder.setDescText(descdata);

    }

    @Override
    public int getItemCount() {
        return eventlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView descview;
        private View mView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setDescText(String text){

            descview = mView.findViewById(R.id.eventdesctext);
            descview.setText(text);

        }
    }

}
