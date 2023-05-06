<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      size="small"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="群号" prop="groupId">
        <el-input
          v-model="queryParams.groupId"
          placeholder="请输入群号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="群名称" prop="groupName">
        <el-input
          v-model="queryParams.groupName"
          placeholder="请输入群名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="群备注" prop="groupMemo">
        <el-input
          v-model="queryParams.groupMemo"
          placeholder="请输入群备注"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="群创建时间" prop="groupCreateTime">
        <el-date-picker
          clearable
          v-model="queryParams.groupCreateTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择群创建时间"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item label="机器人qq" prop="selfId">
        <el-input
          v-model="queryParams.selfId"
          placeholder="请输入机器人qq"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="请选择状态"
          clearable
        >
          <el-option
            v-for="dict in dict.type.status_enum"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置</el-button
        >
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <!-- <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['robot:group:add']"
          >新增</el-button
        >
      </el-col> -->
      <!-- <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['robot:group:edit']"
          >修改</el-button
        >
      </el-col> -->
      <!-- <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['robot:group:remove']"
          >删除</el-button
        >
      </el-col> -->
      <!-- <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['robot:group:export']"
          >导出</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar> -->
    </el-row>

    <el-table
      v-loading="loading"
      :data="groupList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="群号" align="center" prop="groupId" />
      <el-table-column label="群名称" align="center" prop="groupName" />
      <el-table-column label="群备注" align="center" prop="groupMemo" />
      <el-table-column
        label="群创建时间"
        align="center"
        prop="groupCreateTime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.groupCreateTime, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="机器人qq" align="center" prop="selfId" />
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag
            :options="dict.type.status_enum"
            :value="scope.row.status"
          />
        </template>
      </el-table-column>
      <el-table-column
        label="配置"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            @click="settingKeyWord(scope.row)"
            v-hasPermi="['robot:group:add']"
            >配置关键字</el-button
          >
          <el-button
            size="mini"
            type="text"
            @click="settingJob(scope.row)"
            v-hasPermi="['robot:group:add']"
            >配置定时任务</el-button
          >
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            @click="handleToggle(scope.row.id)"
            v-hasPermi="['robot:friends:edit']"
            >{{ scope.row.status == 1 ? "禁用" : "启用" }}</el-button
          >
          <!-- <el-button
            size="mini"
            type="text"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['robot:group:edit']"
            >修改</el-button
          > -->
          <!-- <el-button
            size="mini"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['robot:group:remove']"
            >删除</el-button
          > -->
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改群列对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="群名称" prop="groupName">
          <el-input v-model="form.groupName" placeholder="请输入群名称" />
        </el-form-item>
        <el-form-item label="群备注" prop="groupMemo">
          <el-input v-model="form.groupMemo" placeholder="请输入群备注" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 添加或修改群列对话框 -->
    <el-dialog
      :title="`群号：${keyWordsForm.groupId},群名：${keyWordsForm.groupName}`"
      :visible.sync="isSettingKeyWord"
      width="500px"
      append-to-body
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="选择关键字：">
          <el-checkbox
            :indeterminate="isIndeterminate"
            v-model="checkAll"
            @change="handleCheckAllChange"
            >全选</el-checkbox
          >
          <div style="margin: 15px 0"></div>
          <el-checkbox-group
            v-model="keyWordsForm.relIds"
            @change="handleKeyWordChange"
          >
            <el-checkbox
              v-for="item in keyWords"
              :label="item.id"
              :key="item.id"
              >{{ item.keyWord }}</el-checkbox
            >
          </el-checkbox-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFormSettingKeyWord"
          >确 定</el-button
        >
        <el-button @click="cancelSettingKeyWord">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog
      :title="`群号：${jobsForm.groupId},群名：${jobsForm.groupName}`"
      :visible.sync="isSettingJob"
      width="60%"
      append-to-body
      @close="cancelSettingJob"
    >
      <el-table
        ref="jobTable"
        :data="jobs"
        tooltip-effect="dark"
        style="width: 100%"
        @selection-change="jobChange"
      >
        <el-table-column type="selection" width="55"> </el-table-column>
        <el-table-column label="任务名称" prop="jobName" />
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFormJobs">确 定</el-button>
        <el-button @click="cancelSettingJob">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listGroup,
  getGroup,
  delGroup,
  addGroup,
  updateGroup,
  toggle,
  insertRel,
  getRel,
} from "@/api/robot/group";

import { listMessage } from "@/api/monitor/job";

import { listAll } from "@/api/robot/word";

export default {
  name: "Group",
  dicts: ["status_enum"],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 群列表格数据
      groupList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        groupId: null,
        groupName: null,
        groupMemo: null,
        groupCreateTime: null,
        selfId: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      // 是否显示配置关键字
      isSettingKeyWord: false,
      keyWords: [],
      keyWordsForm: {
        groupId: 0,
        groupName: "",
        relIds: [],
        relType: 1,
      },
      isIndeterminate: true,
      checkAll: false,
      isSettingJob: false,
      jobs: [],
      jobsForm: {
        groupId: 0,
        nickname: "",
        relIds: [],
        relType: 2,
      },
      multipleSelection: [],
    };
  },
  created() {
    this.getList();
    // this.loadKeyWords();
    // this.loadJobs();
  },
  methods: {
    handleCheckAllChange(val) {
      let ids = this.keyWords.map((e) => e.id);
      this.keyWordsForm.relIds = val ? ids : [];
      this.isIndeterminate = false;
    },
    handleKeyWordChange(value) {
      let checkedCount = value.length;
      this.checkAll = checkedCount === this.keyWords.length;
      this.isIndeterminate =
        checkedCount > 0 && checkedCount < this.keyWords.length;
    },
    submitFormSettingKeyWord() {
      insertRel(this.keyWordsForm).then((res) => {
        this.$message.success(res.msg);
        this.isSettingKeyWord = false;
        this.keyWordsForm = {
          groupId: 0,
          groupName: "",
          relIds: [],
          relType: 1,
        };
      });
    },
    cancelSettingKeyWord() {
      this.isSettingKeyWord = false;
      this.keyWordsForm = {
        groupId: 0,
        groupName: "",
        relIds: [],
        relType: 1,
      };
    },
    loadKeyWords() {
      return listAll().then((res) => {
        this.keyWords = res.data;
      });
    },
    loadJobs() {
      return listMessage().then((res) => {
        this.jobs = res.data;
      });
    },
    async settingKeyWord(row) {
      this.isSettingKeyWord = true;
      let params = {
        groupId: row.groupId,
        relType: 1,
      };
      await this.loadKeyWords();
      getRel(params).then((res) => {
        let relData = res.data;
        let relIds = relData.map((item) => item.relId);
        console.log(relIds);
        this.keyWordsForm = {
          groupId: row.groupId,
          groupName: row.groupName,
          relIds: relIds,
          relType: 1,
        };
      });
    },
    cancelSettingJob() {
      this.isSettingJob = false;
      this.jobsForm = {
        groupId: 0,
        relIds: [],
        relType: 2,
      };
      this.$refs.jobTable.clearSelection();
    },
    async settingJob(row) {
      this.isSettingJob = true;
      let params = {
        groupId: row.groupId,
        relType: 2,
      };
      await this.loadJobs();
      getRel(params).then((res) => {
        let relData = res.data;
        let relIds = relData.map((item) => item.relId);
        this.jobsForm = {
          groupName: row.groupName,
          groupId: row.groupId,
          relIds: relIds,
          relType: 2,
        };
        let rows = this.jobs.filter((item) => {
          return relIds.includes(item.jobId);
        });
        console.log(rows);
        rows.forEach((item) => {
          this.$refs.jobTable.toggleRowSelection(item, true);
        });
      });
    },
    jobChange(val) {
      this.multipleSelection = val;
    },
    submitFormJobs() {
      let relIds = this.multipleSelection.map((item) => item.jobId);
      let params = {
        ...this.jobsForm,
        relIds: relIds,
      };
      insertRel(params).then((res) => {
        this.$message.success(res.msg);
        this.isSettingJob = false;
        this.jobsForm = {
          groupId: 0,
          relIds: [],
          relType: 2,
        };
        this.$refs.jobTable.clearSelection();
      });
    },
    handleToggle(id) {
      toggle(id).then((response) => {
        this.$message.success("操作成功");
        this.getList();
      });
    },
    /** 查询群列列表 */
    getList() {
      this.loading = true;
      listGroup(this.queryParams).then((response) => {
        this.groupList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        groupId: null,
        groupName: null,
        groupMemo: null,
        groupCreateTime: null,
        selfId: null,
        status: null,
        createTime: null,
        updateTime: null,
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加群列";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getGroup(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改群列";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateGroup(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addGroup(this.form).then((response) => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal
        .confirm('是否确认删除群列编号为"' + ids + '"的数据项？')
        .then(function () {
          return delGroup(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        "robot/group/export",
        {
          ...this.queryParams,
        },
        `group_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
