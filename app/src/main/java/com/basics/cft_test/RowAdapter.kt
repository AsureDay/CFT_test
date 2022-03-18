package com.basics.cft_test.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.basics.cft_test.databinding.RowForReciclerViewBinding
interface UserActionListener {

    fun onClick(valute: Valute)

}

class RowAdapter(
    private val actionListener: UserActionListener
):RecyclerView.Adapter<RowAdapter.RowViewHolder>() ,View.OnClickListener{
    var valutesList:ArrayList<Valute> = arrayListOf()

    class RowViewHolder(
        val binding:RowForReciclerViewBinding
    ): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RowForReciclerViewBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        return RowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RowViewHolder, position: Int) {
        val valute = valutesList[position]
        with(holder.binding){
            holder.itemView.tag = valute
            charCodeTextView.text = "Char code: "
            charCodeDataTextView.text = valute.char_code

            IDTextView.text = "ID: "
            IDDAtaTextView.text = valute.id

            nameTextView.text = "Name: "
            nameDataTextView.text = valute.name

            valueTextView.text = "Value: "
            valueDataTextView.text = valute.value.toString()

            nominalTextView.text = "Nominal: "
            nominalDataTextView.text = valute.nominal

            numCodeTextView.text = "Num vode: "
            numCodeDataTextView.text = valute.num_code.toString()

            previousValueTextView.text = "Previous value: "
            previousValueDataTextView.text = valute.previous.toString()
        }
    }

    override fun getItemCount(): Int = valutesList.size
    override fun onClick(v: View) {
        actionListener.onClick(v.tag as Valute)
    }
}