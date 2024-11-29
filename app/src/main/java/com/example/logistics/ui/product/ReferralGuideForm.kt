package com.example.logistics.ui.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.logistics.R
import com.example.logistics.model.ReferralGuide
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ReferralGuideForm(
    referralGuide: ReferralGuide,
    isFormValid: Boolean,
    onDateChange: (String) -> Unit,
    onCancelClick: () -> Unit,
    onSaveClick: () -> Unit,
) {
    Box {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = referralGuide.idguiaremision,
                    onValueChange = {},
                    enabled = false,
                    label = { Text(text = "Código") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = referralGuide.idempleado,
                    onValueChange = {},
                    enabled = false,
                    label = { Text(text = "ID del empleado") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = referralGuide.idpedido,
                    onValueChange = {},
                    enabled = false,
                    label = { Text(text = "ID del pedido") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                DatePickerField(
                    selectedDate = referralGuide.fecha_envio,
                    isEnabled = true,
                    onDateSelected = onDateChange
                )
                Spacer(modifier = Modifier.height(16.dp))

            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = onCancelClick) {
                    Icon(imageVector = Icons.Default.Clear, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = stringResource(R.string.cancel_button_desc), fontSize = 14.sp)
                }
                Button(
                    onClick = onSaveClick,
                    enabled = isFormValid
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = stringResource(R.string.save_button), fontSize = 14.sp)
                }
            }

        }

    }
}

fun formatDateTimeLegacy(isoString: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    val date = inputFormat.parse(isoString)
    return outputFormat.format(date)
}