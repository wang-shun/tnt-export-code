package com.lvmama.tnt.test;

import com.github.ltsopensource.core.domain.Job;
import com.github.ltsopensource.jobclient.JobClient;
import com.github.ltsopensource.jobclient.RetryJobClient;
import com.github.ltsopensource.jobclient.domain.Response;

/**
 *
 */
public class LtsClientTest {

    public static void main(String[] args){
        JobClient jobClient = new RetryJobClient();
        jobClient.setNodeGroup("tnt_export_code_client");
        jobClient.setClusterName("tnt_export_code_cluster");
        jobClient.setRegistryAddress("zookeeper://10.200.6.197:2181");
        jobClient.start();

// 提交任务
        Job job = new Job();
        job.setTaskId("3213213123");
        job.setParam("type","add");
        job.setParam("shopId", "11111");
        job.setTaskTrackerNodeGroup("tnt_export_code_tracker");
        Response response = jobClient.submitJob(job);
        System.out.println(response);
    }
}
