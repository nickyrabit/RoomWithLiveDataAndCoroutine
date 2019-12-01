package com.nickyrabit.roomwithlivedatakotlin

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.nickyrabit.roomwithlivedatakotlin.db.FuelPriceDatabase
import com.nickyrabit.roomwithlivedatakotlin.model.PetrolPrice
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(),CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var job: Job

lateinit var fuelPriceDatabase : FuelPriceDatabase


    private val buttonChangeData: Button by lazy { findViewById(R.id.buttonChangeData) as Button }
    private val PetrolPriceEditText: EditText by lazy { findViewById(R.id.PetrolPrice) as EditText }
    private val displayTextView: TextView by lazy { findViewById(R.id.displayTextView) as TextView }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        job = Job() // create the Job


        //
        val handler = CoroutineExceptionHandler { _, exception ->
            Log.d("TAG", "$exception handled !")
        }


        fuelPriceDatabase=   FuelPriceDatabase.invoke(this@MainActivity)
        fuelPriceDatabase.petrolPriceDao().getAllPrices().observe(this, Observer {
            Toast.makeText(this@MainActivity, "has been updated", Toast.LENGTH_SHORT).show()

            for (petrolPrice: PetrolPrice in it) {

                displayTextView.setText(""+petrolPrice.fuel_price)
            }

            })



        buttonChangeData.setOnClickListener {
            //get input
            val price = PetrolPriceEditText.getText().toString().toDouble()
            val petrolPrice = PetrolPrice(1, price)

            //
            GlobalScope.launch(Dispatchers.Main + handler) {
                petrolPriceInsert(petrolPrice)
            }

        }

    }

    //
    private fun petrolPriceInsert(petrolPrice: PetrolPrice) {
        //
        launch(Dispatchers.Default) {
            val ud = FuelPriceDatabase.invoke(context = applicationContext)

            ud.petrolPriceDao().insertAll(petrolPrice)
            Log.v("TAG", "Insert COmplete" )

        }
    }


}

