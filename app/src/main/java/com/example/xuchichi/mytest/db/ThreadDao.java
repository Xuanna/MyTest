package com.example.xuchichi.mytest.db;

import com.example.xuchichi.mytest.model.ThreadInfo;

import java.util.List;

/**
 * Created by xuchichi on 2018/3/24.
 * 数据访问接口
 */

public interface ThreadDao {
    /**
     * 插入线程信息
     * @param info
     */
     void insertThread(ThreadInfo info);

    /**
     * 删除
     * @param url
     * @param threadId
     */
    void deleteThread(String url,int threadId);

    /**
     * 更新线程下载进度
     * @param url
     * @param threadId
     */
    void updateThread(String url,int threadId,int finish);

    /**
     * 查询文件的线程信息
     * @param url 表示文件的
     * @return
     */
    List<ThreadInfo> getThread(String url);

    boolean isExist(String url,int threadId);

}
