package com.android.vengateshm.contactui

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.intent.Intents
import androidx.test.platform.app.InstrumentationRegistry
import com.android.vengateshm.contactui.model.ContactsListUiModel
import com.android.vengateshm.contactui.providers.ContactsUiProvider
import com.android.vengateshm.contactui.repo.ContactsRepo
import com.android.vengateshm.contactui.ui.ContactsListActivity
import com.android.vengateshm.contactui.ui.ContactsListFragment
import com.android.vengateshm.uicore.utils.UiWatcher
import io.reactivex.rxjava3.core.Observable
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

// Testing activity whether fragment is shown
@RunWith(MockitoJUnitRunner::class)
class ContactListActivityTest {

    private lateinit var context: Context
    private lateinit var resources: Resources

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        resources = InstrumentationRegistry.getInstrumentation().targetContext.resources
        ContactsUiProvider.contactsRepo = object : ContactsRepo {
            override fun getContactDetails(): Observable<ContactsListUiModel> {
                val uiModel = StubData.getMultipleContacts()
                return Observable.just(uiModel)
            }

        }
        ContactsUiProvider.init(object : UiWatcher {
            override fun onUiStatusListener(status: Int, bundle: Bundle) {

            }
        })
    }

    @After
    fun tearDown() {
    }

    // ActivityTestRule API is deprecated

    @Test
    fun checkFragmentDisplayed() {
        Intents.init()

        val activityScenario: ActivityScenario<ContactsListActivity> =
            ActivityScenario.launch(ContactsListActivity::class.java)

        activityScenario.onActivity {
            Assert.assertNotNull(it.supportFragmentManager)
            Assert.assertTrue(it.supportFragmentManager.fragments[0] is ContactsListFragment)
        }

        Intents.release()

        activityScenario.close()
    }

    @Test
    fun checkFragmentName() {
        Intents.init()

        val activityScenario: ActivityScenario<ContactsListActivity> =
            ActivityScenario.launch(ContactsListActivity::class.java)

        activityScenario.onActivity {
            Assert.assertNotNull(it.supportFragmentManager)
            Assert.assertEquals(
                (it.supportFragmentManager.fragments[0] as ContactsListFragment).javaClass.simpleName,
                "ContactsListFragment"
            )
        }

        Intents.release()

        activityScenario.close()
    }

    @Test
    fun checkActivityName() {
        Intents.init()

        val activityScenario: ActivityScenario<ContactsListActivity> =
            ActivityScenario.launch(ContactsListActivity::class.java)

        activityScenario.onActivity {
            Assert.assertEquals(it.javaClass.simpleName, "ContactsListActivity")
        }

        Intents.release()

        activityScenario.close()
    }
}