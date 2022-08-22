package me.saechimdaeki.concurrency.facade

import me.saechimdaeki.concurrency.repository.LockRepository
import me.saechimdaeki.concurrency.service.StockService
import org.springframework.stereotype.Service

@Service
class NamedLockStockFacade(
    private val lockRepository: LockRepository,
    private val stockService: StockService,
) {

    fun decrease(id: Long, quantity: Long){
        try{
            lockRepository.getLock(id.toString())
            stockService.decrease(id,quantity)
        }finally {
            lockRepository.releaseLock(id.toString())
        }
    }
}