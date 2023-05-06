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
      <el-form-item label="关键词" prop="keyWord">
        <el-input
          v-model="queryParams.keyWord"
          placeholder="请输入关键词"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="state">
        <el-select
          v-model="queryParams.state"
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
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['robot:word:add']"
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
          v-hasPermi="['robot:word:edit']"
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
          v-hasPermi="['robot:word:remove']"
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
          v-hasPermi="['robot:word:export']"
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
      :data="wordList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="关键词" align="center" prop="keyWord" />
      <el-table-column label="处理器类型" align="center" prop="handleType">
        <template slot-scope="scope">
          <dict-tag
            :options="dict.type.handle_type"
            :value="scope.row.handleType"
          />
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="state">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.status_enum" :value="scope.row.state" />
        </template>
      </el-table-column>
      <el-table-column label="返回类型" align="center" prop="sendMsgType">
        <template slot-scope="scope">
          <dict-tag
            :options="dict.type.send_msg_type"
            :value="scope.row.sendMsgType"
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
            v-hasPermi="['robot:word:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['robot:word:remove']"
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

    <!-- 添加或修改关键字管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="关键词" prop="keyWord">
          <el-input v-model="form.keyWord" placeholder="请输入关键词"/>
        </el-form-item>
        <el-form-item label="备注" prop="remake">
          <el-input
            v-model="form.remake"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
        <el-form-item label="处理器类型" prop="handleId">
          <el-select v-model="form.handleId" placeholder="请选择处理器类型"  @change="handleChange">
            <el-option
              v-for="item in handles"
              :key="item.id"
              :label="item.handleName"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item
          v-if="form.beanName === 'TextSimpleHandler'"
          label="是否随机"
          prop="isRandom"
        >
          <el-radio-group v-model="form.isRandom">
            <el-radio
              v-for="dict in dict.type.yes_no_enum"
              :key="dict.value"
              :label="parseInt(dict.value)"
              >{{ dict.label }}</el-radio
            >
          </el-radio-group>
        </el-form-item>
        <el-form-item
          v-if="form.beanName === 'TextSimpleHandler'"
          label="文本内容"
          prop="contents"
        >
          <div v-for="(item, index) in contents" class="contents" :key="index">
            <el-input v-model="contents[index]" placeholder="请输入匹配文本" />
            <i
              v-if="contents.length != 1"
              class="del-btn el-icon-remove-outline"
              @click="delContent(index)"
            ></i>
          </div>
          <div class="btn-context">
            <i
              v-if="contents.length != 20"
              class="add-btn el-icon-circle-plus-outline"
              @click="addContent"
            ></i>
          </div>
        </el-form-item>
        <el-form-item v-if="form.beanName === 'ImageSimpleHandler'" label="图片" prop="images">
          <image-upload v-model="form.images" :limit="10" />
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
  listWord,
  getWord,
  delWord,
  addWord,
  updateWord,
} from "@/api/robot/word";

import { listAll } from "@/api/robot/handler";

export default {
  name: "Word",
  dicts: ["handle_type", "status_enum", "send_msg_type", "yes_no_enum"],
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
      // 关键字管理表格数据
      wordList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        keyWord: null,
        handleType: null,
        state: null,
        sendMsgType: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        keyWord: [{ required: true, message: "请填写关键字", trigger: "blur" }],
        handleId: [
          { required: true, message: "请选择处理器类型", trigger: "change" },
        ],
      },
      handles: [],
      contents: [""],
    };
  },
  created() {
    this.getList();
    this.getHandles();
  },
  methods: {
    handleChange(val){
      let handle=this.handles.find(item=>item.id===val)
      this.form.beanName = handle.beanName
      // console.log(this.form);
    },
    addContent() {
      this.contents.push("");
    },
    delContent(index) {
      this.contents.splice(index, 1);
    },
    getHandles() {
      listAll({ status: 1 }).then((res) => {
        this.handles = res.data;
      });
    },
    /** 查询关键字管理列表 */
    getList() {
      this.loading = true;
      listWord(this.queryParams).then((response) => {
        this.wordList = response.rows;
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
        keyWord: null,
        remake: null,
        beanName: null,
        method: null,
        handleId: null,
        handleType: null,
        state: null,
        sendMsgType: null,
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
      this.title = "添加关键字管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getWord(id).then((response) => {
        // let data = response.data;
        // data.contents.filter(e->e.)
        this.form = response.data;
        this.contents = response.data.contents;
        this.open = true;
        this.title = "修改关键字管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.handleId != 25) {
            this.contents = [];
          }
          let params = {
            ...this.form,
            contents: this.contents,
          };
          if (this.form.id != null) {
            updateWord(params).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addWord(params).then((response) => {
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
        .confirm('是否确认删除关键字管理编号为"' + ids + '"的数据项？')
        .then(function () {
          return delWord(ids);
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
        "robot/word/export",
        {
          ...this.queryParams,
        },
        `word_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>

<style scoped>
.contents {
  margin-bottom: 1rem;
  display: flex;
  align-items: center;
}
.btn-context {
  display: flex;
}
.add-btn {
  font-size: 20px;
  cursor: pointer;
  margin-right: 1rem;
  color: #409eff;
}
.del-btn {
  font-size: 20px;
  cursor: pointer;
  margin-left: 1rem;
  color: #f56c6c;
}
</style>
