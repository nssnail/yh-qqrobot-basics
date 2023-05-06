<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="处理器beanName" prop="beanName">
        <el-input
          v-model="queryParams.beanName"
          placeholder="请输入处理器beanName"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="处理器名称" prop="handleName">
        <el-input
          v-model="queryParams.handleName"
          placeholder="请输入处理器名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="处理器类型" prop="handleType">
        <el-select v-model="queryParams.handleType" placeholder="请选择处理器类型" clearable>
          <el-option
            v-for="dict in dict.type.handle_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="返回值类型" prop="sendMsgType">
        <el-select v-model="queryParams.sendMsgType" placeholder="请选择返回值类型" clearable>
          <el-option
            v-for="dict in dict.type.send_msg_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.status_enum"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="排序" prop="sortNum">
        <el-input
          v-model="queryParams.sortNum"
          placeholder="请输入排序"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['robot:handler:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['robot:handler:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['robot:handler:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['robot:handler:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row> -->

    <el-table v-loading="loading" :data="handlerList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="id" align="center" prop="id" /> -->
      <el-table-column label="处理器beanName" align="center" prop="beanName" />
      <el-table-column label="处理器名称" align="center" prop="handleName" />
      <el-table-column label="处理器类型" align="center" prop="handleType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.handle_type" :value="scope.row.handleType"/>
        </template>
      </el-table-column>
      <el-table-column label="返回值类型" align="center" prop="sendMsgType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.send_msg_type" :value="scope.row.sendMsgType"/>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.status_enum" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="是否是外部api" align="center" prop="isApi">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.yes_no_enum" :value="scope.row.isApi"/>
        </template>
      </el-table-column>
      <el-table-column label="排序" align="center" prop="sortNum" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['robot:handler:edit']"
          >修改</el-button>
          <!-- <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['robot:handler:remove']"
          >删除</el-button> -->
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改处理器对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <!-- <el-form-item label="处理器beanName" prop="beanName">
          <el-input v-model="form.beanName" placeholder="请输入处理器beanName" />
        </el-form-item>
        <el-form-item label="处理器名称" prop="handleName">
          <el-input v-model="form.handleName" placeholder="请输入处理器名称" />
        </el-form-item>
        <el-form-item label="处理器类型" prop="handleType">
          <el-select v-model="form.handleType" placeholder="请选择处理器类型">
            <el-option
              v-for="dict in dict.type.handle_type"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="返回值类型" prop="sendMsgType">
          <el-select v-model="form.sendMsgType" placeholder="请选择返回值类型">
            <el-option
              v-for="dict in dict.type.send_msg_type"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item> -->
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in dict.type.status_enum"
              :key="dict.value"
              :label="parseInt(dict.value)"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <!-- <el-form-item label="是否是外部api" prop="isApi">
          <el-radio-group v-model="form.isApi">
            <el-radio
              v-for="dict in dict.type.yes_no_enum"
              :key="dict.value"
              :label="parseInt(dict.value)"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item> -->
        <el-form-item label="排序" prop="sortNum">
          <el-input v-model="form.sortNum" placeholder="请输入排序" />
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
import { listHandler, getHandler, delHandler, addHandler, updateHandler } from "@/api/robot/handler";

export default {
  name: "Handler",
  dicts: ['handle_type', 'yes_no_enum', 'send_msg_type', 'status_enum'],
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
      // 处理器表格数据
      handlerList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        beanName: null,
        handleName: null,
        handleType: null,
        sendMsgType: null,
        status: null,
        sortNum: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询处理器列表 */
    getList() {
      this.loading = true;
      listHandler(this.queryParams).then(response => {
        this.handlerList = response.rows;
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
        beanName: null,
        handleName: null,
        handleType: null,
        sendMsgType: null,
        status: null,
        isApi: null,
        sortNum: null,
        createTime: null,
        updateTime: null
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
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加处理器";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getHandler(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改处理器";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateHandler(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addHandler(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除处理器编号为"' + ids + '"的数据项？').then(function() {
        return delHandler(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('robot/handler/export', {
        ...this.queryParams
      }, `handler_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
