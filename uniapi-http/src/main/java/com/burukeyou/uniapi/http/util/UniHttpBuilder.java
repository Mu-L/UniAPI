package com.burukeyou.uniapi.http.util;

import com.burukeyou.uniapi.config.SpringBeanContext;
import com.burukeyou.uniapi.register.UniApiFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;

@Component
public class UniHttpBuilder implements BeanFactoryAware {

    protected static DefaultListableBeanFactory beanFactory;

    public static <T> T getProxy(Class<T> proxyClass){
        //UniApiFactoryBean factoryBean = new UniApiFactoryBean(proxyClass);

        //beanFactory.autowireBean(factoryBean);

        UniApiFactoryBean factoryBean = SpringBeanContext.getBean(UniApiFactoryBean.class);

        try {
            return (T)factoryBean.getObject();
        } catch (Exception e) {
           throw new RuntimeException(e);
        }
    }

    @Override
    public void setBeanFactory(BeanFactory bf) throws BeansException {
        beanFactory = (DefaultListableBeanFactory)bf;
    }
}
