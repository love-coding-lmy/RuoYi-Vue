<template>
  <div class="app-container">
    <!-- 搜索区域 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="标签名称" prop="tagName">
        <el-input
          v-model="queryParams.tagName"
          placeholder="请输入标签名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="标签编码" prop="tagCode">
        <el-input
          v-model="queryParams.tagCode"
          placeholder="请输入标签编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="标签类型" prop="tagType">
        <el-select v-model="queryParams.tagType" placeholder="请选择标签类型" clearable>
          <el-option label="特性" value="feature" />
          <el-option label="促销" value="promo" />
          <el-option label="状态" value="status" />
          <el-option label="品质" value="quality" />
        </el-select>
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
          v-hasPermi="['shop:tag:add']"
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
          v-hasPermi="['shop:tag:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 表格区域 -->
    <el-table v-loading="loading" :data="tagList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="标签ID" align="center" prop="tagId" width="80" />
      <el-table-column label="标签名称" align="center" prop="tagName" width="120">
        <template slot-scope="scope">
          <el-tag :color="scope.row.tagColor">{{ scope.row.tagName }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="标签编码" align="center" prop="tagCode" width="120" />
      <el-table-column label="标签类型" align="center" prop="tagType" width="100">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.tagType === 'feature'" type="success">特性</el-tag>
          <el-tag v-else-if="scope.row.tagType === 'promo'" type="danger">促销</el-tag>
          <el-tag v-else-if="scope.row.tagType === 'status'" type="warning">状态</el-tag>
          <el-tag v-else-if="scope.row.tagType === 'quality'" type="info">品质</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="标签颜色" align="center" prop="tagColor" width="100">
        <template slot-scope="scope">
          <el-color-picker v-model="scope.row.tagColor" :readonly="true" size="mini" />
        </template>
      </el-table-column>
      <el-table-column label="图标" align="center" prop="iconUrl" width="100">
        <template slot-scope="scope">
          <image-preview v-if="scope.row.iconUrl" :src="scope.row.iconUrl" :width="50" :height="50" />
        </template>
      </el-table-column>
      <el-table-column label="排序号" align="center" prop="sortOrder" width="80" />
      <el-table-column label="状态" align="center" prop="status" width="80">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === '0'" type="success">正常</el-tag>
          <el-tag v-else type="danger">停用</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" :show-overflow-tooltip="true" width="150" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="180">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['shop:tag:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['shop:tag:remove']"
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
            <el-form-item label="标签名称" prop="tagName">
              <el-input v-model="form.tagName" placeholder="请输入标签名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="标签编码" prop="tagCode">
              <el-input v-model="form.tagCode" placeholder="请输入标签编码" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="标签类型" prop="tagType">
              <el-select v-model="form.tagType" placeholder="请选择标签类型">
                <el-option label="特性" value="feature" />
                <el-option label="促销" value="promo" />
                <el-option label="状态" value="status" />
                <el-option label="品质" value="quality" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="标签颜色" prop="tagColor">
              <el-color-picker v-model="form.tagColor" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="图标" prop="iconUrl">
              <image-upload v-model="form.iconUrl" :limit="1" />
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
import { listTag, getTag, delTag, addTag, updateTag } from "@/api/shop/snack";

export default {
  name: "SnackTag",
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
      // 零食标签表格数据
      tagList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        tagName: null,
        tagCode: null,
        tagType: null,
        status: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        tagName: [
          { required: true, message: "标签名称不能为空", trigger: "blur" }
        ],
        tagCode: [
          { required: true, message: "标签编码不能为空", trigger: "blur" }
        ],
        tagType: [
          { required: true, message: "标签类型不能为空", trigger: "change" }
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
    /** 查询零食标签列表 */
    getList() {
      this.loading = true;
      listTag(this.queryParams).then(response => {
        this.tagList = response.rows;
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
        tagId: null,
        tagName: null,
        tagCode: null,
        tagType: null,
        tagColor: null,
        iconUrl: null,
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
      this.ids = selection.map(item => item.tagId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加零食标签";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const tagId = row.tagId || this.ids;
      getTag(tagId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改零食标签";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.tagId != null) {
            updateTag(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addTag(this.form).then(response => {
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
      const tagIds = row.tagId || this.ids;
      this.$modal.confirm('是否确认删除零食标签编号为"' + tagIds + '"的数据项？').then(function() {
        return delTag(tagIds);
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
