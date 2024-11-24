package com.example.agrifuture.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.example.agrifuture.presentation.model.Customer
import com.example.agrifuture.presentation.repository.CustomerRepository

class CustomerVM: ViewModel() {
    val customerRepository = CustomerRepository()

    fun getCustomers() : List<Customer>{
        return customerRepository.customers
    }
}