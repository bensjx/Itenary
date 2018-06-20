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

public class ProgramRvAdapter extends RecyclerView.Adapter<ProgramRvAdapter.ProgramClasssViewHolder> {
    private Context mContext;
    private List<ProgramClass> programList;

    public ProgramRvAdapter(Context context, List<ProgramClass> progClass){
        mContext = context;
        programList = progClass;
    }

    @NonNull
    @Override
    public ProgramClasssViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        ProgramClasssViewHolder progClassViewHolder = new ProgramClasssViewHolder(itemView);

        return progClassViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProgramClasssViewHolder holder, final int position) {
        final ProgramClass prog = programList.get(position);
        holder.mType.setText(prog.getTypeOfActivity());
        holder.mTitle.setText(prog.getTypeOfActivity());
        holder.mDate.setText(prog.getDateOfActivity());
        holder.mTime.setText(prog.getTimeOfActivity());
        holder.mDuration.setText(prog.getDurationOfActivity());
        holder.mNotes.setText(prog.getNoteOfActivity());
        holder.mCost.setText(prog.getCostOfActivity());
        holder.mCurrency.setText(prog.getCurrencyOfActivity());
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ItineraryEdit.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("ProgramClass", prog);
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return programList.size();
    }

    public class ProgramClasssViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitle;
        public TextView mType;
        public TextView mDate;
        public TextView mTime;
        public TextView mDuration;
        public TextView mNotes;
        public TextView mCost;
        public TextView mCurrency;
        public CardView mCardView;

        public ProgramClasssViewHolder(View view) {
            super(view);
            mTitle = view.findViewById(R.id.input_title);
            mType = view.findViewById(R.id.input_type);
            mDate = view.findViewById(R.id.input_date);
            mTime = view.findViewById(R.id.input_time);
            mDuration = view.findViewById(R.id.input_duration);
            mNotes = view.findViewById(R.id.input_notes);
            mCost = view.findViewById(R.id.input_cost);
            mCurrency = view.findViewById(R.id.input_currency);
            mCardView = view.findViewById(R.id.cardViewProg);
        }
    }
}
