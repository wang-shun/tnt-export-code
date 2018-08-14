package com.lvmama.tnt.export.code.api.service;

import com.lvmama.tnt.common.domain.PageResponseDTO;
import com.lvmama.tnt.common.domain.ResponseDTO;
import com.lvmama.tnt.export.code.api.domain.vo.DistributorSetupVO;
import com.lvmama.tnt.export.code.api.domain.vo.ExportProductVO;
import com.lvmama.tnt.export.code.api.domain.vo.ExportSetupVO;

import java.util.List;
import java.util.Map;

/**
 *
 */
public interface ITntCodeGoodsRemoteService {
    /**
     * 导码商品列表
     * @param exportProductVO
     * @return
     */
    PageResponseDTO<List<ExportProductVO>> findByPage(ExportProductVO exportProductVO);

    /**
     * 导码商品设置信息
     * @param distributorSetupVOParam
     * @return
     */
    ResponseDTO<ExportSetupVO> findExportSetupInfo(DistributorSetupVO distributorSetupVOParam);

    /**
     * 查询导码分销商
     * @param distributorSetupVO
     * @return
     */
    ResponseDTO<ExportSetupVO> findDistributorInfo(DistributorSetupVO distributorSetupVO);

    /**
     * 保存导码分销商设置
     * @param exportSetupVO
     * @return
     */
    ResponseDTO<String> saveExportSetup(ExportSetupVO exportSetupVO);

    /**
     * 删除分销商
     * @param tntUserSetID
     * @return
     */
    ResponseDTO<String> deleteDistributor(Long tntUserSetID);

    /**
     *查找用户设置信息
     * @param tntUserSetID
     * @param goodsID
     * @return
     */
    ResponseDTO<DistributorSetupVO> findUserSetByIDAndGoodsID(Long tntUserSetID, Long goodsID);

    /**
     * 分销商设置更新
     * @param distributorSetupVO
     * @return
     */
    ResponseDTO<String> updateUserSet(DistributorSetupVO distributorSetupVO);

    /**
     * 根据商品ID和用户ID查找最新导码设置
     * @param goodsId
     * @param userId
     * @return
     */
    ResponseDTO<Map<String, Object>> findExportSet(Long goodsId, Long userId, String userName);
}
