package com.android.vengateshm.contactui.model

import com.android.vengateshm.contactui.constants.LIST_ITEM

data class ContactItem(
    val name: String,
    val phone: String,
    val email: String,
    val displayName:String
) {
    var itemType = LIST_ITEM
    var headerTitle = ""
}
