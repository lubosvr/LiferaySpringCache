package cz.lubosvr.liferay.spring.cache;

import com.liferay.portal.kernel.cache.SingleVMPoolUtil;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;

/**
 * FactoryBean to create single VM caches for Liferay internal
 * User: LubosVrba
 * Date: 29.2.2016
 */
public class LiferaySingleVMCacheFactoryBean implements FactoryBean<LiferayCache>, BeanNameAware, InitializingBean {

    private String name;
    private boolean blocking;
    private LiferayCache cache;

    public LiferaySingleVMCacheFactoryBean() {
    }

    /**
     * @param name name of the cache in Liferay
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param blocking should the cache be blocking. False by default
     */
    public void setBlocking(boolean blocking) {
        this.blocking = blocking;
    }

    public void setBeanName(String beanName) {
        if(!StringUtils.hasLength(this.name)) {
            this.setName(beanName);
        }
    }

    public void afterPropertiesSet() {
        this.cache = new LiferayCache(SingleVMPoolUtil.getCache(name, blocking));
    }

    public LiferayCache getObject() {
        return this.cache;
    }

    public Class<?> getObjectType() {
        return LiferayCache.class;
    }

    public boolean isSingleton() {
        return true;
    }

}
