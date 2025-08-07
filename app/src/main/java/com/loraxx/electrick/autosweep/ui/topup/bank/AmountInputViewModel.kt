package com.loraxx.electrick.autosweep.ui.topup.bank

import androidx.lifecycle.ViewModel
import com.loraxx.electrick.autosweep.ui.topup.TopUpItem
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel(assistedFactory = AmountInputViewModel.Factory::class)
class AmountInputViewModel @AssistedInject constructor(
    @Assisted val selectedTopUpItem: TopUpItem,
) : ViewModel() {
    private val _uiState = MutableStateFlow(AmountInputUiState())
    val uiState: StateFlow<AmountInputUiState> = _uiState.asStateFlow()

    @AssistedFactory
    interface Factory {
        fun create(selectedBank: TopUpItem): AmountInputViewModel
    }

    fun submitTransaction() {
        //Submit transaction details
    }
}
