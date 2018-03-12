package ds.hw2.opt

import ckite.rpc.WriteCommand

/**
  *
  * @param label label of the stack
  * @return id of the stack created corresponding to label
  */
case class sCreate(label: Int) extends WriteCommand[Int]
