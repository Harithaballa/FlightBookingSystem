package com.example.FlightBookingSystem.Caching;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

    @Value("${redis.master.host}")
    private String masterHost;

    @Value("${redis.master.port}")
    private String masterPort;

//    @Value("${redis.master.password}")
//    private String password;
//
//    @Value("${redis.slave.host}")
//    private String slaveHost;
//
//    @Value("${redis.slave.port}")
//    private String slavePort;

    @Bean
    public RedissonClient createClient() {
        Config config = new Config();
        config.useSingleServer()
                //.setMasterAddress("redis://"+masterHost+":"+masterPort)
                //.addSlaveAddress("redis://"+slaveHost+":"+slavePort)
                .setAddress("redis://"+masterHost+":"+masterPort);
        return Redisson.create(config);
    }

}
