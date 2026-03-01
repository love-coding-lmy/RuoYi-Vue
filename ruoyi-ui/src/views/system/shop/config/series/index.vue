<template>
  <div class="app-container">
    <!-- 搜索区域 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="系列名称" prop="seriesName">
        <el-input
          v-model="queryParams.seriesName"
          placeholder="请输入系列名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="系列编码" prop="seriesCode">
        <el-input
          v-model="queryParams.seriesCode"
          placeholder="请输入系列编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option label="正常" value="0" />
          <el-option label="停用" value="1" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['shop:series:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['shop:series:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 表格区域 -->
    <el-table v-loading="loading" :data="seriesList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="系列ID" align="center" prop="seriesId" width="80" />
      <el-table-column label="系列名称" align="center" prop="seriesName" :show-overflow-tooltip="true" />
      <el-table-column label="系列编码" align="center" prop="seriesCode" width="120" />
      <el-table-column label="系列封面" align="center" prop="seriesImage" width="120">
        <template slot-scope="scope">
          <image-preview v-if="scope.row.seriesImage" :src="scope.row.seriesImage" :width="80" :height="60" />
        </template>
      </el-table-column>
      <el-table-column label="系列描述" align="center" prop="seriesDesc" :show-overflow-tooltip="true" width="200" />
      <el-table-column label="排序号" align="center" prop="sortOrder" width="80" />
      <el-table-column label="状态" align="center" prop="status" width="80">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === '0'" type="success">正常</el-tag>
          <el-tag v-else type="danger">停用</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="160" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="180">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['shop:series:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['shop:series:remove']"
          >删除</el-button>
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

    <!-- 添加或修改对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="系列名称" prop="seriesName">
              <el-input v-model="form.seriesName" placeholder="请输入系列名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="系列编码" prop="seriesCode">
              <el-input v-model="form.seriesCode" placeholder="请输入系列编码" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="系列封面" prop="seriesImage">
              <image-upload v-model="form.seriesImage" :limit="1" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="系列描述" prop="seriesDesc">
              <el-input v-model="form.seriesDesc" type="textarea" :rows="3" placeholder="请输入系列描述" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="排序号" prop="sortOrder">
              <el-input-number v-model="form.sortOrder" :min="0" controls-position="right" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-radio-group v-model="form.status">
                <el-radio label="0">正常</el-radio>
                <el-radio label="1">停用</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" type="textarea" :rows="2" placeholder="请输入备注" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listSeries, getSeries, delSeries, addSeries, updateSeries } from "@/api/shop/snack";

export default {
  name: "SnackSeries",
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
      // 零食系列表格数据
      seriesList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        seriesName: null,
        seriesCode: null,
        status: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        seriesName: [
          { required: true, message: "系列名称不能为空", trigger: "blur" }
        ],
        seriesCode: [
          { required: true, message: "系列编码不能为空", trigger: "blur" }
        ],
        sortOrder: [
          { required: true, message: "排序号不能为空", trigger: "blur" }
        ],
        status: [
          { required: true, message: "状态不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询零食系列列表 */
    getList() {
      this.loading = true;
      listSeries(this.queryParams).then(response => {
        this.seriesList = response.rows;
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
        seriesId: null,
        seriesName: null,
        seriesCode: null,
        seriesImage: null,
        seriesDesc: null,
        sortOrder: 0,
        status: "0",
        remark: null
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
      this.ids = selection.map(item => item.seriesId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加零食系列";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const seriesId = row.seriesId || this.ids;
      getSeries(seriesId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改零食系列";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.seriesId != null) {
            updateSeries(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSeries(this.form).then(response => {
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
      const seriesIds = row.seriesId || this.ids;
      this.$modal.confirm('是否确认删除零食系列编号为"' + seriesIds + '"的数据项？').then(function() {
        return delSeries(seriesIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    }
  }
};
</script>

<style lang="scss" scoped>
</style>
