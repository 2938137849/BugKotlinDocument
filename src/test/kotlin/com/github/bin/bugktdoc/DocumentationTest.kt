package com.github.bin.bugktdoc

import com.intellij.codeInsight.editorActions.smartEnter.SmartEnterAction
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase
import org.junit.Test


class DocumentationTest : BasePlatformTestCase() {
	override fun getTestDataPath(): String = "testData"

	private fun byName(sampleName: String) {
		myFixture.configureByFiles("$sampleName.kt")
		myFixture.editor.caretModel.moveToOffset(3)
		myFixture.testAction(SmartEnterAction())
		myFixture.checkResultByFile("$sampleName.txt")
	}

	@Test fun testConstructors() = byName("constructors")
	@Test fun testMain1() = byName("main1")
	@Test fun testFunction() = byName("function")
}
