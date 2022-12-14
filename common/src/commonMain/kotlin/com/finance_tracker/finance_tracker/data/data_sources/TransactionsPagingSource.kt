package com.finance_tracker.finance_tracker.data.data_sources

import app.cash.paging.PagingSource
import app.cash.paging.PagingState
import com.finance_tracker.finance_tracker.data.mappers.fullTransactionMapper
import com.finance_tracker.finance_tracker.domain.models.Transaction
import com.financetracker.financetracker.data.TransactionsEntityQueries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class TransactionsPagingSource(
    private val transactionsEntityQueries: TransactionsEntityQueries,
    private val accountId: Long? = null,
): PagingSource<Long, Transaction>() {

    override fun getRefreshKey(state: PagingState<Long, Transaction>): Long? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }

    override suspend fun load(params: LoadParams<Long>): LoadResult<Long, Transaction> {
        return try {
            val nextPage = params.key ?: 0
            val transactionList = if (accountId == null) {
                getAllFullTransactionsPaginated(
                    page = nextPage,
                    limit = params.loadSize.toLong()
                )
            } else {
                getAllFullTransactionsPaginatedByAccountId(
                    page = nextPage,
                    limit = params.loadSize.toLong(),
                    id = accountId
                )
            }

            LoadResult.Page(
                data = transactionList,
                prevKey = if (nextPage == 0L) {
                    null
                } else {
                    nextPage - transactionList.size
                },
                nextKey = if (transactionList.isEmpty()) {
                    null
                } else {
                    nextPage + transactionList.size
                }
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        }
    }

    private suspend fun getAllFullTransactionsPaginated(
        page: Long,
        limit: Long
    ): List<Transaction> {
        return withContext(Dispatchers.IO) {
            transactionsEntityQueries.getAllFullTransactionsPaginated(
                limit = limit,
                offset = page,
                mapper = fullTransactionMapper
            ).executeAsList()
        }
    }

    private suspend fun getAllFullTransactionsPaginatedByAccountId(
        page: Long,
        limit: Long,
        id: Long,
    ): List<Transaction> {
        return withContext(Dispatchers.IO) {
            transactionsEntityQueries.getFullTransactionsByAccountIdPaginated(
                limit = limit,
                offset = page,
                mapper = fullTransactionMapper,
                accountId = id,
            ).executeAsList()
        }
    }
}

class TransactionsPagingSourceFactory(
    private val transactionsEntityQueries: TransactionsEntityQueries
) {
    fun create(accountId: Long): TransactionsPagingSource {
        return TransactionsPagingSource(transactionsEntityQueries, accountId)
    }
}