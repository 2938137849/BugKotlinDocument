@file:Suppress("unused", "UNUSED_PARAMETER")

package com.github.bin.bugktdoc.samples

/**
 * disable show `@return Unit` by default.
 */

/**
 *
 * @param strings Array<*>?
 * @param map Map<out Any?, String?>
 * @return Map<out Any?, String?>?
 */
fun twoParamUnit(strings: Array<*>?,map: Map<out Any?,String?>): Map<out Any?,String?>? {
    return null
}


/**
 *
 * @param i Int
 * @param strings Array<out String?>
 */
fun paramsWithVarargU(i: Int, vararg strings: String?) {

}
