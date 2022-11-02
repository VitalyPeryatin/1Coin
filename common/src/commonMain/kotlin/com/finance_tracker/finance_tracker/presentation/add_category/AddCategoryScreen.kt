package com.finance_tracker.finance_tracker.presentation.add_category

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finance_tracker.finance_tracker.core.common.LocalContext
import com.finance_tracker.finance_tracker.core.common.StoredViewModel
import com.finance_tracker.finance_tracker.core.common.getLocalizedString
import com.finance_tracker.finance_tracker.core.common.statusBarsPadding
import com.finance_tracker.finance_tracker.core.theme.CoinTheme
import com.finance_tracker.finance_tracker.core.ui.CoinOutlinedTextField
import ru.alexgladkov.odyssey.compose.local.LocalRootController

@Composable
fun AddCategoryScreen(
    modifier: Modifier = Modifier,
    appBarText: String,
) {
    StoredViewModel<AddCategoryViewModel> { viewModel ->
        val rootController = LocalRootController.current

        val context = LocalContext.current

        Column(
            modifier = modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {

            AddCategoryAppBar(textValue = appBarText)

            val chosenCategory by viewModel.chosenCategory.collectAsState()
            val newCategoryName by viewModel.newCategoryName.collectAsState()

            Row(
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        top = 24.dp
                    ),
            ) {

                val categories by viewModel.categories.collectAsState()
                AddCategoryButton(
                    chosenCategory = viewModel.chosenCategory.value,
                    categories = categories,
                    onCategoryChoose = viewModel::onCategoryChoose
                )

                CoinOutlinedTextField(
                    value = newCategoryName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .padding(
                            start = 16.dp,
                            end = 16.dp
                        ),
                    onValueChange = {
                        viewModel.setCategoryName(it)
                    },
                )
            }

            val addButtonColor = ButtonDefaults.buttonColors(
                backgroundColor = CoinTheme.color.primary,
                contentColor = CoinTheme.color.primaryVariant,
            )

            Button(
                onClick = {
                    if (appBarText == "new_expense_category" && newCategoryName != "") {
                        viewModel.addExpenseCategory(
                            categoryName = newCategoryName,
                            categoryIcon = chosenCategory.iconId
                        )
                        rootController.findRootController().popBackStack()
                    } else if (appBarText == "new_income_category" && newCategoryName != "") {
                        viewModel.addIncomeCategory(
                            categoryName = newCategoryName,
                            categoryIcon = chosenCategory.iconId
                        )
                        rootController.findRootController().popBackStack()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 16.dp,
                        start = 16.dp,
                        end = 16.dp,
                    ),
                colors = addButtonColor,
                contentPadding = PaddingValues(
                    top = 12.dp,
                    bottom = 12.dp
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = getLocalizedString(
                        id = "new_account_btn_add",
                        context = context,
                    ),
                    style = CoinTheme.typography.body2_medium,
                )
            }
        }
    }
}