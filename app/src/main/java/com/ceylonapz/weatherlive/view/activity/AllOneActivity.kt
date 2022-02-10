package com.ceylonapz.weatherlive.view.activity

import android.media.RingtoneManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.ceylonapz.mykots.test.TestModel
import com.ceylonapz.mykots.test.TestSingleton
import com.ceylonapz.mykots.test.TestWorker
import com.ceylonapz.weatherlive.R
import com.ceylonapz.weatherlive.model.CityWeather
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/*
 by default interface  is "public"
 */
interface userAction {
    fun onLick(user: CityWeather)
}

/*
 by default class is "final"
 so set "open " before inhenratence class
 */
open class myParent {}

/*
* static class should have "companion"
* or id#2 shound have without class name
* */
class MyStatic {
    companion object {
        fun a(): Int = 5
    }

    //id#2 const val IMAGE_MANIPULATION_WORK_NAME = "image_manipulation_work"
}

/*
* kotlin: object instead of class
* */
object Singleton {}


/*
* structural expressions
* Return: It returns from the nearest enclosing function or anonymous function by default.
  Break: This expression terminates the closest enclosing loop.
  Continue: This expression proceeds you to the next closest enclosing loop.
* */


/* Entry Point
* Java Programs
*   public static void main(String[] args)
* Kotlin Programs
*   fun main(args: Array<String>)
* */

class AllOneActivity : AppCompatActivity(), userAction, CoroutineScope {

    //Coroutine Cancel -> extend CoroutineScope
    private lateinit var jobCoroutine: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + jobCoroutine
    // end Coroutine Cancel

    lateinit var testLateInt: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_one)

        //kotlinArays()
        //kotlinMutable()
        //kotlinNull()
        //println("mywhen " + kotlinWhen())
        //kotlinLet()
        //kotlinSingleton()
        //kotlinSealed()
        //kotlinCoroutines()
        kotlinWorkerManager()

        /*
         default visibility modifier ->
         public, internal, protected, private
         */

        //showNotification()
    }

    private fun kotlinWorkerManager() {
        println("myWorkoer Start")
        val oneTimeRequest = OneTimeWorkRequest.Builder(TestWorker::class.java).build()
        val workManager = WorkManager.getInstance(application)
        workManager.enqueue(oneTimeRequest)
    }


    private fun showNotification() {
        /*val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = TaskStackBuilder.create(applicationContext).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }*/

        val notification =
            NotificationCompat.Builder(applicationContext, "CHANNEL_ID_ONE_TIME_WORK")
        notification.setContentTitle("ONE TIME WORK")
        notification.setContentText("Text")
        notification.priority = NotificationCompat.PRIORITY_HIGH
        notification.setCategory(NotificationCompat.CATEGORY_ALARM)
        notification.setSmallIcon(R.drawable.ic_feellike)
        val sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        notification.setSound(sound)
        val vibrate = longArrayOf(0, 100, 200, 300)
        notification.setVibrate(vibrate)

        with(NotificationManagerCompat.from(applicationContext)) {
            notify(2, notification.build())
        }
    }

    /*
    * call from network = suspend ( started, paused, and resume)
    * Dispatchers.IO (network), Default (CPU intensive work), Main (UI thread work)
    * has retrun values = async and await
    * */
    fun kotlinCoroutines() { // retrun : User

        //cancel -> CoroutineScope check extend way
        jobCoroutine = Job()

        //in ViewModel (no return)
        //viewModelScope.launch(Dispatchers.IO){}

        //in Activity
        //return GlobalScope.async(Dispatchers.IO) {}.await()
        //GlobalScope.launch(Dispatchers.IO) { println("myCortu notworkCall") }
        // GlobalScope.launch(Dispatchers.Main) { println("myCortu displayFetchData") }

        GlobalScope.launch(Dispatchers.Main) {
            val userOne = async(Dispatchers.IO) { println("myCortu fetchFirstuser") }
            delay(2000)
            val userTwo = async(Dispatchers.IO) { println("myCortu fetchSeconduser") }
            delay(1500)
            showUsersCoroutines(userOne.await(), userTwo.await()) // back on UI thread
        }
    }

    private fun showUsersCoroutines(await: Unit, await1: Unit) {
        println("myCortu showUser " + await.toString())
    }

    override fun onPause() {
        super.onPause()
        jobCoroutine.cancel() // cancel the Job
        println("myCortu cancel")
    }

    fun kotlinSealed() {
        // val networkState: NetworkState = NetworkState.Loading(true)
        val networkState: NetworkState = NetworkState.Failure("NullExpection", "400")
        when (networkState) {
            is NetworkState.Loading -> {
                println("kotlinSealed loading")
            }
            is NetworkState.Failure -> {
                println("kotlinSealed fail ${networkState.errorMsg}")
            }
            else -> {
                println("kotlinSealed else")
            }
        }
    }

    fun kotlinSingleton() {
        TestSingleton.printVarName()
        TestSingleton.variableName = "form main"
        TestSingleton.printVarName()
        TestSingleton.printVarNameFull()

        //TestSingleton.newsInstance?.getheadlines("Amal", 2)
    }

    /*
    let function use for null safty
    using "?.let" skip null values
    * */
    private fun kotlinLet() {
        var stringlist: List<String?> = listOf("Amal", "Sihwa", null, "Ranasinh")

        for (str in stringlist) {
            str?.let { println("kotLet let $it") }
            //str?.also { println("kotLet letalso :  $it") }
        }
    }


    fun kotlinWhen(): String {

        val model = TestModel("amal", 100)
        //model.id = 10
        println("testModel inName :  ${model.name}")
        println("testModel inId :  ${model.id}")

        testLateInt = "Some value"
        println("Length of string is " + testLateInt.length)
        testLateInt = "change value"

        val id = 50

        when (id) {
            2 - 1 -> {
                return "when #2"
                //println("myWhen #2")
            }
            5 -> {
                return "when #5"
                //println("myWhen #5")
            }
            10 -> {
                return "when #10"
                //println("myWhen #10")
            }
            in 20..100 -> {
                return "when range #50"
            }
            else -> {
                return "when Else"
                //println("myWhen #Else")
            }
        }
    }

    fun kotlinNull() {
        var str: String? = "JournalDev.com"
        var newStr = str ?: "Default Value a"
        str = null
        newStr = str ?: "Default Value b"
        println("myMuta Null : $newStr")

        val int1 = 10
        val int2 = 10

        val first = Integer(10)
        val second = Integer(10)

        println(int1 == int2)        // true
        println(first === second)       // false (print two refrence)

        val strId = "12"
        val parsedValue = strId.toInt()
        println("nyNull " + parsedValue)       // false (print two refrence) )

        /*
        * Safe Call operator(?.) ->
        * retun null no expections
        * */
        var stra: String? = "JournalDev.com"
        stra = null
        println("nyNull SafeCall: " + stra?.length)

        /*
        * Elvis Operator(?:) –
        * nested if-else expression
        * */
        var strName: String? = "JournalDev.com"
        strName = null
        val myName = strName ?: "Unknown"
        println("nyNull Elvis: " + myName)
        println("nyNull SafeCallElvis : " + strName?.length ?: "-1")

        /*
        * Not null assertion : !!
        * converts any value to a non-null type and throws an exception if the value is null.
        * */
        var strNotNull: String? = "GeeksforGeeks"
        println("nyNull notNull : " + strNotNull!!.length)
        strNotNull = null
        //strNotNull!!.length
        //println("nyNull notNull : " + strNotNull!!.length)

        val name = "UBUNTU"
        val upperCase = name.uppercase()
        name.intern()

    }

    override fun onLick(user: CityWeather) {
        TODO("Not yet implemented")
    }

    fun kotlinMutable() {
        val mutaString = mutableListOf<String>("AMal", "Shiwantrha", "Kuamra", "Ranasinghe")
        println("myMuta : $mutaString")

        mutaString.add("addnewname")
        println("myMuta : $mutaString")

        mutaString.add(1, "newname")
        println("myMuta : $mutaString")

        mutaString.set(1, "setnewname")
        println("myMuta : $mutaString")

        println("myMuta ========================")


        //val mutaString = mutableListOf<String>("AMal", "Shiwantrha", "Kuamra", "Ranasinghe")
        val mapPeople = mutableMapOf<String, Int>("Amal" to 34, "Shiwantrha" to 50, "Kuma" to 10)
        println("myMuta Entries: " + mapPeople.entries)
        println("myMuta Keys:" + mapPeople.keys)
        println("myMuta Values:" + mapPeople.values)
        println(
            "myMuta getOrDefault :" + mapPeople.getOrDefault(
                "Shiwantrha",
                "0"
            )
        )        //Printing Values

        val team = mapPeople.getOrElse("Amalsdkr", { 0 })
        println("myMuta team :" + team)
        println("myMuta ========================")
    }

    /*
    * array initialization
    * support - short,byte,int,long,flot,doble,bool,char
    */
    fun kotlinArays() {
        val numAry: IntArray = intArrayOf(1, 2, 3, 4, 5)
        val aryInt = intArrayOf(11, 22, 33, 44, 55)
        val aryBool = booleanArrayOf(true, false, false, true, true)
        val aryString = arrayOf("Amal", "Shiwantha", "Kumara", "Ranasinghe", "Amal")
        val aryStringGood = arrayOf("Amal", "Shiwantha", "Kumara", "Ranasinghe")

        //foreach
        for (index in aryString) {
            println("myary : $index")
        }
        println("myary ========================")

        //distinct
        val distAry = aryString.distinct()
        for (index in distAry) {
            println("myary dist: $index")
        }
        println("myary ========================")

        //convert
        val aryStringSet = aryString.toHashSet()
        for (index in aryStringSet) {
            println("myary aryStringSet: $index")
        }
        println("myary ========================")

        val aryStringRevers = aryStringGood.reversedArray()
        for (index in aryStringRevers) {
            println("myary aryStringRevers: $index")
        }
        println("myary ========================")
    }
}


data class User(val firstName: String, val mobile: Int)

sealed class NetworkState {
    data class Loading(var loading: Boolean) : NetworkState()
    data class Failure(var errorMsg: String, var errorCode: String) : NetworkState()
}
