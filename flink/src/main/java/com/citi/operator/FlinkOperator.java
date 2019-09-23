package com.citi.operator;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.PrintSinkFunction;
import org.apache.flink.util.Collector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author: Matthew
 * @date: 2019/9/23 21:24
 * keyBy
 * 先看定义，通过keyBy，DataStream→KeyedStream。
 * 逻辑上将流分区为不相交的分区。具有相同Keys的所有记录都分配给同一分区。在内部，keyBy（）是使用散列分区实现的。指定键有不同的方法。
 * 此转换返回KeyedStream，其中包括使用被Keys化状态所需的KeyedStream。
 *
 * reduce表示将数据合并成一个新的数据，返回单个的结果值，并且 reduce 操作每处理一个元素总是创建一个新值。
 * 而且reduce方法不能直接应用于SingleOutputStreamOperator对象，也好理解，因为这个对象是个无限的流，对无限的数据做合并，没有任何意义哈！
 * 所以reduce需要针对分组或者一个window(窗口)来执行，也就是分别对应于keyBy、window/timeWindow 处理后的数据，
 * 根据ReduceFunction将元素与上一个reduce后的结果合并，产出合并之后的结果。
 *
 * 参考：https://www.jianshu.com/p/0cdf1112d995
 */
public class FlinkOperator {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

//        List<String> list = new ArrayList<String>();
//        list.add("aa");list.add("bb");list.add("cc");
//        DataStream<String> source = env.fromCollection(list);//fromElements(1,2,2,2,5);
//
//        DataStream<String> so = source.flatMap(new FlatMapFunction<String, String>() {
//            @Override
//            public void flatMap(String in, Collector<String> out) throws Exception {
//                System.out.println("in=========="+in);
//                out.collect(in);
//            }
//        });


//         this can be used in a streaming program like this (assuming we have a StreamExecutionEnvironment env)
//        env.fromElements(Tuple2.of(2L, 3L), Tuple2.of(1L, 5L), Tuple2.of(1L, 7L), Tuple2.of(2L, 4L), Tuple2.of(1L, 2L))
//                .keyBy(0) // 以数组的第一个元素作为key
//                .map(new MapFunction<Tuple2<Long, Long>, String>() {
//                    @Override
//                    public String map(Tuple2<Long, Long> value) throws Exception {
//                        return "key:" + value.f0 + ",value:" + value.f1;
//                    }
//                })//与下面的等价
//                .map((MapFunction<Tuple2<Long, Long>, String>) longLongTuple2 -> "key:" + longLongTuple2.f0 + ",value:" + longLongTuple2.f1)
//                .print();


//        env.fromElements(Tuple2.of(2L, 3L), Tuple2.of(1L, 5L), Tuple2.of(1L, 7L), Tuple2.of(2L, 4L), Tuple2.of(1L, 2L))
//                .keyBy(0) // 以数组的第一个元素作为key
//                .reduce(new ReduceFunction<Tuple2<Long, Long>>() {
//                    @Override
//                    public Tuple2<Long, Long> reduce(Tuple2<Long, Long> value1, Tuple2<Long, Long> value2) throws Exception {
//                        return new Tuple2(value1.f0,value1.f1+value2.f1);
//                    }
//                })//与下面的等价
//                .reduce((ReduceFunction<Tuple2<Long, Long>>) (t2, t1) -> new Tuple2<>(t1.f0, t2.f1 + t1.f1)) // value做累加
//                .print();

        KeyedStream keyedStream= env.fromElements(Tuple2.of(2L, 3L), Tuple2.of(1L, 5L), Tuple2.of(1L, 7L), Tuple2.of(2L, 4L), Tuple2.of(1L, 2L))
                .keyBy(0); // 以数组的第一个元素作为key

        SingleOutputStreamOperator<Tuple2> sumStream = keyedStream.sum(0);
        sumStream.addSink(new PrintSinkFunction<>());

//        so.print();




        env.execute();
    }

}
