package com.lvmama.tnt.test;

import com.lvmama.tnt.biz.service.TntCodeBatchService;
import com.lvmama.tnt.common.domain.ResponseDTO;
import com.lvmama.tnt.dal.domain.TntCodeBatchJoinCodeGoodsPO;
import com.lvmama.tnt.dal.mapper.TntCodeUserSetMapper;
import com.lvmama.tnt.export.code.api.constant.CommonPolicy;
import com.lvmama.tnt.export.code.api.constant.ExportCodeType;
import com.lvmama.tnt.export.code.api.domain.vo.DistributorSetupVO;
import com.lvmama.tnt.export.code.api.domain.vo.ExportSetupVO;
import com.lvmama.tnt.export.code.api.service.ITntCodeGoodsRemoteService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 */
public class DistributorSetupTest extends AbstractTest {

    @Autowired
    private ITntCodeGoodsRemoteService tntCodeGoodsRemoteService;
    @Autowired
    private TntCodeUserSetMapper tntCodeUserSetMapper;

    @Autowired
    private TntCodeBatchService tntCodeBatchService;

    @Test
    public void testBatchJoinGoods() {
        TntCodeBatchJoinCodeGoodsPO tntCodeBatchJoinCodeGoodsPO = new TntCodeBatchJoinCodeGoodsPO();
        long totalCount = tntCodeBatchService.totalCountByJoin(tntCodeBatchJoinCodeGoodsPO);
        List<TntCodeBatchJoinCodeGoodsPO> list = tntCodeBatchService.findByPageJoin(tntCodeBatchJoinCodeGoodsPO,1, 10);
    }

    @Test
    public void queryMerchant() {
        DistributorSetupVO distributorSetupVO = new DistributorSetupVO();
        distributorSetupVO.setGoodsId(4172456L);
        distributorSetupVO.setUserName("Person");
        ResponseDTO<ExportSetupVO> responseDTO = tntCodeGoodsRemoteService.findDistributorInfo(distributorSetupVO);
        if (responseDTO.isSuccess() && responseDTO.getResult() != null) {
            ExportSetupVO exportSetupVO = responseDTO.getResult();

        }
    }

    @Test
    public void deleteTest() {
        Long goodsId = 4172456L;
        Set<Long> set = new HashSet<>();
        set.add(12345L);
        set.add(12346L);
        set.add(12347L);
        set.add(12348L);
        tntCodeUserSetMapper.deleteExist(goodsId, null);
    }

    @Test
    public void saveTest() {
        ExportSetupVO exportSetupVO = new ExportSetupVO();
        exportSetupVO.setIsHoard("Y");
        exportSetupVO.setCodeType(ExportCodeType.HOARD.name());
        exportSetupVO.setGoodsId(4172456L);
        exportSetupVO.setMaxNum(null);
        exportSetupVO.setPolicy(CommonPolicy.USER.name());

        List<DistributorSetupVO> list = new ArrayList<>();
        DistributorSetupVO vo = null;
        for (int i = 0; i < 20; i++) {
            vo = new DistributorSetupVO();
            vo.setMaxNum(500);
            vo.setGoodsId(4172456L);
            vo.setCodeType(ExportCodeType.HOARD.name());
            vo.setCompanyName("山东无忧旅游服务有限公司"+i);
            vo.setUserName("山东无忧旅游服务有限公司"+i);
            vo.setUserId(12345L+i);
            vo.setOperator("admin");
            list.add(vo);
        }
        exportSetupVO.setDistributorSetupVOS(list);

        tntCodeGoodsRemoteService.saveExportSetup(exportSetupVO);
    }
}
