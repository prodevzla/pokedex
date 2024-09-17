package com.prodevzla.pokedex.domain.model

import java.io.Serializable

data class Sort(
    val sortBy: SortBy = SortBy.ID,
    val sortOrder: SortOrder = SortOrder.Ascending
) : Serializable {

    fun isDefault(): Boolean {
        return this.sortBy == SortBy.ID && this.sortOrder == SortOrder.Ascending
    }
}

enum class SortBy {
    ID,
    Name,
}

enum class SortOrder {
    Ascending,
    Descending,
}
