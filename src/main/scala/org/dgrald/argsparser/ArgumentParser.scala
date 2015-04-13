package org.dgrald.argsparser

import scala.annotation.tailrec

/**
 * Created by dylangrald on 4/8/15.
 */
class ArgumentParser(args: Array[String], argSchema: ArgumentSchema) {

  val loadedArgs: List[(ArgumentType, Any)] = {

    @tailrec
    def parseArguments(argumentToParse: List[String], argsToReturn: List[(ArgumentType, Any)]): List[(ArgumentType, Any)] = argumentToParse.toList match {
      case List() => argsToReturn
      case argName +: tail => {
        if(!argName.startsWith("-")) {
          throw new IllegalArgumentException(s"Could not parse the argument ${argName} from the args ${args}")
        }

        val argsValue = tail.takeWhile(element => !element.startsWith("-"))
        val restOfArgs = tail.diff(argsValue)

        parseArguments(restOfArgs, argsToReturn :+ parseNextArguments(argName.dropWhile(char => char == '-'), argsValue))
      }
    }

    def parseNextArguments(argName: String, argsValue: List[String]): (ArgumentType, Any) = argsValue match {
      case List() => {
        if(isValidBooleanArgument(argName)) {
          return (new BooleanArgument(argName), true)
        }
        throw new IllegalArgumentException(s"Could not parse the argument ${argName} from the args ${args}")
      }
      case _ => {
        parseArgumentValue(argName, argsValue)
      }
    }

    parseArguments(args.toList, List())

  }

  private def parseArgumentValue(argName: String, inputArgValue: List[String]): (ArgumentType, Any) = inputArgValue match {
    case List(firstArgument) => {
      parseSingleArgumentValue(firstArgument, argName)
    }
    case _ => {
      if(isValidStringArrayArgument(argName)) {
        return (new StringArrayArgument(argName), inputArgValue)
      }
      throw new IllegalArgumentException(s"The argument ${argName} with value ${inputArgValue.mkString(", ")} was not part of the schema ${argSchema}")
    }
  }

  private def parseSingleArgumentValue(argValue: String, argName: String): (ArgumentType, Any) = {
    if(isValidDoubleArgument(argName, argValue)) {
      return (new DoubleArgument(argName), argValue.toDouble)
    } else if (isValidIntegerArgument(argName, argValue)) {
      return (new IntegerArgument(argName), argValue.toInt)
    } else if (isValidStringArgument(argName)){
      return (new StringArgument(argName), argValue)
    }
    throw new IllegalArgumentException(s"The argument ${argName} with value ${argValue} was not part of the schema ${argSchema}")

  }

  private def isValidDoubleArgument(argName: String, element: String): Boolean = {
    element.matches("[0-9]+.[0-9]+") && argSchema.isValidArgument(new DoubleArgument(argName))
  }

  private def isValidIntegerArgument(argName: String, element: String): Boolean = {
    element.matches("[0-9]+") && argSchema.isValidArgument(new IntegerArgument(argName))
  }

  private def isValidStringArgument(argName: String): Boolean = {
    argSchema.isValidArgument(new StringArgument(argName))
  }

  private def isValidStringArrayArgument(argName: String): Boolean = {
    argSchema.isValidArgument(new StringArrayArgument(argName))
  }

  private def isValidBooleanArgument(argName: String): Boolean = {
    argSchema.isValidArgument(new BooleanArgument(argName))
  }

}
