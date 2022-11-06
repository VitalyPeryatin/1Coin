package com.finance_tracker.finance_tracker.presentation.add_account

import com.adeo.kviewmodel.KViewModel
import com.finance_tracker.finance_tracker.core.common.DecimalFormatType
import com.finance_tracker.finance_tracker.core.common.EventChannel
import com.finance_tracker.finance_tracker.core.common.toHexString
import com.finance_tracker.finance_tracker.data.repositories.AccountsRepository
import com.finance_tracker.finance_tracker.domain.models.Account
import com.finance_tracker.finance_tracker.domain.models.AccountColorData
import com.finance_tracker.finance_tracker.domain.models.Currency
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AddAccountViewModel(
    private val accountsRepository: AccountsRepository,
    private val _account: Account
): KViewModel() {

    private val account: Account? = _account.takeIf { _account != Account.EMPTY }

    private val _amountCurrencies = MutableStateFlow(Currency.list)
    val amountCurrencies = _amountCurrencies.asStateFlow()

    private val _types = MutableStateFlow(Account.Type.values().toList())
    val types = _types.asStateFlow()

    private val _colors = MutableStateFlow(emptyList<AccountColorData>())
    val colors = _colors.asStateFlow()

    private val _selectedColor = MutableStateFlow<AccountColorData?>(null)
    val selectedColor = _selectedColor.asStateFlow()

    private val _selectedType = MutableStateFlow(account?.type)
    val selectedType = _selectedType.asStateFlow()

    private val _selectedCurrency = MutableStateFlow(
        account?.currency ?: amountCurrencies.value.first()
    )
    val selectedCurrency = _selectedCurrency.asStateFlow()

    private val _enteredAccountName = MutableStateFlow(account?.name.orEmpty())
    val enteredAccountName = _enteredAccountName.asStateFlow()

    private val _enteredAmount = MutableStateFlow(account?.balance?.let(DecimalFormatType.Amount::format) ?: "")
    val enteredAmount = _enteredAmount.asStateFlow()

    private val _events = EventChannel<AddAccountEvent>()
    val events = _events.receiveAsFlow()

    val isAddButtonEnabled = combine(
        enteredAccountName,
        selectedType,
        selectedColor,
        enteredAmount
    ) { accountName, selectedType, selectedColor, enteredAmount ->
        accountName.isNotBlank() && selectedType != null &&
                selectedColor != null && enteredAmount.isNotBlank()
    }.stateIn(viewModelScope, started = SharingStarted.Lazily, initialValue = false)

    init {
        loadAccountColors()
    }

    private fun loadAccountColors() {
        viewModelScope.launch {
            _colors.value = accountsRepository.getAllAccountColors()
            _selectedColor.value = colors.value.firstOrNull { it.color == account?.color }
        }
    }

    fun onAccountNameChange(accountName: String) {
        _enteredAccountName.value = accountName
    }

    fun onAccountTypeSelect(accountType: Account.Type) {
        _selectedType.value = accountType
    }

    fun onCurrencySelect(currency: Currency) {
        _selectedCurrency.value = currency
    }

    fun onAccountColorSelect(accountColor: AccountColorData) {
        _selectedColor.value = accountColor
    }

    fun onAmountChange(amount: String) {
        _enteredAmount.value = amount
    }

    fun onAddAccountClick() {
        viewModelScope.launch {
            val accountName = enteredAccountName.value.takeIf { it.isNotBlank() } ?: run {
                _events.send(AddAccountEvent.ShowToast(
                    textId = "new_account_error_enter_account_name"
                ))
                return@launch
            }
            val selectedColor = selectedColor.value?.color ?: run {
                _events.send(AddAccountEvent.ShowToast(
                    textId = "new_account_error_select_account_color"
                ))
                return@launch
            }
            val balance = enteredAmount.value.toDoubleOrNull() ?: run {
                _events.send(AddAccountEvent.ShowToast(
                    textId = "new_account_error_enter_account_balance"
                ))
                return@launch
            }
            val type = selectedType.value ?: run {
                _events.send(AddAccountEvent.ShowToast(
                    textId = "new_account_error_select_account_type"
                ))
                return@launch
            }
            accountsRepository.insertAccount(
                accountName = accountName,
                balance = balance,
                colorHex = selectedColor.toHexString(),
                type = type,
                currency = selectedCurrency.value
            )
            _events.send(AddAccountEvent.Close)
        }
    }
}