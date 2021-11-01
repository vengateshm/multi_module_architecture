package com.android.vengateshm.contactui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.vengateshm.contactui.model.ContactItem
import com.android.vengateshm.contactui.providers.ContactsUiProvider
import com.android.vengateshm.uicore.utils.BaseViewModel

class ContactsListViewModel : BaseViewModel() {

    private val repository = ContactsUiProvider.contactsRepo
    private val _contactsList = MutableLiveData<List<ContactItem>>()
    val contactsList: LiveData<List<ContactItem>> = _contactsList

    fun getContactsList() {
        disposables.add(repository.getContactDetails()
            .subscribe(
                {
                    _contactsList.postValue(it.contactItemList)
                },
                {

                }
            ))
    }
}