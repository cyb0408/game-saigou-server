package com.houoy.game.saigou.service.rest;


import com.houoy.common.utils.JqueryDataTablesUtil;
import com.houoy.common.vo.JquryDataTablesVO;
import com.houoy.game.saigou.service.IncomeService;
import com.houoy.game.saigou.vo.IncomeAggVO;
import com.houoy.game.saigou.vo.IncomeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by andyzhao on 2017-02-20.
 */
@Api(description = "每期开奖盈利状况")
//@Slf4j
@RestController
@RequestMapping("/api/income")
public class IncomeController {

    @Autowired
    private IncomeService incomeService;

    @ApiOperation(value = "查询开奖状况记录")
    @GetMapping(value = "/retrieve")
    public JquryDataTablesVO<IncomeVO> retrieve(IncomeVO vo, HttpServletRequest request) {
//        log.info("查询开奖状况记录");
        vo = (IncomeVO) JqueryDataTablesUtil.filterParam(vo, request);
        List<IncomeVO> result = incomeService.retrieveAllWithPage(vo);
        Long count = incomeService.retrieveAllCount(vo);

        JquryDataTablesVO rtv = JqueryDataTablesUtil.madeJqueryDatatablesVO(count, result);
        return rtv;
    }

    @ApiOperation(value = "每日庄家收益状况总和",hidden = true)
    @GetMapping(value = "/retrieveAgg")
    public JquryDataTablesVO<IncomeAggVO> retrieveAgg(IncomeAggVO vo, HttpServletRequest request) {
//        log.info("查询开奖状况记录");
        vo = (IncomeAggVO) JqueryDataTablesUtil.filterParam(vo, request);
        if(StringUtils.isEmpty(vo.getOrderColumnName())){
            vo.setOrderColumnName("ts");
        }
        if(StringUtils.isEmpty(vo.getOrderDir())){
            vo.setOrderDir("desc");
        }
        List<IncomeAggVO> result = incomeService.retrieveAggAllWithPage(vo);
        Long count = incomeService.retrieveAggAllCount(vo);

        JquryDataTablesVO rtv = JqueryDataTablesUtil.madeJqueryDatatablesVO(count, result);
        return rtv;
    }
}


