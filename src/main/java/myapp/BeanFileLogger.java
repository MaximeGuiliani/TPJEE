package myapp;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import lombok.Data;

@Service
@Qualifier("beanFileLogger")
@Data
public class BeanFileLogger implements ILogger {

    // parameter: writer name
    private String fileName = "/tmp/myapp.log";

    // property: writer
    private PrintWriter writer;

    // start service
    @PostConstruct
    public void start() {
        if (fileName == null) {
            throw new IllegalStateException("no fileName");
        }
        try {
            OutputStream os = new FileOutputStream(fileName, true);
            this.writer = new PrintWriter(os);
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("bad fileName");
        }
    }

    // stop service
    @PreDestroy
    public void stop() {
        writer.close();
    }

    @Override
    public void log(String message) {
        writer.printf("%tF %1$tR | %s\n", new Date(), message);
    }

}