package com.pankaj.dojoin.ui.categroy_selection

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SelectionViewModel :ViewModel(){

    var personReachStatus= MutableLiveData<PersonReachStatus>()
    var appointmentStatus= MutableLiveData<AppointmentStatus>()


    init {
        personReachStatus.value= PersonReachStatus.NONE
        appointmentStatus.value=AppointmentStatus.NONE
    }



    enum class AppointmentStatus{
        NONE,
        BOOK_A_TIME,
        DROP_IN
    }
    enum class PersonReachStatus{
        NONE,
        IN_PERSON,
        ONLINE
    }

}