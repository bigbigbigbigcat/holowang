package com.holo.springboot.holoclient;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.holo.springboot.holoclient.beans.taodaji.DataItem;
import com.holo.springboot.holoclient.beans.taodaji.Specs;
import com.holo.springboot.holoclient.beans.taodaji.TaoDaJiResp;
import com.holo.springboot.holoclient.utils.ExportExcelUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class TaoDaJiTest {

    @Test
    void contextLoads()  {

        List<String> allFile = getAllFile("D:\\其他\\TaoDaJi", false);
//        CountDownLatch latch = new CountDownLatch(allFile.size());
        int N_CPUS = Runtime.getRuntime().availableProcessors();
        ExecutorService  executor = new ThreadPoolExecutor(N_CPUS*2, N_CPUS*4,60, TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>());
        List<Future<Map<String,List<Map<String,String>>>>> futures = new ArrayList<Future<Map<String,List<Map<String,String>>>>>();

        for (String sa :allFile){
//            System.out.println(sa);

            futures.add(executor.submit(new Callable<Map<String,List<Map<String,String>>>>() {
                @Override
                public Map<String,List<Map<String,String>>> call() {
                    Map<String,List<Map<String,String>>> returnMapList = new HashMap<>();
                    List<Map<String,String>> returnList = new ArrayList<>();
//                    System.out.println(String.format("当前线程：%s,开始处理文件：%s",Thread.currentThread().getName(),sa));


                    StringBuffer stringBuffer = testInputStreamBase64("",sa);
                    String s = stringBuffer.toString();
                    JSONObject jsonObject = JSONObject.parseObject(s);
//        StringBuffer allKey = getAllKey(jsonObject);
//        System.out.println(allKey.toString());
//                    System.out.println("=========================");
                    TaoDaJiResp taoDaJiResp = JSONObject.toJavaObject(jsonObject, TaoDaJiResp.class);
                    if(CollectionUtils.isNotEmpty(taoDaJiResp.getData().getItems())){
                        List<DataItem> items = taoDaJiResp.getData().getItems();
                        for (int i = 0; i < items.size(); i++) {
                            DataItem dataItem = items.get(i);
                            String name = dataItem.getName();
                            List<Specs> specs = dataItem.getSpecs();
                            String price = "";
                            String level1Unit ="";
                            String level2Unit ="";
                            String level2Value ="";
                            String level3Unit ="";
                            String level3Value ="";

                        if (CollectionUtils.isNotEmpty(specs)) {
                                if (specs.size() == 1) {
                                    price = specs.get(0).getPrice();
                                    level1Unit = specs.get(0).getLevel1Unit();
                                    level2Value = specs.get(0).getLevel2Value();
                                    level2Unit = specs.get(0).getLevel2Unit();
                                    level3Unit = specs.get(0).getLevel3Unit();
                                    level3Value = specs.get(0).getLevel3Value();

                                }
                            }
//                System.out.println(String.format("商品名称：%s,价格：%s元/%s(%s%s)",name,price,level1Unit,level2Value,level2Unit));
                            Map<String,String> returnMap = new HashMap<>();
                            returnMap.put("name",name);
                            if(StringUtils.isNotBlank(level3Unit)){
//                                System.out.println(String.format("商品名称：%s,规格：一%s",name,level1Unit));
                                returnMap.put("unit",String.format("%s元/%s(%s%s*%s%s)",price,level1Unit,level2Value,level2Unit,level3Value,level3Unit));

                            }else
                            if(StringUtils.isNotBlank(level2Unit)){

                                returnMap.put("unit",String.format("%s元/%s(%s%s)",price,level1Unit,level2Value,level2Unit));
//                                System.out.println(String.format("商品名称：%s,规格：%s%s",name,level2Value,level2Unit));

                            }else {
                                //                                System.out.println(String.format("商品名称：%s,规格：一%s",name,level1Unit));
                                returnMap.put("unit",String.format("%s元/%s",price,level1Unit));
                            }
                            returnList.add(returnMap);
                        }
//                        latch.countDown();
                    }

                    String[] split = StringUtils.split(sa,"\\");
//                    sheetNames.add(split[4]);
                    String sheelName = split[4];
                    if (StringUtils.isNotBlank(sheelName)){
                        if(returnMapList.get(returnMapList)==null){
                            returnMapList.put(sheelName,returnList);
                        }else{
                            returnMapList.get(sheelName).addAll(returnList);
                        }
                    }
                    return returnMapList;
                }
            }));
        }
        try {
            Map<String,List<Map<Integer,String>>> m1 = new HashMap<>();

            if (CollectionUtils.isNotEmpty(futures)) {
                for (Future f : futures) {

                    System.out.println("===================================");
                    Map<String,List<Map<String,String>>> returnListMap = (Map<String,List<Map<String,String>>>) f.get();
                    if (MapUtils.isNotEmpty(returnListMap)){
                        for(String key:returnListMap.keySet()) {
                            if(m1.get(key) == null){
                                List<Map<Integer,String>> l1 = new ArrayList<>();
                                List<Map<String,String>> value = returnListMap.get(key);
                                for (Map<String,String> mapList :value){
                                    Map<Integer,String> map = new HashMap<>();

                                    System.out.println(String.format("%s,%s",mapList.get("name"),mapList.get("unit")));
                                    map.put(0,mapList.get("name"));
                                    map.put(1,mapList.get("unit"));
                                    l1.add(map);
                                }
                                m1.put(key,l1);
                            }else{
                                List<Map<String,String>> value = returnListMap.get(key);
                                for (Map<String,String> mapList :value){
                                    Map<Integer,String> map = new HashMap<>();

                                    System.out.println(String.format("%s,%s",mapList.get("name"),mapList.get("unit")));
                                    map.put(0,mapList.get("name"));
                                    map.put(1,mapList.get("unit"));
                                    m1.get(key).add(map);
                                }
                            }
                        }
                    }else {
                        System.out.println("本次任务获取到的结果是空");
                    }
                    /*List<Map<String,String>> returnList = (List<Map<String, String>>) f.get();
                    if (CollectionUtils.isNotEmpty(returnList)){
                        for (Map<String,String> returnMap : returnList){
                            Map<Integer,String> map = new HashMap<>();

                            System.out.println(String.format("%s,%s",returnMap.get("name"),returnMap.get("unit")));
                            map.put(0,returnMap.get("name"));
                            map.put(1,returnMap.get("unit"));
                            dataList.add(map);

                        }
                    }else {
                        System.out.println("本次任务获取到的结果是空");
                    }*/

                }
            }
            List<List<Map<Integer,String>>> dataLists = new ArrayList<>();
            Set<String> sheetNames = new HashSet<>();

            if (MapUtils.isNotEmpty(m1)) {
                for (String key : m1.keySet()) {
                    List<Map<Integer,String>> dataList = new ArrayList<>();

                    sheetNames.add(key);
                    dataList.addAll(m1.get(key));
                    dataLists.add(dataList);
                }
            }


            List<String> headers = new ArrayList<>();
            headers.add("商品名称");
            headers.add("规格");

            List<String> sheetNamesList = new ArrayList<>(sheetNames);


            ExportExcelUtil.exportExcel("test", sheetNamesList, null,headers,dataLists,"D:\\",null,true);

        } catch (Exception e) {
            System.out.println("出现异常" + e.getMessage());
        }
        System.out.println("全部执行完");


    }
    /**
     * 递归读取所有的key
     *
     * @param jsonObject
     */
    public static StringBuffer getAllKey(JSONObject jsonObject) {
        StringBuffer stringBuffer = new StringBuffer();
        Iterator<String> keys = jsonObject.keySet().iterator();// jsonObject.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            System.out.println(key.toString());
            if (jsonObject.get(key) instanceof JSONObject) {
                JSONObject innerObject = (JSONObject) jsonObject.get(key);
                stringBuffer.append(getAllKey(innerObject));
            } else if (jsonObject.get(key) instanceof JSONArray) {
                System.out.println(String.format("%s是array类型",key.toString()));
                JSONArray innerObject = (JSONArray) jsonObject.get(key);
                stringBuffer.append(getAllKey(innerObject));
            }
        }
        return stringBuffer;
    }
    public static StringBuffer getAllKey(JSONArray json1) {
        StringBuffer stringBuffer = new StringBuffer();
        if (json1 != null ) {
            Iterator i1 = json1.iterator();
            while (i1.hasNext()) {
                Object key = i1.next();
                if (key instanceof  JSONObject) {
                    JSONObject innerObject = (JSONObject) key;
                    stringBuffer.append(getAllKey(innerObject));
                } else if (key instanceof JSONArray) {
                    JSONArray innerObject = (JSONArray) key;
                    stringBuffer.append(getAllKey(innerObject));
                }else{
                }
            }
        }
        return stringBuffer;
    }

    /**
     * 获取路径下的所有文件/文件夹
     * @param directoryPath 需要遍历的文件夹路径
     * @param isAddDirectory 是否将子文件夹的路径也添加到list集合中
     * @return
     */
    public static List<String> getAllFile(String directoryPath,boolean isAddDirectory) {
        List<String> list = new ArrayList<String>();
        File baseFile = new File(directoryPath);
        if (baseFile.isFile() || !baseFile.exists()) {
            return list;
        }
        File[] files = baseFile.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                if(isAddDirectory){
                    list.add(file.getAbsolutePath());
                }
                list.addAll(getAllFile(file.getAbsolutePath(),isAddDirectory));
            } else {
                list.add(file.getAbsolutePath());
            }
        }
        return list;
    }
    /**
     * 本地测试时使用。
     * @return
     */
    public static StringBuffer testInputStreamBase64(String sid,String fileName){
        InputStream in = null;
        try{
            StringBuffer sb = new StringBuffer();
//            String fileName = StringUtils.EMPTY;
//            fileName = "D:\\无标题-8.txt";
            FileInputStream fis = new FileInputStream(fileName);
            InputStreamReader reader = new InputStreamReader(fis,"UTF-8"); //最后的"GBK"根据文件属性而定，如果不行，改成"UTF-8"试试
            BufferedReader br = new BufferedReader(reader);
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            reader.close();

            return sb;
        }catch (IOException e)
        {
            e.printStackTrace();
        }finally{
            try{
                if(in != null){
                    in.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return new StringBuffer();
    }
}
