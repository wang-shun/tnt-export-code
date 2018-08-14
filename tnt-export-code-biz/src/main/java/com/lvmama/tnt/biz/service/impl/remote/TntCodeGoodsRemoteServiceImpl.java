package com.lvmama.tnt.biz.service.impl.remote;

import com.alibaba.dubbo.config.annotation.Service;
import com.lvmama.order.api.base.vo.BusinessException;
import com.lvmama.scenic.api.hoard.vo.HoardCodeGoodsStockVo;
import com.lvmama.tnt.biz.service.TntCodeCommonSetService;
import com.lvmama.tnt.biz.service.TntCodeGoodsService;
import com.lvmama.tnt.biz.service.TntCodeUserSetService;
import com.lvmama.tnt.common.domain.PageResponseDTO;
import com.lvmama.tnt.common.domain.ResponseDTO;
import com.lvmama.tnt.common.domain.TntMerchantDTO;
import com.lvmama.tnt.common.util.BeanCopyUtils;
import com.lvmama.tnt.dal.domain.TntCodeCommonSetPO;
import com.lvmama.tnt.dal.domain.TntCodeGoodsPO;
import com.lvmama.tnt.dal.domain.TntCodeUserSetPO;
import com.lvmama.tnt.export.code.api.constant.CommonPolicy;
import com.lvmama.tnt.export.code.api.domain.vo.DistributorSetupVO;
import com.lvmama.tnt.export.code.api.domain.vo.ExportProductVO;
import com.lvmama.tnt.export.code.api.domain.vo.ExportSetupVO;
import com.lvmama.tnt.export.code.api.service.ITntCodeGoodsRemoteService;
import com.lvmama.tnt.prod.po.TntProduct;
import com.lvmama.tnt.reference.service.IScenicRefService;
import com.lvmama.tnt.reference.service.ITntProductRefService;
import com.lvmama.tnt.reference.service.ITntUserRefService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.security.Policy;
import java.util.*;

/**
 *
 */
@Service(group = "export")
public class TntCodeGoodsRemoteServiceImpl implements ITntCodeGoodsRemoteService {

    private static Logger logger = LoggerFactory.getLogger(TntCodeGoodsRemoteServiceImpl.class);

    @Autowired
    private TntCodeGoodsService tntCodeGoodsService;
    @Autowired
    private TntCodeCommonSetService tntCodeCommonSetService;
    @Autowired
    private TntCodeUserSetService tntCodeUserSetService;
    @Autowired
    private ITntProductRefService tntProductRefService;
    @Autowired
    private IScenicRefService scenicRefService;
    @Autowired
    private ITntUserRefService tntUserRefService;

    @Override
//    @Transactional
    public ResponseDTO<String> saveExportSetup(ExportSetupVO exportSetupVO) {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        if (exportSetupVO == null) {
            responseDTO.setSuccess(false);
            responseDTO.setRespMsg("param is null");
        }
        Long goodsId = exportSetupVO.getGoodsId();
        try {
            TntCodeCommonSetPO tntCodeCommonSetPO = new TntCodeCommonSetPO();
            BeanCopyUtils.copyBean(exportSetupVO, tntCodeCommonSetPO);
            tntCodeCommonSetPO.setCreateTime(new Date());
            tntCodeCommonSetPO.setUpdateTime(new Date());
            //update or insert
            if (tntCodeCommonSetService.isExist(goodsId)) {
                tntCodeCommonSetService.delete(goodsId);
            }

            tntCodeCommonSetService.saveCommonSet(tntCodeCommonSetPO);
            if (CommonPolicy.USER.name().equals(exportSetupVO.getPolicy())) {
                List<DistributorSetupVO> distributorSetupVOS = exportSetupVO.getDistributorSetupVOS();
                if (distributorSetupVOS != null && distributorSetupVOS.size() > 0) {
                    List<TntCodeUserSetPO> tntCodeUserSetPOS = new ArrayList<>();
                    Set<String> userIdSet = new HashSet<>();
                    TntCodeUserSetPO tntCodeUserSetPO = null;
                    for (DistributorSetupVO vo : distributorSetupVOS) {
                        tntCodeUserSetPO = new TntCodeUserSetPO();
                        BeanCopyUtils.copyBean(vo, tntCodeUserSetPO);
                        tntCodeUserSetPO.setCreateTime(new Date());
                        tntCodeUserSetPO.setUpdateTime(new Date());
                        tntCodeUserSetPOS.add(tntCodeUserSetPO);
                        //
                        userIdSet.add(vo.getUserId()+vo.getUserName());
                    }
                    //delete exist user
                    tntCodeUserSetService.deleteExist(goodsId, userIdSet);
                    tntCodeUserSetService.saveBatch(tntCodeUserSetPOS);
                }
            } else {
                tntCodeUserSetService.deleteExist(goodsId, null);
            }
            responseDTO.setResult("success");
            responseDTO.setRespMsg("save success!");
        } catch (Exception e) {
            logger.error("",e);
            responseDTO.setSuccess(false);
            responseDTO.setRespMsg(e.getMessage());
        }

        return responseDTO;
    }

    @Override
    public ResponseDTO<String> deleteDistributor(Long tntUserSetID) {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        if (tntUserSetID == null) {
            responseDTO.setSuccess(false);
            responseDTO.setRespMsg("参数不能为空！");
            return responseDTO;
        }
        try {
            tntCodeUserSetService.deleteUserSet(tntUserSetID);
        } catch (Exception e) {
            logger.error("", e);
        }
        responseDTO.setRespMsg("delete success");
        return responseDTO;
    }

    @Override
    public ResponseDTO<DistributorSetupVO> findUserSetByIDAndGoodsID(Long tntUserSetID, Long goodsID) {
        ResponseDTO<DistributorSetupVO> responseDTO = new ResponseDTO<>();
        TntCodeUserSetPO tntCodeUserSetPO = tntCodeUserSetService.findById(tntUserSetID);
        DistributorSetupVO distributorSetupVO = new DistributorSetupVO();
        BeanUtils.copyProperties(tntCodeUserSetPO, distributorSetupVO);
        distributorSetupVO.setTntUserSetID(tntCodeUserSetPO.getId());

        distributorSetupVO.setIsHoard(isHoard(goodsID));
        responseDTO.setResult(distributorSetupVO);
        return responseDTO;
    }

    @Override
    public ResponseDTO<String> updateUserSet(DistributorSetupVO distributorSetupVO) {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        String resMsg = null;
        if (distributorSetupVO != null) {
            TntCodeUserSetPO tntCodeUserSetPO = new TntCodeUserSetPO();
            BeanUtils.copyProperties(distributorSetupVO,tntCodeUserSetPO);
            tntCodeUserSetPO.setId(distributorSetupVO.getTntUserSetID());
            tntCodeUserSetService.updateUserSet(tntCodeUserSetPO);
            resMsg = "修改成功!";
        }else {
            resMsg = "修改参数为空！";
        }
        responseDTO.setResult(resMsg);
        return responseDTO;
    }

    @Override
    public ResponseDTO<Map<String, Object>> findExportSet(Long goodsId, Long userId, String userName) {
        Map<String, Object> resultMap = new HashedMap();
        ResponseDTO responseDTO = new ResponseDTO();
        String codeType = "";
        Integer maxOrderNum = 0;
        String policy = "";
        TntCodeCommonSetPO tntCodeCommonSetPO = tntCodeCommonSetService.findByGoodsId(goodsId);
        policy = tntCodeCommonSetPO.getPolicy();
        if (tntCodeCommonSetPO != null) {
            if (CommonPolicy.ALL.name().equals(tntCodeCommonSetPO.getPolicy())) {
                codeType = tntCodeCommonSetPO.getCodeType();
                maxOrderNum =  tntCodeCommonSetPO.getMaxNum();
            } else if (CommonPolicy.USER.name().equals(tntCodeCommonSetPO.getPolicy())) {
                TntCodeUserSetPO tntCodeUserSetPO = new TntCodeUserSetPO();
                tntCodeUserSetPO.setGoodsId(goodsId);
                tntCodeUserSetPO.setUserId(userId);
                tntCodeUserSetPO.setUserName(userName);
                List<TntCodeUserSetPO> list = tntCodeUserSetService.findByParam(tntCodeUserSetPO);
                if (list != null && list.size() > 0) {
                    tntCodeUserSetPO = list.get(0);
                    codeType = tntCodeUserSetPO.getCodeType();
                    maxOrderNum = tntCodeUserSetPO.getMaxNum();
                }
            } else {

            }
        }
        resultMap.put("policy", policy);
        resultMap.put("codeType",codeType );
        resultMap.put("maxOrderNum",maxOrderNum);
        responseDTO.setResult(resultMap);
        return responseDTO;
    }

    private String isHoard(Long goodsId) {
        List<HoardCodeGoodsStockVo> vos = scenicRefService.listHoardCodeGoods(goodsId);
        return vos == null ? "N" : vos.size() > 0 ? "Y" : "N";
    }

    /**
     * 导码商品设置
     * @param distributorSetupVOParam
     * @return
     */
    @Override
    public ResponseDTO<ExportSetupVO> findExportSetupInfo(DistributorSetupVO distributorSetupVOParam) {
        ResponseDTO<ExportSetupVO> responseDTO = new ResponseDTO();
        ExportSetupVO exportSetupVO = new ExportSetupVO();

        if (distributorSetupVOParam == null || distributorSetupVOParam.getGoodsId() == null) {
            throw new IllegalArgumentException("goodsId is null");
        }

        try {
            Long goodsId = distributorSetupVOParam.getGoodsId();

            //导码商品设置
            TntCodeCommonSetPO tntCodeCommonSetPO = tntCodeCommonSetService.findByGoodsId(goodsId);
            if (tntCodeCommonSetPO != null) {
                BeanUtils.copyProperties(tntCodeCommonSetPO, exportSetupVO);
                //指定分销商
                if (CommonPolicy.USER.name().equals(tntCodeCommonSetPO.getPolicy())) {
                    TntCodeUserSetPO tntCodeUserSetPO = new TntCodeUserSetPO();
                    BeanUtils.copyProperties(distributorSetupVOParam,tntCodeUserSetPO);

                    //默认查5000条
                    int pageNo = distributorSetupVOParam.getPageNo();
                    int pageSize = distributorSetupVOParam.getPageSize();
                    List<TntCodeUserSetPO> userSetPOS = tntCodeUserSetService.findByPage(tntCodeUserSetPO, pageNo, pageSize);
                    TntCodeGoodsPO tntCodeGoodsPO = tntCodeGoodsService.findByGoodsId(tntCodeUserSetPO.getGoodsId());
                    List<DistributorSetupVO> distributorSetupVOS = new ArrayList<>();
                    if (CollectionUtils.isNotEmpty(userSetPOS)) {
                        DistributorSetupVO distributorSetupVO = null;
                        for (TntCodeUserSetPO po : userSetPOS) {
                            distributorSetupVO = new DistributorSetupVO();
                            BeanUtils.copyProperties(po, distributorSetupVO);
                            distributorSetupVO.setTntProductId(tntCodeGoodsPO.getTntProductId());
                            distributorSetupVO.setProductId(tntCodeGoodsPO.getProductId());
                            distributorSetupVO.setGoodsName(tntCodeGoodsPO.getGoodsName());
                            distributorSetupVO.setProductName(tntCodeGoodsPO.getProductName());
                            distributorSetupVO.setTntUserSetID(po.getId());
                            distributorSetupVOS.add(distributorSetupVO);
                        }
                    }
                    exportSetupVO.setDistributorSetupVOS(distributorSetupVOS);
                    Long totalCount = tntCodeUserSetService.totalCount(tntCodeUserSetPO);
                    exportSetupVO.setTotalCount(totalCount);
                }
            }
            exportSetupVO.setIsHoard(isHoard(goodsId));
            exportSetupVO.setGoodsId(distributorSetupVOParam.getGoodsId());
            responseDTO.setResult(exportSetupVO);
        } catch (Exception e) {
            logger.error("",e);
            responseDTO.setSuccess(false);
            responseDTO.setRespMsg(e.getMessage());
        }
        return responseDTO;
    }

    @Override
    public ResponseDTO<ExportSetupVO> findDistributorInfo(DistributorSetupVO distributorSetupVO){
        ResponseDTO<ExportSetupVO> responseDTO = new ResponseDTO<>();
        if (distributorSetupVO == null && distributorSetupVO.getGoodsId() == null) {
            throw new IllegalArgumentException("goodsId is null");
        }
        Long goodsId = distributorSetupVO.getGoodsId();
        ExportSetupVO exportSetupVO = new ExportSetupVO();
        TntCodeCommonSetPO tntCodeCommonSetPO = tntCodeCommonSetService.findByGoodsId(goodsId);
        if (tntCodeCommonSetPO != null) {
            BeanUtils.copyProperties(tntCodeCommonSetPO,exportSetupVO);
        }
        exportSetupVO.setIsHoard(isHoard(goodsId));

        String userName = distributorSetupVO.getUserName();
        String operator = distributorSetupVO.getOperator();
        if (!StringUtils.isEmpty(userName) || !StringUtils.isEmpty(operator)) {
            List<TntMerchantDTO> list = tntUserRefService.findTntMerchantInfo(userName, operator);
            exportSetupVO.setTntMerchantDTOS(list);
        }

        responseDTO.setResult(exportSetupVO);
        return responseDTO;
    }

    /**
     * 导码商品列表
     * @param exportProductVO
     * @return
     */
    @Override
    public PageResponseDTO<List<ExportProductVO>> findByPage(ExportProductVO exportProductVO) {
        TntCodeGoodsPO tntCodeGoodsPO = new TntCodeGoodsPO();
        tntCodeGoodsPO.setGoodsId(exportProductVO.getGoodsId());
        tntCodeGoodsPO.setGoodsName(exportProductVO.getGoodsName());
        tntCodeGoodsPO.setProductId(exportProductVO.getProductId());
        tntCodeGoodsPO.setProductName(exportProductVO.getProductName());

        List<TntCodeGoodsPO> list = tntCodeGoodsService.findByPage(tntCodeGoodsPO,
                exportProductVO.getPageNo(), exportProductVO.getPageSize());
        Long totalCount = tntCodeGoodsService.totalCount(tntCodeGoodsPO);

        Set<Long> productIds = buildProductIds(list);
        Map<Long, List<TntProduct>> productMap = tntProductRefService.findProduct(productIds);
        PageResponseDTO<List<ExportProductVO>> result = new PageResponseDTO<>();
        List<ExportProductVO> contentList = new ArrayList<>();
        for (TntCodeGoodsPO po : list) {
            ExportProductVO vo = new ExportProductVO();
            vo.setProductId(po.getProductId());
            vo.setGoodsId(po.getGoodsId());
            vo.setIsExport(po.getIsExport());
            List<TntProduct> products = productMap.get(po.getProductId());
            if (productIds != null && productIds.size() > 0) {
                for (TntProduct product : products) {
                    if (vo.getGoodsId().equals(product.getBranchId())) {
                        vo.setGoodsName(product.getGoodsName());
                        vo.setProductName(product.getProductName());
                        vo.setGoodsType(product.getGoodsType());
                        vo.setPayTarget(product.getPayToLvmama());
                        vo.setStatus(product.getGoodsStatus());
                        vo.setValid(product.getValid());
                    }
                }
            }
            contentList.add(vo);
        }
        result.setSuccess(true);
        result.setResult(contentList);
        result.setTotalCount(totalCount);
        result.setPageNo(exportProductVO.getPageNo());
        result.setPageSize(exportProductVO.getPageSize());

        return result;
    }

    private Set<Long> buildProductIds(List<TntCodeGoodsPO> codeGoodsList){
        Set<Long> productIds = new HashSet<>();
        if(CollectionUtils.isEmpty(codeGoodsList)){
            return productIds;
        }

        for (TntCodeGoodsPO codeGoods : codeGoodsList){
            productIds.add(codeGoods.getProductId());

        }
        return productIds;
    }



}
