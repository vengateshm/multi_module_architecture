package com.android.vengateshm.contactui.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.vengateshm.contactui.R

class ContactsListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts_list)

        launchFragment()
    }

    private fun launchFragment() {
        val contactsListFragment = ContactsListFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.contactListFragmentContainer, contactsListFragment)
            .commit()
    }
}