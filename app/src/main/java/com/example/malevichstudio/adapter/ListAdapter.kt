package com.example.malevichstudio.adapter

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.malevichstudio.activity.MenuActivity
import com.example.malevichstudio.R
import com.example.malevichstudio.database.product.TableProduct
import com.example.malevichstudio.databinding.CostomRowBinding
import com.example.malevichstudio.fragment.MenuFragment
import kotlinx.android.synthetic.main.add_item.view.*
import kotlinx.android.synthetic.main.costom_row.view.*


class ListAdapter(var context: Context): RecyclerView.Adapter<ListAdapter.ListViewModel>() {

    private var productList = emptyList<TableProduct>()
    private  var click:onItemClickListener?=null
   // fun setClickListener(clickListener: onItemClickListener){
     //   this.click = clickListener
   // }
    inner class ListViewModel(var binding:CostomRowBinding) : RecyclerView.ViewHolder(binding.root),View.OnClickListener {

        override fun onClick(v: View?) {
            click?.onItemClick(v,adapterPosition)
        }

        fun bind(product: TableProduct) {

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewModel {
      val view=ListViewModel(CostomRowBinding.inflate(LayoutInflater.from(parent.context),parent,false))
  return view
    }

    override fun onBindViewHolder(holder: ListViewModel, position: Int) {
        var list = productList[position]
       holder.bind(list)
        var imageView = list.photo

            holder.itemView.setOnClickListener (object :View.OnClickListener{
                override fun onClick(v: View?) {
                    val activity=v!!.context as AppCompatActivity
                   // val mfragment =MenuFragment()
                    activity.supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_view,MenuFragment.newInstance())
                        .addToBackStack(null)
                        .commit()
                }


            })


        holder.binding.product.text = list.product
        holder.binding.price.text = list.price
        holder.binding.productImage.setImageBitmap(imageView)

        holder.binding.delet.setOnClickListener {
            val productName = list.product
            val popupMenu = PopupMenu(context, holder.binding.delet)
            popupMenu.inflate(R.menu.show_menu)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.editText -> {
                        val v = LayoutInflater.from(context).inflate(R.layout.add_item, null)
                        val edProduct = v.findViewById<EditText>(R.id.edProduct)
                        val edPrice = v.findViewById<EditText>(R.id.edPrice)

                        val product = edProduct.setText(list.product).toString()
                        val price = edPrice.setText(list.price).toString()

                        val builder = AlertDialog.Builder(context)
                            .setView(v)
                            .setPositiveButton(
                                "Сохранить",
                                DialogInterface.OnClickListener { dialog, which ->
                                    if (input(product, price)) {
                                        val update = TableProduct(list.id, product, price,  list.frame, list.edge,list.framemetr,list.facade,
                                            list.facadepoc,list.facadeedge,list.fittings,list.fittingssize,list.fitting, list.fittinssize,list.photo)
                                        MenuActivity.viewModel.updateItem(update)
                                        Toast.makeText(
                                            context,
                                            "Изменение сохранено",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "Не удалось изменить",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }).setNegativeButton(
                                "Отмена",
                                DialogInterface.OnClickListener { dialog, which -> })
                        val alert = builder.create()
                        alert.show()
                        true
                    }
                    R.id.delet -> {
                        //val imageView = list.photo
                        val product = holder.binding.product.toString()
                        val price = holder.binding.price.toString()
                        //val image =   holder.bindin.productImage.setImageBitmap(imageView)
                        AlertDialog.Builder(context)
                            .setTitle("ВНИМАНИЕ")
                            .setMessage("Вы уверены, что хотите удалить: $productName?")
                            .setPositiveButton(
                                "Да",
                                DialogInterface.OnClickListener { dialog, which ->
                                    val delete= TableProduct( list.id,  product, price, list.frame, list.edge,list.framemetr,list.facade,
                                        list.facadepoc,list.facadeedge,list.fittings,list.fittingssize,list.fitting, list.fittinssize,list.photo)
                                    MenuActivity.viewModel.deletItem(delete)
                                    if (it.itemId == -1) {
                                        Toast.makeText(
                                            context,
                                            "Ошибка",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else
                                        Toast.makeText(context, "Элемент ${productName} удален", Toast.LENGTH_SHORT).show()
                                })
                            .setNegativeButton(
                                "Нет",
                                DialogInterface.OnClickListener { dialog, which ->
                                })
                            .setIcon(R.drawable.ic_warning)
                            .show()
                        true
                    }
                    else -> true
                }
            }
            popupMenu.show()
            val popup = PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true
            val menu = popup.get(popupMenu)
            menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                .invoke(menu, true)
        }
    }

    override fun getItemCount(): Int {
            return productList.size
        }

        fun setData(product: List<TableProduct>) {
            this.productList = product
            notifyDataSetChanged()
        }
    private fun input(product: String, price:String):Boolean{
        return !(TextUtils.isEmpty(product) && TextUtils.isEmpty(price))
    }
    interface onItemClickListener {
        fun onItemClick(v:View?, position: Int)
}

}







