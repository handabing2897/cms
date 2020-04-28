package com.sunshine.cms.system.db;

import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.*;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * DESC：
 *  DB数据源、Mybatis|Plugins、事务
 * @author handabing
 * DATE：2020/4/23
 * TIME：10:25 上午
 */
@Configuration
@MapperScan(basePackages = DataConfig.mapperPackage,sqlSessionFactoryRef = "sqlSessionFactory")
@EnableTransactionManagement
public class DataConfig {

    private Logger logger = LoggerFactory.getLogger(DataConfig.class);

    public final static String mapperPackage = "com.sunshine.cms.mapper";
    public final static String modelPackage = "com.sunshine.cms.entity";
    public final static String xmlMapperLocation = "classpath*:mapper/**/*.xml";

    @Bean(name = "sqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource){
        try {
            SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
            bean.setDataSource(dataSource);
            //使用jar包 必须用VFS
            bean.setVfs(SpringBootVFS.class);
            //实体类位置
            bean.setTypeAliasesPackage(modelPackage);
            //设置mapper.xml文件所在位置
            Resource[] resource = new PathMatchingResourcePatternResolver().getResources(xmlMapperLocation);
            bean.setMapperLocations(resource);

            //添加分页插件
            PageInterceptor pageHelper = new PageInterceptor();
            Properties properties = new Properties();
            properties.setProperty("helperDialect","mysql");//数据库方言，请替换为postgresql/mysql
            properties.setProperty("supportMethodsArguments", "true");
            properties.setProperty("params", "pageNum=pageNo;pageSize=pageSize;");
            pageHelper.setProperties(properties);
            Interceptor[] plugins = new Interceptor[]{pageHelper};
            bean.setPlugins(plugins);
            return bean.getObject();
        } catch (Exception e) {
            logger.error("Database sqlSessionFactory create error!", e);
            return null;
        }

    }

    @Bean(name = "sqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * 事物管理
     * @param dataSource
     * @return
     */
    @Bean(name = "platformTransactionManager")
    @Primary
    public PlatformTransactionManager platformTransactionManager(DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * 声明式事务
     *
     * @param platformTransactionManager
     * @return
     */
    //@Bean(name = "txInterceptor")
    //@Primary
    public TransactionInterceptor transactionInterceptor(@Qualifier("platformTransactionManager") PlatformTransactionManager platformTransactionManager) {
        logger.info("txInterceptor init...", getClass());
        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        /* 只读事务，不做更新操作 */
        RuleBasedTransactionAttribute readOnlyTx = new RuleBasedTransactionAttribute();
        readOnlyTx.setReadOnly(true);
        readOnlyTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);

        /* 当前存在事务就使用当前事务，当前不存在事务就创建一个新的事务 */
        RuleBasedTransactionAttribute requiredTx = new RuleBasedTransactionAttribute();
        requiredTx.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        requiredTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        requiredTx.setTimeout(5);
        Map<String, TransactionAttribute> txMap = new HashMap<>();
        txMap.put("add*", requiredTx);
        txMap.put("save*", requiredTx);
        txMap.put("insert*", requiredTx);
        txMap.put("update*", requiredTx);
        txMap.put("delete*", requiredTx);
        txMap.put("get*", readOnlyTx);
        txMap.put("query*", readOnlyTx);
        source.setNameMap(txMap);

        TransactionInterceptor interceptor = new TransactionInterceptor(platformTransactionManager, source);
        return interceptor;
    }

    //@Bean
    public Advisor txAdviceAdvisor(@Qualifier("txInterceptor") TransactionInterceptor transactionInterceptor) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution (* com.jf.service.*.*(..))");
        return new DefaultPointcutAdvisor(pointcut, transactionInterceptor);
    }

}
