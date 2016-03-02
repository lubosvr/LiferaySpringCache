# LiferaySpringCache
Simple library to implement Spring Caching mechanism which use Liferay's cache as caching storage

Example of usage:
    <bean id="cacheManager" class="org.springframework.cache.support.SimpleCacheManager">
        <property name="caches">
            <set>
                <bean class="eu.ibacz.articleread.core.LiferayMultiVMCacheFactoryBean" name="cacheNameMultiNonBlock" />
                <bean class="eu.ibacz.articleread.core.LiferaySingleVMCacheFactoryBean" name="cacheNameSingleBlock">
                    <property name="blocking" value="true" />
                </bean>
            </set>
        </property>
    </bean>
