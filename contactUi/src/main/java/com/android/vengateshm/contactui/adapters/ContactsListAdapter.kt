package com.android.vengateshm.contactui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.vengateshm.contactui.constants.LIST_HEADER
import com.android.vengateshm.contactui.databinding.ContactListHeaderBinding
import com.android.vengateshm.contactui.databinding.ContactListItemBinding
import com.android.vengateshm.contactui.model.ContactItem

class ContactsListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var contactItemList: List<ContactItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == LIST_HEADER) {
            val headerBinding =
                ContactListHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            HeaderVH(headerBinding)
        } else {
            val itemBinding =
                ContactListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ItemVH(itemBinding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == LIST_HEADER) {
            with(holder as HeaderVH) {
                this.bindHeader(contactItemList[position])
            }
        } else {
            with(holder as ItemVH) {
                this.bindItem(contactItemList[position])
            }
        }
    }

    override fun getItemCount() = contactItemList.size

    override fun getItemViewType(position: Int) = contactItemList[position].itemType

    class HeaderVH(private val headerBinding: ContactListHeaderBinding) :
        RecyclerView.ViewHolder(headerBinding.root) {

        fun bindHeader(contactItem: ContactItem) {
            headerBinding.tvHeaderTitle.text = contactItem.headerTitle
        }
    }

    class ItemVH(private val itemBinding: ContactListItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bindItem(contactItem: ContactItem) {
            itemBinding.tvName.text = contactItem.name
            itemBinding.tvDisplayName.text = contactItem.displayName
            itemBinding.tvPhoneNo.text = contactItem.phone
            itemBinding.tvEmailId.text = contactItem.email
        }
    }
}