package com.blu.collection.jmh;

import com.carrotsearch.hppc.CharByteHashMap;
import com.carrotsearch.hppc.CharIntHashMap;
import com.carrotsearch.hppc.IntHashSet;
import com.carrotsearch.hppc.IntIntHashMap;
import com.carrotsearch.hppc.cursors.IntCursor;
import com.carrotsearch.hppc.cursors.IntIntCursor;
import it.unimi.dsi.fastutil.ints.*;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import static com.carrotsearch.hppc.Containers.*;

import java.util.*;

/**
 * Created by shamim on 10/01/16.
 */

public class CollectionBenchMarking {
    private static final int CNT = 10000;
    // FastUtil ArrayList for primitive integer
    @Benchmark
    @BenchmarkMode( Mode.Throughput )
    public void testArrayListofIntegerJDK8(){
        final List<Integer> integerArrayList = new ArrayList<Integer>(CNT);
        for(int i = 0; i < CNT; i++){
            // add items
            integerArrayList.add(i); // boxing
        }
        Iterator<Integer> ite = integerArrayList.iterator();
        while(ite.hasNext()){
            int inValue = ite.next(); // unboxing
        }
    }
    // FastUtil ArrayList for primitive integer
    @Benchmark
    @BenchmarkMode( Mode.Throughput )
    public void testPrimitiveArrayListFastUtils(){
        final it.unimi.dsi.fastutil.ints.IntArrayList intArrayList = new IntArrayList(CNT);
        for(int i = 0; i < CNT; i++){
            // add items
            intArrayList.add(i);
        }
        IntListIterator ite = intArrayList.iterator();
        while(ite.hasNext()){
            int i;
            i = ite.nextInt();
        }
    }
    //HPPC ArrayList of int primitive type
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void testPrimitiveArrayListHppc(){
        final com.carrotsearch.hppc.IntArrayList intArrayList = new com.carrotsearch.hppc.IntArrayList(CNT);
        for(int i = 0; i < CNT; i++){
            intArrayList.add(i);
        }
        for(IntCursor c : intArrayList){ // cursor
            int i =  c.value;
        }

    }
    /**
     * Benchmarking SET
     * */
     @Benchmark
     @BenchmarkMode( Mode.Throughput )
     public void testIntSetJDK8(){
        final Set<Integer> set = new HashSet<Integer>(CNT);
        // add elements
        for(int i=0; i < CNT; i++){
            set.add(i); // boxing
        }
        // read
        Iterator<Integer> ite = set.iterator();
        while(ite.hasNext()){
            int i = ite.next(); // unboxing
        }
    }
    @Benchmark
    @BenchmarkMode( Mode.Throughput )
    public void testIntSetFastUtils(){
        final it.unimi.dsi.fastutil.ints.IntSet set = new IntArraySet(CNT);

        for(int i = 0; i < CNT; i++){
            set.add(i);
        }
        IntIterator ite = set.iterator();
        while(ite.hasNext()){
            int i = ite.nextInt();
        }
    }
    @Benchmark
    @BenchmarkMode( Mode.Throughput )
    public void testIntSetHppc(){
        final com.carrotsearch.hppc.IntHashSet set = new IntHashSet(CNT);
        for(int i = 0; i < CNT; i++){
            set.add(i);
        }
        for(IntCursor c : set){
            int i = c.value;
        }
    }
    /**
     * Primitive integer Map
     * */

    @Benchmark
    public void testMapJDK8(){
        final Map<Integer, Integer> map = new HashMap<Integer, Integer>(CNT);
        for(int i = 0; i < CNT; i++){
            map.put(i,i);
        }
        for(Integer key : map.keySet()){
            int val = map.get(key);
        }
    }
    @Benchmark
    public void testInthashMapHppc(){
        final com.carrotsearch.hppc.IntIntHashMap map = new IntIntHashMap(CNT);
        for(int i = 0; i < CNT; i++){
            map.put(i,i);
        }

        for(int key : map.keys){
            int val = map.get(key);
        }
    }
    @Benchmark
    public void testIntMapFastutils(){
        final it.unimi.dsi.fastutil.ints.Int2IntArrayMap map = new Int2IntArrayMap(CNT);
        for(int i = 0; i < CNT; i++){
            map.put(i,i);
        }
        IntIterator ite = map.keySet().iterator();
        while(ite.hasNext()){
            int val = ite.nextInt();
        }
    }
    // main method to run JMH
    public static void main(String[] args) throws RunnerException {
        System.out.println("Java String Concatenation Test!!");
        //LOGGER.debug("debug");
        Options opt = new OptionsBuilder()
                .include(CollectionBenchMarking.class.getSimpleName()).warmupIterations(5)
                .measurementIterations(5)//.output("test.csv")
                .forks(3).build();

        new Runner(opt).run();

    }
}
