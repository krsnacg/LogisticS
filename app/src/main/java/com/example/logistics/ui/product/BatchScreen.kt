package com.example.logistics.ui.product


import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.logistics.R

@Composable
fun BatchScreen(
    navController: NavController,
    viewModel: ProductViewModel = viewModel(),
) {

    var showDialog by remember { mutableStateOf(false) }
    val saveState by viewModel.saveState.collectAsState()
    val editableState by viewModel.editableState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    var route = remember { mutableStateOf("") }
    Log.d("BatchScreen", "isLoading: $isLoading")

    LaunchedEffect(saveState) {
        saveState?.let {
            showDialog = true
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn {
            itemsIndexed(viewModel.batchList) { index, batch ->
                HorizontalDivider(thickness = 2.dp)
                Text(text = "Lote ${index + 1}")
                BatchForm(
                    batch = batch,
                    isEditable = editableState,
                    onOperativeStateChange = {
                        viewModel.updateBatchOperativeState(index, it)
                    },
                    onAvailabilityStateChange = {
                        viewModel.updateBatchAvailabilityState(index, it)
                    },
                    onSecurityStateChange = { viewModel.updateBatchSecurityState(index, it) },
                    onDateChange = { viewModel.updateBatchDate(index, it) }
                )
                Spacer(modifier = Modifier.height(8.dp))

            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomEnd)
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.End
        ) {
//            Button(onClick = { navController.popBackStack() }) {
//                Text(text = stringResource(R.string.cancel_button_desc))
//            }
//            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = {
                    if (editableState) {
                        viewModel.saveProductAndBatches()
                        viewModel.getProductLastCode()
                        route.value = "products/addProduct"
                    }
                    else {
                        viewModel.updateProductAndBatches()
                        viewModel.getAllProducts()
                        route.value = "products/editProduct"
                    }
                    viewModel.toggleEditable(true)
                },
                enabled = viewModel.areAllBatchesComplete()
            ) {
                Icon(imageVector = Icons.AutoMirrored.Default.Send, contentDescription = null)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = stringResource(R.string.save_button))
            }
        }

        if (showDialog) {
            AlertDialogExample(
                onDismissRequest = {
//                    showDialog = false
//                    viewModel.resetSaveState()
//                    if (saveState?.isSuccess == true) {
//                        navController.navigate(route.value) {
//                            popUpTo(route.value) { inclusive = true }
//                            launchSingleTop = true
//                        }
//                    }
                },
                onConfirmation = {
                    showDialog = false
                    viewModel.resetSaveState()
                    if (saveState?.isSuccess == true) {
                        navController.navigate(route.value) {
                            popUpTo(route.value) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                },
                dialogTitle = if (saveState?.isSuccess == true) "Éxito" else "Error",
                dialogText = saveState?.getOrNull() ?: "Error al guardar producto",
                icon = if (saveState?.isSuccess == true) Icons.Default.Check else Icons.Default.Clear
            )
        }
        LoadingIndicator(isLoading = isLoading)
    }
}
