package com.orbital.itenary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class TripRvAdapter extends RecyclerView.Adapter<TripRvAdapter.TripClassViewHolder> {
    private Context mContext;
    private List<ProgramClass> programList;
    private titleTrip;

    public TripRvAdapter(Context context, List<ProgramClass> progClass){
        mContext = context;
        programList = progClass;
    }

    @NonNull
    @Override
    public TripClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_trip, parent, false);
        TripClassViewHolder tripClassVH = new TripClassViewHolder(itemView);

        return tripClassVH;
    }

    @Override
    public void onBindViewHolder(@NonNull TripClassViewHolder holder, final int position) {
        final ProgramClass prog = programList.get(position);
        holder.tripTitle.setText(tripTitle);
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ProgramDisplay.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("prog", prog);
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return programList.size();
    }

    public class TripClassViewHolder extends RecyclerView.ViewHolder {
        public TextView tripTitle;
        public CardView mCardView;

        public TripClassViewHolder(View view) {
            super(view);
            tripTitle = view.findViewById(R.id.tripTitle);
            mCardView = view.findViewById(R.id.cardViewTrip);
        }
    }
}
