package com.android.vengateshm.contactdomain.repo

import com.android.vengateshm.contactdomain.converter.ModelConverter
import com.android.vengateshm.contactdomain.network.model.response.ContactItem
import com.android.vengateshm.contactdomain.network.model.response.ContactsListResponse
import com.android.vengateshm.contactui.model.ContactsListUiModel
import com.android.vengateshm.contactui.repo.ContactsRepo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class ContactsRepositoryImpl(private val modelConverter: ModelConverter) : ContactsRepo {

    override fun getContactDetails(): Observable<ContactsListUiModel> {
        return Observable.just(getContactsListResponse())
            .map {
                modelConverter.prepareUiModel(it)
            }
            //.onErrorReturn {  }
            //.startWith {  }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun getContactsListResponse() = ContactsListResponse().apply {
        statusCode = "0000"
        contactsList = mutableListOf<ContactItem>()
            .apply {
                add(
                    ContactItem(
                        name = "Andy Johnson",
                        phone = "+91 97152 15555",
                        email = "ajohnson@xyz.com",
                        displayName = "Marketing Manager",
                        isMarketingTeam = true,
                        isSalesTeam = false
                    )
                )
                add(
                    ContactItem(
                        name = "Florina M",
                        phone = "+91 88152 15555",
                        email = "mflorina@xyz.com",
                        displayName = "Sales Head",
                        isMarketingTeam = false,
                        isSalesTeam = true
                    )
                )
            }
    }
}