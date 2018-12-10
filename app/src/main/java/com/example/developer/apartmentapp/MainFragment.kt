package com.example.developer.apartmentapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.developer.apartmentapp.entity.Apartment
import android.app.DatePickerDialog
import java.text.SimpleDateFormat
import java.util.*


class MainFragment: Fragment(), ClickListener  {


    override fun clicked(apartment: Apartment) {
        val bundle = Bundle()
        bundle.putSerializable("apartment", apartment)
        val fragment = BookApartmentFragment()
        fragment.arguments = bundle
        (context as? MainActivity)?.supportFragmentManager?.beginTransaction()?.add(R.id.flMain, fragment)?.addToBackStack(null)?.commit()
    }

    var apartmentList = mutableListOf<Apartment>()
    var rvRecycler: RecyclerView? = null
    var etFrom: EditText? = null
    var etTo: EditText? = null
    var from: Long? = null
    var to: Long? = null
    lateinit var adapter: ApartmentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        for (index in 1..10) {
            var numberOfApartment = 0
            when(index) {
                in 1..4 -> numberOfApartment = 1
                in 5..7 -> numberOfApartment = 2
                in 8..10 -> numberOfApartment = 3
            }
            apartmentList.add(Apartment(index, numberOfApartment))
        }


    }

    fun bookApartment(id: Int, from: Long, to: Long) {
        for (apartment in apartmentList) {
            if (apartment.id == id) {
                apartment.bookedInterval = from..to
                filterApartmentList()
                break
            }
        }

    }

    fun filterApartmentList() {
        var tempList = mutableListOf<Apartment>()
        apartmentList.forEach {
            tempList.add(it)
        }
        if (from == null && to == null) {
            tempList = tempList.filter {
                it.bookedInterval == null
            }.toMutableList()
            adapter = ApartmentAdapter(tempList)
            adapter.listener = this
            rvRecycler?.adapter = adapter
        } else {
            if (from != null) {

                tempList = tempList.filter {
                    !(it.bookedInterval?.contains(from!!) ?: false)
                }.toMutableList()
            }
            if (to != null) {
                tempList = tempList.filter {
                    !(it.bookedInterval?.contains(to!!) ?: false)
                }.toMutableList()
            }
            adapter = ApartmentAdapter(tempList)
            adapter.listener = this
            rvRecycler?.adapter = adapter
        }

    }


    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    var fromListener: DatePickerDialog.OnDateSetListener =
        DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            // TODO Auto-generated method stub
            val myCalendar = Calendar.getInstance()
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val formatter = SimpleDateFormat("dd/MM/yyyy")
            etFrom?.setText(formatter.format(myCalendar.time))
            from = myCalendar.timeInMillis
            filterApartmentList()
        }

    @SuppressLint("SimpleDateFormat")
    var toListener: DatePickerDialog.OnDateSetListener =
        DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            // TODO Auto-generated method stub
            val myCalendar = Calendar.getInstance()
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val formatter = SimpleDateFormat("dd/MM/yyyy")
            etTo?.setText(formatter.format(myCalendar.time))
            to = myCalendar.timeInMillis
            filterApartmentList()
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.main_fragment_layout, container, false)

        etFrom = view.findViewById(R.id.etFrom)
        val instance = Calendar.getInstance()
        etFrom?.setOnClickListener {
            DatePickerDialog(context!!, fromListener, instance.get(Calendar.YEAR), instance.get(Calendar.MONTH), instance.get(Calendar.DAY_OF_MONTH)).show()
        }

        etTo = view.findViewById(R.id.etTo)
        etTo?.setOnClickListener {
            DatePickerDialog(context!!, toListener, instance.get(Calendar.YEAR), instance.get(Calendar.MONTH), instance.get(Calendar.DAY_OF_MONTH)).show()
        }

        rvRecycler = view.findViewById(R.id.rvRecycler)
        rvRecycler?.layoutManager = LinearLayoutManager(context)
        adapter = ApartmentAdapter(apartmentList)
        adapter.listener = this
        rvRecycler?.adapter = adapter



        filterApartmentList()
        return view
    }
}