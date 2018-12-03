package com.reacttive.aadilmirrani.multilistselector

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.reacttive.aadilmirrani.mlslibrary.listener.OnTagSelectListener
import com.reacttive.aadilmirrani.mlslibrary.model.TagHeader
import com.reacttive.aadilmirrani.mlslibrary.model.TagName
import com.reacttive.aadilmirrani.mlslibrary.model.Variant
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnTagSelectListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listVariant = arrayListOf<Variant>()
        listVariant.add(Variant(TagHeader("A", "Color"), arrayListOf(TagName("a1", "Alpha"), TagName("a2", "Beta", true), TagName("a3", "Gamma"))))
        listVariant.add(Variant(TagHeader("B", "Size"), arrayListOf(TagName("b1", "Small", true), TagName("b2", "Medium"), TagName("b3", "Large"))))
        listVariant.add(Variant(TagHeader("C", "Home", true), arrayListOf(TagName("c1", "Cup"), TagName("c2", "Light"), TagName("c3", "Table"))))
//        listVariant.add(Variant(TagHeader("D", "Office", true), arrayListOf(TagName("d1", "Laptop"), TagName("d2", "Printer"), TagName("d3", "Scanner"))))

        val mapValue = hashMapOf<String, Int>()
        mapValue["a1+b2"] = 10
        mapValue["a2+b3"] = 10
        mapValue["a2+b1"] = 10

        recycler_view.enableAll(true)
        recycler_view.enablePreSelect(true)
//        recycler_view.hideDisabledTag(true)
        recycler_view.setData(this, listVariant, mapValue, "+")
        recycler_view.setOnTagSelectListener(this)
    }

    override fun onTagSelect(index: Int) {
        Toast.makeText(this, "2233 $index", Toast.LENGTH_SHORT).show()
    }
}
