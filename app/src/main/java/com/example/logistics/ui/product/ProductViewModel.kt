package com.example.logistics.ui.product

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.logistics.ProductApplication
import com.example.logistics.data.ProductRepository
import com.example.logistics.model.Batch
import com.example.logistics.model.Product
import com.example.logistics.service.ProductAPI
import com.example.logistics.model.ProductRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import com.example.logistics.data.CategoryChart
import com.example.logistics.data.ExpiringProduct
import com.example.logistics.data.LowerStockProduct
import com.example.logistics.model.BatchRequest
import com.example.logistics.service.ProductApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Locale


class ProductViewModel(private val productRepository: ProductRepository) : ViewModel() {


    private val _lowerStockProduct = MutableStateFlow<LowerStockProduct?>(null)
    val lowerStockProduct: StateFlow<LowerStockProduct?> get() = _lowerStockProduct
    private val _expiringProduct = MutableStateFlow<ExpiringProduct?>(null)
    val expiringProduct: StateFlow<ExpiringProduct?> get() = _expiringProduct
    private val _quantityProducts = MutableStateFlow<Int?>(null)
    val quantityProducts: StateFlow<Int?> get() = _quantityProducts
    private val _categoriesQuantity = MutableStateFlow<List<CategoryChart>>(emptyList())
    val categoriesQuantity: StateFlow<List<CategoryChart>> get() = _categoriesQuantity
    private val _totalCategorias = MutableStateFlow<Int?>(null)
    val totalCategorias: StateFlow<Int?> get() = _totalCategorias
    private val _totalLotes = MutableStateFlow<Int?>(null)
    val totalLotes: StateFlow<Int?> get() = _totalLotes
    private val productService: ProductApiService

    val productos = mutableStateListOf<Product>()
    var selectedProduct by mutableStateOf<Product?>(value = null)

    val products: MutableState<List<ProductRequest>> = mutableStateOf(emptyList())
    val lots: MutableState<List<Batch>> = mutableStateOf(emptyList())

    private val _productos = MutableLiveData<List<Product>>()
    val productoss: LiveData<List<Product>> get() = _productos

    private val _lotes = MutableLiveData<List<Batch>>()
    val lotes: LiveData<List<Batch>> get() = _lotes

    private val _codeState = MutableStateFlow(value = "")
    val codeState: StateFlow<String> = _codeState.asStateFlow()

    private var batchCode: String = ""

    var product by mutableStateOf(value = Product())
        private set

    var batchList = mutableStateListOf<Batch>()
        private set

    var batch by mutableStateOf(value = Batch())
        private set


    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:9000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        productService = retrofit.create(ProductApiService::class.java)
        getLowerStockProduct()
        getExpiringProduct()
        getQuantityProducts()
        getQuantityByCategory()
        getTotalCategorias()
        getTotalLotes()
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as ProductApplication)
                val productRepository = application.container.productRepository
                ProductViewModel(productRepository = productRepository)
            }
        }
    }


    fun getLowerStockProduct() {
        viewModelScope.launch {
            try {
                var product = productService.getLowerStockProduct().body()
                _lowerStockProduct.value = productService.getLowerStockProduct().body();

            }catch (e: Exception) {
                Log.e("ERROR", e.message.toString())
            }
        }
    }

    fun getExpiringProduct() {
        viewModelScope.launch {
            try {
                var product = productService.getExpiringProduct().body();
                _expiringProduct.value = productService.getExpiringProduct().body()
            }catch (e: Exception) {
                Log.e("ERROR", e.message.toString())
            }
        }
    }

    fun getQuantityProducts() {
        viewModelScope.launch {
            try {
                var quantity = productService.getTotalProducts();
                _quantityProducts.value = productService.getTotalProducts().body()
                Log.e("OBTENER CANTIDAD","GAAAAAAAAAAAAAAAAA ${quantity}")
            }catch (e: Exception) {
                Log.e("CANTIDAD ERROR", e.message.toString())
            }
        }
    }

    fun getTotalCategorias() {
        viewModelScope.launch {
            try {
                _totalCategorias.value = productService.getTotalCategorias().body()

            }catch (e: Exception) {
                Log.e("CANTIDAD ERROR", e.message.toString())
            }
        }
    }

    fun getTotalLotes() {
        viewModelScope.launch {
            try {
                _totalLotes.value = productService.getTotalLotes().body()

            }catch (e: Exception) {
                Log.e("CANTIDAD ERROR", e.message.toString())
            }
        }
    }

    fun getQuantityByCategory() {
        viewModelScope.launch {
            try {
                _categoriesQuantity.value = productService.getQuantityByCategory().body()!!

            }catch (e: Exception) {
                Log.e("CANTIDAD ERROR", e.message.toString())
            }
        }
    }

    fun updateProduct(producto: Product) {
        viewModelScope.launch {
            // Implementa la lógica de actualizar el producto (solicitud PUT)
            // productos.addAll(/**/)
        }
    }

    fun onProductSelected(producto: Product) {
        selectedProduct = producto
    }

    fun listarProductos() {
        viewModelScope.launch {
            try {
                val response = ProductAPI.api.listProducts()
                if (response.isSuccessful) {
                    //_productos.value = response.body()
                } else {
                    Log.e("Error", "Error al listar productos: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("Exception", e.message.toString())
            }
        }
    }

    fun saveProductAndBatches() {
        viewModelScope.launch {
            try {
                val response = productRepository.saveProductAndBatches(buildProductSaveRequest())
                if (response.status == "success") {
                    Log.d("Success", "Producto guardado exitosamente $response")
                } else {
                    Log.e("Error", "Error al guardar producto: $response")
                }
            } catch (e: Exception) {
                Log.e("Exception", e.message.toString())
            }
        }
    }

    fun getProductLastCode() {
        viewModelScope.launch {
            try {
//                _codeState.value = incrementCode(productRepository.getLastCodeProducto())
//                Log.d("viewModelGetCodeSuccess", _codeState.value)
                product = product.copy(codigo = incrementCode(productRepository.getProductLastCode()))
                Log.d("viewModelGetCodeSuccess", product.codigo)
            } catch (e: IOException) {
                product = product.copy(codigo = "EXCEPTION ERROR")
                Log.d("viewModelGetCodeError", e.message.toString())
            }

        }
    }

    fun getBatchLastCode() {
        viewModelScope.launch {
            try {
                batchCode = productRepository.getBatchLastCode()
                Log.d("viewModelGetCodeSuccess", batchCode)
                generateBatches(batchCode)
            } catch (e: IOException) {
                // batch = batch.copy(codigoLote = "ERROR")
                Log.d("viewModelGetCodeError", e.message.toString())
            }
        }
    }

    private fun generateBatches(batchCode: String) {
        var code = batchCode
        val stock = product.cantidad.toInt()
        val batchCount = (stock / 200) + if (stock % 200 > 0) 1 else 0
        Log.d("BatchGenerationStock", stock.toString())
        Log.d("BatchGenerationCount", batchCount.toString())

        if (batchList.isNotEmpty()) batchList.clear()

        for (i in 1..batchCount) {
            code = incrementCode(code)
            batchList.add(
                Batch(
                    codigoLote = code,
                    nombreProducto = product.nombreProducto,
                    stock = "200"
                )
            )
        }
        Log.d("BatchGeneration", batchList[0].codigoLote)
        Log.d("BatchGenerationSize", batchList.size.toString())
    }

    private fun buildProductSaveRequest(): ProductRequest {
        val batchRequestList = batchList.map { batch ->
            BatchRequest(
                code = batch.codigoLote,
                operativeStatus = batch.estadoOperativo,
                availabilityState = batch.estadoDisponibilidad,
                securityState = batch.estadoSeguridad,
                stock = batch.stock.toInt(),
                expiredDate = convertDateFormat(batch.fechaVencimiento)
            )
        }
        return ProductRequest(
            code = product.codigo,
            category = product.categoria,
            type = product.tipo,
            name = product.nombreProducto,
            price = product.precio.toDouble(),
            concentration = product.concentracion,
            presentation = product.presentacion,
            description = product.descripcion,
            batches = batchRequestList
        )
    }

    private fun incrementCode(code: String): String {
        val prefix = code.take(2)
        val number = code.drop(2).toIntOrNull() ?:0
        val newNumber = number + 1
        return "$prefix${newNumber.toString().padStart(4,'0')}"
    }

    fun convertDateFormat(inputDate: String): String {
        val inputFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return try {
            val date = inputFormat.parse(inputDate)
            outputFormat.format(date)
        } catch (e: Exception) {
            inputDate // retorna la fecha original si hay error
        }
    }

    fun updateProductName(productName: String) {
//        _product.update { currentState ->
//            currentState.copy(nombreProducto = field)
//        }
        product = product.copy(nombreProducto = productName)
    }

    fun updateProductCategory(productCategory: String) {
        product = product.copy(categoria = productCategory)
    }

    fun updateProductType(productType: String) {
        product = product.copy(tipo = productType)
    }

    fun updateProductPrice(productPrice: String) {
        product = product.copy(precio = productPrice)
    }

    fun updateProductConcentration(productConcentration: String) {
        product = product.copy(concentracion = productConcentration)
    }

    fun updateProductPresentation(productPresentation: String) {
        product = product.copy(presentacion = productPresentation)
    }

    fun updateProductDescription(productDescription: String) {
        product = product.copy(descripcion = productDescription)
    }

    fun updateProductQuantity(productQuantity: String) {
        product = product.copy(cantidad = productQuantity)
    }


    fun updateBatchOperativeState(index: Int, batchOperativeState: String) {
        // val index = batchList.indexOfFirst { it.codigoLote == id }
        if (index != -1) {
            batchList[index] = batchList[index].copy(estadoOperativo = batchOperativeState)
        }
    }

    fun updateBatchAvailabilityState(index: Int, batchAvailabilityState: String) {
        if (index != -1) {
            batchList[index] = batchList[index].copy(estadoDisponibilidad = batchAvailabilityState)
        }
    }

    fun updateBatchSecurityState(index: Int, batchSecurityState: String) {
        if (index >= 0) {
            batchList[index] = batchList[index].copy(estadoSeguridad = batchSecurityState)
        }
    }

    fun updateBatchDate(index: Int, batchDate: String) {
        if (index >= 0) {
            batchList[index] = batchList[index].copy(fechaVencimiento = batchDate)
        }
    }

}