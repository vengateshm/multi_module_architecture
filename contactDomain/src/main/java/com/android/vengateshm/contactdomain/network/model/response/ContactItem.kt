package com.android.vengateshm.contactdomain.network.model.response

data class ContactItem(
    val name: String,
    val phone: String,
    val email: String,
    val displayName: String,
    val isMarketingTeam: Boolean,
    val isSalesTeam: Boolean
)
