package com.yh.web.controller.robot;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yh.robot.domain.RobotHandler;
import com.yh.robot.enums.StateEnum;
import com.yh.robot.params.KeyWorldParams;
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
import com.yh.robot.domain.RobotKeyWord;
import com.yh.robot.service.RobotKeyWordService;
import com.yh.common.utils.poi.ExcelUtil;
import com.yh.common.core.page.TableDataInfo;

/**
 * 关键字管理Controller
 *
 * @author nssnail
 * @date 2023-03-31
 */
@RestController
@RequestMapping("/robot/word")
public class RobotKeyWordController extends BaseController {
    @Autowired
    private RobotKeyWordService robotKeyWordService;

    /**
     * 查询关键字管理列表
     */
    @PreAuthorize("@ss.hasPermi('robot:word:list')")
    @GetMapping("/list")
    public TableDataInfo list(RobotKeyWord robotKeyWord) {
        startPage();
        List<RobotKeyWord> list = robotKeyWordService.selectRobotKeyWordList(robotKeyWord);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('robot:word:list')")
    @PostMapping("/listAll")
    public AjaxResult listAll() {
        List<RobotKeyWord> robotKeyWords = robotKeyWordService.list(Wrappers.<RobotKeyWord>lambdaQuery()
                .eq(RobotKeyWord::getState, StateEnum.NORMAL.getType())
                .ne(RobotKeyWord::getKeyWord, "帮助"));
        return AjaxResult.success(robotKeyWords);
    }

    /**
     * 导出关键字管理列表
     */
    @PreAuthorize("@ss.hasPermi('robot:word:export')")
    @Log(title = "关键字管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RobotKeyWord robotKeyWord) {
        List<RobotKeyWord> list = robotKeyWordService.selectRobotKeyWordList(robotKeyWord);
        ExcelUtil<RobotKeyWord> util = new ExcelUtil<RobotKeyWord>(RobotKeyWord.class);
        util.exportExcel(response, list, "关键字管理数据");
    }

    /**
     * 获取关键字管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('robot:word:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(robotKeyWordService.selectRobotKeyWordById(id));
    }

    /**
     * 新增关键字管理
     */
    @PreAuthorize("@ss.hasPermi('robot:word:add')")
    @Log(title = "关键字管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody KeyWorldParams keyWorldParams) {
        return toAjax(robotKeyWordService.insertRobotKeyWord(keyWorldParams));
    }

    /**
     * 修改关键字管理
     */
    @PreAuthorize("@ss.hasPermi('robot:word:edit')")
    @Log(title = "关键字管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody KeyWorldParams keyWorldParams) {
        return toAjax(robotKeyWordService.updateRobotKeyWord(keyWorldParams));
    }

    /**
     * 删除关键字管理
     */
    @PreAuthorize("@ss.hasPermi('robot:word:remove')")
    @Log(title = "关键字管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(robotKeyWordService.deleteRobotKeyWordByIds(ids));
    }
}
