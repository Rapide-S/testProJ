package com.citi.time;

import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;

/**
 * @author: Matthew
 * @date: 2019/9/25 23:51
 */
public class EventTime {
    //  获取执行环境
    val env = StreamExecutionEnvironment.getExecutionEnvironment
env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    //  创建 SocketSource
    val stream = env.socketTextStream("localhost", 11111)
    // 对 对 stream  进行处理并按 key  聚合
    val streamKeyBy = stream.assignTimestampsAndWatermarks(
            new BoundedOutOfOrdernessTimestampExtractor[String](Time.milliseconds(3000)) {
        override def extractTimestamp(element: String): Long = {
                val sysTime = element.split(" ")(0).toLong
                println(sysTime)
                sysTime
        }}).map(item => (item.split(" ")(1), 1)).keyBy(0)
    //  引入滚动窗口
    val streamWindow = streamKeyBy.window(TumblingEventTimeWindows.of(Time.seconds(10)))
    //  执行聚合操作
    val streamReduce = streamWindow.reduce(
            (item1, item2) => (item1._1, item1._2 + item2._2)
}
