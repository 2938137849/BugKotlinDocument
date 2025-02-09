@file:Suppress("unused", "UNUSED_PARAMETER")

package com.github.bin.bugktdoc.samples


/**
 * disable both fields `@property` and `@constructor`
 */

/**
 *
 * @param T Any?
 * @param t String
 * @param strings Array<out String>
 */
class AFieldDisabled<T>(t: String, private vararg val strings: String) {
	/**
	 *
	 * @param name String
	 */
	constructor(name: String) : this(name, "")

	private val aInt = 0
	val bInt = 1
}
