package me.saechimdaeki.concurrency.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Version

@Entity
class Stock(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var productId: Long? = null,

    var quantity: Long,

    @Version
    var version: Long? = 0L,

) {

    fun decrease(quantity: Long) {
        if(this.quantity - quantity <0)
            throw RuntimeException("재고 부족")
        this.quantity = this.quantity - quantity
    }

}