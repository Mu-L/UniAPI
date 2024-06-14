package com.burukeyou.uniapi.config;

import com.burukeyou.uniapi.annotation.HttpApi;
import com.burukeyou.uniapi.core.proxy.AnnotationInvokeProxy;
import com.burukeyou.uniapi.core.proxy.ApiProxyFactory;
import com.burukeyou.uniapi.core.proxy.HttpApiAnnotationProxy;
import com.burukeyou.uniapi.support.HttpApiAnnotationMeta;
import com.burukeyou.uniapi.support.ProxySupport;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Component
public class HttpApiProxyFactory implements ApiProxyFactory {

    @Override
    public boolean isProxy(ProxySupport proxySupport) {
        return  AnnotatedElementUtils.getMergedAnnotation(proxySupport.getTargetClass(), HttpApi.class) != null;
    }

    @Override
    public AnnotationInvokeProxy getProxy(ProxySupport proxySupport) {
        Class<?> targetClass = proxySupport.getTargetClass();
        HttpApi apiAnnotation = AnnotatedElementUtils.getMergedAnnotation(targetClass, HttpApi.class);

        Annotation proxyAnnotation =  null;
        lab: for (Annotation annotation : targetClass.getAnnotations()) {
                if (AnnotatedElementUtils.findMergedAnnotation(annotation.annotationType(),HttpApi.class) != null){
                    proxyAnnotation = annotation;
                    break lab;
                }
        }

        if (proxyAnnotation == null){
            throw new IllegalArgumentException("can not find proxyAnnotation for " + targetClass.getName());
        }

        HttpApiAnnotationMeta meta = new HttpApiAnnotationMeta(proxyAnnotation, (HttpApi) apiAnnotation);
        meta.setProxySupport(proxySupport);
        return new HttpApiAnnotationProxy(meta);
    }
}
