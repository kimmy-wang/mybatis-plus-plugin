package com.baomidou.plugin.idea.mybatisx.service;

import com.intellij.codeInsight.navigation.NavigationUtil;
import com.intellij.formatting.FormatTextRanges;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.ScrollType;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.codeStyle.CodeStyleSettingsManager;
import com.intellij.psi.impl.source.codeStyle.CodeFormatterFacade;

import org.jetbrains.annotations.NotNull;

/**
 * 编辑文件的服务
 * @author yanglin
 */
public class PlusEditorService {

    private Project project;

    private FileEditorManager fileEditorManager;

    private CodeFormatterFacade codeFormatterFacade;

    public PlusEditorService(Project project) {
        this.project = project;
        this.fileEditorManager = FileEditorManager.getInstance(project);
    }

    public static PlusEditorService getInstance(@NotNull Project project) {
        return ServiceManager.getService(project, PlusEditorService.class);
    }

    public void format(@NotNull PsiFile file, @NotNull PsiElement element) {
        this.codeFormatterFacade = new CodeFormatterFacade(CodeStyleSettingsManager.getSettings(element.getProject()), element.getLanguage());
        codeFormatterFacade.processText(file, new FormatTextRanges(element.getTextRange(), true), true);
    }

    public void scrollTo(@NotNull PsiElement element, int offset) {
        NavigationUtil.activateFileWithPsiElement(element, true);
        Editor editor = fileEditorManager.getSelectedTextEditor();
        if (null != editor) {
            editor.getCaretModel().moveToOffset(offset);
            editor.getScrollingModel().scrollToCaret(ScrollType.RELATIVE);
        }
    }

}
