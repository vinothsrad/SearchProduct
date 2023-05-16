package com.example.product_list


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.product_list.model.productlistItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    lateinit var rvMain: RecyclerView
    lateinit var myAdapter: ProductListAdapter
    var BASE_URL= "https://fakestoreapi.com"
    private var mlist= ArrayList<productlistItem>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        rvMain = findViewById(R.id.recycler_View)
        rvMain.layoutManager = LinearLayoutManager(this)
        getAllData()



    }



    private fun getAllData() {
        val retrofit= Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        val retroData= retrofit.getDate()
        retroData.enqueue(object: Callback<List<productlistItem>> {
            override fun onResponse(
                call: Call<List<productlistItem>>,
                response: Response<List<productlistItem>>
            ) {
                val data=response.body()!!
                myAdapter= ProductListAdapter(baseContext, data as ArrayList<productlistItem>)
                rvMain.adapter=myAdapter

                Log.d("data",data.toString())
            }

            override fun onFailure(call: Call<List<productlistItem>>, t: Throwable) {

            }
        })
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.menu_item, menu)
        val searchItem: MenuItem = menu.findItem(R.id.menu_search)
        var searchView: SearchView? = null
        if (searchItem != null) {
            searchView = searchItem.getActionView() as SearchView?

        }
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                return false

            }

            override fun onQueryTextChange(newText: String): Boolean {
                myAdapter.getFilter()?.filter(newText)

                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }



}

