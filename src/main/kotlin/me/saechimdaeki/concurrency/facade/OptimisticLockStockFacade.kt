package me.saechimdaeki.concurrency.facade

import me.saechimdaeki.concurrency.service.OptimisticStockService
import org.springframework.stereotype.Service

@Service
class OptimisticLockStockFacade(
    private val optimisticStockService: OptimisticStockService,
) {

    fun decrease(id : Long, quantity: Long) {
        while(true) {
            try {
                optimisticStockService.decreaseByOptimisticLock(id, quantity)
                break
            } catch (e: Exception) {
                Thread.sleep(50)
            }
        }
    }
}