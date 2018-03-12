package ds.hw2.opt

import ckite.rpc.ReadCommand
/**
  * @param stackId Id of the stack
  * @return size of the stack with given stackId
  */
// TODO: What to do in case stackId doesn't exist?
case class sSize(stackId: Int) extends ReadCommand[Option[Int]]
