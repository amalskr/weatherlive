package com.ceylonapz.mykots.test

class TestModel(var name: String) {
    var id: Int = -1

    init {
        println("testModel $name")
    }

    constructor(address: String, phone: Int) : this(address) {
        this.id = phone
    }

}