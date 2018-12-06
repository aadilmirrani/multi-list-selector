package com.reacttive.aadilmirrani.mlslibrary.helper

import com.reacttive.aadilmirrani.mlslibrary.model.Variant

object RecyclerData {

    internal val listNormal = hashMapOf<String, LinkedHashMap<String, Int>>()
    internal val listSelected = linkedMapOf<String, String>()
    internal val listIndependentSelected = linkedMapOf<String, String>()

    internal var delimiter: String = ""
    internal var listVariant = arrayListOf<Variant>()
    internal var listValue = hashMapOf<String, Int>()
    internal var enableAll: Boolean = false
    internal var preSelected: Boolean = false

    internal fun generateNormal() {
        if(delimiter.isNotEmpty() && listVariant.size > 0/* && listValue.size > 0*/) {

            addIndependent()

            if(enableAll) {
                listVariant.forEach { variant ->
                    val hashMap = linkedMapOf<String, Int>()
                    var pre = false
                    variant.data.forEach { tag ->
                        if(tag.selected) {
                            pre = true
                            if(preSelected && !variant.title.independent) listSelected[variant.title.key] = tag.key
                        }
                        hashMap[tag.key] = 1
                    }
                    listNormal[variant.title.key] = hashMap
                    if(preSelected && !pre && !variant.title.independent) listSelected[variant.title.key] = variant.data[0].key
                }
            } else {
                val hashMap = linkedMapOf<String, Int>()
                listVariant[0].data.forEach { tag ->
                    listValue.forEach { value ->
                        if(value.key.contains(tag.key)) {
                            hashMap[tag.key] = 1
                        }
                    }
                    if(hashMap.size > 0) listNormal[listVariant[0].title.key] = hashMap
                }
                if(preSelected) generatePerSelected()
            }
        }
    }

    fun generateUpdatedValues() {
        if(delimiter.isNotEmpty() && listVariant.size > 0 && listValue.size > 0) {
            if(!enableAll) {

                var clearRest = false
                var key = ""
                listVariant.forEach { variant ->
                    if(clearRest) {
                        if(!variant.title.independent) {
                            listNormal.remove(variant.title.key)
                            listSelected.remove(variant.title.key)
                        }
                    } else {
                        val hashMap = linkedMapOf<String, Int>()
                        variant.data.forEach { tag ->
                            listValue.filter { it.key.contains(key) }.forEach { value ->
                                if(value.key.contains(tag.key)) {
                                    hashMap[tag.key] = 1
                                }
                            }
                            if(hashMap.size > 0) listNormal[variant.title.key] = hashMap
                        }

                        listSelected[variant.title.key]?.let { select ->
                            listNormal[variant.title.key]?.get(select)?.let {
                                key = if(key.isEmpty()) select else "$key+$select"
                            } ?: kotlin.run {
                                listSelected.remove(variant.title.key)
                                clearRest = true
                            }
                        } ?: kotlin.run {
                            clearRest = true
                        }
                    }
                }
            }
        }
    }

    fun addIndependentGroup(variant: Variant) {
        val hashMap = linkedMapOf<String, Int>()
        var pre = false
        variant.data.forEach { tag ->
            if(tag.selected) {
                pre = true
                listIndependentSelected[variant.title.key] = tag.key
            }
            hashMap[tag.key] = 1
        }
        listNormal[variant.title.key] = hashMap
        if(preSelected && !pre) listIndependentSelected[variant.title.key] = variant.data[0].key
    }

    private fun addIndependent() {
        listVariant.forEach { variant ->
            if(!variant.title.independent)
                return@forEach
            val hashMap = linkedMapOf<String, Int>()
            var pre = false
            variant.data.forEach { tag ->
                if(tag.selected) {
                    pre = true
                    listIndependentSelected[variant.title.key] = tag.key
                }
                hashMap[tag.key] = 1
            }
            listNormal[variant.title.key] = hashMap
            if(preSelected && !pre) listIndependentSelected[variant.title.key] = variant.data[0].key
        }
    }

    private fun generatePerSelected() {
        listVariant.forEach { variant ->
            if(variant.title.independent)
                return@forEach
            var pre = false
            variant.data.forEach { tag ->
                if(tag.selected) {
                    listNormal[variant.title.key]?.get(tag.key)?.let {
//                        RecyclerData.updateSelectedData(variant.title.key, tag.key)
//                        RecyclerData.updateNormalList(variant.title.key)
                        updateData(variant, tag.key)
                        pre = true
                    }
                }
            }
            if(!pre) {
                listNormal[variant.title.key]?.entries?.first()?.let { tag ->
                    listNormal[variant.title.key]?.get(tag.key)?.let {
//                        RecyclerData.updateSelectedData(variant.title.key, tag.key)
//                        RecyclerData.updateNormalList(variant.title.key)
                        updateData(variant, tag.key)
                    }
                }
            }
        }
        AppData.notifyDataSetChanged()
    }

    fun updateData(group: Variant, item: String) {
        if(group.title.independent) listIndependentSelected[group.title.key] = item
        else {
            listSelected[group.title.key] = item
            listVariant.forEachIndexed { index, variant ->
                if(variant.title.key == group.title.key) {
                    if(!enableAll) {
                        updateSelectedFrom(index)
                        updateNormalFrom(index + 1)
                    }
                }
            }
        }
    }

/*
    fun updateSelectedData(group: String, item: String) {
        listSelected[group] = item
        listVariant.forEachIndexed { index, variant ->
            if(variant.title.key == group) {
                updateSelectedFrom(index)
            }
        }
    }
*/
    private fun updateSelectedFrom(index: Int) {
        listVariant.forEachIndexed { i, variant ->
            if(i > index) {
                listSelected.remove(variant.title.key)
            }
        }
    }
/*
    fun updateNormalList(group: String) {
        listVariant.forEachIndexed { index, variant ->
            if(variant.title.key == group) {
                updateNormalFrom(index+1)
            }
        }
    }
*/
    private fun updateNormalFrom(index: Int) {
        var once = true
        listVariant.forEachIndexed { i, variant ->
            if(i >= index) {
                if(once) {
                    if(variant.title.independent) return@forEachIndexed

                    val hashMap = linkedMapOf<String, Int>()
                    variant.data.forEach { tag ->
                        listValue.filter { it.key.contains(getPQKey()) }.forEach { value ->
                            if(value.key.contains(tag.key)) {
                                hashMap[tag.key] = 1
                            }
                        }
                        if(hashMap.size > 0) listNormal[variant.title.key] = hashMap
                    }

                    once = false
                } else {
                    if(!variant.title.independent) {
                        listNormal.remove(variant.title.key)
                        listSelected.remove(variant.title.key)
                    }
                }
            }
/*
            if(i == index) {
                val hashMap = linkedMapOf<String, Int>()
                variant.data.forEach { tag ->
                    listValue.filter { it.key.contains(getPQKey()) }.forEach { value ->
                        if(value.key.contains(tag.key)) {
                            hashMap[tag.key] = 1
                        }
                    }
                    if(hashMap.size > 0) listNormal[variant.title.key] = hashMap
                }

            } else if (i > index) {
                if(!variant.title.independent) {
                    listNormal.remove(variant.title.key)
                    listSelected.remove(variant.title.key)
                }
            }
*/
        }
    }

    internal fun getPQKey(): String {
        var key = ""
        for(item in listSelected)
            key += item.value + delimiter
        return if(key.isNotEmpty()) key.substring(0, key.length - 1) else key
    }
}