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
import com.yh.robot.domain.RobotKeyWordRel;
import com.yh.robot.service.RobotKeyWordRelService;
import com.yh.common.utils.poi.ExcelUtil;
import com.yh.common.core.page.TableDataInfo;

/**
 * 关键词关联Controller
 *
 * @author nssnail
 * @date 2023-03-31
 */
@RestController
@RequestMapping("/robot/wordrel")
public class RobotKeyWordRelController extends BaseController {
    @Autowired
    private RobotKeyWordRelService robotKeyWordRelService;

    /**
     * 查询关键词关联列表
     */
    @PreAuthorize("@ss.hasPermi('robot:wordrel:list')")
    @GetMapping("/list")
    public TableDataInfo list(RobotKeyWordRel robotKeyWordRel) {
        startPage();
        List<RobotKeyWordRel> list = robotKeyWordRelService.selectRobotKeyWordRelList(robotKeyWordRel);
        return getDataTable(list);
    }

    /**
     * 导出关键词关联列表
     */
    @PreAuthorize("@ss.hasPermi('robot:wordrel:export')")
    @Log(title = "关键词关联", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RobotKeyWordRel robotKeyWordRel) {
        List<RobotKeyWordRel> list = robotKeyWordRelService.selectRobotKeyWordRelList(robotKeyWordRel);
        ExcelUtil<RobotKeyWordRel> util = new ExcelUtil<RobotKeyWordRel>(RobotKeyWordRel.class);
        util.exportExcel(response, list, "关键词关联数据");
    }

    /**
     * 获取关键词关联详细信息
     */
    @PreAuthorize("@ss.hasPermi('robot:wordrel:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(robotKeyWordRelService.selectRobotKeyWordRelById(id));
    }

    /**
     * 新增关键词关联
     */
    @PreAuthorize("@ss.hasPermi('robot:wordrel:add')")
    @Log(title = "关键词关联", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RobotKeyWordRel robotKeyWordRel) {
        return toAjax(robotKeyWordRelService.insertRobotKeyWordRel(robotKeyWordRel));
    }

    /**
     * 修改关键词关联
     */
    @PreAuthorize("@ss.hasPermi('robot:wordrel:edit')")
    @Log(title = "关键词关联", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RobotKeyWordRel robotKeyWordRel) {
        return toAjax(robotKeyWordRelService.updateRobotKeyWordRel(robotKeyWordRel));
    }

    /**
     * 删除关键词关联
     */
    @PreAuthorize("@ss.hasPermi('robot:wordrel:remove')")
    @Log(title = "关键词关联", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(robotKeyWordRelService.deleteRobotKeyWordRelByIds(ids));
    }
}
