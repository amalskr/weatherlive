package com.ceylonapz.weatherlive.view.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ceylonapz.weatherlive.databinding.ActivityRxKotBinding
import com.ceylonapz.weatherlive.model.Data
import com.ceylonapz.weatherlive.utilities.rxkots.GitHubData
import com.ceylonapz.weatherlive.utilities.rxkots.RxKotViewModel
import com.infinity.movieapp.extensions.showToast
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.concurrent.Flow
import java.util.concurrent.TimeUnit


//RxDI Task #6
@AndroidEntryPoint
class RxKotActivity : AppCompatActivity() {

    private val TAG: String = "MyRxKots"
    private val rxKotViewModel: RxKotViewModel by viewModels()
    private var compositeDisposable = CompositeDisposable() // can't use for long run task

    private lateinit var binding: ActivityRxKotBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRxKotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //good sample for ROOM db https://www.section.io/engineering-education/comparing-rxkotlin-to-kotlin-coroutines/
        callGetAllRepo()
        //callGetAllJobs()
    }

    private fun callGetAllRepo() {
        val username = "That1guy17"
        compositeDisposable += rxKotViewModel.getAllRepoData(username)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { repoDataList ->
                    setupUi(repoDataList)
                    Log.d(TAG, "addingDetails: Completed")
                    showToast("Completed")
                },
                onError = { error ->
                    setupUi(null)
                    Log.d(TAG, "addingDetails: Error " + error)
                    showToast("Error")
                }
            )
    }

    private fun callGetAllJobs() {
        val selectedDate = "2021-07-19"
        compositeDisposable += rxKotViewModel.getAllTempJobs(selectedDate)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .delay(100,TimeUnit.SECONDS)
            .retry(100)
            .subscribeBy(
                onSuccess = { repoDataList ->
                    setupUiTempJobs(repoDataList)
                    Log.d(TAG, "addingDetails: Completed")
                    showToast("Completed")
                },
                onError = { error ->
                    setupUiTempJobs(null)
                    Log.d(TAG, "addingDetails: Error " + error.stackTraceToString())
                    showToast("Error")
                }
            )
    }

    private fun setupUiTempJobs(jobData: List<Data>?) {
        if (jobData != null) {
            for (myJob in jobData) {
                Log.d(TAG, "setupUi: " + myJob.status)
            }
            binding.textView.setText("Success JobData : " + jobData.size)
        } else {
            binding.textView.setText("No JobData")
        }

        binding.progressBar.visibility = View.GONE
    }

    @SuppressLint("SetTextI18n")
    private fun setupUi(repoDataList: List<GitHubData>?) {
        if (repoDataList != null) {
            for (GitHubData in repoDataList) {
                Log.d(TAG, "setupUi: " + GitHubData.name)
            }
            binding.textView.setText("Success GitData : " + repoDataList.size)
        } else {
            binding.textView.setText("No GitData")
        }

        binding.progressBar.visibility = View.GONE
    }


    //Clear all your disposables
    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

}