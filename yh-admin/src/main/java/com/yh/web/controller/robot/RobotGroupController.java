package com.yh.web.controller.robot;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.yh.robot.params.GroupRelParams;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
import com.yh.robot.domain.RobotGroup;
import com.yh.robot.service.RobotGroupService;
import com.yh.common.utils.poi.ExcelUtil;
import com.yh.common.core.page.TableDataInfo;

/**
 * 群列Controller
 *
 * @author nssnail
 * @date 2023-03-30
 */
@RestController
@RequestMapping("/robot/group")
public class RobotGroupController extends BaseController {
    @Autowired
    private RobotGroupService robotGroupService;

    /**
     * 查询群列列表
     */
    @PreAuthorize("@ss.hasPermi('robot:group:list')")
    @GetMapping("/list")
    public TableDataInfo list(RobotGroup robotGroup) {
        startPage();
        List<RobotGroup> list = robotGroupService.selectRobotGroupList(robotGroup);
        return getDataTable(list);
    }

    /**
     * 导出群列列表
     */
    @PreAuthorize("@ss.hasPermi('robot:group:export')")
    @Log(title = "群列", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RobotGroup robotGroup) {
        List<RobotGroup> list = robotGroupService.selectRobotGroupList(robotGroup);
        ExcelUtil<RobotGroup> util = new ExcelUtil<RobotGroup>(RobotGroup.class);
        util.exportExcel(response, list, "群列数据");
    }

    /**
     * 获取群列详细信息
     */
    @PreAuthorize("@ss.hasPermi('robot:group:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(robotGroupService.selectRobotGroupById(id));
    }

    /**
     * 新增群列
     */
    @PreAuthorize("@ss.hasPermi('robot:group:add')")
    @Log(title = "群列", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RobotGroup robotGroup) {
        return toAjax(robotGroupService.insertRobotGroup(robotGroup));
    }

    /**
     * 修改群列
     */
    @PreAuthorize("@ss.hasPermi('robot:group:edit')")
    @Log(title = "群列", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RobotGroup robotGroup) {
        return toAjax(robotGroupService.updateRobotGroup(robotGroup));
    }

    /**
     * 删除群列
     */
    @PreAuthorize("@ss.hasPermi('robot:group:remove')")
    @Log(title = "群列", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(robotGroupService.deleteRobotGroupByIds(ids));
    }


    @PreAuthorize("@ss.hasPermi('robot:group:edit')")
    @Log(title = "更新状态", businessType = BusinessType.UPDATE)
    @PostMapping("/toggle/{id}")
    public AjaxResult toggle(@PathVariable Long id) {
        robotGroupService.toggle(id);
        return AjaxResult.success("操作成功");
    }

    /**
     * 新增群列
     */
    @PreAuthorize("@ss.hasPermi('robot:group:add')")
    @Log(title = "群列", businessType = BusinessType.INSERT)
    @PostMapping("/insertRel")
    public AjaxResult insertRel(@RequestBody @Validated GroupRelParams groupRelParams) {
        robotGroupService.insertOrUpdateGroupRel(groupRelParams);
        return AjaxResult.success("操作成功");
    }

    @PreAuthorize("@ss.hasPermi('robot:group:add')")
    @PostMapping("/getRel")
    public AjaxResult getRel(@RequestBody @Validated GroupRelParams groupRelParams) {
        return AjaxResult.success(robotGroupService.getRel(groupRelParams));
    }
}
