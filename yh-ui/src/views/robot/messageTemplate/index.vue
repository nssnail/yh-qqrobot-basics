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
      <el-form-item label="模板名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入模板名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="模板类型" prop="tempType">
        <el-select
          v-model="queryParams.tempType"
          placeholder="请选择模板类型"
          clearable
        >
          <el-option
            v-for="dict in dict.type.message_template_type"
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
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['robot:messageTemplate:add']"
          >新增</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['robot:messageTemplate:edit']"
          >修改</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['robot:messageTemplate:remove']"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['robot:messageTemplate:export']"
          >导出</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="messageTemplateList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="模板名称" align="center" prop="name" />
      <el-table-column label="内容" align="center" prop="content" />
      <!-- <el-table-column label="图片" align="center" prop="images" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.images" :width="50" :height="50"/>
        </template>
      </el-table-column> -->
      <el-table-column label="模板类型" align="center" prop="tempType">
        <template slot-scope="scope">
          <dict-tag
            :options="dict.type.message_template_type"
            :value="scope.row.tempType"
          />
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
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['robot:messageTemplate:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['robot:messageTemplate:remove']"
            >删除</el-button
          >
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

    <!-- 添加或修改消息模板对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="70%" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="模板名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入模板名称" />
        </el-form-item>
        <el-form-item label="模板类型" prop="tempType">
          <el-select v-model="form.tempType" placeholder="请选择模板类型">
            <el-option
              v-for="dict in dict.type.message_template_type"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item v-if="form.tempType != 3" label="内容" prop="content">
          <el-input
            v-model="form.content"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
        <!-- <el-form-item label="图片" prop="images">
          <image-upload v-model="form.images"/>
        </el-form-item> -->
        <el-form-item
          v-if="form.tempType == 1"
          label="工作日发送"
          prop="isHoliday"
        >
          <el-radio-group v-model="form.isHoliday">
            <el-radio
              v-for="dict in dict.type.yes_no_enum"
              :key="dict.value"
              :label="parseInt(dict.value)"
              >{{ dict.label }}</el-radio
            >
          </el-radio-group>
        </el-form-item>
        <el-form-item v-if="form.tempType == 2" label="倒计时时间" prop="time">
          <el-date-picker
            v-model="form.time"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择倒计时时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item v-if="form.tempType == 3" label="选择api" prop="handleId">
          <el-select v-model="form.handleId" placeholder="请选择处理器类型">
            <el-option
              v-for="item in handles"
              :key="item.id"
              :label="item.handleName"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item v-if="form.tempType == 3" label="参数" prop="param">
          <el-input v-model="form.param" placeholder="请输入api参数" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listMessageTemplate,
  getMessageTemplate,
  delMessageTemplate,
  addMessageTemplate,
  updateMessageTemplate,
} from "@/api/robot/messageTemplate";

import { listAll } from "@/api/robot/handler";

export default {
  name: "MessageTemplate",
  dicts: ["message_template_type", "yes_no_enum"],
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
      // 消息模板表格数据
      messageTemplateList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        content: null,
        images: null,
        tempType: null,
        config: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [{ required: true, message: "请填写模板名称", trigger: "blur" }],
        content: [
          { required: true, message: "请填写模板内容", trigger: "blur" },
        ],
        tempType: [
          { required: true, message: "请选择模板类型", trigger: "change" },
        ],
        isHoliday: [
          {
            required: true,
            message: "请选择是否仅工作日发送",
            trigger: "change",
          },
        ],
        time: [
          {
            required: true,
            message: "请选择倒计时时间",
            trigger: "change",
          },
        ],
        handleId: [{ required: true, message: "请选择api", trigger: "change" }],
      },
      handles: [],
    };
  },
  created() {
    this.getList();
    this.getHandles();
  },
  methods: {
    getHandles() {
      listAll({ status: 1, isApi: 1 }).then((res) => {
        this.handles = res.data;
      });
    },
    /** 查询消息模板列表 */
    getList() {
      this.loading = true;
      listMessageTemplate(this.queryParams).then((response) => {
        this.messageTemplateList = response.rows;
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
        name: null,
        content: null,
        images: null,
        tempType: null,
        config: null,
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
      this.title = "添加消息模板";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getMessageTemplate(id).then((response) => {
        let data = response.data;
        let config = JSON.parse(data.config);
        let res = {
          ...data,
          ...config,
        };
        this.form = res;

        this.open = true;
        this.title = "修改消息模板";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        let config = {
          isHoliday: this.form.isHoliday,
          time: this.form.time,
          handleId: this.form.handleId,
          param: this.form.param,
        };
        let params = {
          ...this.form,
          templateConfig: config,
        };
        if (valid) {
          if (this.form.id != null) {
            updateMessageTemplate(params).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addMessageTemplate(params).then((response) => {
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
        .confirm('是否确认删除消息模板编号为"' + ids + '"的数据项？')
        .then(function () {
          return delMessageTemplate(ids);
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
        "robot/messageTemplate/export",
        {
          ...this.queryParams,
        },
        `messageTemplate_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
