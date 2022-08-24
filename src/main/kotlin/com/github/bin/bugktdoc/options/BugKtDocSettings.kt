package com.github.bin.bugktdoc.options

/**
 * @author zxj5470
 * @date 2018/4/2
 */

data class BugKtDocSettings(
	var useDoc: Boolean = true,
	var useFunctionDoc: Boolean = true,
	var useClassDoc: Boolean = true,
	var useConstructorDoc: Boolean = true,
	var theFirstTile: Boolean = true,
	var alwaysShowUnitReturnType: Boolean = false,
	var alwaysShowClassFieldProperty: Boolean = true,
	var alwaysShowConstructor: Boolean = true,
)
