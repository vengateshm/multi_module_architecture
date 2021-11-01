package com.android.vengateshm.contactdomain

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.android.vengateshm.contactdomain.converter.ModelConverter
import com.android.vengateshm.contactdomain.network.model.response.ContactsListResponse
import com.android.vengateshm.contactdomain.repo.ContactsRepositoryImpl
import com.android.vengateshm.contactui.model.ContactsListUiModel
import com.android.vengateshm.uicore.utils.readFromAsset
import com.google.gson.Gson
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ContactsRepositoryImplTest {

    private lateinit var context: Context
    private lateinit var contactsRepositoryImpl: ContactsRepositoryImpl
    private lateinit var contactsListResponse: ContactsListResponse

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().context
        contactsRepositoryImpl = ContactsRepositoryImpl(ModelConverter())
        val contactsListJsonStr = context.readFromAsset("contactsListResponse.json")
        contactsListResponse =
            Gson().fromJson(contactsListJsonStr, ContactsListResponse::class.java)
    }

    @Test
    fun testContactsListResponse() {
        val responseObservable = contactsRepositoryImpl.getContactDetails()
        val result = responseObservable.blockingLast()
        Assert.assertTrue(result is ContactsListUiModel)
        Assert.assertEquals(result.contactItemList.size, 4)
    }
}