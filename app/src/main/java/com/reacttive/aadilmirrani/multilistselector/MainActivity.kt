package com.reacttive.aadilmirrani.multilistselector

import android.os.Bundle
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

        var value = 1

        val listVariant = arrayListOf<Variant>()
        listVariant.add(Variant(TagHeader("A", "Color"), arrayListOf(TagName("a1", "Alpha"), TagName("a2", "Beta"), TagName("a3", "Gamma"))))
        listVariant.add(Variant(TagHeader("B", "Size"), arrayListOf(TagName("b1", "Small"), TagName("b2", "Medium"), TagName("b3", "Large"))))
//        listVariant.add(Variant(TagHeader("D", "Office", true), arrayListOf(TagName("d1", "Laptop"), TagName("d2", "Printer"), TagName("d3", "Scanner"))))

        val mapValue = hashMapOf<String, Int>()

//        recycler_view.enableAll(true)
//        recycler_view.enablePreSelect(true)
//        recycler_view.hideDisabledTag(true)

        mapValue["a1+b1"] = 10
        mapValue["a1+b2"] = 10

        recycler_view.setData(this, listVariant, mapValue, "+")
        recycler_view.setOnTagSelectListener(this)

        btn_add_values.setOnClickListener {

            val mapValue2 = hashMapOf<String, Int>()

            when (value) {
                1 -> {
                    mapValue2["a1+b1"] = 10
                    mapValue2["a1+b2"] = 10
                    value++
                }
                2 -> {
                    mapValue2["a1+b2"] = 10
                    mapValue2["a1+b3"] = 10
                    value++
                }
                3 -> {
                    mapValue2["a2+b2"] = 10
                    mapValue2["a2+b3"] = 10
                    value++
                }
            }

            recycler_view.updateValues(mapValue2)

        }

        btn_add_group.setOnClickListener {
            recycler_view.addIndependentGroup(Variant(TagHeader("C", "Home", true), arrayListOf(TagName("c1", "Cup"), TagName("c2", "Light"), TagName("c3", "Table"))))
        }

    }

    override fun onTagSelect(index: Int, key: String, group: TagHeader, tag: TagName, mapIndependent: LinkedHashMap<String, String>) {
        Toast.makeText(this, "2233 \n$index\n$key\n$group\n$tag\n$mapIndependent", Toast.LENGTH_SHORT).show()
    }
}
