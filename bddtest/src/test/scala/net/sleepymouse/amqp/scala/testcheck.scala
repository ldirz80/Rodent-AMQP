package net.sleepymouse.amqp.scala

import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.Matchers

@RunWith(classOf[JUnitRunner])
class MySpec extends FlatSpec with Matchers {

  "this test" should "run without crashing" in {
    val someValue = false;

    assert(someValue === false)
  }
}