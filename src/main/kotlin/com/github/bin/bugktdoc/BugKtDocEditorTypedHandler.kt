package com.github.bin.bugktdoc

import com.github.bin.bugktdoc.util.getCurrentLineToCurrentChar
import com.intellij.codeInsight.editorActions.TypedHandlerDelegate
import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile

class BugKtDocEditorTypedHandler : TypedHandlerDelegate() {
	override fun charTyped(c: Char, project: Project, editor: Editor, file: PsiFile): Result {
		// avoid NoClassDef if no Kotlin plugin
		if (!file.language.id.equals("Kotlin", true)) return super.charTyped(c, project, editor, file)

		if (Settings.theFirstTile && c == '*' && getCurrentLineToCurrentChar(editor).endsWith("/**")) {
			Notifications.Bus.notify(
				Notification(
					"com.github.bin.bugktdoc.notification",
					BugKtDocBundle("bugktdoc.notation.title"),
					BugKtDocBundle("bugktdoc.notation.content"),
					NotificationType.INFORMATION
				)
			)
			Settings.theFirstTile = false
		}
		return super.charTyped(c, project, editor, file)
	}
}
