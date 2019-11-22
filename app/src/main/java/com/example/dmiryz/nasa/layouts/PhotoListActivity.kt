package com.example.dmiryz.nasa.layouts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dmiryz.nasa.R
import com.example.dmiryz.nasa.adapters.PhotoListAdapter
import com.example.dmiryz.nasa.contracts.ContractM
import com.example.dmiryz.nasa.models.Photo
import com.example.dmiryz.nasa.presenters.PhotoListPresenter
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_photo_list.*


class PhotoListActivity : AppCompatActivity(),ContractM.ViewPhotoListActivity {

    lateinit var adapter: PhotoListAdapter
    private lateinit var lists: ArrayList<Photo>
    var disposable: CompositeDisposable = CompositeDisposable()
    private lateinit var mPresenter: ContractM.PresenterPhotoListActivity
    private val EXTRA_DATE = "EXTRA_DATE"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_list)
        mPresenter = PhotoListPresenter(this)
        lists = ArrayList()
        adapter = PhotoListAdapter(lists)
        val dateCurrentDay = intent.getStringExtra(EXTRA_DATE)
        recyclerView2.setHasFixedSize(true)
        recyclerView2.adapter = adapter
        recyclerView2.layoutManager = LinearLayoutManager(this)
        disposable = mPresenter.loadDate(application,dateCurrentDay)


    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }

    override fun setDate(dates: ArrayList<Photo>) {
        adapter.setDates(dates)
    }
}
