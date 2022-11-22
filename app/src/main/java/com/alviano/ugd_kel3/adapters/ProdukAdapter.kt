package com.alviano.ugd_kel3.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.alviano.ugd_kel3.ProdukView
import com.alviano.ugd_kel3.R
import com.alviano.ugd_kel3.addEditProduk
import com.alviano.ugd_kel3.models.Produk
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*
import kotlin.collections.ArrayList

class ProdukAdapter(private var produkList: List<Produk>, context: Context):
    RecyclerView.Adapter<ProdukAdapter.ViewHolder>(), Filterable {

    private var filteredProdukList: MutableList<Produk>
    private val context: Context

    init {
        filteredProdukList = ArrayList(produkList)
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_produk, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filteredProdukList.size
    }

    fun setProdukList(produkList: Array<Produk>){
        this.produkList = produkList.toList()
        filteredProdukList = produkList.toMutableList()
    }

    override fun onBindViewHolder(holder: ViewHolder, position:Int) {
        val produk = filteredProdukList[position]
        holder.tvNamaProduk.text = produk.namaProduk
        holder.tvHargaProduk.text = produk.harga
        holder.tvDeskripsiProduk.text = produk.deskripsi

        holder.btnDelete.setOnClickListener {
            val materialAlertDialogBuilder = MaterialAlertDialogBuilder(context)
            materialAlertDialogBuilder.setTitle("Konfirmasi")
                .setMessage("Apakah anda yakin ingin menghapus data produk ini?")
                .setNegativeButton("Batal", null)
                .setPositiveButton("Hapus") { _, _ ->
                    if (context is ProdukView) produk.id?.let { it1 ->
                        context.deleteProduk(it1)
                    }
                }
                .show()
        }

        holder.cvProduk.setOnClickListener {
            val i = Intent(context, addEditProduk::class.java)
            i.putExtra("id", produk.id)
            if (context is ProdukView)
                context.startActivityForResult(i, ProdukView.LAUNCH_ADD_ACTIVITY)
        }
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charSequenceString = charSequence.toString()
                val filtered:MutableList<Produk> = java.util.ArrayList()
                if(charSequenceString.isEmpty()){
                    filtered.addAll(produkList)
                }else{
                    for(produk in produkList){
                        if(produk.namaProduk.lowercase(Locale.getDefault())
                                .contains(charSequenceString.lowercase(Locale.getDefault()))
                        )filtered.add(produk)
                    }
                }
                val filterResults= FilterResults()
                filterResults.values = filtered
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                filteredProdukList.clear()
                filteredProdukList.addAll(filterResults.values as kotlin.collections.List<Produk>)
                notifyDataSetChanged()
            }
        }
    }

    inner class  ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var  tvNamaProduk: TextView
        var  tvHargaProduk: TextView
        var  tvDeskripsiProduk: TextView
        var  btnDelete: ImageButton
        var  cvProduk: CardView

        init {
            tvNamaProduk = itemView.findViewById(R.id.tv_namaProduk)
            tvHargaProduk = itemView.findViewById(R.id.tv_hargaProduk)
            tvDeskripsiProduk = itemView.findViewById(R.id.tv_deskripsiProduk)
            btnDelete = itemView.findViewById(R.id.btn_delete)
            cvProduk = itemView.findViewById(R.id.cv_produk)
        }
    }
}