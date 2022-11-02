package com.finance_tracker.finance_tracker.presentation.add_account

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.LocalContext
import com.finance_tracker.finance_tracker.core.common.StoredViewModel
import com.finance_tracker.finance_tracker.core.common.statusBarsPadding
import com.finance_tracker.finance_tracker.core.common.stringResource
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.theme.staticTextSize
import com.finance_tracker.finance_tracker.core.ui.*
import com.finance_tracker.finance_tracker.domain.models.AccountColorData
import com.finance_tracker.finance_tracker.presentation.add_account.dropdown_menus.AccountColorsDropdownMenu
import com.finance_tracker.finance_tracker.presentation.add_account.dropdown_menus.AccountTypesDropdownMenu
import com.finance_tracker.finance_tracker.presentation.add_account.views.CurrencySelector
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.alexgladkov.odyssey.compose.local.LocalRootController

private const val AccountNameCharsLimit = 40

@Composable
fun AddAccountScreen() {
    StoredViewModel<AddAccountViewModel> { viewModel ->
        val rootController = LocalRootController.current
        val context = LocalContext.current
        LaunchedEffect(Unit) {
            viewModel.events
                .onEach { event -> handleEvent(event, context, rootController) }
                .launchIn(this)
        }
        Column {
            AddAccountTopBar(
                modifier = Modifier
                    .statusBarsPadding()
            )

            val titleAccount by viewModel.enteredAccountName.collectAsState()
            CoinOutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                value = titleAccount,
                label = {
                    Text(
                        text = stringResource("new_account_field_name_label")
                    )
                },
                placeholder = {
                    Text(
                        text = stringResource("new_account_field_name_placeholder"),
                        style = CoinTheme.typography.body1.staticTextSize()
                    )
                },
                onValueChange = viewModel::onAccountNameChange,
                maxLines = 1,
                singleLine = true,
                charsLimit = AccountNameCharsLimit,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            Row(
                modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
            ) {
                AccountTypeTextField(viewModel = viewModel)
                Spacer(modifier = Modifier.width(8.dp))
                AccountColorTextField(viewModel = viewModel)
            }

            val amountAccount by viewModel.enteredAmount.collectAsState()
            val amountCurrencies by viewModel.amountCurrencies.collectAsState()
            val selectedCurrency by viewModel.selectedCurrency.collectAsState()
            CoinOutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                value = amountAccount,
                label = {
                    Text(
                        text = stringResource("new_account_field_amount_label"),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                placeholder = {
                    Text(
                        text = stringResource("new_account_field_amount_placeholder"),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = CoinTheme.typography.body1.staticTextSize()
                    )
                },
                onValueChange = viewModel::onAmountChange,
                maxLines = 1,
                trailingIcon = {
                    CurrencySelector(
                        items = amountCurrencies,
                        selectedCurrency = selectedCurrency,
                        onCurrencySelect = viewModel::onCurrencySelect
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            val isAddButtonEnabled by viewModel.isAddButtonEnabled.collectAsState()
            PrimaryButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                text = stringResource("new_account_btn_add"),
                onClick = viewModel::onAddAccountClick,
                enabled = isAddButtonEnabled
            )
        }
    }
}

@Composable
private fun RowScope.AccountTypeTextField(
    viewModel: AddAccountViewModel,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val accountTypeMenuExpanded = remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val selectedType by viewModel.selectedType.collectAsState()
    val valueTextId = selectedType?.textId
    CoinOutlinedSelectTextField(
        value = if (valueTextId != null) {
            stringResource(valueTextId)
        } else {
            ""
        },
        modifier = modifier
            .weight(1f)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                focusManager.clearFocus()
                accountTypeMenuExpanded.value = true
            },
        label = {
            Text(
                text = stringResource("new_account_field_type_label"),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        placeholder = {
            Text(
                text = stringResource("new_account_field_type_placeholder"),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = CoinTheme.typography.body1.staticTextSize()
            )
        },
        selected = accountTypeMenuExpanded.value
    )

    val accountTypes by viewModel.types.collectAsState()
    AccountTypesDropdownMenu(
        items = accountTypes,
        expandedState = accountTypeMenuExpanded,
        onSelect = viewModel::onAccountTypeSelect
    )
}

@Composable
fun RowScope.AccountColorTextField(
    viewModel: AddAccountViewModel,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val accountColorMenuOffsetX = remember { mutableStateOf(0) }
    val accountColorMenuExpanded = remember { mutableStateOf(false) }
    val selectedColor by viewModel.selectedColor.collectAsState()
    CoinOutlinedSelectTextField(
        value = selectedColor?.name.orEmpty(),
        modifier = modifier
            .onGloballyPositioned {
                accountColorMenuOffsetX.value = it.positionInParent().x.toInt()
            }
            .weight(1f)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                focusManager.clearFocus()
                accountColorMenuExpanded.value = true
            },
        label = {
            Text(
                text = stringResource("new_account_field_color_label"),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        leadingIcon = if (selectedColor != null) {
            { ColorIcon(selectedColor) }
        } else {
            null
        },
        placeholder = {
            Text(
                text = stringResource("new_account_field_color_placeholder"),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = CoinTheme.typography.body1.staticTextSize()
            )
        },
        selected = accountColorMenuExpanded.value
    )

    val accountColors by viewModel.colors.collectAsState()
    AccountColorsDropdownMenu(
        items = accountColors,
        expandedState = accountColorMenuExpanded,
        offsetXState = accountColorMenuOffsetX,
        onSelect = viewModel::onAccountColorSelect
    )
}

@Composable
fun ColorIcon(accountColorData: AccountColorData?) {
    if (accountColorData != null) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(
                    color = accountColorData.color,
                    shape = CircleShape
                )
        )
    }
}

@Composable
private fun AddAccountTopBar(
    modifier: Modifier = Modifier
) {
    val rootController = LocalRootController.current
    TopAppBar(
        modifier = modifier,
        backgroundColor = CoinTheme.color.background,
        contentColor = CoinTheme.color.content,
        navigationIcon = {
            AppBarIcon(
                painter = rememberVectorPainter("ic_arrow_back"),
                onClick = { rootController.popBackStack() }
            )
        },
        title = {
            Text(
                text = stringResource("new_account_title"),
                style = CoinTheme.typography.h4
            )
        }
    )
}