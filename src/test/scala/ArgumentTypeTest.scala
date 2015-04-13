import org.dgrald.argsparser.{IntegerArgument, BooleanArgument}
import org.scalatest.{Matchers, FlatSpec}

/**
 * Created by dylangrald on 4/9/15.
 */
class ArgumentTypeTest extends FlatSpec with Matchers {

  "Boolean type" should "not equal integer type" in {
    val booleanType = new BooleanArgument("t")
    val integerType = new IntegerArgument("t")

    assert(booleanType != integerType)
  }

  it should "equal another boolean type with same arg key" in {
    val boolType1 = new BooleanArgument("t")
    val boolType2 = new BooleanArgument("t")

    assert(boolType1 == boolType2)
  }
}
