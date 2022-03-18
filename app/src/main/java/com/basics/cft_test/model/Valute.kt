package com.basics.cft_test.model

import android.os.Parcel
import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty

data class Valute(
    @JsonProperty("ID") val id: String?,
    @JsonProperty("NumCode") val num_code:Int,
    @JsonProperty("CharCode") val char_code:String?,
    @JsonProperty("Nominal") val nominal:String?,
    @JsonProperty("Name") val name:String?,
    @JsonProperty("Value") val value:Double,
    @JsonProperty("Previous") val previous:Double
): Parcelable{

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readDouble()
    )

    override fun describeContents(): Int {
       return 0
    }

    override fun writeToParcel(out: Parcel, p1: Int){
        out.writeString(id)
        out.writeInt(num_code)
        out.writeString(char_code)
        out.writeString(nominal)
        out.writeString(name)
        out.writeDouble(value)
        out.writeDouble(previous)
    }

    companion object CREATOR : Parcelable.Creator<Valute> {
        override fun createFromParcel(parcel: Parcel): Valute {
            return Valute(parcel)
        }

        override fun newArray(size: Int): Array<Valute?> {
            return arrayOfNulls(size)
        }
    }
}
//data class Valute(
//    val id:String,
//    val num_code:Int,
//    val char_code:String,
//    val nominal:String,
//    val name:String,
//    val value:Double,
//    val previous:Double
//)