package com.example.logistics.ui.product

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


@Composable
fun AddProductScreen(navController: NavController, viewModel: ProductViewModel = viewModel()) {
    // val codigoState by viewModel.codeState.collectAsState()
    // val productoState by viewModel.product.collectAsState()
    val editableState by viewModel.editableState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val categoryList by viewModel.categoryList.collectAsState()
    val formList by viewModel.formList.collectAsState()

    LaunchedEffect(Unit) {
        if (editableState) {
            viewModel.toggleEditable(toggle = true)
            viewModel.getProductLastCode()
            viewModel.getCategoryAndForm()
        }
    }

    Box {
        ProductForm(
            // code = codigoState,
            product = viewModel.product,
            categoryList = categoryList,
            formList = formList,
            isEditable = editableState,
            isFormValid = viewModel.isProductComplete(),
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
                navController.navigate("products/addProduct/batchScreen")
            }
        )
        LoadingIndicator(isLoading = isLoading)
    }
}

@Preview(showBackground = true)
@Composable
fun ProductFormPreview() {
    val productNavController = rememberNavController()
    val productViewModel: ProductViewModel = viewModel()
    AddProductScreen(
        productNavController,
        productViewModel
    )
}
