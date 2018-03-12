package ds.hw2.opt

import ckite.rpc.ReadCommand

/**
  * @param label Label of the stack
  * @return stack id with corresponding label
  */
case class sId (label: Int) extends ReadCommand[Option[Int]]
