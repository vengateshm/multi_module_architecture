package com.android.vengateshm.contactdomain.network.model.response

class ContactsListResponse : BaseResponse() {
    val contactsList: List<ContactItem> = mutableListOf()
}