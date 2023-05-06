package com.yh.web.controller.robot;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.yh.robot.params.FriendsRelParams;
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
import com.yh.robot.domain.RobotFriends;
import com.yh.robot.service.RobotFriendsService;
import com.yh.common.utils.poi.ExcelUtil;
import com.yh.common.core.page.TableDataInfo;

/**
 * 好友列Controller
 *
 * @author nssnail
 * @date 2023-03-29
 */
@RestController
@RequestMapping("/robot/friends")
public class RobotFriendsController extends BaseController {
    @Autowired
    private RobotFriendsService robotFriendsService;

    /**
     * 查询好友列列表
     */
    @PreAuthorize("@ss.hasPermi('robot:friends:list')")
    @GetMapping("/list")
    public TableDataInfo list(RobotFriends robotFriends) {
        startPage();
        List<RobotFriends> list = robotFriendsService.selectRobotFriendsList(robotFriends);
        return getDataTable(list);
    }

    /**
     * 导出好友列列表
     */
    @PreAuthorize("@ss.hasPermi('robot:friends:export')")
    @Log(title = "好友列", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RobotFriends robotFriends) {
        List<RobotFriends> list = robotFriendsService.selectRobotFriendsList(robotFriends);
        ExcelUtil<RobotFriends> util = new ExcelUtil<RobotFriends>(RobotFriends.class);
        util.exportExcel(response, list, "好友列数据");
    }

    /**
     * 获取好友列详细信息
     */
    @PreAuthorize("@ss.hasPermi('robot:friends:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(robotFriendsService.selectRobotFriendsById(id));
    }

    /**
     * 新增好友列
     */
    @PreAuthorize("@ss.hasPermi('robot:friends:add')")
    @Log(title = "好友列", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RobotFriends robotFriends) {
        return toAjax(robotFriendsService.insertRobotFriends(robotFriends));
    }

    /**
     * 修改好友列
     */
    @PreAuthorize("@ss.hasPermi('robot:friends:edit')")
    @Log(title = "好友列", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RobotFriends robotFriends) {
        return toAjax(robotFriendsService.updateRobotFriends(robotFriends));
    }

    /**
     * 删除好友列
     */
    @PreAuthorize("@ss.hasPermi('robot:friends:remove')")
    @Log(title = "好友列", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(robotFriendsService.deleteRobotFriendsByIds(ids));
    }

    @PreAuthorize("@ss.hasPermi('robot:friends:edit')")
    @Log(title = "更新状态", businessType = BusinessType.UPDATE)
    @PostMapping("/toggle/{id}")
    public AjaxResult toggle(@PathVariable Long id) {
        robotFriendsService.toggle(id);
        return AjaxResult.success("操作成功");
    }


    /**
     * 新增群列
     */
    @PreAuthorize("@ss.hasPermi('robot:friends:add')")
    @Log(title = "新增好友关联", businessType = BusinessType.INSERT)
    @PostMapping("/insertRel")
    public AjaxResult insertRel(@RequestBody @Validated FriendsRelParams friendsRelParams) {
        robotFriendsService.insertOrUpdateFriendsRel(friendsRelParams);
        return AjaxResult.success("操作成功");
    }

    @PreAuthorize("@ss.hasPermi('robot:friends:add')")
    @PostMapping("/getRel")
    public AjaxResult getRel(@RequestBody @Validated FriendsRelParams friendsRelParams) {
        return AjaxResult.success(robotFriendsService.getRel(friendsRelParams));
    }
}
