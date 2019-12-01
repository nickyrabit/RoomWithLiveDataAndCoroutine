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

/**
 Here we are using Kotlin Coroutine Scope
 This enables us to cancel the task when we want cancel the task as soon as the activity is Destroyed
** So to do that we extend Coroutines
**/
class MainActivity : AppCompatActivity(),CoroutineScope {
    //initializing a A background job.
    private lateinit var job: Job

    //initializing the coroutine scope
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job


    //db instance
    lateinit var fuelPriceDatabase : FuelPriceDatabase

    //ui declarations
    private val buttonChangeData: Button by lazy { findViewById(R.id.buttonChangeData) as Button }
    private val PetrolPriceEditText: EditText by lazy { findViewById(R.id.PetrolPrice) as EditText }
    private val displayTextView: TextView by lazy { findViewById(R.id.displayTextView) as TextView }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        job = Job() // create the Job


        // creating an exception handler like below:
        val handler = CoroutineExceptionHandler { _, exception ->
            Log.d("TAG", "$exception handled !")
        }

        //initiate a database instance
        fuelPriceDatabase=   FuelPriceDatabase.invoke(this@MainActivity)

        //placing an observer for the livedata we set on DAO
        fuelPriceDatabase.petrolPriceDao().getAllPrices().observe(this, Observer {
            Toast.makeText(this@MainActivity, "has been updated", Toast.LENGTH_SHORT).show()
                //In Kotlin we dont have the onchanged method
            for (petrolPrice: PetrolPrice in it) {
                //showing the changed value
                displayTextView.setText(""+petrolPrice.fuel_price)
            }

            })


            //getting the input value
        buttonChangeData.setOnClickListener {
            //get input
            val price = PetrolPriceEditText.getText().toString().toDouble()
            val petrolPrice = PetrolPrice(1, price)

            // Global Scopes are used when we want to run the funciton even if the carrying activity is destroyed.
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
            //Dispatcher Default hekp us to run this action async
            ud.petrolPriceDao().insertAll(petrolPrice)
            Log.v("TAG", "Insert COmplete" )

        }
    }


}

