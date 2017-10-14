package com.souvenotes.souvenotes.models

import android.os.Parcel
import android.os.Parcelable

/**
 * Created on 10/11/17.
 */
data class RegistrationModel(var email: String = "", var password: String = "", var passwordConfirmation: String = "") : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(email)
        parcel.writeString(password)
        parcel.writeString(passwordConfirmation)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<RegistrationModel> {
        override fun createFromParcel(parcel: Parcel): RegistrationModel = RegistrationModel(parcel)

        override fun newArray(size: Int): Array<RegistrationModel?> = arrayOfNulls(size)
    }
}