<template>
  <div class="app-container">
    <!-- 搜索区域 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="推荐位名称" prop="positionName">
        <el-input
          v-model="queryParams.positionName"
          placeholder="请输入推荐位名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="推荐位编码" prop="positionCode">
        <el-input
          v-model="queryParams.positionCode"
          placeholder="请输入推荐位编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="位置类型" prop="positionType">
        <el-select v-model="queryParams.positionType" placeholder="请选择位置类型" clearable>
          <el-option label="首页" value="index" />
          <el-option label="分类页" value="category" />
          <el-option label="详情页" value="detail" />
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
          v-hasPermi="['shop:recommend:add']"
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
          v-hasPermi="['shop:recommend:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 表格区域 -->
    <el-table v-loading="loading" :data="recommendList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="推荐位ID" align="center" prop="positionId" width="100" />
      <el-table-column label="推荐位名称" align="center" prop="positionName" :show-overflow-tooltip="true" width="150" />
      <el-table-column label="推荐位编码" align="center" prop="positionCode" width="150" />
      <el-table-column label="位置类型" align="center" prop="positionType" width="100">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.positionType === 'index'" type="success">首页</el-tag>
          <el-tag v-else-if="scope.row.positionType === 'category'" type="warning">分类页</el-tag>
          <el-tag v-else-if="scope.row.positionType === 'detail'" type="info">详情页</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="最大商品数" align="center" prop="maxItems" width="100" />
      <el-table-column label="尺寸" align="center" prop="width" width="120">
        <template slot-scope="scope">
          <span v-if="scope.row.width && scope.row.height">{{ scope.row.width }} × {{ scope.row.height }}</span>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="模板类型" align="center" prop="templateType" width="100">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.templateType === 'single'" type="info">单图</el-tag>
          <el-tag v-else-if="scope.row.templateType === 'swipe'" type="warning">轮播</el-tag>
          <el-tag v-else-if="scope.row.templateType === 'grid'" type="success">网格</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status" width="80">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === '0'" type="success">正常</el-tag>
          <el-tag v-else type="danger">停用</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="250">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['shop:recommend:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-goods"
            @click="handleManageItems(scope.row)"
            v-hasPermi="['shop:recommend:edit']"
          >商品管理</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['shop:recommend:remove']"
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
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="推荐位名称" prop="positionName">
              <el-input v-model="form.positionName" placeholder="请输入推荐位名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="推荐位编码" prop="positionCode">
              <el-input v-model="form.positionCode" placeholder="请输入推荐位编码" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="位置类型" prop="positionType">
              <el-select v-model="form.positionType" placeholder="请选择位置类型">
                <el-option label="首页" value="index" />
                <el-option label="分类页" value="category" />
                <el-option label="详情页" value="detail" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="最大商品数" prop="maxItems">
              <el-input-number v-model="form.maxItems" :min="1" controls-position="right" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="宽度" prop="width">
              <el-input-number v-model="form.width" :min="0" controls-position="right" style="width: 100%" />
              <span style="color: #909399; font-size: 12px;">px</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="高度" prop="height">
              <el-input-number v-model="form.height" :min="0" controls-position="right" style="width: 100%" />
              <span style="color: #909399; font-size: 12px;">px</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="模板类型" prop="templateType">
              <el-select v-model="form.templateType" placeholder="请选择模板类型">
                <el-option label="单图" value="single" />
                <el-option label="轮播" value="swipe" />
                <el-option label="网格" value="grid" />
              </el-select>
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

    <!-- 商品管理对话框 -->
    <el-dialog :title="itemTitle" :visible.sync="itemOpen" width="1000px" append-to-body>
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="handleAddItem"
          >添加商品</el-button>
        </el-col>
      </el-row>

      <el-table v-loading="itemLoading" :data="itemList" row-key="itemId">
        <el-table-column label="商品ID" align="center" prop="productId" width="100" />
        <el-table-column label="商品名称" align="center" prop="productName" :show-overflow-tooltip="true" />
        <el-table-column label="商品图片" align="center" prop="productImage" width="120">
          <template slot-scope="scope">
            <image-preview v-if="scope.row.productImage" :src="scope.row.productImage" :width="80" :height="60" />
          </template>
        </el-table-column>
        <el-table-column label="排序" align="center" prop="sortOrder" width="100" />
        <el-table-column label="开始时间" align="center" prop="startTime" width="160" />
        <el-table-column label="结束时间" align="center" prop="endTime" width="160" />
        <el-table-column label="状态" align="center" prop="status" width="80">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.status === '0'" type="success">正常</el-tag>
            <el-tag v-else type="danger">停用</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" width="100">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDeleteItem(scope.row)"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div slot="footer" class="dialog-footer">
        <el-button @click="itemOpen = false">关 闭</el-button>
      </div>
    </el-dialog>

    <!-- 添加商品对话框 -->
    <el-dialog title="添加推荐商品" :visible.sync="addItemOpen" width="600px" append-to-body>
      <el-form ref="itemForm" :model="itemForm" :rules="itemRules" label-width="120px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="商品ID" prop="productId">
              <el-input v-model="itemForm.productId" placeholder="请输入商品ID" />
              <span style="color: #909399; font-size: 12px;">请输入商品管理中真实的商品ID</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="排序" prop="sortOrder">
              <el-input-number v-model="itemForm.sortOrder" :min="0" controls-position="right" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="开始时间" prop="startTime">
              <el-date-picker
                v-model="itemForm.startTime"
                type="datetime"
                placeholder="选择开始时间"
                value-format="yyyy-MM-dd HH:mm:ss"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="结束时间" prop="endTime">
              <el-date-picker
                v-model="itemForm.endTime"
                type="datetime"
                placeholder="选择结束时间"
                value-format="yyyy-MM-dd HH:mm:ss"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-radio-group v-model="itemForm.status">
                <el-radio label="0">正常</el-radio>
                <el-radio label="1">停用</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitItemForm">确 定</el-button>
        <el-button @click="addItemOpen = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listRecommend, getRecommend, delRecommend, addRecommend, updateRecommend, listRecommendItems, addRecommendItem, delRecommendItem } from "@/api/shop/snack";

export default {
  name: "SnackRecommend",
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
      // 推荐位表格数据
      recommendList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 商品管理对话框标题
      itemTitle: "",
      // 是否显示商品管理对话框
      itemOpen: false,
      // 商品列表加载状态
      itemLoading: false,
      // 商品列表
      itemList: [],
      // 当前推荐位ID
      currentPositionId: null,
      // 是否显示添加商品对话框
      addItemOpen: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        positionName: null,
        positionCode: null,
        positionType: null,
        status: null
      },
      // 表单参数
      form: {},
      // 商品表单参数
      itemForm: {},
      // 表单校验
      rules: {
        positionName: [
          { required: true, message: "推荐位名称不能为空", trigger: "blur" }
        ],
        positionCode: [
          { required: true, message: "推荐位编码不能为空", trigger: "blur" }
        ],
        positionType: [
          { required: true, message: "位置类型不能为空", trigger: "change" }
        ],
        maxItems: [
          { required: true, message: "最大商品数不能为空", trigger: "blur" }
        ],
        status: [
          { required: true, message: "状态不能为空", trigger: "blur" }
        ]
      },
      // 商品表单校验
      itemRules: {
        productId: [
          { required: true, message: "商品ID不能为空", trigger: "blur" }
        ],
        sortOrder: [
          { required: true, message: "排序不能为空", trigger: "blur" }
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
    /** 查询推荐位列表 */
    getList() {
      this.loading = true;
      listRecommend(this.queryParams).then(response => {
        this.recommendList = response.rows;
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
        positionId: null,
        positionName: null,
        positionCode: null,
        positionType: null,
        maxItems: 10,
        width: 0,
        height: 0,
        templateType: null,
        status: "0",
        remark: null
      };
      this.resetForm("form");
    },
    // 商品表单重置
    resetItemForm() {
      this.itemForm = {
        positionId: null,
        productId: null,
        sortOrder: 0,
        startTime: null,
        endTime: null,
        status: "0"
      };
      this.resetForm("itemForm");
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
      this.ids = selection.map(item => item.positionId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加推荐位";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const positionId = row.positionId || this.ids;
      getRecommend(positionId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改推荐位";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.positionId != null) {
            updateRecommend(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addRecommend(this.form).then(response => {
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
      const positionIds = row.positionId || this.ids;
      this.$modal.confirm('是否确认删除推荐位编号为"' + positionIds + '"的数据项？').then(function() {
        return delRecommend(positionIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 商品管理 */
    handleManageItems(row) {
      this.currentPositionId = row.positionId;
      this.itemTitle = "【" + row.positionName + "】商品管理";
      this.itemOpen = true;
      this.getItemList();
    },
    /** 查询商品列表 */
    getItemList() {
      this.itemLoading = true;
      listRecommendItems(this.currentPositionId).then(response => {
        this.itemList = response.data;
        this.itemLoading = false;
      });
    },
    /** 添加商品 */
    handleAddItem() {
      this.resetItemForm();
      this.itemForm.positionId = this.currentPositionId;
      this.addItemOpen = true;
    },
    /** 提交商品表单 */
    submitItemForm() {
      this.$refs["itemForm"].validate(valid => {
        if (valid) {
          addRecommendItem(this.itemForm).then(response => {
            this.$modal.msgSuccess("添加成功");
            this.addItemOpen = false;
            this.getItemList();
          });
        }
      });
    },
    /** 删除商品 */
    handleDeleteItem(row) {
      this.$modal.confirm('是否确认删除该商品？').then(function() {
        return delRecommendItem(row.itemId);
      }).then(() => {
        this.getItemList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    }
  }
};
</script>

<style lang="scss" scoped>
</style>
