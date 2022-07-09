package com.example.malevichstudio.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.malevichstudio.R
import com.example.malevichstudio.database.product.TableProduct

class ProductAdapter(val products:List<TableProduct>):ListAdapter<TableProduct, ProductAdapter.ProductViewHolder>(WORDS_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
       return ProductViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
       val current = getItem(position)
        holder.bind(current)
    }

    class ProductViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val image:ImageView=view.findViewById(R.id.product_image)
        val delete:Button = view.findViewById(R.id.delet)
        val textProduct:TextView=view.findViewById(R.id.product)
        val textPrice:TextView=view.findViewById(R.id.price)

        fun bind(tableProduct: TableProduct){
            textProduct.text=tableProduct.product
            textPrice.text=tableProduct.price
        }
        companion object {
            fun create(parent: ViewGroup): ProductViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return ProductViewHolder(view)
            }
        }

    }
    companion object {
        private val WORDS_COMPARATOR = object : DiffUtil.ItemCallback<TableProduct>() {
            override fun areItemsTheSame(oldItem: TableProduct, newItem: TableProduct): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TableProduct, newItem: TableProduct): Boolean {
                return oldItem == newItem
            }
        }
    }



}