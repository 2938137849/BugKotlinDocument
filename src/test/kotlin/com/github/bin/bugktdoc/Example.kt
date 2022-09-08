@file:Suppress("unused", "UNUSED_PARAMETER")

package com.github.bin.bugktdoc

/**
 *
 * @param A Any?
 * @param B CharSequence
 * @param a context(Int) (Pair<String, *>?.() -> Int)?
 * @param b Result<Int>?
 * @constructor
 */
class Example<A, B : CharSequence>(
	a: (context(Int)Pair<String, *>?.() -> Int)?,
	private val b: Result<Int>?,
) : JavaClass() {
	private var c: String? = null

	/**
	 *
	 * @param a context(Int) (Pair<String, *>?.() -> Int)?
	 * @param b Result<Int>?
	 * @param c String
	 * @constructor
	 */
	constructor(
		a: (context(Int)Pair<String, *>?.() -> Int)?,
		b: Result<Int>?,
		c: String,
	) : this(a, b) {
		this.c = c
	}

	/**
	 *
	 * @context String.() -> Int
	 * @context Result<Int>
	 * @receiver Int.Companion
	 * @param block context(Int) Pair<String, Long>.(Short) -> List<Map<*, Float>>
	 * @return Int.(Pair<String, Long>, Short) -> List<Map<*, Float>>
	 * @throws NumberFormatException
	 * @throws NullPointerException
	 */
	context(String.() -> Int, p@Result<Int>)
		@kotlin.jvm.Throws(NumberFormatException::class, NullPointerException::class)
	fun Int.Companion.function(
		block: context(Int)Pair<String, Long>.(Short) -> List<Map<*, Float>>,
	): Int.(Pair<String, Long>, Short) -> List<Map<*, Float>> {
		return block
	}

	/**
	 *
	 * @return Unit?
	 */
	fun nullOrUnit() = if (true) null else Unit

	/**
	 *
	 * @return (Mutable)Map<String!, *>!
	 */
	override fun getMap() = super.getMap()

	/**
	 *
	 * @return (Mutable)List<*>!
	 */
	override fun getList() = super.getList()

	var value: String
		get() = ""
		set(v) {}
}
