package org.dgrald.argsparser

/**
 * Created by dylangrald on 4/8/15.
 */
abstract class ArgumentType {
  val argName: String

  def equals(other: ArgumentType): Boolean = {
    if (!other.getClass.equals(this.getClass)) {
      return false
    }

    other.argName.equals(this.argName)
  }
}

case class StringArgument(argName: String) extends ArgumentType

case class BooleanArgument(argName: String) extends ArgumentType

case class DoubleArgument(argName: String) extends ArgumentType

case class IntegerArgument(argName: String) extends ArgumentType

case class StringArrayArgument(argName: String) extends ArgumentType