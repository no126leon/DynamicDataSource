package com.teamface.datasource;

final class DataSourceBean
{
    private final String beanName;
    
    private final String validationQuery;
    
    private final String driverClassName;
    
    private final String url;
    
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
    
    public DataSourceBean(DataSourceBeanBuilder beanBuilder)
    {
        this.beanName = beanBuilder.getBeanName();
        this.validationQuery = beanBuilder.getValidationQuery();
        this.driverClassName = beanBuilder.getDriverClassName();
        this.url = beanBuilder.getUrl();
        this.username = beanBuilder.getUsername();
        this.password = beanBuilder.getPassword();
        this.testWhileIdle = beanBuilder.isTestWhileIdle();
        this.testOnBorrow = beanBuilder.isTestOnBorrow();
        this.testOnReturn = beanBuilder.isTestOnReturn();
        this.initialSize = beanBuilder.getInitialSize();
        this.maxActive = beanBuilder.getMaxActive();
        this.maxWait = beanBuilder.getMaxWait();
        this.timeBetweenEvictionRunsMillis = beanBuilder.getTimeBetweenEvictionRunsMillis();
        this.minEvictableIdleTimeMillis = beanBuilder.getMinEvictableIdleTimeMillis();
    }
    
    public String getBeanName()
    {
        return beanName;
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
        return url;
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
    
    @Override
    public String toString()
    {
        return "DataSourceBeanBuilder{" + "driverClassName='" + driverClassName + '\'' + ", url='" + url + '\'' + ", username='" + username + '\'' + ", password='" + password
            + '\'' + ", validationQuery='" + validationQuery + '\'' + ", testOnBorrow=" + testOnBorrow + '}';
    }
}
