package com.team.mystory.admin.visitant.util;

import com.team.mystory.admin.visitant.domain.Visitant;
import com.team.mystory.admin.visitant.repository.VisitantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class VisitantScheduler {

    private final RedisTemplate<String, String> redisTemplate;

    private final VisitantRepository visitantRepository;

    @Scheduled(cron = "0 0 0 1/1 * ? *")
    public void updateVisitorData() {
        Set<String> keys = redisTemplate.keys("*_*");

        for (String key : keys) {
            String[] parts = key.split("_");
            String userIp = parts[0];
            LocalDate date = LocalDate.parse(parts[1]);

            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
            String userAgent = valueOperations.get(key);

            if(!visitantRepository.existsByUserIpAndVisitDate(userIp, date)){
                Visitant visitor = Visitant.builder()
                        .userIp(userIp)
                        .userAgent(userAgent)
                        .visitDate(date)
                        .build();

                visitantRepository.save(visitor);
            }

            redisTemplate.delete(key);
        }
    }

}
