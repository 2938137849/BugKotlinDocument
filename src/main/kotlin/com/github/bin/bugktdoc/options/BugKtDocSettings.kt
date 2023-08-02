package com.github.bin.bugktdoc.options

/**
 * @author zxj5470
 * @date 2018/4/2
 */

data class BugKtDocSettings(
    var flag: Int = 0b011_1101_1110_1111_1111,
) {

    var theFirstTile: Boolean
        get() = bit(0)
        set(value) = bit(0, value)

    // --separator--
    var useDoc: Boolean
        get() = bit(1)
        set(value) = bit(1, value)

    // --separator--
    var useFunctionDoc: Boolean
        get() = bit(2)
        set(value) = bit(2, value)

    var funGeneric: Boolean
        get() = bit(3)
        set(value) = bit(3, value)
    var funContext: Boolean
        get() = bit(4)
        set(value) = bit(4, value)
    var funReceiver: Boolean
        get() = bit(5)
        set(value) = bit(5, value)
    var funParam: Boolean
        get() = bit(6)
        set(value) = bit(6, value)
    var funReturn: Boolean
        get() = bit(7)
        set(value) = bit(7, value)
    var alwaysShowUnitReturnType: Boolean
        get() = bit(8)
        set(value) = bit(8, value)
    var funThrows: Boolean
        get() = bit(9)
        set(value) = bit(9, value)

    // --separator--
    var useClassDoc: Boolean
        get() = bit(10)
        set(value) = bit(10, value)
    var classGeneric: Boolean
        get() = bit(11)
        set(value) = bit(11, value)
    var classParam: Boolean
        get() = bit(12)
        set(value) = bit(12, value)
    var classProperty: Boolean
        get() = bit(13)
        set(value) = bit(13, value)
    var classConstructor: Boolean
        get() = bit(14)
        set(value) = bit(14, value)

    // --separator--
    var useConstructorDoc: Boolean
        get() = bit(15)
        set(value) = bit(15, value)
    var constructorParam: Boolean
        get() = bit(16)
        set(value) = bit(16, value)
    var constructorConstructor: Boolean
        get() = bit(17)
        set(value) = bit(17, value)

    // --separator--
    var showBuiltinType: Boolean
        get() = bit(18)
        set(value) = bit(18, value)

    private fun bit(index: Int): Boolean {
        return flag and (1 shl index) != 0
    }

    private fun bit(index: Int, bit: Boolean) {
        val aBit = 1 shl index
        flag = if (bit) flag or aBit
        else flag and aBit.inv()
    }
}
