package com.mem.vo.sponsor;
import java.util.Date;

import com.mem.vo.ApplicationTests;
import com.mem.vo.business.base.model.po.Sponsor;
import com.mem.vo.business.base.model.po.SponsorQuery;
import com.mem.vo.business.base.service.SponsorService;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author lvxiao
 * @version V1.0
 * @date 2019-06-22 19:53
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTests.class)
@Log4j2
public class SponsorTest {

    @Autowired
    private SponsorService sponsorService;

    @Test
    public void test() {
        List<Sponsor> sponsors = sponsorService.findByCondition(new SponsorQuery());
        System.out.println(sponsors);
    }

    @Test
    public void testUpdate() {
        Sponsor sponsor = new Sponsor();
        sponsor.setId(1);
        sponsor.setName("asfadddd");
        sponsor.setPhone("asdfsadfa");
        sponsor.setContactName("asfdsafdsafsdfsdfds");
        sponsor.setStatus(0);
        sponsor.setCreateTime(new Date());
        sponsor.setCreateUser("");
        sponsor.setUpdateTime(new Date());
        sponsor.setUpdateUser("");
        sponsor.setIsDelete(0);


        sponsorService.updateById(sponsor);
    }
}
