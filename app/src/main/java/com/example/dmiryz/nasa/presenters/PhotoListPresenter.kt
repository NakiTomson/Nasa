package com.example.dmiryz.nasa.presenters

import android.app.Application
import android.content.Intent.getIntent
import android.util.Log
import android.widget.Toast
import com.example.dmiryz.nasa.App
import com.example.dmiryz.nasa.contracts.ContractM
import com.example.dmiryz.nasa.models.Dates
import com.example.dmiryz.nasa.models.Photo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PhotoListPresenter(mView: ContractM.ViewPhotoListActivity):ContractM.PresenterPhotoListActivity {

    private val mView: ContractM.ViewPhotoListActivity = mView

    var disposable: CompositeDisposable = CompositeDisposable()

    override fun loadDate(application: Application,date:String): CompositeDisposable {

        val app = application as App

        disposable.add(
            app.getNasaService().api.getPhotosForDate(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { t1, t2 ->
                    if (t2 != null){
                        Toast.makeText(application, "Data loading error", Toast.LENGTH_SHORT).show();
                    }else{
                        mView.setDate(t1 as ArrayList<Photo>)
                    }
                }
        )
        return disposable
    }

}