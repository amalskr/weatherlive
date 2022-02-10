package com.ceylonapz.mykots.test

import okhttp3.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://newsapi.org/"
const val API_KEY = "ff30357667f94aca9793cc35b9e447c1"

/*
* singleton shold be an OBJECT
* OBJECT have -> functions, properties, and the init method.
*
* */
object TestSingleton {

    init {
        println("Singleton class invoked.")
    }

    var variableName = "I am Var"
    fun printVarName() {
        println(variableName)
    }

    fun printVarNameFull() {
        println(variableName + " Full")
    }


    /*sample #1*/
    /*private var instance: Api? = null
    private val BASE_URL = "https://jsonplaceholder.typicode.com/"

    fun getInstance(): Api? {
        if (instance == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            instance = retrofit.create(Api::class.java)
        }
        return instance
    }*/


    /*sample #2*/
    /*//Globally accessible object reference
    private var instance: TestSingleton? = null

    //A private constructor
    private fun TestSingleton() {}

    //A static reference of its class / One static method
    fun getInstance(): TestSingleton? {
        if (instance == null) {
            //Consistency across multiple threads
            synchronized(TestSingleton::class.java) {
                if (instance == null) {
                    instance = TestSingleton()
                }
            }
        }
        return instance
    }*/


    /*sample #3*/
    /*var newsInstance: NewsInterface? = null

    init {
        if (newsInstance == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                //.addConverterFactory(GsonConverterFactory.create())
                .build()

            newsInstance = retrofit.create(NewsInterface::class.java)
            println("mySingleton creetaed")
        } else {
            println("mySingleton not creetaed")
        }

    }*/
}

interface NewsInterface {
    @GET("v2/top-headlines?apiKey=$API_KEY")
    fun getheadlines(@Query("country") country: String, @Query("page") page: Int): Response

    @GET("v2/top-headlines?apiKey=$API_KEY")
    fun getheadAll(@Query("country") country: String, @Query("page") page: Int): Response
}