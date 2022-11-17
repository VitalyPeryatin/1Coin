package com.finance_tracker.finance_tracker.data.database.data_sources

import androidx.paging.PagingState
import app.cash.paging.PagingSource
import com.finance_tracker.finance_tracker.data.repositories.TransactionsRepository
import com.finance_tracker.finance_tracker.domain.models.Transaction

class TransactionSource(
    private val repository: TransactionsRepository
): PagingSource<Long, Transaction>() {
    override fun getRefreshKey(state: PagingState<Long, Transaction>): Long? {
        return state.anchorPosition?.toLong()
    }

    override suspend fun load(params: LoadParams<Long>): LoadResult<Long, Transaction> {
        return try {

            val nextPage = params.key ?: 1
            val transactionList = repository.getAllFullTransactionsPaginated(page = nextPage)

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
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }
}