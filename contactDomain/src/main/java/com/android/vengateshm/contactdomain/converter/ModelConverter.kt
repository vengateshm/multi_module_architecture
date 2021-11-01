package com.android.vengateshm.contactdomain.converter

import com.android.vengateshm.contactdomain.network.model.response.ContactsListResponse
import com.android.vengateshm.contactui.constants.LIST_HEADER
import com.android.vengateshm.contactui.model.ContactItem
import com.android.vengateshm.contactui.model.ContactsListUiModel

open class ModelConverter {

    open fun prepareUiModel(contactsListResponse: ContactsListResponse): ContactsListUiModel {
        val contactItemList = mutableListOf<ContactItem>()

        val groupedList = contactsListResponse.contactsList.groupBy { it.toKey() }
        groupedList[Key(isMarketing = true, isSales = false)]
            ?.also {
                contactItemList.add(ContactItem("", "", "", "")
                    .apply {
                        headerTitle = "Marketing"
                        itemType = LIST_HEADER
                    })
                contactItemList.addAll(it.map { item ->
                    ContactItem(
                        name = item.name,
                        phone = item.phone,
                        email = item.email,
                        displayName = item.displayName
                    )
                })
            }
        groupedList[Key(isMarketing = false, isSales = true)]
            ?.also {
                contactItemList.add(ContactItem("", "", "", "")
                    .apply {
                        headerTitle = "Sales"
                        itemType = LIST_HEADER
                    })
                contactItemList.addAll(it.map { item ->
                    ContactItem(
                        name = item.name,
                        phone = item.phone,
                        email = item.email,
                        displayName = item.displayName
                    )
                })
            }

        return ContactsListUiModel(contactItemList)
    }
}

data class Key(val isMarketing: Boolean, val isSales: Boolean)

fun com.android.vengateshm.contactdomain.network.model.response.ContactItem.toKey() =
    Key(this.isMarketingTeam, this.isSalesTeam)