package com.mem.vo.DownloadTest;

import com.mem.vo.ApplicationTests;
import com.mem.vo.common.util.FileUtil;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author lvxiao
 * @date 2019/6/17
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTests.class)
@Log4j2
public class DownloadTest {

    @Autowired
    private FileUtil fileUtil;

    @Test
    public void testUpload() {
    }

    @Test
    public void testDownload() {
        System.out.println(fileUtil.getFileUrl("images.png"));
    }

    @Test
    public void testAllFile() {
        fileUtil.findFiles().forEach(System.out::println);
    }
}
