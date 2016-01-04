/**
 * 
 */
package net.sleepymouse.amqp.spring.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import net.sleepymouse.amqp.SystemConstants;
import net.sleepymouse.amqp.SystemConstants.*;
import net.sleepymouse.amqp.spring.ServerBoot;

/**
 * @author Alan Smithee
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ServerBoot.class)
@WebAppConfiguration
public class TestSpringMVC
{
	// demo test to ensure unit testing working ok

	final String					BASE_URL	= "http://localhost:8081/";

	@Autowired
	private WebApplicationContext	wac;

	private MockMvc					mockMvc;

	/**
	 * 
	 */
	public TestSpringMVC()
	{
		System.setProperty(SystemConstants.SYSTEM_NAME, LogSystem.SERVER.name() + "_TEST");
		System.setProperty(SystemConstants.SUBSYSTEM_NAME, LogSubSystem.SPRING.name() + "_TEST");
	}

	@Before
	public void setup()
	{
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void testSayHelloWorld() throws Exception
	{
		mockMvc.perform(get("/health").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
				.andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"));
	}

}
