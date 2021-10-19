package com.android.vengateshm.contactdomain.provider

import android.content.Context
import com.android.vengateshm.contactui.providers.ContactsUiProvider
import com.android.vengateshm.uicore.utils.UiWatcher

class ContactDomainProvider {
    fun init(uiWatcher: UiWatcher) {
        ContactsUiProvider.init(uiWatcher)
    }

    fun launchContactsList(context: Context) {
        ContactsUiProvider.launchContactsList(context)
    }
}