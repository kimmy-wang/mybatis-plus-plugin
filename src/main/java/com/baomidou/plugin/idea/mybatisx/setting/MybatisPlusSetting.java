package com.baomidou.plugin.idea.mybatisx.setting;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.baomidou.plugin.idea.mybatisx.generate.GenerateModel;
import com.baomidou.plugin.idea.mybatisx.generate.AbstractStatementGenerator;

import org.jdom.Element;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;
import java.util.Set;

import static com.baomidou.plugin.idea.mybatisx.generate.AbstractStatementGenerator.DELETE_GENERATOR;
import static com.baomidou.plugin.idea.mybatisx.generate.AbstractStatementGenerator.INSERT_GENERATOR;
import static com.baomidou.plugin.idea.mybatisx.generate.AbstractStatementGenerator.SELECT_GENERATOR;
import static com.baomidou.plugin.idea.mybatisx.generate.AbstractStatementGenerator.UPDATE_GENERATOR;

/**
 * @author yanglin
 */
@State(
        name = "MybatisSettings",
        storages = @Storage(file = "$APP_CONFIG$/mybatis.xml"))
public class MybatisPlusSetting implements PersistentStateComponent<Element> {

    private GenerateModel statementGenerateModel;

    private Gson gson = new Gson();

    private Type gsonTypeToken = new TypeToken<Set<String>>() {
    }.getType();

    public MybatisPlusSetting() {
        statementGenerateModel = GenerateModel.START_WITH_MODEL;
    }

    public static MybatisPlusSetting getInstance() {
        return ServiceManager.getService(MybatisPlusSetting.class);
    }

    /**
     * 保存到文件
     */
    @Nullable
    @Override
    public Element getState() {
        Element element = new Element("MybatisSettings");
        element.setAttribute(INSERT_GENERATOR.getId(), gson.toJson(INSERT_GENERATOR.getPatterns()));
        element.setAttribute(DELETE_GENERATOR.getId(), gson.toJson(DELETE_GENERATOR.getPatterns()));
        element.setAttribute(UPDATE_GENERATOR.getId(), gson.toJson(UPDATE_GENERATOR.getPatterns()));
        element.setAttribute(SELECT_GENERATOR.getId(), gson.toJson(SELECT_GENERATOR.getPatterns()));
        element.setAttribute("statementGenerateModel", String.valueOf(statementGenerateModel.getIdentifier()));
        return element;
    }

    /**
     * 从文件读取数据
     * @param state
     */
    @Override
    public void loadState(Element state) {
        loadState(state, INSERT_GENERATOR);
        loadState(state, DELETE_GENERATOR);
        loadState(state, UPDATE_GENERATOR);
        loadState(state, SELECT_GENERATOR);
        statementGenerateModel = GenerateModel.getInstance(state.getAttributeValue("statementGenerateModel"));
    }

    private void loadState(Element state, AbstractStatementGenerator generator) {
        String attribute = state.getAttributeValue(generator.getId());
        if (null != attribute) {
            generator.setPatterns((Set<String>) gson.fromJson(attribute, gsonTypeToken));
        }
    }

    public GenerateModel getStatementGenerateModel() {
        return statementGenerateModel;
    }

    public void setStatementGenerateModel(GenerateModel statementGenerateModel) {
        this.statementGenerateModel = statementGenerateModel;
    }
}
