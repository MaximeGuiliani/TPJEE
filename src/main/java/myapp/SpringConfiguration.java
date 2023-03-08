package myapp;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Configuration des services logiciels déployés par Spring
 */
@Configuration
public class SpringConfiguration {
	
	@Bean
	public String bye() {
		return "Bye.";
	}
	
	@Bean
	@Primary
	@Qualifier("fileLoggerWithConstructor")
	public ILogger fileLoggerWithConstructor(@Value("${logfile}") String logFile) {
	    return new FileLogger(logFile);
	}
	
	
	@Bean
	@Qualifier("calculatorNullLogger")
	public ICalculator calculatorNullLogger() {
		CalculatorWithLog calculatorWithLog = new CalculatorWithLog();
		calculatorWithLog.setLogger(new NullLogger());
	    return calculatorWithLog;
	}
	
}

