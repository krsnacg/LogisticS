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
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProductScreen(navController: NavController, viewModel: ProductViewModel ) {

    // val codigoState by viewModel.codeState.collectAsState()
    val productList by viewModel.filteredProductList.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    var expanded by rememberSaveable { mutableStateOf(false) }
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.toggleEditable(true)
        viewModel.getAllProducts()
    }

    Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Column {
            SearchBar(
                query = searchQuery,
                onQueryChange = { query ->
                    viewModel.updateSearchQuery(query)
                },
                onSearch = { expanded = false },
                active = false,
                onActiveChange = { expanded = it },
                placeholder = { Text("Buscar producto") },
                leadingIcon = { Icon(Icons.Default.Menu, contentDescription = null) },
                trailingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                modifier = Modifier
                    .padding(bottom = 8.dp),
                content = { }
            )
            // Spacer(modifier = Modifier.height(8.dp))
            ProductList(
                products = productList,
                onEditSelected = { index ->
                    viewModel.editProductSelected(index)
                    viewModel.toggleEditable(false)
                    navController.navigate("products/addProduct")
                }
            )
        }
        LoadingIndicator(isLoading = isLoading)
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

