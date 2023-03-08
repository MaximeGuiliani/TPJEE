package myapp;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service("stderrLogger")
@Qualifier("stderr")
public class StderrLogger implements ILogger {

    @PostConstruct
    public void start() {
        System.err.printf("Start %s\n", this);
    }

    @PreDestroy
    public void stop() {
        System.err.printf("Stop %s\n", this);
    }

    @Override
    public void log(String message) {
        System.err.printf("%tF %1$tR | %s\n", new Date(), message);
    }

}