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

fun main(args: Array<String>) {
    println("runmain")
}

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
        //kotlinWorkerManager()
        //myAlgo()
        myAlgo2()

        /*
         default visibility modifier ->
         public, internal, protected, private

         scope function
         let, apply, with, run, also

         */

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

        //showNotification()
    }

    /*
    * Function to find and print longest, substring without repeating characters.
    *  */
    private fun myAlgo2() {
        val inputStr: String = "GEEKSFORGEEKS" //EKSFORG
        //val inputStr: String = "YELLOW"
        var index: Int = 0
        val inputStrLenth: Int = inputStr.length

        // Starting point of current substring.
        var st = 0
        // length of current substring.
        var currlen = 0
        // maximum length substring without repeating characters.
        var maxlen = 0
        // starting index of maximum length substring.
        var start = 0
        // Hash Map to store last occurrence of each already visited character.
        val pos = HashMap<Char, Int>()
        pos.put(inputStr[0], 0) //Last occurrence of first character is index 0;

        for (i in 1 until inputStrLenth) {
            index++
            // If this character is not present in hash, then this is first occurrence of this character, store this in hash.
            println("myAlgo pos : $pos")
            if (!pos.containsKey(inputStr[i])) {
                pos.put(inputStr[i], i)
                println("myAlgo AddedPos : " + inputStr[i])
            } else {
                // If this character is present in hash then this character has previous occurrence,
                // check if that occurrence is before or after starting  point of current substring.
                //y e l
                println("myAlgo having :" + inputStr[i] + " at " + pos.get(inputStr[i]))
                if (pos.get(inputStr[i])!! >= st) { // 2 > 0

                    // find length of current substring and update maxlen and start accordingly.
                    currlen = i - st // 3 - 0 = 3 (currlen)
                    if (maxlen < currlen) { //0 < 3
                        maxlen = currlen; // 3(maxlen) = 3(currlen)
                        start = st; // start0 = 0(st)
                    }
                    println(
                        "myAlgo findLenthSubstr : max " + maxlen + " start " + start + " " + inputStr.substring(
                            start,
                            maxlen
                        )
                    )

                    // Next substring will start after the last occurrence of current character to avoid its repetition.
                    st = pos.get(inputStr[i])!! + 1;
                    println("myAlgo findLenthSubstr : nextSubStartAt " + st + " " + pos.get(inputStr[i]))
                }

                // Update last occurrence of current character.
                pos.replace(inputStr[i], i);
            }
        }

        // Compare length of last substring with maxlen and update maxlen and start accordingly.
        if (maxlen < index - st) {
            maxlen = index - st;
            start = st;
        }

        // The required longest substring without  repeating characters is from str[start] to str[start+maxlen-1].
        val finalStr = inputStr.substring(start, start + maxlen);
        println("myAlgo final : $finalStr")


        /*val myInout = "YELLOW"
        var lastVisitId = 1
        val defultIndex = 1
        val store = LinkedHashMap<Char, Int>()

        *//*
        * HashMap -> retrun LIFO
        * LinkedHashMap -> retrin FIFO
        * *//*

        //GET FIRST TWO LETTERS (by defult)
        store.put(myInout[0], 1)
        store.put(myInout[1], 1) //lastVisitId = 1

        var storeLetter = ""
        var finalStoreLetter = ""

        for (index in 2 until myInout.length) {
            val nextChar = myInout[index]
            println("myALdo Allletter : $nextChar")

            //set word from store list
            for (letter in store) {
                storeLetter += letter.key
            }
            finalStoreLetter = storeLetter
            println("myALdo letter : " + storeLetter)

            //check contain having new letter in saved list
            if (storeLetter.contentEquals(nextChar.toString())) {
                println("myALdo letterContent")
            } else {
                println("myALdo NOT letterContent")
                store.put(nextChar, 1)
            }

            //clear temp store letter
            storeLetter = ""
        }

        println("myALdo LAST : "+finalStoreLetter)*/
    }

    private fun myAlgo(): Int {
        val userInput = "abcdddddeffZZZZZZZZZ"
        var ans = 1
        var temp = 1
        var letters = ""

        for (i in 1 until userInput.length) {
            println("myLetter " + userInput[i] + " " + userInput[i - 1])

            //check left and right letters are same or not
            if (userInput[i] == userInput[i - 1]) {
                //if its same increment temp value and leters
                ++temp
                letters += userInput[i]
                println("myLetter " + temp)
            } else {
                //else ans value is max value of temp and ans
                ans = Math.max(ans, temp);
                println("myLetter else " + ans + " " + temp)
                //set temp 1, or else increment temp value with another letter
                temp = 1;
            }
        }

        ans = Math.max(ans, temp)
        println("myLetter FINALFINAL " + ans + " -> " + letters)
        return ans
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

        val myMap = mutableMapOf<String, Int>() // MapOf - cant add duplicate ket,valiues
        myMap.put("a", 10)
        myMap.put("a", 10)
        myMap.put("b", 10)
        myMap.put("c", 20)

        val mySet = mutableSetOf<Int>() // SetOf - cant add duplicares
        mySet.add(10)
        mySet.add(10)
        mySet.add(20)
        mySet.add(40)

        val myList = mutableListOf<Int>() // ListOf - can add duplicates
        myList.add(10)
        myList.add(10)
        myList.add(20)
        myList.add(40)

        println("nyCollectuin mySet " + mySet)
        println("nyCollectuin myMap " + myMap)
        println("nyCollectuin myList " + myList)

        val name: String = "banana"

        for (index in name) {
            println("mySetIndex : $index")
        }

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
