package com.example.logistics.ui.product

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.logistics.ProductApplication
import com.example.logistics.data.ProductRepository
import com.example.logistics.model.Batch
import com.example.logistics.model.Product
import com.example.logistics.model.ProductRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.text.SimpleDateFormat
import java.util.Locale

import com.example.logistics.data.CategoryChart
import com.example.logistics.data.ExpiringProduct
import com.example.logistics.data.LowerStockProduct
import com.example.logistics.model.BatchRequest
import com.example.logistics.service.ApiConfig
import com.example.logistics.service.ProductApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ProductViewModel(private val productRepository: ProductRepository) : ViewModel() {

//    private val _codeState = MutableStateFlow(value = "")
//    val codeState: StateFlow<String> = _codeState.asStateFlow()
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

    private val _lastCodeProduct = MutableStateFlow<String?>(null)
    val lastCodeProduct: StateFlow<String?> get() = _lastCodeProduct

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _saveState = MutableStateFlow<Result<String>?>(null)
    val saveState: StateFlow<Result<String>?> = _saveState.asStateFlow()
    private val _editableState = MutableStateFlow(value = true)
    val editableState: StateFlow<Boolean> = _editableState.asStateFlow()

    private val _productList = MutableStateFlow<List<Product>>(value = emptyList())
//    val productList: StateFlow<List<Product>> = _productList.asStateFlow()

    private val _categoryList = MutableStateFlow<List<String>>(value = emptyList())
    val categoryList: StateFlow<List<String>> = _categoryList.asStateFlow()
    private val _formList = MutableStateFlow<List<String>>(value = emptyList())
    val formList: StateFlow<List<String>> = _formList.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    val filteredProductList = _searchQuery
        .combine(_productList) { query, products ->
            val trimmedQuery = query.trim()
            if (trimmedQuery.isEmpty()) {
                products
            } else {
                products.filter {
                    it.nombreProducto.contains(query, ignoreCase = true)
                }
            }
        }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private var batchCode: String = ""

    var product by mutableStateOf(value = Product())
        private set

    var batchList = mutableStateListOf<Batch>()
        private set

//    var batch by mutableStateOf(value = Batch())
//        private set

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("${ApiConfig.BASE_URL}:9000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        productService = retrofit.create(ProductApiService::class.java)
        getLowerStockProduct()
        getExpiringProduct()
        getQuantityProducts()
        getQuantityByCategory()
        getTotalCategorias()
        getTotalLotes()
        getLastCodeProduct()
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

    fun getLastCodeProduct() {
        viewModelScope.launch {
            try {
                _lastCodeProduct.value = productService.getLastProductCode().body()

            }catch (e: Exception) {
                Log.e("ERROR", e.message.toString())
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


    fun getAllProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = productRepository.getAllProducts()
                if (response.status == "success" && response.data?.isNotEmpty() == true) {
                    _productList.value = response.data.sortedBy {
                        it.codigo.filter { char -> char.isDigit() }.toInt()
                    }
                } else {
                    Log.e("Error", "Error obtaining products: ${response.message}")
                }
            } catch (e: Exception) {
                Log.e("ExceptionGettingProducts", e.message.toString())
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun saveProductAndBatches() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
//                val response: ApiResponse<String> = if (_editableState.value)
//                    productRepository.saveProductAndBatches(buildProductSaveRequest())
//                else
//                    productRepository.updateProductAndBatches(buildProductSaveRequest())
                val response = productRepository.saveProductAndBatches(buildProductSaveRequest())
                if (response.status == "success") {
                    _saveState.value = Result.success("Producto guardado exitosamente")
                    resetProduct()
                    Log.d("Success", "Producto guardado exitosamente $response")
                } else {
                    _saveState.value = Result.failure(Exception("Error al guardar el producto"))
                    Log.e("Error", "Error al guardar producto: $response")
                }
            } catch (e: Exception) {
                _saveState.value = Result.failure(e)
                Log.e("ExceptionAtSavingProduct", e.message.toString())
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateProductAndBatches() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = productRepository.updateProductAndBatches(buildProductSaveRequest())
                if (response.status == "success") {
                    _saveState.value = Result.success("Producto actualizado exitosamente")
                    resetProduct()
                    Log.d("Success", "Producto actualizado exitosamente $response")
                } else {
                    _saveState.value = Result.failure(Exception("Error al actualizar el producto"))
                    Log.e("Error", "Error al actualizar producto: $response")
                }
            } catch (e: Exception) {
                _saveState.value = Result.failure(e)
                Log.e("ExceptionAtUpdatingProduct", e.message.toString())
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getProductWithBatches() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = productRepository.getProductWithBatches(product.codigo)
                if (response.status == "success") {
                    if (batchList.isNotEmpty()) batchList.clear()
                    response.data?.batches?.forEach { batch ->
                        batchList.add(Batch(
                            codigoLote = batch.code,
                            estadoOperativo = batch.operativeStatus,
                            estadoDisponibilidad = batch.availabilityState,
                            estadoSeguridad = batch.securityState,
                            stock = batch.stock.toString(),
                            fechaVencimiento = batch.expiredDate
                        ))
                    }
                    Log.d("Success", "Product y Lotes asociados obtenidos $response")
                } else {
                    Log.e("Error", "Error al cargar producto y lotes: $response")
                }
            } catch (e: IOException) {
                Log.e("getProductWithBatches", e.message.toString())
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getProductLastCode() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
//                _codeState.value = incrementCode(productRepository.getLastCodeProducto())
//                Log.d("viewModelGetCodeSuccess", _codeState.value)
                if (isProductNotEmpty()) resetProduct()
                product = product.copy(codigo = incrementCode(productRepository.getProductLastCode()))
                Log.d("viewModelGetCodeSuccess", product.codigo)
            } catch (e: IOException) {
                product = product.copy(codigo = "EXCEPTION ERROR")
                Log.d("viewModelGetCodeError", e.message.toString())
            } finally {
                _isLoading.value = false
            }

        }
    }

    fun getBatchLastCode() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                batchCode = productRepository.getBatchLastCode()
                Log.d("viewModelGetCodeSuccess", batchCode)
                generateBatches(batchCode)
            } catch (e: IOException) {
                // batch = batch.copy(codigoLote = "ERROR")
                Log.d("viewModelGetCodeError", e.message.toString())
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getCategoryAndForm(){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val categoryRes = productRepository.getCategories()
                val formRes = productRepository.getFormas()
                if (categoryRes.status == "success" && categoryRes.data?.isNotEmpty() == true)
                    _categoryList.value = categoryRes.data
                if (formRes.status == "success" && formRes.data?.isNotEmpty() == true)
                    _formList.value = formRes.data
                Log.d("viewModelGetCat&EtcSuccess", _categoryList.toString())
            } catch (e: IOException) {
                Log.d("viewModelGetCatError", e.message.toString())
            } finally {
                _isLoading.value = false
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

    fun resetProduct() {
        product = Product()
    }

    fun resetBatches() {
        if (batchList.isNotEmpty())
            batchList.clear()
    }

    fun resetSaveState() {
        _saveState.value = null
    }

    fun toggleEditable(toggle: Boolean) {
        _editableState.value = toggle
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    private fun incrementCode(code: String): String {
        val regex = Regex(pattern = "([A-Z]+)(\\d+)")
        val matchResult = regex.matchEntire(code)
        if (matchResult != null) {
            val (prefix, numberStr) = matchResult.destructured
            val number = numberStr.toIntOrNull()

            if (number != null) {
                val newNumber = number + 1
                val newNumberStr = newNumber.toString().padStart(numberStr.length,'0')
                return "$prefix$newNumberStr"
            }
            else {
                Log.d("IncrementCode","No se pudo convertir la parte numérica del código: $code")
            }
        }
        throw IllegalArgumentException("El código no tiene el formato esperado: $code")
//        val maxCodeLength = 6
//        val prefix = code.take(prefixLength)
//        val number = code.drop(prefixLength).toIntOrNull() ?:0
//        val newNumber = number + 1
//        return "$prefix${newNumber.toString().padStart(maxCodeLength - prefixLength,'0')}"
    }

    private fun convertDateFormat(inputDate: String): String {
        val inputFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return try {
            val date = inputFormat.parse(inputDate)
            outputFormat.format(date)
        } catch (e: Exception) {
            inputDate // retorna la fecha original si hay error
        }
    }

    fun isProductComplete(): Boolean {
        return isProductUpdatable() &&
                product.cantidad.isNotBlank()
    }

    fun isProductUpdatable(): Boolean {
        return product.codigo.isNotBlank() &&
                product.nombreProducto.isNotBlank() &&
                product.categoria.isNotBlank() &&
                product.tipo.isNotBlank() &&
                product.precio.isNotBlank() &&
                product.concentracion.isNotBlank() &&
                product.presentacion.isNotBlank() &&
                product.descripcion.isNotBlank()
    }

    fun isProductModified(): Boolean {
        return product.codigo.isNotBlank() && (
                product.nombreProducto.isNotBlank() ||
                        product.categoria.isNotBlank() ||
                        product.tipo.isNotBlank() ||
                        product.precio.isNotBlank() ||
                        product.concentracion.isNotBlank() ||
                        product.presentacion.isNotBlank() ||
                        product.descripcion.isNotBlank() ||
                        product.cantidad.isNotBlank())
    }

    fun areAllBatchesComplete(): Boolean {
        return batchList.all { batch ->
            batch.codigoLote.isNotBlank() &&
                    batch.estadoOperativo.isNotBlank() &&
                    batch.estadoDisponibilidad.isNotBlank() &&
                    batch.estadoSeguridad.isNotBlank() &&
                    batch.nombreProducto.isNotBlank() &&
                    batch.stock.isNotBlank() &&
                    batch.fechaVencimiento.isNotBlank()
        }
    }

    private fun isProductNotEmpty(): Boolean {
        return product.codigo.isNotBlank() ||
                product.nombreProducto.isNotBlank() ||
                product.categoria.isNotBlank() ||
                product.tipo.isNotBlank() ||
                product.precio.isNotBlank() ||
                product.concentracion.isNotBlank() ||
                product.presentacion.isNotBlank() ||
                product.descripcion.isNotBlank() ||
                product.cantidad.isNotBlank()
    }

    fun editProductSelected(index: Int) {
        // Copiar el producto seleccionado, de la lista de productos a la variable product
//        product = _productList.value[index]
        product = filteredProductList.value[index]
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