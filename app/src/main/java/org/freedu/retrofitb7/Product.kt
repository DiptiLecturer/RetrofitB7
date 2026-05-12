package org.freedu.retrofitb7

//this is the data source
//Data class is hold the data (not save the data in the device)
data class Product(
    val description: String,
    val id: Int,
    val image: String,
    val price: Double,
    val title: String
)