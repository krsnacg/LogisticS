package com.example.logistics.ui.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.logistics.R
import com.example.logistics.model.ReferralGuide

@Composable
fun ReferralGuideListScreen(
    navController: NavController,
    productViewModel: ProductViewModel,
    referralViewModel: ReferralViewModel
) {

    val isLoading by referralViewModel.isLoading.collectAsState()
    val referralGuideList by referralViewModel.referralList.collectAsState()
    val pendingList by referralViewModel.pendingList.collectAsState()

    LaunchedEffect(Unit) {
        referralViewModel.getAllReferralGuides()
        //TODO: Get all pending orders for referrals
    }

    Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Column {
            PendingList(
                pendingList = pendingList, // Change to actual pending data class
                onEditSelected = { index ->
                    referralViewModel.editPendingSelected(index)
                    navController.navigate("referralGuide")
                }
            )
            Spacer(Modifier.height(8.dp))
            HorizontalDivider(thickness = 2.dp)
            Spacer(Modifier.height(8.dp))
            ReferralGuideList(
                referralGuides = referralGuideList,
                onEditSelected = { }
            )
        }
        LoadingIndicator(isLoading)
    }

}

@Composable
fun ReferralGuideList(
    referralGuides: List<ReferralGuide>,
    onEditSelected: (Int) -> Unit
) {
    Text("Guias de remisión")
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        itemsIndexed(referralGuides) { index, referralGuide ->
            ReferralGuideCard(
                referralGuide = referralGuide,
                onEdit = { onEditSelected(index) }
            )
        }
    }
}

@Composable
fun PendingList(
    pendingList: List<ReferralGuide>, // Change to actual pending object data class
    onEditSelected: (Int) -> Unit
) {
    Text("Pendientes")
    if (pendingList.isNotEmpty()){
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            itemsIndexed(pendingList) { index, pendingItem ->
                PendingCard(
                    pendingItem = pendingItem,
                    onEdit = { onEditSelected(index) }
                )
            }
        }
    } else {
        Text(
            text = "No existen solicitudes pendientes",
            fontSize = 16.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun PendingCard(
    modifier: Modifier = Modifier,
    pendingItem: ReferralGuide, // Change to actual pending data class
    onEdit: () -> Unit
) {
    Card(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(modifier = Modifier.padding(end = 8.dp)) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = ""
                )
            }
            Text(
                text = "Solicitud Pendiente",
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(1f)
            )
            Box(modifier = Modifier.padding(start = 8.dp)) {
                IconButton(onClick = onEdit) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(R.string.edit_button)
                    )
                }
            }
        }
    }
}

@Composable
fun ReferralGuideCard(
    modifier: Modifier = Modifier,
    referralGuide: ReferralGuide,
    onEdit: () -> Unit
) {
    Card(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(modifier = Modifier.padding(end = 8.dp)) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = ""
                )
            }
            Text(
                text = referralGuide.idguiaremision + " - " +
                        referralGuide.idempleado + " - " +
                        referralGuide.idpedido + " - " +
                        formatDateTimeLegacy(referralGuide.fecha_envio),
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(1f)
            )
        }
    }
}