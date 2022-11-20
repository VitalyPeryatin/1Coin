package com.finance_tracker.finance_tracker.data.data_sources

import androidx.paging.PagingState
import app.cash.paging.PagingSource
import com.finance_tracker.finance_tracker.domain.interactors.TransactionsInteractor
import com.finance_tracker.finance_tracker.domain.models.TransactionListModel
import java.io.IOException

class TransactionSource(
    private val interactor: TransactionsInteractor
): PagingSource<Long, TransactionListModel>() {
    override fun getRefreshKey(state: PagingState<Long, TransactionListModel>): Long? {
        return state.anchorPosition?.toLong()
    }

    override suspend fun load(params: LoadParams<Long>): LoadResult<Long, TransactionListModel> {
        return try {

            val nextPage = params.key ?: 1
            val transactionList = interactor.getTransactions(page = nextPage)

            LoadResult.Page(
                data = transactionList,
                prevKey = if (nextPage.toInt() == 1) {
                    null
                } else {
                    nextPage - 1
                },
                nextKey = if (transactionList.isEmpty()) {
                    null
                } else {
                    nextPage + 1
                }
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        }
    }
}