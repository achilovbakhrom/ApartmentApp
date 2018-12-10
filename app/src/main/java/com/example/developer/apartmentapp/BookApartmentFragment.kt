package com.example.developer.apartmentapp

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.developer.apartmentapp.entity.Apartment
import java.text.SimpleDateFormat
import java.util.*

class BookApartmentFragment: Fragment() {
    var apartment: Apartment? = null

    var tvApartmentNumber: TextView? = null
    var tvNumberOfBeds: TextView? = null
    var etFrom: EditText? = null
    var etTo: EditText? = null
    var button: Button? = null
    var from: Long? = null
    var to: Long? = null
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

        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.book_apartment, container, false)

        tvApartmentNumber = view.findViewById(R.id.tvApartmentNumber)
        tvNumberOfBeds = view.findViewById(R.id.tvNumberOfBeds)
        val instance = Calendar.getInstance()
        etFrom = view.findViewById(R.id.etFrom)
        etFrom?.setOnClickListener {
            DatePickerDialog(context!!, fromListener, instance.get(Calendar.YEAR), instance.get(Calendar.MONTH), instance.get(Calendar.DAY_OF_MONTH)).show()
        }

        etTo = view.findViewById(R.id.etTo)
        etTo?.setOnClickListener {
            DatePickerDialog(context!!, toListener, instance.get(Calendar.YEAR), instance.get(Calendar.MONTH), instance.get(
                Calendar.DAY_OF_MONTH)).show()
        }
        button = view.findViewById(R.id.button)

        if (arguments != null) {
            apartment = arguments?.getSerializable("apartment") as? Apartment
        }

        if (apartment != null) {
            tvApartmentNumber?.setText("Apartment No. ${apartment?.id ?: 0}")
            tvNumberOfBeds?.setText("Number of Beds: ${apartment?.numberOfBeds ?: 0}")
        }

        button?.setOnClickListener {
            if (from != null && to != null) {
                val fragment = (context as MainActivity).supportFragmentManager.findFragmentByTag("main")
                if (fragment != null) {
                    (fragment as MainFragment).bookApartment(apartment?.id!!, from!!, to!!)
                }
                (context as MainActivity).supportFragmentManager.popBackStack()
            } else {
                Toast.makeText(context, "Please select from and to dates", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

}