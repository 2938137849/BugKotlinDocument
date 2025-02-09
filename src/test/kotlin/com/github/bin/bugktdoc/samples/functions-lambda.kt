@file:Suppress("unused")

package com.github.bin.bugktdoc.samples

/**
 * @author zxj5470
 * @date 27/05/2018
 */

/**
 *
 * @param block () -> Unit
 * @return () -> Unit
 */
fun lambda(block: () -> Unit): Function0<Unit> {
	return block
}

/**
 *
 * @param block String.() -> Unit
 * @return (String) -> Unit
 */
fun lambda(block: String.() -> Unit): Function1<String, Unit> {
	return block
}

/**
 *
 * @param block Pair<String, Int>.() -> Unit
 * @return (Pair<String, Int>) -> Unit
 */
fun lambda2(block: Pair<String, Int>.() -> Unit): Function1<Pair<String, Int>, Unit> {
	return block
}

/**
 *
 * @param block (String, Int) -> Unit
 * @return (String, Int) -> Unit
 */
fun lambda2(block: (String, Int) -> Unit): Function2<String, Int, Unit> {
	return block
}
