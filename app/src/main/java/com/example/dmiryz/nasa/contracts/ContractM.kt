package com.example.dmiryz.nasa.contracts

import android.app.Application
import com.example.dmiryz.nasa.models.Dates
import com.example.dmiryz.nasa.models.Photo
import io.reactivex.disposables.CompositeDisposable

interface ContractM {


    interface ViewMainActivity {
        fun setDate(list: List<Dates>)
    }

    interface PresenterMainActivity {
        fun loadDate(application: Application): CompositeDisposable
    }


    interface ViewPhotoListActivity {
        fun setDate(list: ArrayList<Photo>)
    }

    interface PresenterPhotoListActivity {
        fun loadDate(application: Application,date:String): CompositeDisposable
    }


}