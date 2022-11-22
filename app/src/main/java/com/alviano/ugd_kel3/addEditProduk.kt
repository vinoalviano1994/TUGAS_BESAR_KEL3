package com.alviano.ugd_kel3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.*
import com.alviano.ugd_kel3.api.ProdukApi
import com.alviano.ugd_kel3.models.Produk
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class addEditProduk : AppCompatActivity() {

    private var etNamaProduk: EditText? = null
    private var etHarga: EditText? = null
    private var etDeskripsi: EditText? = null
    private var layoutLoading: LinearLayout? = null
    private var queue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_produk)

        queue = Volley.newRequestQueue(this)
        etNamaProduk = findViewById(R.id.et_namaProduk)
        etHarga = findViewById(R.id.et_hargaProduk)
        etDeskripsi = findViewById(R.id.et_deskripsiProduk)
        layoutLoading = findViewById(R.id.layout_loading)

        val btnCancel = findViewById<Button>(R.id.btn_cancel)
        btnCancel.setOnClickListener { finish() }
        val btnSave = findViewById<Button>(R.id.btn_save)
        val tvTitle = findViewById<TextView>(R.id.tv_title)
        val id = intent.getLongExtra("id", -1)
        if (id == -1L) {
            tvTitle.setText("Tambah Produk")
            btnSave.setOnClickListener { createProduk() }
        } else {
            tvTitle.setText("Edit Produk")
            getProdukById(id)

            btnSave.setOnClickListener { updateProduk(id) }
        }
    }

    private fun getProdukById(id: Long){
        setLoading(true)
        val stringRequest: StringRequest = object : StringRequest(
            Method.GET,
            ProdukApi.GET_BY_ID_URL + id,
            Response.Listener{ response ->
                val gson = Gson()
                val produk = gson.fromJson(response, Produk::class.java)

                etNamaProduk!!.setText(produk.namaProduk)
                etHarga!!.setText(produk.harga)
                etDeskripsi!!.setText(produk.deskripsi)
                Toast.makeText(this@addEditProduk, "Data berhasil diambil!", Toast.LENGTH_SHORT)
                    .show()
                setLoading(false)
            },
            Response.ErrorListener { error ->
                setLoading(false)
                try {
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(
                        this@addEditProduk,
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception) {
                    Toast.makeText(this@addEditProduk, e.message, Toast.LENGTH_SHORT).show()
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }
        }
        queue!!.add(stringRequest)
    }

    private fun createProduk() {
        setLoading(true)

        val produk = Produk(
            etNamaProduk!!.text.toString(),
            etHarga!!.text.toString(),
            etDeskripsi!!.text.toString(),
        )

        val stringRequest: StringRequest =
            object :
                StringRequest(Method.POST, ProdukApi.ADD_URL, Response.Listener { response ->
                    val gson = Gson()
                    var produk = gson.fromJson(response, Produk::class.java)
                    if (produk != null)
                        Toast.makeText(
                            this@addEditProduk,
                            "Data Berhasil Diupdate",
                            Toast.LENGTH_SHORT
                        ).show()
                    val returnIntent = Intent()
                    setResult(RESULT_OK, returnIntent)
                    finish()
                    setLoading(false)
                }, Response.ErrorListener { error ->
                    setLoading(false)
                    try {
                        val responseBody =
                            String(error.networkResponse.data, StandardCharsets.UTF_8)
                        val errors = JSONObject(responseBody)
                        Toast.makeText(
                            this@addEditProduk,
                            errors.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                    } catch (e: Exception) {
                        Toast.makeText(this@addEditProduk, e.message, Toast.LENGTH_SHORT).show()
                    }
                }){
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Accept"] = "application/json"
                    return headers
                }

                @Throws(AuthFailureError::class)
                override fun getBody(): ByteArray {
                    val gson = Gson()
                    val requestBody = gson.toJson(produk)
                    return requestBody.toByteArray(StandardCharsets.UTF_8)
                }

                override fun getBodyContentType(): String {
                    return "application/json"
                }

            }
        queue!!.add(stringRequest)

    }

    private fun updateProduk(id: Long) {
        setLoading(true)

        val produk = Produk(
            etNamaProduk!!.text.toString(),
            etHarga!!.text.toString(),
            etDeskripsi!!.text.toString(),
        )

        val stringRequest: StringRequest =
            object : StringRequest(
                Method.PUT,
                ProdukApi.UPDATE_URL + id,
                Response.Listener{ response ->
                    val gson = Gson()
                    var produk = gson.fromJson(response, Produk::class.java)
                    if (produk != null)
                        Toast.makeText(
                            this@addEditProduk,
                            "Data Berhasil Diupdate",
                            Toast.LENGTH_SHORT
                        ).show()
                    val returnIntent = Intent()
                    setResult(RESULT_OK, returnIntent)
                    finish()
                    setLoading(false)
                }, Response.ErrorListener { error ->
                    setLoading(false)
                    try {
                        val responseBody =
                            String(error.networkResponse.data, StandardCharsets.UTF_8)
                        val errors = JSONObject(responseBody)
                        Toast.makeText(
                            this@addEditProduk,
                            errors.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                    } catch (e: Exception) {
                        Toast.makeText(this@addEditProduk, e.message, Toast.LENGTH_SHORT).show()
                    }
                }){
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Accept"] = "application/json"
                    return headers
                }

                @Throws(AuthFailureError::class)
                override fun getBody(): ByteArray {
                    val gson = Gson()
                    val requestBody = gson.toJson(produk)
                    return requestBody.toByteArray(StandardCharsets.UTF_8)
                }

                override fun getBodyContentType(): String {
                    return "application/json"
                }

            }
        queue!!.add(stringRequest)

    }

    private fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
            layoutLoading!!.visibility = View.VISIBLE
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            layoutLoading!!.visibility = View.INVISIBLE
        }
    }
}