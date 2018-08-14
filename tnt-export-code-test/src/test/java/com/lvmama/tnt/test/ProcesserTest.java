package com.lvmama.tnt.test;

import com.github.ltsopensource.core.domain.Job;
import com.github.ltsopensource.jobclient.JobClient;
import com.github.ltsopensource.jobclient.RetryJobClient;
import com.github.ltsopensource.jobclient.domain.Response;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 */
public class ProcesserTest  {



//    @Test
    public void testSendMessage(){
        JobClient jobClient = new RetryJobClient();
        jobClient.setNodeGroup("tnt_export_code_client");
        jobClient.setClusterName("tnt_export_code_cluster");
        jobClient.setRegistryAddress("zookeeper://10.200.6.197:2181");
        jobClient.start();
        //--
        Job job = new Job();
        job.setTaskId("1001");
        job.setParam("bizId","123456");
        job.setParam("eventType", "ADD");
        job.setTaskTrackerNodeGroup("tnt_export_code_tracker");
        job.setNeedFeedback(true);
        job.setReplaceOnExist(true);
        job.setTriggerTime(null);
        Response response = jobClient.submitJob(job);

    }

}
