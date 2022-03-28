package com.ceylonapz.weatherlive.utilities.rxkots

import javax.inject.Inject

//RxDI Task #3
class GitHubRepo @Inject constructor(private val api: GitHubClient) {
    fun getAllGitData(name: String) = api.getStarredRepositories(name)
    fun getAllJobs(date: String) = api.getAllJobs(date)
}