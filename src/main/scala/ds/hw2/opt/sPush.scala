package ds.hw2.opt

import ckite.rpc.WriteCommand

case class sPush(stackId: Int, item: Int) extends WriteCommand[Unit]
