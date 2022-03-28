package com.ceylonapz.weatherlive.utilities.rxkots

import androidx.lifecycle.ViewModel
import com.ceylonapz.weatherlive.model.Data
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Single
import javax.inject.Inject

//RxDI Task #5
@HiltViewModel
class RxKotViewModel @Inject constructor(private val repository: GitHubRepo) : ViewModel() {
    fun getAllRepoData(userName: String): Single<List<GitHubData>> {
        return repository.getAllGitData(userName)
    }

    fun getAllTempJobs(selecetdDate: String): Single<List<Data>> {
        return repository.getAllJobs(selecetdDate)
    }
}