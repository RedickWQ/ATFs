package org.automation.atfs.proxy;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;

/**
 * Created by wangqiang on 2018/6/13
 */
public class ClassPathScanner extends ClassPathScanningCandidateComponentProvider {

    public ClassPathScanner(final boolean useDefaultFilters) {
        super(useDefaultFilters);
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isIndependent();
    }

}
