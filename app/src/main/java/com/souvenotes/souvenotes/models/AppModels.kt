package com.souvenotes.souvenotes.models

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.database.Exclude

/**
 * Created on 10/11/17.
 */
data class RegistrationModel(var email: String = "", var password: String = "",
                             var passwordConfirmation: String = "") : Parcelable {
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

data class LoginModel(var email: String = "", var password: String = "") : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(email)
        parcel.writeString(password)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<LoginModel> {
        override fun createFromParcel(parcel: Parcel): LoginModel = LoginModel(parcel)

        override fun newArray(size: Int): Array<LoginModel?> = arrayOfNulls(size)
    }
}

data class NoteModel(var title: String = "", var content: String = "",
                     var timestamp: Long = System.currentTimeMillis()) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readLong())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(content)
        parcel.writeLong(timestamp)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<NoteModel> {
        override fun createFromParcel(parcel: Parcel): NoteModel = NoteModel(parcel)

        override fun newArray(size: Int): Array<NoteModel?> = arrayOfNulls(size)
    }

    @Exclude
    fun toMap(): Map<String, Any> {
        return HashMap<String, Any>().apply {
            put("title", title)
            put("content", content)
            put("timestamp", timestamp)
        }
    }
}

data class NoteListModel(val title: String = "",
                         val timestamp: Long = -1 * System.currentTimeMillis()) {

    @Exclude
    fun toMap(): Map<String, Any> {
        return HashMap<String, Any>().apply {
            put("title", title)
            put("timestamp", timestamp)
        }
    }
}