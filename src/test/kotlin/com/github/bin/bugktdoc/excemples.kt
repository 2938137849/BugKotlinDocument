package com.github.bin.bugktdoc

import kotlin.jvm.Throws

/**
 *
 * @param int Int
 * @param block [@kotlin.ExtensionFunctionType] [@kotlin.ContextFunctionTypeParams] Function3<Int, Pair<String, Long>, Short, List<Map<*, Float>>>
 * @throws NumberFormatException
 * @throws NullPointerException
 */
context(String)
	@Throws(NumberFormatException::class, NullPointerException::class)
fun function(
	int: Int,
	block: context(Int)Pair<String, Long>.(Short) -> List<Map<*, Float>>,
) {
}
