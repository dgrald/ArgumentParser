package org.dgrald.argsparser

import scala.annotation.tailrec

/**
 * Created by dylangrald on 4/8/15.
 */
class ArgumentSchema(schema: String) {

  val argumentSchema: List[ArgumentType] = {

    @tailrec
    def possibleArgs(schemaList: List[String], outgoingList: List[ArgumentType]): List[ArgumentType] = schemaList match {
      case List() => outgoingList
      case nextArg +: tail => possibleArgs(tail, outgoingList :+ parseArg(nextArg))
    }

    def parseArg(arg: String): ArgumentType = arg.toCharArray match {
      case Array(letter, '*') => new StringArgument(letter.toString)
      case Array(letter, '#') => new IntegerArgument(letter.toString)
      case Array(letter, '#', '#') => new DoubleArgument(letter.toString)
      case Array(letter, '[', '*', ']') => new StringArrayArgument(letter.toString)
      case Array(letter) => new BooleanArgument(letter.toString)
      case _ => throw new IllegalArgumentException(s"Couldn't find an argument type match for ${arg}.")
    }

    possibleArgs(schema.split(',').toList, List[ArgumentType]())
  }

  def isValidArgument(argument: ArgumentType): Boolean = {
    argumentSchema.contains(argument)
  }
}
