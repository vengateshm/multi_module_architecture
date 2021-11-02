package com.android.vengateshm.contactui.ui

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.vengateshm.contactui.R
import com.android.vengateshm.contactui.adapters.ContactsListAdapter
import com.android.vengateshm.contactui.databinding.LayoutBottomSheetBinding
import com.android.vengateshm.contactui.model.ContactItem
import com.android.vengateshm.contactui.viewmodel.ContactsListViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT
import com.google.android.material.snackbar.Snackbar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ContactsListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ContactsListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var contactToSave = ""

    private lateinit var viewModel: ContactsListViewModel

    private lateinit var contactsListAdapter: ContactsListAdapter
    private lateinit var rvContactList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contacts_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        contactsListAdapter = ContactsListAdapter()
        setAdapterClickListeners()
        rvContactList = view.findViewById(R.id.rvContactList)
        rvContactList.layoutManager = LinearLayoutManager(context)
        rvContactList.adapter = contactsListAdapter

        viewModel = ViewModelProvider(requireActivity()).get(ContactsListViewModel::class.java)
        viewModel.contactsList.observe(viewLifecycleOwner, {
            populateContactsList(it)

        })
        viewModel.getContactsList()

        view.findViewById<LinearLayout>(R.id.layout_hotline_no)?.setOnClickListener {
            showBottomSheetDialog(
                true, ContactItem(
                    name = "Customer Service Hotline",
                    phone = "1800 667700",
                    email = "",
                    displayName = ""
                )
            )
        }
    }

    private fun setAdapterClickListeners() {
        contactsListAdapter.onPhoneNumberSelected = this::onPhoneNumberSelected
        contactsListAdapter.onEmailSelected = this::onEmailSelected
    }

    fun populateContactsList(data: List<ContactItem>?) {
        contactsListAdapter.contactItemList = data!!
        contactsListAdapter.notifyDataSetChanged()
    }

    private fun onPhoneNumberSelected(contactItem: ContactItem) {
        showBottomSheetDialog(true, contactItem)
    }

    private fun onEmailSelected(contactItem: ContactItem) {
        showBottomSheetDialog(false, contactItem)
    }

    private fun showBottomSheetDialog(isPhoneNumberSelected: Boolean, contactItem: ContactItem) {
        val dialog = BottomSheetDialog(requireContext())
        val bsDialogBinding = LayoutBottomSheetBinding.inflate(layoutInflater)
        dialog.setContentView(bsDialogBinding.root)

        bsDialogBinding.tvCallOrSendMail.text =
            if (isPhoneNumberSelected) String.format(
                getString(R.string.call_btn),
                contactItem.phone
            ) else getString(R.string.new_message)

        bsDialogBinding.tvCallOrSendMail.setOnClickListener {
            dialog.dismiss()
            if (isPhoneNumberSelected) {
                Intent(Intent.ACTION_CALL)
                    .apply {
                        data = Uri.parse("tel:" + contactItem.phone)
                    }
                    .also {
                        startActivity(it)
                    }
            } else {
                Intent(Intent.ACTION_SENDTO)
                    .apply {
                        data = Uri.parse("mailto:")
                        putExtra(Intent.EXTRA_EMAIL, arrayOf(contactItem.email))
                    }.also { intent ->
                        intent.resolveActivity(requireActivity().packageManager)?.let {
                            startActivity(intent)
                        }
                    }
            }
        }

        bsDialogBinding.tvAddToContacts.setOnClickListener {
            contactToSave = contactItem.displayName
            dialog.dismiss()
            Intent(ContactsContract.Intents.Insert.ACTION)
                .apply {
                    type = ContactsContract.RawContacts.CONTENT_TYPE
                    // Sets the special extended data for navigation
                    putExtra("finishActivityOnSaveCompleted", true)
                    putExtra(ContactsContract.Intents.Insert.NAME, contactItem.displayName)
                    putExtra(ContactsContract.Intents.Insert.PHONE, contactItem.phone)
                    putExtra(
                        ContactsContract.Intents.Insert.PHONE_TYPE,
                        ContactsContract.CommonDataKinds.Phone.TYPE_WORK
                    )
                    putExtra(ContactsContract.Intents.Insert.EMAIL, contactItem.email)
                    putExtra(
                        ContactsContract.Intents.Insert.EMAIL_TYPE,
                        ContactsContract.CommonDataKinds.Email.TYPE_WORK
                    )
                    putExtra(ContactsContract.Intents.Insert.COMPANY, "XYZ Inc.")
                }
                .also {
                    launcher.launch(it)
                }
        }

        bsDialogBinding.tvCopy.setOnClickListener {
            dialog.dismiss()
            val clipboardManager =
                requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText(
                "label",
                if (isPhoneNumberSelected) contactItem.phone else contactItem.email
            )
            clipboardManager.setPrimaryClip(clip)
            Snackbar.make(requireView(), getString(R.string.contact_copied), LENGTH_SHORT).show()
        }

        dialog.show()
    }

    val activityResult =
        ActivityResultCallback<ActivityResult> { result ->
            if (result?.resultCode == Activity.RESULT_OK) {
                Snackbar.make(
                    requireView(),
                    String.format(getString(R.string.contact_added_snackbar_msg), contactToSave),
                    LENGTH_LONG
                )
                    .show()
            }
        }

    private val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        activityResult
    )

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ContactsListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ContactsListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

        @JvmStatic
        fun newInstance() = ContactsListFragment()
    }
}