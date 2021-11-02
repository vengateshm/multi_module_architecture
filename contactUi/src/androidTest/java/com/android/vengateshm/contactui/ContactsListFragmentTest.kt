package com.android.vengateshm.contactui

import android.Manifest
import android.os.Bundle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.swipeUp
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.GrantPermissionRule
import com.android.vengateshm.contactui.model.ContactsListUiModel
import com.android.vengateshm.contactui.providers.ContactsUiProvider
import com.android.vengateshm.contactui.repo.ContactsRepo
import com.android.vengateshm.contactui.ui.ContactsListActivity
import com.android.vengateshm.contactui.ui.ContactsListFragment
import com.android.vengateshm.uicore.utils.UiWatcher
import io.reactivex.rxjava3.core.Observable
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ContactsListFragmentTest {

    @get:Rule
    val callPhoneRule = GrantPermissionRule.grant(Manifest.permission.CALL_PHONE)

    @get:Rule
    val writeContactsRule = GrantPermissionRule.grant(Manifest.permission.WRITE_CONTACTS)

    @Before
    fun setUp() {
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

    @Test
    fun test_contacts_list_when_no_contact_list_item() {
        Intents.init()

        val activityScenario: ActivityScenario<ContactsListActivity> =
            ActivityScenario.launch(ContactsListActivity::class.java)

        activityScenario.onActivity {
            val uiModel = ContactsListUiModel(emptyList())
            (it.supportFragmentManager.findFragmentById(R.id.contactListFragmentContainer) as? ContactsListFragment)
                ?.apply {
                    populateContactsList(uiModel.contactItemList)
                }
        }
        Espresso.onView(withId(R.id.tvName)).check(matches(isDisplayed()))
        onView(withText("1800 667700")).check(matches(isDisplayed()))

        Intents.release()

        activityScenario.close()

        // New fragment testing API
        /*launchFragmentInContainer<ContactsListFragment>()
        Espresso.onView(withId(R.id.tvName)).check(ViewAssertions.matches(isDisplayed()))
        Espresso.onView(withText("1800 667700")).check(ViewAssertions.matches(isDisplayed()))*/
    }

    @Test
    fun test_contacts_list_with_multiple_contact_list_items() {
        Intents.init()

        val activityScenario: ActivityScenario<ContactsListActivity> =
            ActivityScenario.launch(ContactsListActivity::class.java)

        activityScenario.onActivity {
            val uiModel = StubData.getMultipleContacts()
            (it.supportFragmentManager.findFragmentById(R.id.contactListFragmentContainer) as? ContactsListFragment)
                ?.apply {
                    populateContactsList(uiModel.contactItemList)
                }
        }

        // Recyclerview
        onView(withId(R.id.rvContactList)).check(matches(isDisplayed()))
        onView(withText("Marketing")).check(matches(isDisplayed()))
        onView(withText("Andy Johnson")).check(matches(isDisplayed()))
        onView(withText("ajohnson@xyz.com")).check(matches(isDisplayed()))
        onView(withText("Sales")).check(matches(isDisplayed()))
        onView(withText("Florina M")).check(matches(isDisplayed()))
        onView(withText("mflorina@xyz.com")).check(matches(isDisplayed()))

        // Hotline layout
        onView(withId(R.id.nestedScrollView)).perform(swipeUp())
        onView(withId(R.id.tvHotlineContactLabel)).check(matches(isDisplayed()))
        onView(withText("1800 667700")).check(matches(isDisplayed()))

        Intents.release()

        activityScenario.close()

        // New fragment testing API
        /*launchFragmentInContainer<ContactsListFragment>()
        Espresso.onView(withId(R.id.tvName)).check(ViewAssertions.matches(isDisplayed()))
        Espresso.onView(withText("1800 667700")).check(ViewAssertions.matches(isDisplayed()))*/
    }
}