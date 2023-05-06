package com.yh.web.controller.robot;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.yh.common.annotation.Log;
import com.yh.common.core.controller.BaseController;
import com.yh.common.core.domain.AjaxResult;
import com.yh.common.enums.BusinessType;
import com.yh.robot.domain.RobotHandler;
import com.yh.robot.service.RobotHandlerService;
import com.yh.common.utils.poi.ExcelUtil;
import com.yh.common.core.page.TableDataInfo;

/**
 * 处理器Controller
 *
 * @author nssnail
 * @date 2023-03-31
 */
@RestController
@RequestMapping("/robot/handler")
public class RobotHandlerController extends BaseController {
    @Autowired
    private RobotHandlerService robotHandlerService;

    /**
     * 查询处理器列表
     */
    @PreAuthorize("@ss.hasPermi('robot:handler:list')")
    @GetMapping("/list")
    public TableDataInfo list(RobotHandler robotHandler) {
        startPage();
        List<RobotHandler> list = robotHandlerService.selectRobotHandlerList(robotHandler);
        return getDataTable(list);
    }

    /**
     * 查询处理器列表
     */
    @PreAuthorize("@ss.hasPermi('robot:handler:list')")
    @PostMapping("/listAll")
    public AjaxResult listAll(@RequestBody RobotHandler robotHandler) {
        List<RobotHandler> list = robotHandlerService.selectRobotHandlerList(robotHandler);
        return AjaxResult.success(list);
    }

    /**
     * 导出处理器列表
     */
    @PreAuthorize("@ss.hasPermi('robot:handler:export')")
    @Log(title = "处理器", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RobotHandler robotHandler) {
        List<RobotHandler> list = robotHandlerService.selectRobotHandlerList(robotHandler);
        ExcelUtil<RobotHandler> util = new ExcelUtil<RobotHandler>(RobotHandler.class);
        util.exportExcel(response, list, "处理器数据");
    }

    /**
     * 获取处理器详细信息
     */
    @PreAuthorize("@ss.hasPermi('robot:handler:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(robotHandlerService.selectRobotHandlerById(id));
    }

    /**
     * 新增处理器
     */
    @PreAuthorize("@ss.hasPermi('robot:handler:add')")
    @Log(title = "处理器", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RobotHandler robotHandler) {
        return toAjax(robotHandlerService.insertRobotHandler(robotHandler));
    }

    /**
     * 修改处理器
     */
    @PreAuthorize("@ss.hasPermi('robot:handler:edit')")
    @Log(title = "处理器", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RobotHandler robotHandler) {
        return toAjax(robotHandlerService.updateRobotHandler(robotHandler));
    }

    /**
     * 删除处理器
     */
    @PreAuthorize("@ss.hasPermi('robot:handler:remove')")
    @Log(title = "处理器", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(robotHandlerService.deleteRobotHandlerByIds(ids));
    }
}
