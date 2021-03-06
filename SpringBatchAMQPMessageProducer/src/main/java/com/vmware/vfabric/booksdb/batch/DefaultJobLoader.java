/*
 * Copyright (c) 2012. Written by Pronam Chatterjee. Copying and Use prohibited unless explicit permission is granted.
 */

package com.vmware.vfabric.booksdb.batch;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.DuplicateJobException;
import org.springframework.batch.core.configuration.ListableJobLocator;
import org.springframework.batch.core.configuration.support.ApplicationContextFactory;
import org.springframework.batch.core.configuration.support.JobLoader;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyAccessorUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;

public class DefaultJobLoader implements JobLoader, ApplicationContextAware {

    private ListableJobLocator registry;

    private ApplicationContext applicationContext;

    private Map<String, String> configurations = new HashMap<String, String>();

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void setRegistry(ListableJobLocator registry) {
        this.registry = registry;
    }

    public Map<String, String> getConfigurations() {
        Map<String, String> result = new HashMap<String, String>(configurations);
        for (String jobName : registry.getJobNames()) {
            try {
                Job configuration = registry.getJob(jobName);
                String name = configuration.getName();
                if (!configurations.containsKey(name)) {
                    result.put(name, "<unknown path>: " + configuration);
                }
            }
            catch (NoSuchJobException e) {
                throw new IllegalStateException("Registry could not locate its own job (NoSuchJobException).");
            }
        }
        return result;
    }

    public void loadResource(String path) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { path },
                applicationContext);
        String[] names = context.getBeanNamesForType(Job.class);
        for (String name : names) {
            configurations.put(name, path);
        }
    }

    public Object getJobConfiguration(String name) {
        try {
            return registry.getJob(name);
        }
        catch (NoSuchJobException e) {
            return null;
        }
    }

    public Object getProperty(String path) {
        int index = PropertyAccessorUtils.getFirstNestedPropertySeparatorIndex(path);
        BeanWrapperImpl wrapper = createBeanWrapper(path, index);
        String key = path.substring(index + 1);
        return wrapper.getPropertyValue(key);
    }

    public void setProperty(String path, String value) {
        int index = PropertyAccessorUtils.getFirstNestedPropertySeparatorIndex(path);
        BeanWrapperImpl wrapper = createBeanWrapper(path, index);
        String key = path.substring(index + 1);
        wrapper.setPropertyValue(key, value);
    }

    private BeanWrapperImpl createBeanWrapper(String path, int index) {
        Assert.state(index > 0, "Path must be nested, e.g. bean.value");
        String name = path.substring(0, index);
        Object bean = getJobConfiguration(name);
        Assert.notNull(bean, "No JobConfiguration exists with name=" + name);
        return new BeanWrapperImpl(bean);
    }

    @Override
    public Collection<Job> load(ApplicationContextFactory applicationContextFactory) throws DuplicateJobException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Collection<Job> reload(ApplicationContextFactory applicationContextFactory) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void clear() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
