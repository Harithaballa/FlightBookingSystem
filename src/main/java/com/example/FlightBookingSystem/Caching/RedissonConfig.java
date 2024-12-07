package com.example.FlightBookingSystem.Caching;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RedissonConfig {

    @Value("${redis.master.host}")
    private String masterHost;

    @Value("${redis.master.port}")
    private String masterPort;

    @Value("${redis.master.password}")
    private String password;

    @Value("${redis.slave.host}")
    private String slaveHost;

    @Value("${redis.slave.port}")
    private String slavePort;

    public RedissonClient createClient() {
        Config config = new Config();
        config.useMasterSlaveServers()
                .setMasterAddress("redis://"+masterHost+":"+masterPort)
                .addSlaveAddress("redis://"+slaveHost+":"+slavePort)
                .setPassword(password);
        return Redisson.create(config);
    }

}
