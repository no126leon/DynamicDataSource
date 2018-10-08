package com.teamface.datasource;

public class DataSourceBeanBuilder
{
    private final String beanName;
    
    private final String databaseIP;
    
    private final String databasePort;
    
    private final String databaseName;
    
    private final String username;
    
    private final String password;
    
    private boolean testWhileIdle;
    
    private boolean testOnBorrow;
    
    private boolean testOnReturn;
    
    private final int initialSize;
    
    private final int maxActive;
    
    private final long maxWait;
    
    private final long timeBetweenEvictionRunsMillis;
    
    private final long minEvictableIdleTimeMillis;
    
    private final String validationQuery = "select 1";
    
    private final String driverClassName = "org.postgresql.Driver";
    
    private static final String URL_FORMATTER = "jdbc:postgresql://%s:%s/%s?zeroDateTimeBehavior=convertToNull&userUnicode=true&amp;characterEncoding=utf8;";
    
    public DataSourceBeanBuilder(String beanName, String databaseIP, String databasePort, String databaseName, String username, String password)
    {
        this.beanName = beanName;
        this.databaseIP = databaseIP;
        this.databasePort = databasePort;
        this.databaseName = databaseName;
        this.username = username;
        this.password = password;
        this.testWhileIdle = DataSourceConstant.testWhileIdle;
        this.testOnBorrow = DataSourceConstant.testOnBorrow;
        this.testOnReturn = DataSourceConstant.testOnReturn;
        this.initialSize = DataSourceConstant.initialSize;
        this.maxActive = DataSourceConstant.maxActive;
        this.maxWait = DataSourceConstant.maxWait;
        this.timeBetweenEvictionRunsMillis = DataSourceConstant.timeBetweenEvictionRunsMillis;
        this.minEvictableIdleTimeMillis = DataSourceConstant.minEvictableIdleTimeMillis;
    }
    
    public String getBeanName()
    {
        return beanName;
    }
    
    public String getUsername()
    {
        return username;
    }
    
    public String getPassword()
    {
        return password;
    }
    
    public boolean isTestWhileIdle()
    {
        return testWhileIdle;
    }
    
    public boolean isTestOnBorrow()
    {
        return testOnBorrow;
    }
    
    public boolean isTestOnReturn()
    {
        return testOnReturn;
    }
    
    public int getInitialSize()
    {
        return initialSize;
    }
    
    public int getMaxActive()
    {
        return maxActive;
    }
    
    public long getMaxWait()
    {
        return maxWait;
    }
    
    public long getTimeBetweenEvictionRunsMillis()
    {
        return timeBetweenEvictionRunsMillis;
    }
    
    public long getMinEvictableIdleTimeMillis()
    {
        return minEvictableIdleTimeMillis;
    }
    
    public String getValidationQuery()
    {
        return validationQuery;
    }
    
    public String getDriverClassName()
    {
        return driverClassName;
    }
    
    public String getUrl()
    {
        return String.format(URL_FORMATTER, this.databaseIP, this.databasePort, this.databaseName);
    }
    
    @Override
    public String toString()
    {
        return "DataSourceBeanBuilder{" + "driverClassName='" + driverClassName + '\'' + ", databaseIP='" + databaseIP + '\'' + ", databasePort='" + databasePort + '\''
            + ", databaseName='" + databaseName + '\'' + ", username='" + username + '\'' + ", password='" + password + '\'' + ", validationQuery='" + validationQuery + '\''
            + ", testOnBorrow=" + testOnBorrow + '}';
    }
}
