package com.android.vengateshm.multilayermodulararchitecture

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.vengateshm.contactdomain.converter.ModelConverter
import com.android.vengateshm.contactdomain.provider.ContactDomainProvider
import com.android.vengateshm.contactdomain.repo.ContactsRepositoryImpl
import com.android.vengateshm.uicore.utils.UiWatcher

class MainActivity : AppCompatActivity() {

    private lateinit var contactDomainProvider: ContactDomainProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        launchContactsList()
    }

    private fun launchContactsList() {
        contactDomainProvider = ContactDomainProvider()
        contactDomainProvider.contactsRepo = ContactsRepositoryImpl(ModelConverter())
        contactDomainProvider.init(object : UiWatcher {
            override fun onUiStatusListener(status: Int, bundle: Bundle) {

            }
        })
        contactDomainProvider.launchContactsList(this)
    }
}