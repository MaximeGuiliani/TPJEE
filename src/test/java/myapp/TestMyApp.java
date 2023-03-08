package myapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import javax.annotation.Resource;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * Test Spring services
 */
@SpringBootTest
public class TestMyApp {
/*
	@Autowired
	ApplicationContext context;

	@Autowired
	IHello helloByService;

	@Resource(name = "helloService")
	IHello helloByName;
	
	@Autowired
	String bye;

	@Test
	public void testHelloService() {
		assertTrue(helloByService instanceof HelloService);
		helloByService.hello();
	}

	@Test
	public void testHelloByName() {
		assertEquals(helloByService, helloByName);
	}

	@Test
	public void testHelloByContext() {
		assertEquals(helloByName, context.getBean(IHello.class));
		assertEquals(helloByName, context.getBean("helloService"));
	}

	@Test
	public void testBye() {
		assertEquals(bye, "Bye.");
	}
	
	@Autowired
	ILogger loggerService;
	
	@Resource(name = "stderrLogger") 
	ILogger logger;
	
	@Disabled

	@Test
	public void testLoggerService() {
		assertTrue(loggerService instanceof ILogger);
		
		
		ByteArrayOutputStream byteArrayOutputStream= new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(byteArrayOutputStream);
		System.setErr(printStream);
		
		loggerService.log(bye);
		
		assertTrue( byteArrayOutputStream.toString().contains(bye));
		
		
	}
	@Disabled
	@Test 
	public void testLogger() {
		assertEquals(loggerService, logger); 
		
	}
	
	@Disabled
	  @Test public void testLoggerByContext() {
		  assertEquals(logger,context.getBean(ILogger.class)); 
		  assertEquals(logger, context.getBean("stderrLogger")); 
		  }
	 
	  
	  
	    @Autowired
	    @Qualifier("fileLoggerWithConstructor")
	    ILogger fileLoggerWithConstructor;

	    @Test
	    public void testFileLoggerWithConstructor() {
	    	
	    	fileLoggerWithConstructor.log(bye);
	    
	    }
	    
	    
	    @Autowired
	    BeanFileLogger beanFileLoggerWithConstructor;
	    @Disabled
	    @Test
	    public void testBeanFileLoggerWithConstructor() {
	    	
	    	assertEquals(beanFileLoggerWithConstructor.getFileName() , "/tmp/myapp.log");
	    	beanFileLoggerWithConstructor.log(bye);
	    	beanFileLoggerWithConstructor.setFileName("text123.txt");
	    	assertEquals(beanFileLoggerWithConstructor.getFileName(), "text123.txt");
	    
	    } 
	    

	

		@Autowired 
	    ICalculator calculator;

	    @Test
	    public void testCalculator() {
	    	
	        var res = calculator.add(10, 20);
	        assertEquals(30, res);
	        assertTrue(calculator instanceof CalculatorWithLog);
	    }
	     
	    */
	
		@Autowired
    	@Qualifier("calculatorNullLogger")
		CalculatorWithLog calculator;
	   

	    @Test
	    public void testCalculator() {
	        assertTrue(calculator instanceof CalculatorWithLog);
	        assertTrue(calculator.getLogger() instanceof NullLogger);
	    }
	
	    
}

