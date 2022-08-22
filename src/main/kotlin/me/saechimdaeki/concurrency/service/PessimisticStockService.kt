package me.saechimdaeki.concurrency.service

import me.saechimdaeki.concurrency.repository.StockRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PessimisticStockService(
    private val stockRepository: StockRepository,
) {

    @Transactional
    fun decreaseByPessmimiticLock(id:Long, quantity:Long) {
        // get stock
        // 재고 감소
        // 저장
        val stock = stockRepository.findByIdWithPessimisticLock(id)?: kotlin.run {
            throw RuntimeException()
        }

        stock.decrease(quantity)
    }
}