package ds.hw2

import java.nio.ByteBuffer

import ckite.statemachine.StateMachine
import ckite.util.Serializer
import ds.hw2.opt._

import scala.collection.mutable
import ckite.util.Logging

class FTStack extends StateMachine with Logging {

  private val labelIdMapKey = "labelIdMap"
  private val idStackMapKey = "idStackMap"

  private var map =  mutable.Map(
    // Making it Stack[Int] for easy of de/serialization
    labelIdMapKey -> mutable.Map[Int, mutable.Stack[Int]](),
    idStackMapKey -> mutable.Map[Int, mutable.Stack[Int]]()
  )
  private var lastIndex: Long = 0

  override def getLastAppliedIndex: Long = lastIndex

  override def applyRead = {

    case sId(label) => {
      val labelIdMap = map(labelIdMapKey)
      val stack = labelIdMap(label)
      Some(stack.top)
    }

    case sSize(stackId) => {
      val idStackMap = map(idStackMapKey)
      idStackMap.getOrElse(stackId, None) match {
        case (stack: mutable.Stack[_]) => Some(stack.size)
        case _ => None
      }
    }

    case sTop(stackId) => {
      val idStackMap = map(idStackMapKey)
      logger.info(s"In sTop method stackId: $stackId")
      idStackMap.getOrElse(stackId, None) match {
        case (stack: mutable.Stack[_]) => Some (stack.top)
        case _ => None
      }
    }
  }

  override def applyWrite = {

    case (index, sCreate(label: Int)) => {
      logger.info(s"Creating a stack with label: $label")
      val labelIdMap = map(labelIdMapKey)
      val idStackMap = map(idStackMapKey)

      labelIdMap.put(label, mutable.Stack[Int]())
      labelIdMap(label).push(label) // TODO: clarify if label and id can be same

      idStackMap.put(label, mutable.Stack[Int]())
      lastIndex = index
      logger.info(s"Created a stack with label: $label")
      label
    }
    case (index, sPop(stackId: Int)) => {
      val idStackMap = map(idStackMapKey)
      lastIndex = index
      idStackMap(stackId).pop
    }
    case (index, sPush(stackId: Int, item: Int)) => {
      logger.info(s"Pushing $item to $stackId")
      val idStackMap = map(idStackMapKey)
      idStackMap(stackId).push(item)
      lastIndex = index
      logger.info(s"Push completed")
    }
  }

  override def restoreSnapshot(byteBuffer: ByteBuffer) = {
    map = Serializer.deserialize[mutable.Map[String, mutable.Map[Int, mutable.Stack[Int]]]](byteBuffer.array())
  }

  override def takeSnapshot(): ByteBuffer = ByteBuffer.wrap(Serializer.serialize(map))

}
