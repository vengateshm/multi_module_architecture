package com.android.vengateshm.contactui.repo

import com.android.vengateshm.contactui.model.ContactsListUiModel
import io.reactivex.rxjava3.core.Observable

interface ContactsRepo {
    fun getContactDetails(): Observable<ContactsListUiModel>
}