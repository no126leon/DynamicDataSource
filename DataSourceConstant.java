package com.teamface.datasource;

import org.apache.commons.configuration2.Configuration;

import com.teamface.common.PropertiesConfigObject;

/**
 * @Description:
 * @author: Administrator
 * @date: 2018年9月30日 下午6:20:39
 * @version: 1.0
 */

public class DataSourceConstant
{
    // 获取配置文件实例
    private static final PropertiesConfigObject properties = PropertiesConfigObject.getInstance();
    
    // 获取安全配置对象
    private static final Configuration config = properties.getConfig();
    
    public static boolean testWhileIdle = config.getBoolean("testWhileIdle", true);
    
    public static boolean testOnBorrow = config.getBoolean("testWhileIdle", true);
    
    public static boolean testOnReturn = config.getBoolean("testWhileIdle", true);
    
    public static final int initialSize = config.getInt("initialSize", 5);
    
    public static final int maxActive = config.getInt("maxActive", 30);
    
    public static final long maxWait = config.getLong("maxWait", 60000l);
    
    public static final long timeBetweenEvictionRunsMillis = config.getLong("timeBetweenEvictionRunsMillis", 60000l);
    
    public static final long minEvictableIdleTimeMillis = config.getLong("minEvictableIdleTimeMillis", 300000l);
}
