package com.example.logistics.ui.product

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    val saveState by viewModel.saveState.collectAsState()

    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (editableState) {
            viewModel.toggleEditable(toggle = true)
            viewModel.getProductLastCode()
            viewModel.getCategoryAndForm()
        } else {
            viewModel.getProductWithBatches()
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
            isProductUpdatable = viewModel.isProductUpdatable(),
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
            onUpdateClick = {
                viewModel.updateProductAndBatches()
                showDialog = true
            },
            onSaveClick = {
                viewModel.getBatchLastCode()
                if (editableState)
                    navController.navigate("products/batchScreen")
                else
                    navController.navigate("products/editProduct/batchScreen")
            }
        )
        if (showDialog && !editableState) {
            AlertDialogExample(
                onDismissRequest = {},
                onConfirmation = {
                    showDialog = false
                    viewModel.resetSaveState()
                    if (saveState?.isSuccess == true) {
                        navController.navigate("products/editProduct") {
                            popUpTo("products/editProduct") { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                },
                dialogTitle = if (saveState?.isSuccess == true) "Éxito" else "Error",
                dialogText = saveState?.getOrNull() ?: "Error al actualizar producto",
                icon = if (saveState?.isSuccess == true) Icons.Default.Check else Icons.Default.Clear
            )
        }
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
