package com.bjtu.edu.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;

public class HDFSClient {

    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException, ParseException {

        //1.获取文件系统
        Configuration configuration = new Configuration();
//        configuration.set("fs.defaultFS","hdfs://master:9000");

        FileSystem fs = FileSystem.get(new URI("hdfs://master:9000"),configuration,"gfh");
        //2.拷贝本地文件到文件系统
        fs.copyFromLocalFile(new Path("D:\\bigdata\\dc.txt"),new Path("/"));
        //3.关闭fs
        fs.close();


    }

}
