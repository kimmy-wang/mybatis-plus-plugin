package com.baomidou.plugin.idea.mybatisx.dom.converter;

import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.util.xml.ConvertContext;
import com.intellij.util.xml.CustomReferenceConverter;
import com.intellij.util.xml.DomJavaUtil;
import com.intellij.util.xml.GenericDomValue;
import com.intellij.util.xml.PsiClassConverter;
import com.baomidou.plugin.idea.mybatisx.alias.AliasClassReference;
import com.baomidou.plugin.idea.mybatisx.alias.PlusAliasFacade;
import com.baomidou.plugin.idea.mybatisx.util.MybatisConstants;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author yanglin
 */
public class AliasConverter extends ConverterAdaptor<PsiClass> implements CustomReferenceConverter<PsiClass> {

    private PsiClassConverter delegate = new PsiClassConverter();

    @Nullable
    @Override
    public PsiClass fromString(@Nullable @NonNls String s, ConvertContext context) {
        if (StringUtil.isEmptyOrSpaces(s)) return null;
        if (!s.contains(MybatisConstants.DOT_SEPARATOR)) {
            return PlusAliasFacade.getInstance(context.getProject()).findPsiClass(context.getXmlElement(), s).orNull();
        }
        return DomJavaUtil.findClass(s.trim(), context.getFile(), context.getModule(), GlobalSearchScope.allScope(context.getProject()));
    }

    @Nullable
    @Override
    public String toString(@Nullable PsiClass psiClass, ConvertContext context) {
        return delegate.toString(psiClass, context);
    }

    @NotNull
    @Override
    public PsiReference[] createReferences(GenericDomValue<PsiClass> value, PsiElement element, ConvertContext context) {
        if (((XmlAttributeValue) element).getValue().contains(MybatisConstants.DOT_SEPARATOR)) {
            return delegate.createReferences(value, element, context);
        } else {
            return new PsiReference[]{new AliasClassReference((XmlAttributeValue) element)};
        }
    }
}
