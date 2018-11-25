package com.dev.adi.ecosystem.model

import android.os.Parcel
import android.os.Parcelable

class DataSpecies(
        val id : Int,
        val name : String
): Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readInt(),
                parcel.readString())

        override fun toString(): String {
                return name
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeInt(id)
                parcel.writeString(name)
        }

        override fun describeContents(): Int {
                return 0
        }

        companion object CREATOR : Parcelable.Creator<DataSpecies> {
                override fun createFromParcel(parcel: Parcel): DataSpecies {
                        return DataSpecies(parcel)
                }

                override fun newArray(size: Int): Array<DataSpecies?> {
                        return arrayOfNulls(size)
                }
        }
}
