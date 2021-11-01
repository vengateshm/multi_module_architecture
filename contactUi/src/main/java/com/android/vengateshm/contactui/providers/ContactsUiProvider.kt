package com.android.vengateshm.contactui.providers

import android.content.Context
import android.content.Intent
import com.android.vengateshm.contactui.repo.ContactsRepo
import com.android.vengateshm.contactui.ui.ContactsListActivity
import com.android.vengateshm.uicore.utils.UiWatcher

object ContactsUiProvider {
    private lateinit var uiWatcher: UiWatcher
    lateinit var contactsRepo: ContactsRepo

    fun init(uiWatcher: UiWatcher) {
        this.uiWatcher = uiWatcher
    }

    fun launchContactsList(context: Context) {
        Intent(context, ContactsListActivity::class.java)
            .apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            .also {
                context.startActivity(it)
            }
    }
}