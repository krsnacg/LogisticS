package com.example.logistics.ui.product

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.logistics.model.Product
import com.example.logistics.model.ProductRequest

@Composable
fun AddProductScreen(navController: NavController, viewModel: ProductViewModel) {
    val codigoState by viewModel.codeState.collectAsState()
    //val productoState by viewModel.product.collectAsState()

    ProductForm(
        code = codigoState,
        product = viewModel.product,
        optionName = "Registrar",
        onNameChange = {
            Log.d("TEST",it)
            viewModel.updateProductName(it)
        },
        onCategoryChange = { viewModel.updateProductCategory(it) },
        onTypeChange = { viewModel.updateProductType(it) },
        onPriceChange = { viewModel.updateProductPrice(it) },
        onConcentrationChange = { viewModel.updateProductConcentration(it) },
        onPresentationChange = { viewModel.updateProductPresentation(it) },
        onDescriptionChange = { viewModel.updateProductDescription(it) },
        onQuantityChange = { viewModel.updateProductQuantity(it) },
        onCancelClick = { navController.popBackStack() },
        onSaveClick = {
            viewModel.getBatchLastCode()
            navController.navigate("lote")
        },
        buttonText = "Generar Lotes"
    )
}