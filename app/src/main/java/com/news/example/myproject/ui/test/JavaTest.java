package com.news.example.myproject.ui.test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiemy2
 * @date 2019/7/2
 */
public class JavaTest {
    public static void main1(String[] args) {
        List<String> list1 = new ArrayList<>();
        list1.add("1111");
        list1.add("2222");
        list1.add("3333");
        list1.add("4444");
        list1.add("5555");

        List<String> list2 = new ArrayList<>();
        list2.add("3333");
        list2.add("4444");

//        list1.retainAll(list2); //交集，前者list必须比后者list范围大，求出来的才对
//        list1.removeAll(list2); //差异集合，相差得元素
//        list1.addAll(list2); //有重复并集

        //无重复并集
        list2.removeAll(list1);
        list1.addAll(list2);
        System.out.println(list1.toString());
    }

    public static void main(String[] args) {
        List<T> t1 = new ArrayList<>();
        t1.add(new T(0, "AA"));
        t1.add(new T(1, "BB"));
        t1.add(new T(2, "CC"));
        t1.add(new T(3, "DD"));

        List<T> t2 = new ArrayList<>();
        t2.add(new T(0, "AA"));
        t2.add(new T(1, "bb"));
        t2.add(new T(2, "CC"));
        t2.add(new T(3, "666"));
        t2.add(new T(4, "ee"));

        //交集，前者list必须比后者list范围大，求出来的才对
        t1.retainAll(t2);
        //差异集合，相差得元素
        t1.removeAll(t2);
        //有重复并集
        t1.addAll(t2);
        System.out.println(t1);
    }
}

