package ds.hw2.opt

import ckite.rpc.ReadCommand

/**
  * @param stackId id of the stack
  * @return element on the top of the stack
  */
case class sTop(stackId: Int) extends ReadCommand[Option[Int]]
