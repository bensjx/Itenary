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

public class ProgramRvAdapter extends RecyclerView.Adapter<ProgramRvAdapter.ProgramClassViewHolder> {
    private Context mContext;
    private List<ProgramClass> programList;

    public ProgramRvAdapter(Context context, List<ProgramClass> progClass){
        mContext = context;
        programList = progClass;
    }

    @NonNull
    @Override
    public ProgramClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_program, parent, false);
        ProgramClassViewHolder progClassVH = new ProgramClassViewHolder(itemView);

        return progClassVH;
    }

    @Override
    public void onBindViewHolder(@NonNull ProgramClassViewHolder holder, final int position) {
        final ProgramClass prog = programList.get(position);
        holder.mType.setText(prog.getTypeOfActivity());
        holder.mTitle.setText(prog.getTitleOfActivity());
        holder.mDate.setText(prog.getDateOfActivity());
        holder.mTime.setText(prog.getTimeOfActivity());
        holder.mDuration.setText(prog.getDurationOfActivity());
        holder.mNotes.setText(prog.getNoteOfActivity());
        holder.mCost.setText(prog.getCostOfActivity());
        holder.mCurrency.setText(prog.getCurrencyOfActivity());
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ProgramEdit.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("progDisplayToProgEdit", prog);
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return programList.size();
    }

    public class ProgramClassViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitle;
        public TextView mType;
        public TextView mDate;
        public TextView mTime;
        public TextView mDuration;
        public TextView mNotes;
        public TextView mCost;
        public TextView mCurrency;
        public CardView mCardView;

        public ProgramClassViewHolder(View view) {
            super(view);
            mTitle = view.findViewById(R.id.titleActivity);
            mType = view.findViewById(R.id.typeActivity);
            mDate = view.findViewById(R.id.dateActivity);
            mTime = view.findViewById(R.id.timeActivity);
            mDuration = view.findViewById(R.id.durationActivity);
            mNotes = view.findViewById(R.id.notesActivity);
            mCost = view.findViewById(R.id.costActivity);
            mCurrency = view.findViewById(R.id.currencyActivity);
            mCardView = view.findViewById(R.id.cardViewProg);
        }
    }
}
