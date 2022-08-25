@file:Suppress("unused", "UNUSED_PARAMETER", "MemberVisibilityCanBePrivate")

package com.github.bin.bugktdoc.samples


/**
 * @author zxj5470
 * @date 2018/4/9
 */

/**
 *
 * @param T in Cloneable
 * @param t String
 * @param strings Array<out String>
 * @property strings Array<out String>
 * @property aInt Int
 * @property bInt Int
 * @constructor
 */
class ADisable<in T : Cloneable>(t: String, private vararg val strings: String) {

	/**
	 *
	 * @param name String
	 */
	constructor(name: String) : this(name, "")

	private val aInt = 0
	val bInt = 1
}
