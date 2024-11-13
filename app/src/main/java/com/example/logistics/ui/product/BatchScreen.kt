package com.example.logistics.ui.product


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn {
            itemsIndexed(viewModel.batchList) { index, batch ->
                BatchForm(
                    batch = batch,
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
            Button(onClick = {
                viewModel.saveProductAndBatches()
                viewModel.getProductLastCode()
            }) {
                Text("Guardar")
            }
        }
    }
}
