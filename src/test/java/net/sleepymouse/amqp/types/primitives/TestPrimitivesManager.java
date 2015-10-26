package net.sleepymouse.amqp.types.primitives;

import static org.junit.Assert.fail;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.sleepymouse.amqp.spring.managers.logManager.LogManager;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { PrimitivesManager.class, LogManager.class })
public class TestPrimitivesManager
{

	@Before
	public void setUp() throws Exception
	{}

	@After
	public void tearDown() throws Exception
	{}

	@Test
	public final void test()
	{
		fail("Not yet implemented"); // TODO
	}

}
