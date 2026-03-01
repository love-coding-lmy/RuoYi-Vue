<template>
  <div class="app-container">
    <!-- 搜索区域 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="轮播图标题" prop="bannerTitle">
        <el-input
          v-model="queryParams.bannerTitle"
          placeholder="请输入轮播图标题"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="显示状态" prop="showStatus">
        <el-select v-model="queryParams.showStatus" placeholder="请选择显示状态" clearable>
          <el-option label="显示" value="0" />
          <el-option label="隐藏" value="1" />
        </el-select>
      </el-form-item>
      <el-form-item label="活动时间">
        <el-date-picker
          v-model="dateRange"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
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
          v-hasPermi="['shop:banner:add']"
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
          v-hasPermi="['shop:banner:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 表格区域 -->
    <el-table v-loading="loading" :data="bannerList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="轮播图ID" align="center" prop="bannerId" width="80" />
      <el-table-column label="轮播图标题" align="center" prop="bannerTitle" :show-overflow-tooltip="true" />
      <el-table-column label="轮播图片" align="center" prop="bannerImage" width="150">
        <template slot-scope="scope">
          <image-preview v-if="scope.row.bannerImage" :src="scope.row.bannerImage" :width="100" :height="50" />
        </template>
      </el-table-column>
      <el-table-column label="链接地址" align="center" prop="linkUrl" width="200" :show-overflow-tooltip="true" />
      <el-table-column label="打开方式" align="center" prop="target" width="100">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.target === '_self'" type="success">当前页</el-tag>
          <el-tag v-else-if="scope.row.target === '_blank'" type="info">新页面</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="排序号" align="center" prop="sortOrder" width="80" />
      <el-table-column label="显示状态" align="center" prop="showStatus" width="100">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.showStatus === '0'" type="success">显示</el-tag>
          <el-tag v-else type="info">隐藏</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="开始时间" align="center" prop="startTime" width="160" />
      <el-table-column label="结束时间" align="center" prop="endTime" width="160" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="180">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['shop:banner:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
              v-hasPermi="['shop:banner:remove']"
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
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="轮播图标题" prop="bannerTitle">
              <el-input v-model="form.bannerTitle" placeholder="请输入轮播图标题" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="排序号" prop="sortOrder">
              <el-input-number v-model="form.sortOrder" :min="0" controls-position="right" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="轮播图片" prop="bannerImage">
              <image-upload v-model="form.bannerImage" :limit="1" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="链接地址" prop="linkUrl">
              <el-input v-model="form.linkUrl" placeholder="请输入链接地址" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="打开方式" prop="target">
              <el-select v-model="form.target" placeholder="请选择打开方式">
                <el-option label="当前页" value="_self" />
                <el-option label="新页面" value="_blank" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="显示状态" prop="showStatus">
              <el-radio-group v-model="form.showStatus">
                <el-radio label="0">显示</el-radio>
                <el-radio label="1">隐藏</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="活动时间" prop="startTime">
              <el-date-picker
                v-model="activityTimeRange"
                type="daterange"
                range-separator="-"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                value-format="yyyy-MM-dd HH:mm:ss"
                style="width: 100%"
              />
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
import { listBanner, getBanner, delBanner, addBanner, updateBanner } from "@/api/shop/banner";

export default {
  name: "Banner",
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
      // 轮播图表格数据
      bannerList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 活动时间范围
      dateRange: [],
      // 活动时间范围（表单）
      activityTimeRange: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        bannerTitle: null,
        showStatus: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        bannerTitle: [
          { required: true, message: "轮播图标题不能为空", trigger: "blur" }
        ],
        bannerImage: [
          { required: true, message: "轮播图片不能为空", trigger: "blur" }
        ],
        sortOrder: [
          { required: true, message: "排序号不能为空", trigger: "blur" }
        ],
        showStatus: [
          { required: true, message: "显示状态不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询轮播图列表 */
    getList() {
      this.loading = true;
      listBanner(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.bannerList = response.rows;
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
        bannerId: null,
        bannerTitle: null,
        bannerImage: null,
        linkUrl: null,
        target: "_self",
        sortOrder: 0,
        showStatus: "0",
        startTime: null,
        endTime: null
      };
      this.activityTimeRange = [];
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.bannerId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加轮播图";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const bannerId = row.bannerId || this.ids;
      getBanner(bannerId).then(response => {
        this.form = response.data;
        if (this.form.startTime && this.form.endTime) {
          this.activityTimeRange = [this.form.startTime, this.form.endTime];
        }
        this.open = true;
        this.title = "修改轮播图";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          // 处理活动时间
          if (this.activityTimeRange && this.activityTimeRange.length === 2) {
            this.form.startTime = this.activityTimeRange[0];
            this.form.endTime = this.activityTimeRange[1];
          } else {
            this.form.startTime = null;
            this.form.endTime = null;
          }
          if (this.form.bannerId != null) {
            updateBanner(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addBanner(this.form).then(response => {
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
      const bannerIds = row.bannerId || this.ids;
      this.$modal.confirm('是否确认删除轮播图编号为"' + bannerIds + '"的数据项？').then(function() {
        return delBanner(bannerIds);
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
