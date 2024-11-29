package com.example.logistics.ui.product

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController

@Composable
fun ReferralGuideScreen(
    navController: NavController,
    referralViewModel: ReferralViewModel
) {
    val isLoading by referralViewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        referralViewModel.getLastReferralCode()
    }

    Box {
        ReferralGuideForm(
            referralGuide = referralViewModel.referralGuide,
            isFormValid = referralViewModel.isReferralGuideComplete(),
            onDateChange = { referralViewModel.updateDate(it) },
            onCancelClick = { navController.popBackStack() },
            onSaveClick = {
                referralViewModel.saveReferralGuide()
                navController.popBackStack()
            }
        )
        LoadingIndicator(isLoading)
    }
}