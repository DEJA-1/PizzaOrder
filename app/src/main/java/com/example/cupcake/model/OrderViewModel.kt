package com.example.cupcake.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Calendar
import java.util.*

private const val PRICE_SMALL = 10.00
private const val PRICE_MEDIUM = 15.00
private const val PRICE_LARGE = 20.00
private const val PRICE_CLASSIC = 1.00
private const val PRICE_MARGARITTA = 2.00
private const val PRICE_CAPRICIOSSA = 3.00
private const val PRICE_KEBAB = 4.00
private const val PRICE_INFERNO = 5.00

class OrderViewModel : ViewModel() {
    private val _size = MutableLiveData<String>()
    val size: LiveData<String> = _size

    private val _type = MutableLiveData<String>()
    val type: LiveData<String> = _type

    private val _date = MutableLiveData<String>()
    val date: LiveData<String> = _date

    private val _price = MutableLiveData<Double>()
    val price: LiveData<String> = Transformations.map(_price){
        NumberFormat.getCurrencyInstance().format(it)
    }

    fun setSize(size: String){
        _size.value = size
        updatePrice()
    }

    fun setType(desiredType: String) {
        _type.value = desiredType
        updatePrice()
    }

    fun setDate(pickupDate: String){
        _date.value = pickupDate
        updatePrice()
    }

    fun hasNoTypeSet(): Boolean {
        return _type.value.isNullOrEmpty()
    }

    private fun getPickupOptions(): List<String>{
        val options = mutableListOf<String>()
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        val calendar = Calendar.getInstance()

        repeat(4){
            options.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }
        return options
    }

    val dateOptions = getPickupOptions()

    fun resetOrder(){
        _size.value = ""
        _type.value = ""
        _date.value = dateOptions[0]
        _price.value = 0.0
    }

    init{
        resetOrder()
    }

   private fun updatePrice(){
       var tempPrice = 0.0
       when(_size.value){
           "Small" -> tempPrice = PRICE_SMALL
           "Medium" -> tempPrice = PRICE_MEDIUM
           "Large" -> tempPrice = PRICE_LARGE
       }
       when (_type.value){
           "Classic" -> tempPrice += PRICE_CLASSIC
           "Margaritta" -> tempPrice += PRICE_MARGARITTA
           "Capriciossa" -> tempPrice += PRICE_CAPRICIOSSA
           "Kebab" -> tempPrice += PRICE_KEBAB
           "Inferno" -> tempPrice += PRICE_INFERNO
       }

       _price.value = tempPrice


   }
}