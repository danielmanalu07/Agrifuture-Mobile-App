package com.example.agrifuture.presentation.repository

import android.provider.ContactsContract.CommonDataKinds.Phone
import com.example.agrifuture.presentation.model.Customer

class CustomerRepository {
    val customers = mutableListOf(
        Customer(1, "Daniel", "daniel@gmail.com", "085762649422", "Tarutung", "daman12345", null, null)
    )

    var isLoggedIn = false

    fun login(email: String, password: String): Boolean {
        isLoggedIn = customers.any{ it.email == email && it.password == password }
        return isLoggedIn
    }

    fun register(name: String, email: String, phone: String, address: String, password: String): Boolean{
        if (customers.any{it.email == email && it.phone == phone}){
            return false
        }
        customers.add(Customer(2, name, email, phone, address, password, null, null))
        return true
    }
}