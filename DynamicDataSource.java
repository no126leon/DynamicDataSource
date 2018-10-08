package com.teamface.datasource;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.alibaba.druid.pool.DruidDataSource;

final class DynamicDataSource extends AbstractRoutingDataSource implements ApplicationContextAware
{
    
    private static final String DATA_SOURCES_NAME = "targetDataSources";
    
    private ApplicationContext applicationContext;
    
    private static final Logger log = LoggerFactory.getLogger(DynamicDataSource.class);
    
    @Override
    protected Object determineCurrentLookupKey()
    {
        DataSourceBeanBuilder dataSourceBeanBuilder = DataSourceHolder.getDataSource();
        log.info("determineCurrentLookupKey:{}", dataSourceBeanBuilder);
        if (dataSourceBeanBuilder == null)
        {
            return null;
        }
        DataSourceBean dataSourceBean = new DataSourceBean(dataSourceBeanBuilder);
        try
        {
            Map<Object, Object> map = getTargetDataSources();
            synchronized (map)
            {
                if (!map.containsKey(dataSourceBean.getBeanName()))
                {
                    map.put(dataSourceBean.getBeanName(), createDataSource(dataSourceBean));
                    super.afterPropertiesSet();// 通知spring有bean更新
                }
            }
            return dataSourceBean.getBeanName();
        }
        catch (NoSuchFieldException | IllegalAccessException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    private Object createDataSource(DataSourceBean dataSourceBean)
        throws IllegalAccessException
    {
        // 在spring容器中创建并且声明bean
        ConfigurableApplicationContext context = (ConfigurableApplicationContext)applicationContext;
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory)context.getBeanFactory();
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(DruidDataSource.class);
        
        beanDefinitionBuilder.setInitMethodName("init");
        beanDefinitionBuilder.setDestroyMethodName("close");
        
        // 将dataSourceBean中的属性值赋给目标bean
        Map<String, Object> properties = getPropertyKeyValues(DataSourceBean.class, dataSourceBean);
        for (Map.Entry<String, Object> entry : properties.entrySet())
        {
            beanDefinitionBuilder.addPropertyValue((String)entry.getKey(), entry.getValue());
        }
        beanFactory.registerBeanDefinition(dataSourceBean.getBeanName(), beanDefinitionBuilder.getBeanDefinition());
        return applicationContext.getBean(dataSourceBean.getBeanName());
    }
    
    private Map<Object, Object> getTargetDataSources()
        throws NoSuchFieldException, IllegalAccessException
    {
        Field field = AbstractRoutingDataSource.class.getDeclaredField(DATA_SOURCES_NAME);
        field.setAccessible(true);
        return (Map<Object, Object>)field.get(this);
    }
    
    private <T> Map<String, Object> getPropertyKeyValues(Class<T> clazz, Object object)
        throws IllegalAccessException
    {
        Field[] fields = clazz.getDeclaredFields();
        Map<String, Object> result = new HashMap<>();
        for (Field field : fields)
        {
            field.setAccessible(true);
            result.put(field.getName(), field.get(object));
        }
        result.remove("beanName");
        return result;
    }
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
        throws BeansException
    {
        this.applicationContext = applicationContext;
    }
}
