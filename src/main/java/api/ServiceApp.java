package api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

@SpringBootApplication
@EnableScheduling
@RestController
public class ServiceApp implements SchedulingConfigurer {

    @Value("${spring.application.name}")
    private String AppName;

    @Value("${active.pool.size}")
    private Integer PollSize;

    @CrossOrigin
    @RequestMapping(value = "", produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity<?> healthy() {
        HttpHeaders responseHeaders = new HttpHeaders();
        Map result = new HashMap<>();
        try {
            result.put("service", AppName);
            result.put("state", "ok");
            return new ResponseEntity<>(result, responseHeaders, HttpStatus.OK);
        } catch (Exception e) {
            result.put("error", e.getMessage());
            result.put("state", "error");
            return new ResponseEntity<>(result, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setThreadNamePrefix("a-");
        taskScheduler.setPoolSize(PollSize);
        taskScheduler.initialize();
        taskRegistrar.setTaskScheduler(taskScheduler);
    }

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Moscow"));
        SpringApplication.run(ServiceApp.class, args);
    }
}
