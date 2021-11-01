package com.android.vengateshm.contactui

import androidx.test.platform.app.InstrumentationRegistry
import com.android.vengateshm.contactui.constants.LIST_HEADER
import com.android.vengateshm.contactui.model.ContactItem
import com.android.vengateshm.contactui.model.ContactsListUiModel

object StubData {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    fun getMultipleContacts(): ContactsListUiModel {
        val contactItemList = mutableListOf<ContactItem>()
            .apply {
                add(
                    ContactItem("", "", "", "")
                        .apply {
                            headerTitle = "Marketing"
                            itemType = LIST_HEADER
                        })
                add(
                    ContactItem(
                        name = "Andy Johnson",
                        phone = "+91 97152 15555",
                        email = "ajohnson@xyz.com",
                        displayName = "Marketing Manager",
                    )
                )
                add(
                    ContactItem("", "", "", "")
                        .apply {
                            headerTitle = "Sales"
                            itemType = LIST_HEADER
                        })
                add(
                    ContactItem(
                        name = "Florina M",
                        phone = "+91 88152 15555",
                        email = "mflorina@xyz.com",
                        displayName = "Sales Head",
                    )
                )
            }
        return ContactsListUiModel(contactItemList)
    }

    fun getSingleContact(): ContactsListUiModel {
        val contactItemList = mutableListOf<ContactItem>()
            .apply {
                add(
                    ContactItem("", "", "", "")
                        .apply {
                            headerTitle = "Sales"
                            itemType = LIST_HEADER
                        })
                add(
                    ContactItem(
                        name = "Florina M",
                        phone = "+91 88152 15555",
                        email = "mflorina@xyz.com",
                        displayName = "Sales Head",
                    )
                )
            }
        return ContactsListUiModel(contactItemList)
    }
}