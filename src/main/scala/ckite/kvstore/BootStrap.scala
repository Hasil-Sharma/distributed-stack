package ckite.kvstore

import ckite.CKiteBuilder
import ckite.rpc.FinagleThriftRpc

import scala.concurrent.Future
import scala.concurrent._
import ExecutionContext.Implicits.global

object BootStrap extends App {

  val ckite_leader = CKiteBuilder().listenAddress("localhost:9091").rpc(FinagleThriftRpc).stateMachine(new KVStore()).bootstrap(true).build
  ckite_leader.start()

  val ckite_follower = CKiteBuilder().listenAddress("localhost:9092").rpc(FinagleThriftRpc).stateMachine(new KVStore()).build
  ckite_follower.start()

  ckite_leader.addMember("localhost:9092")

  val writeFuture:Future[String] = ckite_leader.write(Put("key1","value1"))
  val readFuture:Future[Option[String]] = ckite_leader.read(Get("key1"))

  readFuture.map { value => println(value)}

  ckite_follower.stop()
  ckite_leader.stop()

}
