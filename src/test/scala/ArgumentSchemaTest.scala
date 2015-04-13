import org.dgrald.argsparser.{IntegerArgument, BooleanArgument, StringArgument, ArgumentSchema}
import org.scalatest._

/**
 * Created by dylangrald on 4/8/15.
 */
class ArgumentSchemaTest extends FlatSpec with Matchers {

  "The argument schema" should "parse booleans, strings, and integers correctly" in {
    val schema = new ArgumentSchema("l*,r,a#").argumentSchema
    val expected = List(new StringArgument("l"), new BooleanArgument("r"), new IntegerArgument("a"))

    assert(schema == expected)
  }

  it should "throw an exception with improper schema arguments" in {
    the[IllegalArgumentException] thrownBy {
      val schema = new ArgumentSchema("a*,r%")
      schema.argumentSchema
    } should have message "Couldn't find an argument type match for r%."
  }

}
