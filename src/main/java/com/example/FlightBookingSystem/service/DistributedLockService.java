package com.example.FlightBookingSystem.service;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class DistributedLockService {

    private final RedissonClient redissonClient;
    private static final int LOCK_TIMEOUT_MINUTE = 15;

    public DistributedLockService(RedissonClient redissonClient){
        this.redissonClient = redissonClient;
    }

    public boolean tryLockSeats(long tripId, List<Long> selectedSeats) {
        for(long seat:selectedSeats){
            if(!lockSeat(tripId,seat))
                return false;
        }
        return true;
    }

    public boolean lockSeat(long tripId, long seatNumber) {
        String lockKey = generateKey(tripId,seatNumber);
        RLock lock = redissonClient.getLock(lockKey);

        try {
            return lock.tryLock(LOCK_TIMEOUT_MINUTE, LOCK_TIMEOUT_MINUTE, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    public void unlockSeat(long tripId, long seatNumber) {
        String lockKey = generateKey(tripId,seatNumber);
        RLock lock = redissonClient.getLock(lockKey);

        if (lock.isHeldByCurrentThread()) {
            lock.unlock(); // Release the lock
        }
    }

    private String generateKey(long tripId, long seatNumber){
        return  "lock:seat:" + tripId + ":" + seatNumber;
    }

    public void unlockSeats(long tripId, List<Long> selectedSeats) {
        for(long seat:selectedSeats){
            unlockSeat(tripId,seat);
        }
    }
}
