package com.example.malevichstudio.fragment

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import com.example.malevichstudio.R
import com.example.malevichstudio.activity.MenuActivity
import com.example.malevichstudio.converters.Converters
import com.example.malevichstudio.database.product.TableProduct
import com.example.malevichstudio.databinding.FragmentAddBinding
import com.example.malevichstudio.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.activity_add.view.*
import kotlinx.android.synthetic.main.costom_row.*
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.coroutines.launch
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.URI
import java.util.jar.Manifest


class AddFragment : Fragment(R.layout.fragment_add) {
    var pickedPhoto:Uri?=null
    var pickedBitmap: Bitmap?=null

    private var _binding:FragmentAddBinding?=null
private val binding get() = _binding!!

    private lateinit var viewModel: ProductViewModel
    private val CAMERA_REQUEST_CODE = 100
    private val STOREGE_REQUEST_CODE = 101
    private val IMAGE_PICK_CAMERA_CODE = 102
    private val IMAGE_PICK_GALERY_CODE = 103
    private lateinit var cameraPermission: Array<String>
    private lateinit var storagePermission: Array<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
      _binding= FragmentAddBinding.inflate(inflater, container,false)
             //val view = inflater.inflate(R.layout.fragment_add, container, false)
        val view= binding.root

        viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        cameraPermission = arrayOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        storagePermission = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

        binding.imageView.setImageResource(R.drawable.ic_add_photo)
        binding.imageView.setOnClickListener { imagePickDialog() }

        binding.seve.setOnClickListener {
            insertToDatabase()
        }
        return view

    }

    private fun insertToDatabase() {
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val product= binding.edProduct.text.toString()
        val price = binding.edPrice.text.toString()
        val namemat= binding.edMaterial.text.toString()
        val matamount=binding.edMatcrom.text.toString().toInt()
        val metermat = binding.edMetr.text.toString().toInt()
        val facade = binding.edMetFas.text.toString()
        val facadepoc = binding.edMatPok.text.toString().toInt()
        val facadeedge=binding.edMatcrom.text.toString().toInt()
        val fittings=binding.edPet.text.toString()
        val fittingssize = binding.edPetpod.text.toString().toInt()
        val fitting = binding.edMatpod.text.toString()
        val fittinssize =binding.edMatamount.text.toString().toInt()
        val byteArray = bitmapByteArray(bitmap)
        val image = byteArrayBitmap(byteArray)
        if (inputCheck(product, price)){
            Toast.makeText(requireContext(), "Данные сохранены", Toast.LENGTH_SHORT).show()
            val product = TableProduct(0,  product, price,namemat, matamount,metermat, facade, facadepoc, facadeedge, fittings, fittingssize,fitting,fittinssize, image )

            lifecycle.coroutineScope.launch{
                viewModel.addProduct(product)}
        }else{
            Toast.makeText(requireContext(), "Заполните все данные", Toast.LENGTH_SHORT).show()

        }
    }

    private fun byteArrayBitmap(byteArray: ByteArray): Bitmap {
        return  BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)
    }

    private fun bitmapByteArray(bitmap: Bitmap?):ByteArray {
        val stream = ByteArrayOutputStream()
        if (bitmap != null) {
            bitmap.compress(Bitmap.CompressFormat.PNG,0,stream)
        }
        return stream.toByteArray()

    }


    private fun imagePickDialog() {
        val option = arrayOf("Камера","Галерея")
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Выбрать изображение из..")
        builder.setItems(option){
                dialog, which ->
            if (which ==0){
                if (!checkCameraPermission()) {
                    requestCameraPermission()
                }else{
                    pickFromCamera()
                }
            }else{
                if (!checkStoregePermission()) {
                    requestStoregePermission()
                } else {
                    pickFromGallery()
                }
            }
        }
        builder.show()
    }

    private fun pickFromGallery() {
        val gallaryIntent = Intent(Intent.ACTION_PICK)
        gallaryIntent.type= "image/+"
        startActivityForResult(gallaryIntent, IMAGE_PICK_GALERY_CODE)
    }

    private fun requestStoregePermission() {
        ActivityCompat.requestPermissions(requireContext() as Activity,storagePermission, STOREGE_REQUEST_CODE)
    }

    private fun checkStoregePermission(): Boolean {
        return  ContextCompat.checkSelfPermission(
            requireContext(),android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )== PackageManager.PERMISSION_GRANTED
    }

    private fun pickFromCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE)
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(requireContext() as Activity,cameraPermission,CAMERA_REQUEST_CODE)
    }

    private fun checkCameraPermission(): Boolean {
        val  resultcamera = ContextCompat.checkSelfPermission(
            requireContext(),android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        val result =ContextCompat.checkSelfPermission(
            requireContext(),android.Manifest.permission.CAMERA
        )==PackageManager.PERMISSION_GRANTED
        return resultcamera && result
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode){
            CAMERA_REQUEST_CODE->{
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickFromCamera()
                }else{
                    Toast.makeText(requireContext(),"Требуется разрешение на использование камеры и хранилища", Toast.LENGTH_SHORT).show()
                }
            }
            STOREGE_REQUEST_CODE ->{
                if (grantResults.isNotEmpty()){
                    val storegAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                if (storegAccepted){
                    pickFromGallery()
                }else{
                    Toast.makeText(
                        requireContext(),
                        " Storage permission are required",
                        Toast.LENGTH_SHORT
                    )
                        .show()

                }
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
     // if (resultCode == Activity.RESULT_OK){
     if (resultCode == IMAGE_PICK_CAMERA_CODE && resultCode== Activity.RESULT_OK && data != null) {
         pickedBitmap = data.extras!!.get("data") as Bitmap
        // if (pickedBitmap != null) {
             binding.imageView.setImageBitmap(pickedBitmap)
        // }
     }
if (requestCode == IMAGE_PICK_GALERY_CODE && resultCode == Activity.RESULT_OK && data != null) {
    pickedPhoto= data.data

    try {
   val uri = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, pickedPhoto)
    // val bitmap = (imageView.drawable as BitmapDrawable).bitmap
      val byteArray = byteArrayImage(uri)
        val bitmapImage= bitmapImage(byteArray)
       // val source = ImageDecoder.createSource(requireContext().contentResolver,pickedPhoto!!)
        binding.imageView.setImageBitmap(bitmapImage)
  } catch (e: IOException){
        e.printStackTrace()
    }

    //binding.imageView.setImageURI(data.data) // if (pickedPhoto != null){
       // if (Build.VERSION.SDK_INT>=28){
          // val source = ImageDecoder.createSource(requireContext().contentResolver,pickedPhoto!!)
           // pickedBitmap =ImageDecoder.decodeBitmap(source)

        //}
   // }


}
            // }
          // }

        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun bitmapImage(image:ByteArray): Bitmap {

        return  BitmapFactory.decodeByteArray(image,0,image.size)
    }

    private fun byteArrayImage(bitmap: Bitmap): ByteArray{

        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG,200,stream)
        return stream.toByteArray()
    }


    private fun inputCheck(product:String, price:String): Boolean {
        return !(TextUtils.isEmpty(product) && TextUtils.isEmpty(price))
    }

    companion object {

        @JvmStatic
        fun newInstance()=AddFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}
