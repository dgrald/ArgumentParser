import org.dgrald.argsparser._
import org.scalatest.{Matchers, FlatSpec}

/**
 * Created by dylangrald on 4/9/15.
 */
class ArgumentParserTest extends FlatSpec with Matchers {

  "The argument parser should" should "parse booleans, strings, and integers" in {
    val schema = new ArgumentSchema("s#,r*,t")
    val args = Array("-r", "download", "-s", "25", "-t")
    val argsParser = new ArgumentParser(args, schema)
    val actualParsedArgs = argsParser.loadedArgs

    val expected = List((new StringArgument("r"), "download"), (new IntegerArgument("s"), 25), (new BooleanArgument("t"), true))

    assert(actualParsedArgs == expected)
  }

  it should "parse doubles and String arrays correctly" in {
    val schema = new ArgumentSchema("d##,t[*],r#")
    val args = Array("-d", "5.67", "-t", "a", "string", "array")
    val argsParser = new ArgumentParser(args, schema)
    val actualParsedArgs = argsParser.loadedArgs

    val expected = List((new DoubleArgument("d"), 5.67), (new StringArrayArgument("t"), List("a", "string", "array")))

    assert(actualParsedArgs == expected)
  }

  it should "throw an exception when an argument is passed that is not in the schema" in {
    an [IllegalArgumentException] should be thrownBy  {
      val args = Array("-d", "5.67", "-v", "a", "string", "array")
      val schema = new ArgumentSchema("d##,t[*],r#")
      val argsParser = new ArgumentParser(args, schema)
      argsParser.loadedArgs
    }
  }

  it should "throw an argument exception when an argument is declared more than once" in {
    val args = Array("-d", "5.67", "-d", "6.723")
    the [IllegalArgumentException] thrownBy {
      val schema = new ArgumentSchema("d##,t[*]")
      val argsParser = new ArgumentParser(args, schema)
      argsParser.loadedArgs
    } should have message s"There are two instances of the argument name d declared in the args '${args.mkString(", ")}'"
  }
}
