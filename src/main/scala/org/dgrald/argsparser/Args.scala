package org.dgrald.argsparser

/**
 * Created by dylangrald on 4/10/15.
 */
class Args(args: Array[String], schemaString: String) {

  private val parsedArguments: List[(ArgumentType, Any)] = {
    val argSchema = new ArgumentSchema(schemaString)
    val argParser = new ArgumentParser(args, argSchema)
    argParser.loadedArgs
  }

  def getStringArgumentValue(argName: String): String = {
    val matchingArgs = parsedArguments.find({case (key, value) => key equals new StringArgument(argName)})
    matchingArgs match {
      case Some((_, argValue: String)) => argValue
      case _ => throw new IllegalArgumentException(s"Couldn't find a matching string argument for ${argName}")
    }
  }

  def getIntegerArgumentValue(argName: String): Int = {
    val matchingArgs = parsedArguments.find({case (key, value) => key equals new IntegerArgument(argName)})
    matchingArgs match {
      case Some((_, argValue: Int)) => argValue
      case _ => throw new IllegalArgumentException(s"Couldn't find a matching integer argument for ${argName}")
    }
  }

  def getBooleanArgumentValue(argName: String): Boolean = {
    val matchingArgs = parsedArguments.find({case (key, value) => key equals new BooleanArgument(argName)})
    matchingArgs match {
      case Some((_, argValue: Boolean)) => argValue
      case _ => throw new IllegalArgumentException(s"Couldn't find a matching boolean argument for ${argName}")
    }
  }

  def getDoubleArgumentValue(argName: String): Double = {
    val matchingArgs = parsedArguments.find({case (key, value) => key equals new DoubleArgument(argName)})
    matchingArgs match {
      case Some((_, argValue: Double)) => argValue
      case _ => throw new IllegalArgumentException(s"Couldn't find a matching double argument for ${argName}")
    }
  }

  def getStringArrayArgumentValue(argName: String): List[String] = {
    val matchingArgs = parsedArguments.find({case (key, value) => key equals new StringArrayArgument(argName)})
    matchingArgs match {
      case Some((_, argValue: List[String])) => argValue
      case _ => throw new IllegalArgumentException(s"Couldn't find a matching string array argument for ${argName}")
    }
  }

}
