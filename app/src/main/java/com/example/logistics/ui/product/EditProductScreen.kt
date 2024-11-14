package com.example.logistics.ui.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.logistics.R
import com.example.logistics.model.Product

@Composable
fun EditProductScreen(navController: NavController, viewModel: ProductViewModel = viewModel()) {

    // val codigoState by viewModel.codeState.collectAsState()
    val productList by viewModel.productList.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ProductList(
            products = productList,
            onEditSelected = { index ->
                viewModel.editProductSelected(index)
                navController.navigate("addProduct")
            }
        )
    }
}

@Composable
fun ProductList(
    products: List<Product>,
    onEditSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        itemsIndexed(products) { index, product ->
            ProductCard(
                product = product,
                onEdit = { onEditSelected(index) }
            )
        }
    }

}

@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    product: Product,
    onEdit: () -> Unit
) {
    Card(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = ""
                )
            }
            Text(
                text = product.nombreProducto + " - " +
                        product.concentracion + " - " +
                        product.presentacion,
                fontWeight = FontWeight.SemiBold
            )
            Box {
                IconButton(
                    onClick = onEdit
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(R.string.edit_button)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductPreview() {
    val productList = listOf(
        Product(
            nombreProducto = "PRODUCTO 1",
            concentracion = "50 mg",
            presentacion = "Caja"
        ),
        Product(
            nombreProducto = "PRODUCTO 2",
            concentracion = "100 mg",
            presentacion = "Botella"
        ),
        Product(
            nombreProducto = "PRODUCTO 3",
            concentracion = "200 mg",
            presentacion = "Blister"
        )
    )
}

