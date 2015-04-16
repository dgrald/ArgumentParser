import org.dgrald.argsparser.{Args, ArgumentSchema}
import org.scalatest.{Matchers, FlatSpec}

/**
 * Created by dylangrald on 4/11/15.
 */
class ArgsTest extends FlatSpec with Matchers {

  val args = new Args(Array("-s", "something", "-d", "23", "-k", "a", "string", "array", "-d", "5.23", "-b"), "s*,d#,k[*],b,d##")

  "The args" should "return the correct string arg" in {
    val sStringValue =  args.getStringArgumentValue("s")
    val expected = "something"
    assert(sStringValue == expected)
  }

  it should "return the correct integer arg" in {
    val dIntegerValue = args.getIntegerArgumentValue("d")
    val expected = 23
    assert(dIntegerValue == expected)
  }

  it should "return the correct string array arg" in {
    val kStringArrayValue = args.getStringArrayArgumentValue("k")
    val expected = List("a", "string", "array")
    assert(kStringArrayValue == expected)
  }

  it should "return the correct boolean arg" in {
    val bBooleanValue = args.getBooleanArgumentValue("b")
    val expected = true
    assert(bBooleanValue == expected)
  }

  it should "return the correct double args" in {
    val dDoubleValue = args.getDoubleArgumentValue("d")
    val expected = 5.23
    assert(dDoubleValue == expected)
  }
}
