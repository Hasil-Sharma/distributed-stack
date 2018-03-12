package ds.hw2.opt

import ckite.rpc.WriteCommand

/**
  * @param stackId id of the stack
  * @return removes the element on top and returns it
  */
case class sPop(stackId: Int) extends WriteCommand[Int]
