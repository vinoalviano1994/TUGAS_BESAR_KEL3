package com.alviano.ugd_kel3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alviano.ugd_kel3.adapters.ProdukAdapter
import com.alviano.ugd_kel3.api.ProdukApi
import com.alviano.ugd_kel3.models.Produk
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class ProdukView : AppCompatActivity() {

    private var srProduk: SwipeRefreshLayout? = null
    private var adapter: ProdukAdapter? = null
    private var svProduk: SearchView? = null
    private var layoutLoading: LinearLayout? = null
    private var queue: RequestQueue? = null

    companion object {
        const val LAUNCH_ADD_ACTIVITY = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produk_view)

        queue = Volley.newRequestQueue(this)
        layoutLoading = findViewById(R.id.layout_loading)
        srProduk = findViewById(R.id.sr_produk)
        svProduk = findViewById(R.id.sv_produk)

        srProduk?.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener { allProduk() })
        svProduk?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                return false
            }

            override fun onQueryTextChange(s: String?): Boolean {
                adapter!!.filter.filter(s)
                return false
            }
        })

        val fabAdd = findViewById<FloatingActionButton>(R.id.fab_add)
        fabAdd.setOnClickListener {
            val i = Intent(this@ProdukView, addEditProduk::class.java)
            startActivityForResult(i, LAUNCH_ADD_ACTIVITY)
        }

        val rvProduk = findViewById<RecyclerView>(R.id.rv_produk)
        adapter = ProdukAdapter(ArrayList(), this)
        rvProduk.layoutManager = LinearLayoutManager(this)
        rvProduk.adapter = adapter
        allProduk()
    }

    private fun allProduk(){
        srProduk!!.isRefreshing = true
        val stringRequest: StringRequest = object :
            StringRequest(Method.GET, ProdukApi.GET_ALL_URL, Response.Listener { response ->
                val gson = Gson()
                var produk: Array<Produk> =
                    gson.fromJson(response, Array<Produk>::class.java)

                adapter!!.setProdukList(produk)
                adapter!!.filter.filter(svProduk!!.query)
                srProduk!!.isRefreshing = false

                if (!produk.isEmpty())
                    Toast.makeText(this@ProdukView, "Data berhasil diambil!", Toast.LENGTH_SHORT)
                        .show()
                else
                    Toast.makeText(this@ProdukView, "Data Kosong!", Toast.LENGTH_SHORT)
                        .show()
            }, Response.ErrorListener { error ->
                srProduk!!.isRefreshing = false
                try {
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(
                        this@ProdukView,
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception) {
                    Toast.makeText(this@ProdukView, e.message, Toast.LENGTH_SHORT).show()
                }
            }){
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }
        }
        queue!!.add(stringRequest)
    }

    fun deleteProduk(id: Long){
        setLoading(true)
        val stringRequest: StringRequest = object :
            StringRequest(
                Method.DELETE,
                ProdukApi.DELETE_URL + id,
                Response.Listener { response ->
                    setLoading(false)

                    val gson = Gson()
                    var produk = gson.fromJson(response, Produk::class.java)
                    if (produk != null)
                        Toast.makeText(
                            this@ProdukView,
                            "Data Berhasil dihapus",
                            Toast.LENGTH_SHORT
                        ).show()
                    allProduk()
                },
                Response.ErrorListener { error ->
                    setLoading(false)
                    try {
                        val responseBody =
                            String(error.networkResponse.data, StandardCharsets.UTF_8)
                        val errors = JSONObject(responseBody)
                        Toast.makeText(
                            this@ProdukView,
                            errors.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                    } catch (e: java.lang.Exception) {
                        Toast.makeText(this@ProdukView, e.message, Toast.LENGTH_SHORT).show()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LAUNCH_ADD_ACTIVITY && resultCode == RESULT_OK) allProduk()
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