package com.orbital.itenary;

import android.os.Parcel;
import android.os.Parcelable;

public class ProgramClass implements Parcelable{
    private String typeOfActivity;
    private String titleOfActivity;
    private String dateOfActivity;
    private String timeOfActivity;
    private String durationOfActivity;
    private String noteOfActivity;
    private String costOfActivity;
    private String currencyOfActivity;
    private String id;

    //constructors
    public ProgramClass(){
    }

    public ProgramClass(String typeOfActivity, String titleOfActivity, String dateOfActivity, String timeOfActivity, String durationOfActivity, String noteOfActivity, String costOfActivity, String currencyOfActivity) {
        this.typeOfActivity = typeOfActivity;
        this.titleOfActivity = titleOfActivity;
        this.dateOfActivity = dateOfActivity;
        this.timeOfActivity = timeOfActivity;
        this.durationOfActivity = durationOfActivity;
        //this.noteOfActivity = noteOfActivity;
        //this.costOfActivity = costOfActivity;
        //this.currencyOfActivity = currencyOfActivity;
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

    public String getId() {
        return id;
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

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(typeOfActivity);
        dest.writeString(titleOfActivity);
        dest.writeString(dateOfActivity);
        dest.writeString(timeOfActivity);
        dest.writeString(durationOfActivity);
        dest.writeString(noteOfActivity);
        dest.writeString(costOfActivity);
        dest.writeString(currencyOfActivity);

    }

    public ProgramClass(Parcel source) {
        this.id = source.readString();
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

