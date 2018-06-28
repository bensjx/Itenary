package com.orbital.itenary;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class ProgramClass implements Parcelable{
    private String typeOfActivity;
    private String titleOfActivity;
    private String dateOfActivity;
    private String timeOfActivity;
    private String durationOfActivity;
    private String noteOfActivity;
    private String costOfActivity;
    private String currencyOfActivity;
    private String programId;
    private String tripId;

    private ArrayList<String> allowedUser;

    //constructors
    public ProgramClass(){
    }

    public ProgramClass(String typeOfActivity, String titleOfActivity, String dateOfActivity, String timeOfActivity, String durationOfActivity, String noteOfActivity, String costOfActivity, String currencyOfActivity, String programId, String tripId) {
        this.typeOfActivity = typeOfActivity;
        this.titleOfActivity = titleOfActivity;
        this.dateOfActivity = dateOfActivity;
        this.timeOfActivity = timeOfActivity;
        this.durationOfActivity = durationOfActivity;
        this.noteOfActivity = noteOfActivity;
        this.costOfActivity = costOfActivity;
        this.currencyOfActivity = currencyOfActivity;
        this.programId = programId;
        this.tripId = tripId;
    }

    //getters
    public String getTypeOfActivity() {
        return typeOfActivity;
    }

    public String getTitleOfActivity() {
        return titleOfActivity;
    }

    public String getDateOfActivity() {
        return dateOfActivity;
    }

    public String getTimeOfActivity() {
        return timeOfActivity;
    }

    public String getDurationOfActivity() {
        return durationOfActivity;
    }

    public String getNoteOfActivity() {
        return noteOfActivity;
    }

    public String getCostOfActivity() {
       return costOfActivity;
    }

    public String getCurrencyOfActivity() {
        return currencyOfActivity;
    }

    public String getProgramId() {
        return programId;
    }

    public String getTripId() {
        return tripId;
    }


    //setters

    public void setTypeOfActivity(String typeOfActivity) {
        this.typeOfActivity = typeOfActivity;
    }

    public void setTitleOfActivity(String titleOfActivity) {
        this.titleOfActivity = titleOfActivity;
    }

    public void setDateOfActivity(String dateOfActivity) {
        this.dateOfActivity = dateOfActivity;
    }

    public void setTimeOfActivity(String timeOfActivity) {
        this.timeOfActivity = timeOfActivity;
    }

    public void setDurationOfActivity(String durationOfActivity) {
        this.durationOfActivity = durationOfActivity;
    }

    public void setNoteOfActivity(String noteOfActivity) {
        this.noteOfActivity = noteOfActivity;
    }

    public void setCostOfActivity(String costOfActivity) {
        this.costOfActivity = costOfActivity;
    }

    public void setCurrencyOfActivity(String currencyOfActivity) {
        this.currencyOfActivity = currencyOfActivity;
    }

    public void setProgramId(String progId) {
        this.programId = progId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(programId);
        dest.writeString(typeOfActivity);
        dest.writeString(titleOfActivity);
        dest.writeString(dateOfActivity);
        dest.writeString(timeOfActivity);
        dest.writeString(durationOfActivity);
        dest.writeString(noteOfActivity);
        dest.writeString(costOfActivity);
        dest.writeString(currencyOfActivity);
        dest.writeString(tripId);

    }

    public ProgramClass(Parcel source) {
        this.tripId = source.readString();
        this.programId = source.readString();
        this.typeOfActivity = source.readString();
        this.titleOfActivity = source.readString();
        this.dateOfActivity = source.readString();
        this.timeOfActivity = source.readString();
        this.durationOfActivity = source.readString();
        this.noteOfActivity = source.readString();
        this.costOfActivity = source.readString();
        this.currencyOfActivity = source.readString();
    }



    public static final Parcelable.Creator<ProgramClass> CREATOR
            = new Parcelable.Creator<ProgramClass>() {
        public ProgramClass createFromParcel(Parcel in) {
            return new ProgramClass(in);
        }

        public ProgramClass[] newArray(int size) {
            return new ProgramClass[size];
        }
    };
}

