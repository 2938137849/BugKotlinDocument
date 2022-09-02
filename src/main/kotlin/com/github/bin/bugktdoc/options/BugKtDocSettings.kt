package com.github.bin.bugktdoc.options

/**
 * @author zxj5470
 * @date 2018/4/2
 */

data class BugKtDocSettings(
	var theFirstTile: Boolean = true,
	// --separator--
	var useDoc: Boolean = true,
	// --separator--
	var useFunctionDoc: Boolean = true,
	var funGeneric: Boolean = true,
	var funContext: Boolean = true,
	var funReceiver: Boolean = true,
	var funParam: Boolean = true,
	var funReturn: Boolean = true,
	var alwaysShowUnitReturnType: Boolean = false,
	var funThrows: Boolean = true,
	// --separator--
	var useClassDoc: Boolean = true,
	var classGeneric: Boolean = true,
	var classParam: Boolean = true,
	var classProperty: Boolean = true,
	var classFieldProperty: Boolean = true,
	var classConstructor: Boolean = true,
	// --separator--
	var useConstructorDoc: Boolean = true,
	var constructorParam: Boolean = true,
	var constructorConstructor: Boolean = true,
	// --separator--
	var showBuiltinType: Boolean = false,
)
