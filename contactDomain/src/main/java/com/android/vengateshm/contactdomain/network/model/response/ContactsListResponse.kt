package com.android.vengateshm.contactdomain.network.model.response

class ContactsListResponse : BaseResponse() {
    var contactsList: List<ContactItem> = mutableListOf()
}