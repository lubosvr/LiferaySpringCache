# LiferaySpringCache
Simple library to implement Spring Caching mechanism which use Liferay's cache as caching storage

Example of usage:<br>
    &lt;bean id="cacheManager" class="org.springframework.cache.support.SimpleCacheManager"&gt;<br>
        &nbsp;&lt;property name="caches"&gt;<br>
            &nbsp;&nbsp;&lt;set&gt;<br>
                &nbsp;&nbsp;&nbsp;&lt;bean class="cz.lubosvr.liferay.spring.cache.LiferayMultiVMCacheFactoryBean" name="cacheNameMultiNonBlock" /&gt;<br>
                &nbsp;&nbsp;&nbsp;&lt;bean class="cz.lubosvr.liferay.spring.cache.LiferaySingleVMCacheFactoryBean" name="cacheNameSingleBlock"&gt;<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&lt;property name="blocking" value="true" /&gt;<br>
                &nbsp;&nbsp;&nbsp;&lt;/bean&gt;<br>
            &nbsp;&nbsp;&lt;/set&gt;<br>
        &nbsp;&lt;/property&gt;<br>
    &lt;/bean&gt;<br>
