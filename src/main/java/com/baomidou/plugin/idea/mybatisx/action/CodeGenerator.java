package com.baomidou.plugin.idea.mybatisx.action;

import com.baomidou.plugin.idea.mybatisx.codegenerator.MysqlUtil;
import com.baomidou.plugin.idea.mybatisx.codegenerator.domain.vo.ColumnInfo;
import com.baomidou.plugin.idea.mybatisx.codegenerator.domain.vo.TableInfo;
import com.baomidou.plugin.idea.mybatisx.codegenerator.view.ShowTableInfo;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.SystemIndependent;

import java.util.List;

public class CodeGenerator extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        String projectRoot = event.getProject().getBasePath();
        ShowTableInfo dialog = new ShowTableInfo(projectRoot);
        dialog.pack();
        dialog.setVisible(true);
        dialog.setLocationRelativeTo(null);
        dialog.setSize(800,600);
    }
}
