package me.saechimdaeki.concurrency.service

import me.saechimdaeki.concurrency.repository.StockRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class OptimisticStockService(
    private val stockRepository: StockRepository,
) {

    @Transactional
    fun decreaseByOptimisticLock(id:Long, quantity:Long) {
        // get stock
        // 재고 감소
        // 저장
        val stock = stockRepository.findByIdWithOptimisticLock(id)?: kotlin.run {
            throw RuntimeException()
        }

        stock.decrease(quantity)
    }
}