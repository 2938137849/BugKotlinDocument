@file:Suppress("unused")

package com.github.bin.bugktdoc

import kotlin.jvm.Throws

/**
 * @author bin
 * @date 2022/09/02
 */
typealias F<T> = () -> T

abstract class IF {
	/**
	 *
	 * @param T Any?
	 * @param R () -> Any?
	 * @receiver R?
	 * @param f R
	 * @return R?
	 * @throws Exception
	 */
	@Throws(Exception::class)
	open fun <T, R : F<T>> R?.func(f: R): R? = f
}

class FI : IF() {
	/**
	 * 
	 * @param T Any?
	 * @param R () -> Any?
	 * @receiver R?
	 * @param f R
	 * @return R
	 */
	override fun <T, R : F<T>> R?.func(f: R) = f
}
