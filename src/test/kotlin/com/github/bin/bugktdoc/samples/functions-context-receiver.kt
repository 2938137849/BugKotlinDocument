@file:Suppress("unused")

package com.github.bin.bugktdoc.samples

/**
 * @author zxj5470
 * @date 27/05/2018
 */

/**
 *
 * @context String
 * @context i@ Int
 * @param block () -> Unit
 * @return () -> Unit
 */
context (String, i@ Int)
fun contextReceiver(block: () -> Unit): Function0<Unit> {
	this@i.toString()
	return block
}

/**
 *
 * @context String
 * @param block String.() -> Unit
 * @return (String) -> Unit
 */
context (String)
fun contextReceiver(block: String.() -> Unit): Function1<String, Unit> {
	return block
}

/**
 *
 * @context String
 * @param block Pair<String, Int>.() -> Unit
 * @return (Pair<String, Int>) -> Unit
 */
context (String)
fun contextReceiver2(block: Pair<String, Int>.() -> Unit): Function1<Pair<String, Int>, Unit> {
	return block
}

/**
 *
 * @context Pair<String, Int>
 * @param block (String, Int) -> Unit
 * @return (String, Int) -> Unit
 */
context (Pair<String, Int>)
fun contextReceiver2(block: (String, Int) -> Unit): Function2<String, Int, Unit> {
	return block
}
