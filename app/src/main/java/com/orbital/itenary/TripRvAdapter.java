package com.orbital.itenary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class TripRvAdapter extends RecyclerView.Adapter<TripRvAdapter.TripViewHolder> {
    private Context mContext;
    private List<ProgramClass> tripList;

    public TripRvAdapter(Context context, List<ProgramClass> programClass){
        mContext = context;
        tripList = programClass;
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_trip, parent, false);
        TripViewHolder programClassVH = new TripViewHolder(itemView);

        return programClassVH;
    }

    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, final int position) {
        final ProgramClass trip = tripList.get(position);
        holder.mTitle.setText(trip.getTripTitle());
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ProgramDisplay.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("tripToProgDisplay", trip);
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }

    public class TripViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitle;
        public CardView mCardView;

        public TripViewHolder(View view) {
            super(view);
            mTitle = view.findViewById(R.id.tripTitle);
            mCardView = view.findViewById(R.id.cardViewTrip);
        }
    }
}
