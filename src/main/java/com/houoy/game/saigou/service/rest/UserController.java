package com.houoy.game.saigou.service.rest;

import com.houoy.common.utils.JqueryDataTablesUtil;
import com.houoy.common.vo.JquryDataTablesVO;
import com.houoy.common.vo.RequestResultVO;
import com.houoy.common.vo.UserVO;
import com.houoy.game.saigou.service.UserService;
import com.houoy.game.saigou.vo.Result;
import com.houoy.game.saigou.vo.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author andyzhao
 */
@Api(description = "用户增删改查")
@RestController
@RequestMapping("/root/base/user")
public class UserController {
    private static final Log logger = LogFactory.getLog(UserController.class);

    @Resource
    private UserService userService;

    @ApiOperation(value = "保存用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userVO", value = "用户信息", required = true, paramType = "body", dataType = "UserVO")
    })
    @PostMapping("/save")
    public RequestResultVO add(@RequestBody UserVO userVO) {
        Integer num = 0;
        RequestResultVO resultVO = new RequestResultVO();
        if (userVO != null) {
            if (userVO.getPk_user() != null) {//如果前端传递过来pk,则判断为更新操作
                num = userService.updateUserByVO(userVO);
            } else {
                if (StringUtils.isEmpty(userVO.getDef1())) {
                    resultVO.setSuccess(false);
                    resultVO.setMsg("保存失败,用户积分不能为null");
                    return resultVO;
                } else {
                    num = userService.saveUserByVO(userVO);
                }
            }

            if (num >= 1) {
                resultVO.setSuccess(true);
                resultVO.setMsg("保存成功");
                resultVO.setResultData(num);
            } else {
                resultVO.setSuccess(false);
                resultVO.setMsg("保存失败");
            }
        } else {
            resultVO.setSuccess(false);
            resultVO.setMsg("参数不可为null");
        }
        return resultVO;
    }

    @ApiOperation(value = "根据Pk值删除", notes = "根据Pk值删除", hidden = true)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pks", value = "用户的pk列表", required = true, dataType = "List", paramType = "body")
    })
    @PostMapping("/delete")
    public RequestResultVO delete(@RequestBody List<String> pk_users) {
        Integer num = userService.deleteUsers(pk_users);
        RequestResultVO resultVO = new RequestResultVO();
        if (num >= 1) {
            resultVO.setSuccess(true);
            resultVO.setMsg("查询成功");
            resultVO.setResultData(num);
        } else {
            resultVO.setSuccess(false);
            resultVO.setMsg("保存");
        }

        return resultVO;
    }

    @ApiOperation(value = "根据用户pk查询用户信息,积分字段是def1")
    @GetMapping(value = "retrieveByPK")
    public Result<UserVO> retrieveByPK(String pk_user) {
        UserVO userVO = userService.retrieveByPk(pk_user);
        Result result = new Result();
        if (userVO == null) {
            result.setContent(userVO);
            result.setMsg("没有找到用户");
            result.setCode(ResultCode.ERROR_DATA);
        } else {
            result.setContent(userVO);
            result.setMsg("查询成功");
            result.setCode(ResultCode.SUCCESS);
        }
        return result;
    }

    @ApiOperation(value = "分页查询用户", hidden = true)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userVO", value = "用户信息", required = true, paramType = "body", dataType = "UserVO")
    })
    @GetMapping(value = "retrieve")
    public JquryDataTablesVO<UserVO> retrieve(UserVO vo, HttpServletRequest request) {

        String orderColumnIndex = request.getParameter("order[0][column]");
        String orderColumnName = request.getParameter("columns[" + orderColumnIndex + "][data]");
        String orderDir = request.getParameter("order[0][dir]");
        vo.setOrderColumnName(orderColumnName);
        vo.setOrderDir(orderDir);

        List<UserVO> result = userService.retrieveAllWithPage(vo);
        Long count = userService.retrieveAllCount(vo);
        JquryDataTablesVO rtv = JqueryDataTablesUtil.madeJqueryDatatablesVO(count, result);
        return rtv;
    }

    @ApiOperation(value = "更新用户角色", hidden = true)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userVO", value = "用户信息", required = true, paramType = "body", dataType = "UserVO")
    })
    @PostMapping(value = "updateUserRole")
    public RequestResultVO updateUserRole(UserVO vo, HttpServletRequest request) {

        String pk_user = request.getParameter("pk_user");
        String pk_role = request.getParameter("pk_role");

        vo.setPk_user(pk_user);
        vo.setPk_role(pk_role);

        boolean isSuccess = userService.updateUserRole(vo);
        RequestResultVO resultVO = new RequestResultVO();
        resultVO.setSuccess(isSuccess);
        return resultVO;
    }

//    @ApiOperation(value = "忘记秘密处理")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "userVO", value = "用户信息", required = true, paramType = "body", dataType = "UserVO")
//    })
//    @PostMapping(value = "forgetPW")
//    public RequestResultVO forgetPassword(UserVO vo) {
//        Boolean isSuccess = userService.forgetPassword(vo);
//        RequestResultVO resultVO = new RequestResultVO();
//        resultVO.setSuccess(isSuccess);
//        return resultVO;
//    }

}