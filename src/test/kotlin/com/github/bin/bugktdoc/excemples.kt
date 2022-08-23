@file:Suppress("unused")

package com.github.bin.bugktdoc

import kotlin.jvm.Throws

/**
 * 
 * @context String.() -> Int
 * @context p@ Result<Int>
 * @receiver Int.Companion
 * @param block context(Int)Pair<String, Long>.(Short) -> List<Map<*, Float>>
 * @return Int.(Pair<String, Long>, Short) -> List<Map<*, Float>>
 * @throws NumberFormatException
 * @throws NullPointerException
 */
context(String.() -> Int, p@Result<Int>)
	@Throws(NumberFormatException::class, NullPointerException::class)
fun Int.Companion.function(
	block: context(Int)Pair<String, Long>.(Short) -> List<Map<*, Float>>,
): Int.(Pair<String, Long>, Short) -> List<Map<*, Float>> {
	return block
}
