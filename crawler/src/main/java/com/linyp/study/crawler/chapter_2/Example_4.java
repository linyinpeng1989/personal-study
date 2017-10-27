package com.linyp.study.crawler.chapter_2;

import com.sleepycat.bind.serial.ClassCatalog;
import com.sleepycat.bind.serial.SerialBinding;
import com.sleepycat.bind.serial.StoredClassCatalog;
import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.collections.StoredSortedMap;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;

import java.io.File;
import java.util.SortedMap;

/**
 * Created by linyp on 2017/8/2.
 */
public class Example_4 {
    public static void main(String[] args) {
        String dir = "D:/db/";
        File dirFile = new File(dir);
        Environment environment = null;
        try {
            EnvironmentConfig envConfig = new EnvironmentConfig();
            // 不需要事务
            envConfig.setTransactional(false);
            // 允许创建新的数据库文件
            envConfig.setAllowCreate(true);
            environment = new Environment(dirFile, envConfig);

            //使用一个通用的数据库配置
            DatabaseConfig dbConfig = new DatabaseConfig();
            dbConfig.setTransactional(false);
            dbConfig.setAllowCreate(true);

            // 序列化绑定需要的数据库配置，并初始化用来存储序列化对象的catalog类
            Database catalogDb = environment.openDatabase(null,"catalog",dbConfig);
            ClassCatalog catalog = new StoredClassCatalog(catalogDb);

            TupleBinding<String> keyBinding = TupleBinding.getPrimitiveBinding(String.class);
            // 把value作为对象的序列化方式存储
            SerialBinding<String> valueBinding = new SerialBinding(catalog, NewsSource.class);

            Database db = environment.openDatabase(null, "url", dbConfig);
            // 创建一个映射
            SortedMap<String, String> map = new StoredSortedMap<>(db, keyBinding, valueBinding, true);

            // 把抓取过来的URL地址作为关键字放入映射
            String url = "https://www.baidu.com";
            map.put(url, null);
            if (map.containsKey(url)) {
                System.out.println("抓取成功");
            }

        } finally {
            if (environment != null) {
                // 同步内存中的数据到硬盘
                //environment.sync();
                // 关闭环境变量
                environment.close();
            }
        }
    }
}

class NewsSource {
    private String title;
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
