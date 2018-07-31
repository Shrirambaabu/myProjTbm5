package com.forzo.holdMyCard.ui.services;

import com.forzo.holdMyCard.ui.models.MyLibrary;
import com.forzo.holdMyCard.ui.models.MyNotes;
import com.forzo.holdMyCard.ui.models.MyRemainder;

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
                "Chennai","1"));

        myLibraryArrayList.add(new MyLibrary("TamilArasan", "Circuit Designer",
                "Bangalore","2"));

        myLibraryArrayList.add(new MyLibrary("Mohan", "Electrical Engineering",
                "Chennai","3"));

        myLibraryArrayList.add(new MyLibrary("Mani", "Electrical Engineering",
                "Villupuram","4"));

        myLibraryArrayList.add(new MyLibrary("Sugumar", "Electrical Engineering",
                "Vellore","5"));

        myLibraryArrayList.add(new MyLibrary("Makesh", "Electrical Engineering",
                "Mayiladuthurai","6"));


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

public static Collection<? extends MyRemainder> getRemainderList() {

        ArrayList<MyRemainder> myRemainders = new ArrayList<>();

    myRemainders.add(new MyRemainder("Shriram Android Developer Chennai","12-04-2018 12:00PM"));

    myRemainders.add(new MyRemainder("TamilArasan Circuit Designer Bangalore","22-04-2018 12:00PM"));

    myRemainders.add(new MyRemainder("Mohan Electrical Engineering Chennai","18-04-2018 12:00PM"));

    myRemainders.add(new MyRemainder("Mani Electrical Engineering Villupuram","17-04-2018 12:00PM"));

    myRemainders.add(new MyRemainder("Sugumar Electrical Engineering Vellore","21-04-2018 12:00PM"));

    myRemainders.add(new MyRemainder("Makesh Electrical Engineering Mayiladuthurai","08-04-2018 12:00PM"));

    myRemainders.add(new MyRemainder("SAM Electrical Engineering Bangalore","12-04-2018 12:00PM"));

    myRemainders.add(new MyRemainder("Kd Electrical Engineering Chennai","11-04-2018 12:00PM"));


        return myRemainders;

    }

}
