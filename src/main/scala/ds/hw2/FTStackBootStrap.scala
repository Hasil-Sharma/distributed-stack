package ds.hw2

import ckite.CKiteBuilder
import ckite.rpc.FinagleThriftRpc
import ckite.util.Logging
import ds.hw2.opt._

import scala.concurrent.Future
import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.util.Success
import scala.concurrent.duration._

object FTStackBootStrap extends App with Logging {
  val ckiteLeader = CKiteBuilder()
    .listenAddress("localhost:9091")
    .rpc(FinagleThriftRpc)
    .stateMachine(new FTStack())
    .bootstrap(true).build

  ckiteLeader.start()
  // Starting the leader


  var stackIdFuture: Future[Int] = ckiteLeader.write(sCreate(1))
  while (!stackIdFuture.isCompleted) {

  }

  var pushFuture: Future[Unit] = ckiteLeader.write(sPush(1, 10))
  while (!pushFuture.isCompleted) {

  }

  var topElemFuture: Future[Option[Int]] = ckiteLeader.read(sTop(1))
  topElemFuture.foreach { topElem => println(s"1: Top element on stack with id: 1 is ${topElem.getOrElse(None)}") }


  topElemFuture = ckiteLeader.read(sTop(2))
  topElemFuture.foreach { topElem => println(s"2: Top element on stack with id: 2 is ${topElem.getOrElse(None)}") }

  stackIdFuture = ckiteLeader.write(sCreate(2))

  while (!stackIdFuture.isCompleted) {

  }

  pushFuture = ckiteLeader.write(sPush(2, 20))
  while (!pushFuture.isCompleted) {

  }

  topElemFuture = ckiteLeader.read(sTop(2))
  topElemFuture.foreach { topElem => println(s"3: Top element on stack with id: 2 is ${topElem.getOrElse(None)}") }

  pushFuture = ckiteLeader.write(sPush(2, 30))
  while (!pushFuture.isCompleted) {

  }

  topElemFuture = ckiteLeader.read(sTop(2))
  topElemFuture.foreach { topElem => println(s"4: Top element on stack with id: 2 is ${topElem.getOrElse(None)}") }

  var sizeFuture: Future[Option[Int]] = ckiteLeader.read(sSize(2))
  sizeFuture.foreach { size => println(s"5: Size of stack with id: 2 is ${size.getOrElse(None)}") }

  sizeFuture = ckiteLeader.read(sSize(1))
  sizeFuture.foreach { size => println(s"6: Size of stack with id: 1 is ${size.getOrElse(None)}") }

  var popFuture: Future[Int] = ckiteLeader.write(sPop(2))
  while(!popFuture.isCompleted){

  }

  popFuture.foreach {elem => println(s"7: Popped stack with id: 2 = $elem")}

  sizeFuture = ckiteLeader.read(sSize(2))
  sizeFuture.foreach { size => println(s"8: Size of stack with id: 2 is ${size.getOrElse(None)}") }

}
