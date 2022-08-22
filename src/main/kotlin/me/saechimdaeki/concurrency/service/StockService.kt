package me.saechimdaeki.concurrency.service

import me.saechimdaeki.concurrency.repository.StockRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
//@Transactional(readOnly = true)
class StockService(
    private val stockRepository: StockRepository,
) {
    @Transactional
    fun decrease(id: Long, quantity: Long) {
        // get stock
        // 재고 감소
        // 저장

        val stock = stockRepository.findByIdOrNull(id)?: kotlin.run {
            throw RuntimeException()
        }

        stock.decrease(quantity)
    }

//    @Transactional
    @Synchronized fun decreaseSync(id: Long, quantity: Long) {
        // get stock
        // 재고 감소
        // 저장
        val stock = stockRepository.findByIdOrNull(id)?: kotlin.run {
           throw RuntimeException()
        }

        stock.decrease(quantity)
    }
}