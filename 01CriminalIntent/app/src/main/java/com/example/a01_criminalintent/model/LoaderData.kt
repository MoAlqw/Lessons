package com.example.a01_criminalintent.model

class LoaderData {

    fun load(): List<Crime> {
        val list = mutableListOf<Crime>()
        for (i in 0..15) {
            val crime = Crime()
            crime.title = "Crime #$i"
            crime.isSolved = i % 2 == 0
            list += crime
        }
        return list
    }

}