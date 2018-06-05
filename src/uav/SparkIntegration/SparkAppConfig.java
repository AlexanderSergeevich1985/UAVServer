package uav.SparkIntegration;

import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

/**
 * Created by administrator on 05.06.18.
 */

@Configuration
@PropertySource("classpath:sparkApp.properties")
public class SparkAppConfig {
    private static Logger log = Logger.getLogger(SparkAppConfig.class.getName());

    @Autowired
    private Environment env;
    @Value("${sparkApp.name:searcher}")
    private String appName;
    @Value("${spark.home}")
    private String sparkHome;
    @Value("${master.uri:local}")
    private String masterUri;
    @Value("${spark.executor.memory}")
    private String seMem;
    @Value("${spark.driver.memory}")
    private String sdMem;
    @Value("${spark.driver.maxResultSize}")
    private String sdMaxRS;
    @Value("${spark.submit.deployMode}")
    private String ssDepMode;

    @Bean
    public SparkConf sparkConf() {
        SparkConf sparkConf = new SparkConf()
                .setAppName(appName)
                .setSparkHome(sparkHome)
                .setMaster(masterUri)
                .set("spark.executor.memory", seMem)
                .set("spark.driver.memory", sdMem)
                .set("spark.driver.maxResultSize", sdMaxRS)
                .set("spark.submit.deployMode", ssDepMode);
        return sparkConf;
    }
    @Bean
    public JavaSparkContext javaSparkContext() {
        return new JavaSparkContext(sparkConf());
    }
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
