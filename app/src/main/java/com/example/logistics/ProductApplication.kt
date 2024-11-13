package com.example.logistics

import android.app.Application
import com.example.logistics.data.DefaultProductContainer
import com.example.logistics.data.ProductContainer

class ProductApplication: Application() {
    lateinit var container: ProductContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultProductContainer()
    }
}