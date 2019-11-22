package com.example.dmiryz.nasa.layouts

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dmiryz.nasa.R
import com.example.dmiryz.nasa.adapters.DateAdapter
import com.example.dmiryz.nasa.contracts.ContractM.*
import com.example.dmiryz.nasa.models.Dates
import com.example.dmiryz.nasa.presenters.MainPresenter
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), ViewMainActivity {

    lateinit var adapter: DateAdapter
    private lateinit var lists: ArrayList<Dates>
    var disposable: CompositeDisposable = CompositeDisposable()

    private lateinit var mPresenter: PresenterMainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mPresenter = MainPresenter(this)
        supportActionBar?.title = getString(com.example.dmiryz.nasa.R.string.choose_day)
        lists = ArrayList()
        adapter = DateAdapter(lists)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        disposable = mPresenter.loadDate(application)
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }

    override fun setDate(list: List<Dates>) {
        adapter.setDates(list)
    }
    
}
