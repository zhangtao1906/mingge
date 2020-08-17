package com.huajie.controller;

import java.util.HashMap;
import java.util.Random;

public class Test {
    // 生成各专业材料消耗的数据
    public static void main(String[] args) {
        String[] majors = {"弱电专业", "接触网专业", "通信专业", "信号专业", "风水电专业", "供电专业"};
        HashMap<String, String> map = new HashMap<>();
        map.put("钢筋", "吨");
        map.put("水泥", "吨");
        map.put("铁丝", "米");
        map.put("钉子", "个");
        map.put("钢板", "块");
        map.put("钢轨", "条");
        String[] goods = {"钢筋", "水泥", "铁丝", "钉子", "钢板", "钢轨"};
        String tableName = "major_consume_records";
        for (int i = 1; i < 7; i++) {
            String station = i + "号站";
            for (int j = 0; j < 1; j ++) {
                String major = majors[j];
                for (int k = 10; k <= 10; k++) {
                    String goodsName = goods[new Random().nextInt(goods.length)];
                    System.out.println(String.format("INSERT INTO `%s` VALUES (null, '%s', '%s', '%s', '%d', '%s', '施工目的',  '2020-08-%d');",
                            tableName, station, major, goodsName,  1 + new Random().nextInt(10), map.get(goodsName), k));
                }
            }
        }
    }

    // 生成各小队的进度数据
//    public static void main(String[] args) {
//        String[] majors = {"弱电专业", "接触网专业", "通信专业", "信号专业", "风水电专业", "供电专业"};
//        String tableName = "major_jindu";
//        for (int i = 1; i < 7; i++) {
//            String station = i + "号站";
//            for (int j = 0; j < 6; j ++) {
//                String major = majors[j];
//                for (int k = 10; k <= 20; k++) {
//                    System.out.println(String.format("INSERT INTO `%s` VALUES (null, '%s', '%s', '2020-08-%d', '10', '%d', '%d', '100');",
//                            tableName, station, major, k, new Random().nextInt(10), new Random().nextInt(100)));
//                }
//            }
//        }
//    }
}
