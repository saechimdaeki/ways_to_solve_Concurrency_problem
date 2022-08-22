package me.saechimdaeki.concurrency.facade

import me.saechimdaeki.concurrency.domain.Stock
import me.saechimdaeki.concurrency.repository.StockRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

@SpringBootTest
internal class RedissonLockStockFacadeTest @Autowired constructor(
    private val redissonLockStockFacade: RedissonLockStockFacade,
    private val stockRepository: StockRepository,
){

    @BeforeEach
    fun before() {
        stockRepository.save(Stock(productId = 1L, quantity = 100L))
    }

    @AfterEach
    fun after(){
        stockRepository.deleteAll()
    }

    @Test
    fun `동시에 100개의 요청 by redisson`(){
        val threadCnt = 100
        val executorService = Executors.newFixedThreadPool(32)
        val latch = CountDownLatch(threadCnt)

        for(i in 0 until threadCnt) {
            executorService.execute{
                try{
                    redissonLockStockFacade.decrease(1L,1L)
                }finally {
                    latch.countDown()
                }
            }
        }
        latch.await()
        val stock = stockRepository.findByIdOrNull(1L) ?: throw RuntimeException()

        Assertions.assertThat(stock.quantity).isEqualTo(0L)
    }
}