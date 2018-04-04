package com.forzo.holdMyCard.ui.services;

import com.forzo.holdMyCard.ui.models.MyLibrary;
import com.forzo.holdMyCard.ui.models.MyNotes;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Shriram on 4/2/2018.
 */

public class DataService {

    private static final DataService ourInstance = new DataService();

    private DataService() {
    }

    static DataService getInstance() {
        return ourInstance;
    }



    public static Collection<? extends MyLibrary> getCurrentCardList() {

        ArrayList<MyLibrary> myLibraryArrayList = new ArrayList<>();

        myLibraryArrayList.add(new MyLibrary("Shriram", "Android Developer",
                "Chennai"));

        myLibraryArrayList.add(new MyLibrary("TamilArasan", "Circuit Designer",
                "Bangalore"));

        myLibraryArrayList.add(new MyLibrary("Mohan", "Electrical Engineering",
                "Chennai"));

        myLibraryArrayList.add(new MyLibrary("Mani", "Electrical Engineering",
                "Villupuram"));

        myLibraryArrayList.add(new MyLibrary("Sugumar", "Electrical Engineering",
                "Vellore"));

        myLibraryArrayList.add(new MyLibrary("Makesh", "Electrical Engineering",
                "Mayiladuthurai"));


        return myLibraryArrayList;

    }

    public static Collection<? extends MyNotes> getCardNameList() {

        ArrayList<MyNotes> myNotes = new ArrayList<>();

        myNotes.add(new MyNotes("Shriram Android Developer Chennai"));

        myNotes.add(new MyNotes("TamilArasan Circuit Designer Bangalore"));

        myNotes.add(new MyNotes("Mohan Electrical Engineering Chennai"));

        myNotes.add(new MyNotes("Mani Electrical Engineering Villupuram"));

        myNotes.add(new MyNotes("Sugumar Electrical Engineering Vellore"));

        myNotes.add(new MyNotes("Makesh Electrical Engineering Mayiladuthurai"));

        myNotes.add(new MyNotes("SAM Electrical Engineering Bangalore"));

        myNotes.add(new MyNotes("Kd Electrical Engineering Chennai"));


        return myNotes;

    }

}
