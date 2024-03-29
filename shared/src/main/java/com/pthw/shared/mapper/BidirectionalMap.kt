package com.pthw.shared.mapper

/**
 * Created by Vincent on 2/21/19
 */
interface BidirectionalMap<F, T> {


  fun map(item: F): T

  fun reverseMap(item: T): F

}
