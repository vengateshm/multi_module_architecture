package com.android.vengateshm.contactdomain.provider

import android.content.Context
import com.android.vengateshm.contactui.providers.ContactsUiProvider
import com.android.vengateshm.contactui.repo.ContactsRepo
import com.android.vengateshm.uicore.utils.UiWatcher

class ContactDomainProvider {
    lateinit var contactsRepo: ContactsRepo

    fun init(uiWatcher: UiWatcher) {
        ContactsUiProvider.init(uiWatcher)
        ContactsUiProvider.contactsRepo = contactsRepo
    }

    fun launchContactsList(context: Context) {
        ContactsUiProvider.launchContactsList(context)
    }
}