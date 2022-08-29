@file:Suppress("unused")

package com.github.bin.bugktdoc

/**
 * 
 * @context String.() -> Int
 * @context Result<Int>
 * @receiver Int.Companion
 * @param block context(Int)Pair<String, Long>.(Short) -> List<Map<*, Float>>
 * @return Int.(Pair<String, Long>, Short) -> List<Map<*, Float>>
 */
context(String.() -> Int, p@Result<Int>)
	@kotlin.jvm.Throws(NumberFormatException::class, NullPointerException::class)
fun Int.Companion.function(
	block: context(Int)Pair<String, Long>.(Short) -> List<Map<*, Float>>,
): Int.(Pair<String, Long>, Short) -> List<Map<*, Float>> {
	return block
}
