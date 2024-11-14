package com.example.logistics.ui.product


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
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

    LaunchedEffect(saveState) {
        saveState?.let {
            showDialog = true
        }
    }

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn {
            itemsIndexed(viewModel.batchList) { index, batch ->
                BatchForm(
                    batch = batch,
                    isEditable = true,
                    onOperativeStateChange = {
                        viewModel.updateBatchOperativeState(index, it)
                    },
                    onAvailabilityStateChange = {
                        viewModel.updateBatchAvailabilityState(index, it)
                    },
                    onSecurityStateChange = { viewModel.updateBatchSecurityState(index, it) },
                    onDateChange = { viewModel.updateBatchDate(index, it) }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Button(onClick = { navController.popBackStack() }) {
                Text(text = stringResource(R.string.cancel_button_desc))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = {
                    viewModel.saveProductAndBatches()
                    viewModel.getProductLastCode()
                },
                enabled = viewModel.areAllBatchesComplete()
            ) {
                Text("Guardar")
            }
        }

        if (showDialog) {
            AlertDialogExample(
                onDismissRequest = {
                    showDialog = false
                    viewModel.resetSaveState()
                },
                onConfirmation = {
                    showDialog = false
                    viewModel.resetSaveState()
                    if (saveState?.isSuccess == true) {
                        navController.popBackStack()
                        navController.popBackStack()
                    }
                },
                dialogTitle = if (saveState?.isSuccess == true) "Éxito" else "Error",
                dialogText = saveState?.getOrNull() ?: "Error al guardar producto",
                icon = if (saveState?.isSuccess == true) Icons.Default.Check else Icons.Default.Clear
            )
        }
    }
}
