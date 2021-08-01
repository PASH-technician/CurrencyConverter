package com.bypavelshell.mathcreater.currencyconverter.data

data class Currency(
    var id: String?,
    var numCode: String?,
    var charCode: String?,
    var nominal: Long?,
    var name: String?,
    var value: Double?,
    var previous: Double?,
){
    override fun toString(): String {
        return "$charCode $name"
    }
}