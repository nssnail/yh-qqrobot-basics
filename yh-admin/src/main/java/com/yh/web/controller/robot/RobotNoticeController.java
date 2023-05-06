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
import com.yh.robot.domain.RobotNotice;
import com.yh.robot.service.RobotNoticeService;
import com.yh.common.utils.poi.ExcelUtil;
import com.yh.common.core.page.TableDataInfo;

/**
 * 通知公告Controller
 *
 * @author nssnail
 * @date 2023-03-30
 */
@RestController
@RequestMapping("/robot/notice")
public class RobotNoticeController extends BaseController {
    @Autowired
    private RobotNoticeService robotNoticeService;

    /**
     * 查询通知公告列表
     */
    @PreAuthorize("@ss.hasPermi('robot:notice:list')")
    @GetMapping("/list")
    public TableDataInfo list(RobotNotice robotNotice) {
        startPage();
        List<RobotNotice> list = robotNoticeService.selectRobotNoticeList(robotNotice);
        return getDataTable(list);
    }

    /**
     * 导出通知公告列表
     */
    @PreAuthorize("@ss.hasPermi('robot:notice:export')")
    @Log(title = "通知公告", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RobotNotice robotNotice) {
        List<RobotNotice> list = robotNoticeService.selectRobotNoticeList(robotNotice);
        ExcelUtil<RobotNotice> util = new ExcelUtil<RobotNotice>(RobotNotice.class);
        util.exportExcel(response, list, "通知公告数据");
    }

    /**
     * 获取通知公告详细信息
     */
    @PreAuthorize("@ss.hasPermi('robot:notice:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(robotNoticeService.selectRobotNoticeById(id));
    }

    /**
     * 新增通知公告
     */
    @PreAuthorize("@ss.hasPermi('robot:notice:add')")
    @Log(title = "通知公告", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RobotNotice robotNotice) {
        return toAjax(robotNoticeService.insertRobotNotice(robotNotice));
    }

    /**
     * 修改通知公告
     */
    @PreAuthorize("@ss.hasPermi('robot:notice:edit')")
    @Log(title = "通知公告", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RobotNotice robotNotice) {
        return toAjax(robotNoticeService.updateRobotNotice(robotNotice));
    }

    /**
     * 删除通知公告
     */
    @PreAuthorize("@ss.hasPermi('robot:notice:remove')")
    @Log(title = "通知公告", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(robotNoticeService.deleteRobotNoticeByIds(ids));
    }


    @Log(title = "通知", businessType = BusinessType.OTHER)
    @PreAuthorize("@ss.hasPermi('robot:notice:edit')")
    @PostMapping("/noticeAll/{id}")
    public AjaxResult notice(@PathVariable Long id) {
        robotNoticeService.notice(id);
        return AjaxResult.success("通知成功");
    }
}
