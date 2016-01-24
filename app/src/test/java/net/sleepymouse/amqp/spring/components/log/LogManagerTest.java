package net.sleepymouse.amqp.spring.components.log;

import javax.inject.Inject;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import net.sleepymouse.amqp.spring.config.ApplicationConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = ApplicationConfig.class)
public class LogManagerTest
{
	// demo test to ensure unit testing working ok

	@Mock
	private PropertiesFactoryBean	pfb;

	@InjectMocks
	@Inject
	private ILogManager				logManager;

	@Before
	public void setUp() throws Exception
	{}

	@After
	public void tearDown() throws Exception
	{}

	@Test
	public final void testLogRegisteredComponents()
	{
		logManager.logRegisteredComponents();
	}

}
