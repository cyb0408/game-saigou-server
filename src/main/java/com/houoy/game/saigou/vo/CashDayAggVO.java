package com.houoy.game.saigou.vo;

import com.houoy.common.vo.SuperVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 每日记录vo，日报表
 */
@Data
@NoArgsConstructor
public class CashDayAggVO extends SuperVO {
    @ApiModelProperty(value = "账号，用户pk", example = "23", hidden = false)
    private String pk_user;
    @ApiModelProperty(value = "当前总积分", example = "1000", hidden = false)
    private String total;
    @ApiModelProperty(value = "下注积分", example = "12", hidden = false)
    private String bet;
    @ApiModelProperty(value = "获奖积分", example = "大big小little单odd双even，数字1-10", hidden = false)
    private String win;
    @ApiModelProperty(value = "日期", example = "2018-02-18", hidden = false)
    private String date;

    @Override
    public String getPKField() {
        return null;
    }

    @Override
    public String getTableName() {
        return null;
    }

    @Override
    public Object getPKValue() {
        return null;
    }
}
