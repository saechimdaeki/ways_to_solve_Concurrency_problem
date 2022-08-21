package me.saechimdaeki.concurrency.repository

import me.saechimdaeki.concurrency.domain.Stock
import org.springframework.data.jpa.repository.JpaRepository

interface StockRepository : JpaRepository<Stock, Long> {
}