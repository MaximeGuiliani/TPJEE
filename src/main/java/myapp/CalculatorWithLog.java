package myapp;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import lombok.Data;

@Service("calculatorWithLog")
@Data
public class CalculatorWithLog implements ICalculator {

    
    @Autowired
    private ILogger logger;

    @PostConstruct
    public void start() {
        if (logger == null) {
            throw new IllegalStateException("null logger");
        }
    }

    @Override
    public int add(int a, int b) {
        logger.log(String.format("add(%d,%d)", a, b));
        return (a + b);
    }
}