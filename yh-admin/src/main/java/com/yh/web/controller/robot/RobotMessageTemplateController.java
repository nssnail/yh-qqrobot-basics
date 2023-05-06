package com.yh.web.controller.robot;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.yh.robot.params.MessageTemplateParams;
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
import com.yh.robot.domain.RobotMessageTemplate;
import com.yh.robot.service.RobotMessageTemplateService;
import com.yh.common.utils.poi.ExcelUtil;
import com.yh.common.core.page.TableDataInfo;

/**
 * 消息模板Controller
 *
 * @author nssnail
 * @date 2023-04-06
 */
@RestController
@RequestMapping("/robot/messageTemplate")
public class RobotMessageTemplateController extends BaseController {
    @Autowired
    private RobotMessageTemplateService robotMessageTemplateService;

    /**
     * 查询消息模板列表
     */
    @PreAuthorize("@ss.hasPermi('robot:messageTemplate:list')")
    @GetMapping("/list")
    public TableDataInfo list(RobotMessageTemplate robotMessageTemplate) {
        startPage();
        List<RobotMessageTemplate> list = robotMessageTemplateService.selectRobotMessageTemplateList(robotMessageTemplate);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('robot:messageTemplate:list')")
    @GetMapping("/listAll")
    public AjaxResult listAll() {
        List<RobotMessageTemplate> list = robotMessageTemplateService.list();
        return AjaxResult.success(list);
    }


    /**
     * 导出消息模板列表
     */
    @PreAuthorize("@ss.hasPermi('robot:messageTemplate:export')")
    @Log(title = "消息模板", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RobotMessageTemplate robotMessageTemplate) {
        List<RobotMessageTemplate> list = robotMessageTemplateService.selectRobotMessageTemplateList(robotMessageTemplate);
        ExcelUtil<RobotMessageTemplate> util = new ExcelUtil<RobotMessageTemplate>(RobotMessageTemplate.class);
        util.exportExcel(response, list, "消息模板数据");
    }

    /**
     * 获取消息模板详细信息
     */
    @PreAuthorize("@ss.hasPermi('robot:messageTemplate:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(robotMessageTemplateService.selectRobotMessageTemplateById(id));
    }

    /**
     * 新增消息模板
     */
    @PreAuthorize("@ss.hasPermi('robot:messageTemplate:add')")
    @Log(title = "消息模板", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MessageTemplateParams messageTemplateParams) {
        return toAjax(robotMessageTemplateService.insertRobotMessageTemplate(messageTemplateParams));
    }

    /**
     * 修改消息模板
     */
    @PreAuthorize("@ss.hasPermi('robot:messageTemplate:edit')")
    @Log(title = "消息模板", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MessageTemplateParams messageTemplateParams) {
        return toAjax(robotMessageTemplateService.updateRobotMessageTemplate(messageTemplateParams));
    }

    /**
     * 删除消息模板
     */
    @PreAuthorize("@ss.hasPermi('robot:messageTemplate:remove')")
    @Log(title = "消息模板", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(robotMessageTemplateService.deleteRobotMessageTemplateByIds(ids));
    }
}
