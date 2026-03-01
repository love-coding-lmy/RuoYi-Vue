<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="商品名称" prop="productName">
        <el-input
          v-model="queryParams.productName"
          placeholder="请输入商品名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="商品编码" prop="productCode">
        <el-input
          v-model="queryParams.productCode"
          placeholder="请输入商品编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="分类" prop="categoryId">
        <el-cascader
          v-model="categoryIdList"
          :options="categoryOptions"
          :props="{ value: 'categoryId', label: 'categoryName', emitPath: false, checkStrictly: true }"
          placeholder="请选择分类"
          clearable
          @change="handleCategoryChange"
        />
      </el-form-item>
      <el-form-item label="是否上架" prop="isOnSale">
        <el-select v-model="queryParams.isOnSale" placeholder="请选择" clearable>
          <el-option label="上架" value="1" />
          <el-option label="下架" value="0" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
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
          v-hasPermi="['shop:product:add']"
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
          v-hasPermi="['shop:product:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="productList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="商品ID" align="center" prop="productId" width="80" />
      <el-table-column label="主图" align="center" prop="mainImage" width="100">
        <template slot-scope="scope">
          <image-preview v-if="scope.row.mainImage" :src="scope.row.mainImage" :width="50" :height="50" />
        </template>
      </el-table-column>
      <el-table-column label="商品名称" align="center" prop="productName" :show-overflow-tooltip="true" />
      <el-table-column label="商品编码" align="center" prop="productCode" width="120" />
      <el-table-column label="分类" align="center" prop="categoryName" width="120" />
      <el-table-column label="销售价" align="center" prop="price" width="100">
        <template slot-scope="scope">
          <span>¥{{ scope.row.price }}</span>
        </template>
      </el-table-column>
      <el-table-column label="库存" align="center" prop="stock" width="80" />
      <el-table-column label="销量" align="center" prop="sales" width="80" />
      <el-table-column label="状态" align="center" prop="isOnSale" width="80">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.isOnSale === '1'" type="success">上架</el-tag>
          <el-tag v-else type="info">下架</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="220">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['shop:product:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-top"
            @click="handleOnSale(scope.row)"
            v-hasPermi="['shop:product:edit']"
            v-if="scope.row.isOnSale === '0'"
          >上架</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-bottom"
            @click="handleOffSale(scope.row)"
            v-hasPermi="['shop:product:edit']"
            v-if="scope.row.isOnSale === '1'"
          >下架</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['shop:product:remove']"
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

    <!-- 添加或修改商品对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="900px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-tabs v-model="activeTab">
          <el-tab-pane label="基本信息" name="basic">
            <el-row>
              <el-col :span="12">
                <el-form-item label="商品名称" prop="productName">
                  <el-input v-model="form.productName" placeholder="请输入商品名称" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="商品编码" prop="productCode">
                  <el-input v-model="form.productCode" placeholder="请输入商品编码" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="商品副标题" prop="productSubTitle">
                  <el-input v-model="form.productSubTitle" placeholder="请输入商品副标题" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="商品分类" prop="categoryId">
                  <el-cascader
                    v-model="form.categoryId"
                    :options="categoryOptions"
                    :props="{ value: 'categoryId', label: 'categoryName', emitPath: false, checkStrictly: true }"
                    placeholder="请选择分类"
                    clearable
                  />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="单位" prop="unit">
                  <el-input v-model="form.unit" placeholder="请输入单位" />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="排序号" prop="sortOrder">
                  <el-input-number v-model="form.sortOrder" :min="0" controls-position="right" />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="商品重量" prop="weight">
                  <el-input-number v-model="form.weight" :min="0" :precision="2" controls-position="right" />
                </el-form-item>
              </el-col>
            </el-row>
          </el-tab-pane>
          <el-tab-pane label="价格库存" name="price">
            <el-row>
              <el-col :span="8">
                <el-form-item label="销售价" prop="price">
                  <el-input-number v-model="form.price" :min="0" :precision="2" controls-position="right" />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="原价" prop="originalPrice">
                  <el-input-number v-model="form.originalPrice" :min="0" :precision="2" controls-position="right" />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="会员价" prop="memberPrice">
                  <el-input-number v-model="form.memberPrice" :min="0" :precision="2" controls-position="right" />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="成本价" prop="costPrice">
                  <el-input-number v-model="form.costPrice" :min="0" :precision="2" controls-position="right" />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="库存数量" prop="stock">
                  <el-input-number v-model="form.stock" :min="0" controls-position="right" />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="库存预警值" prop="lowStock">
                  <el-input-number v-model="form.lowStock" :min="0" controls-position="right" />
                </el-form-item>
              </el-col>
            </el-row>
          </el-tab-pane>
          <el-tab-pane label="商品图片" name="images">
            <el-form-item label="主图" prop="mainImage">
              <image-upload v-model="form.mainImage" :limit="1" />
            </el-form-item>
            <el-form-item label="商品图片">
              <image-upload v-model="form.images" />
            </el-form-item>
          </el-tab-pane>
          <el-tab-pane label="商品详情" name="detail">
            <el-form-item label="商品详情" prop="detail">
              <editor v-model="form.detail" :min-height="300" />
            </el-form-item>
          </el-tab-pane>
          <el-tab-pane label="其他信息" name="other">
            <el-row>
              <el-col :span="12">
                <el-form-item label="关键词">
                  <el-input v-model="form.keywords" placeholder="请输入关键词" />
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="备注">
                  <el-input v-model="form.remark" type="textarea" placeholder="请输入备注" />
                </el-form-item>
              </el-col>
            </el-row>
          </el-tab-pane>
        </el-tabs>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listProduct, getProduct, delProduct, addProduct, updateProduct, onSale, offSale } from "@/api/shop/product/product";
import { listCategory } from "@/api/shop/product/category";

export default {
  name: "ShopProduct",
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
      // 商品表格数据
      productList: [],
      // 分类选项
      categoryOptions: [],
      // 分类ID列表
      categoryIdList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 当前Tab
      activeTab: "basic",
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        productName: null,
        productCode: null,
        categoryId: null,
        isOnSale: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        productName: [
          { required: true, message: "商品名称不能为空", trigger: "blur" }
        ],
        productCode: [
          { required: true, message: "商品编码不能为空", trigger: "blur" }
        ],
        categoryId: [
          { required: true, message: "请选择商品分类", trigger: "change" }
        ],
        price: [
          { required: true, message: "销售价不能为空", trigger: "blur" }
        ],
        stock: [
          { required: true, message: "库存数量不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getCategoryTree();
  },
  methods: {
    /** 查询商品列表 */
    getList() {
      this.loading = true;
      listProduct(this.queryParams).then(response => {
        this.productList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询分类树 */
    getCategoryTree() {
      listCategory().then(response => {
        this.categoryOptions = this.handleTree(response.rows, "categoryId", "parentId");
      });
    },
    // 分类变化
    handleCategoryChange(value) {
      this.queryParams.categoryId = value;
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        productId: null,
        productName: null,
        productCode: null,
        productSubTitle: null,
        categoryId: null,
        brandId: null,
        unit: "件",
        price: null,
        originalPrice: null,
        memberPrice: null,
        costPrice: null,
        stock: 0,
        lowStock: 10,
        sales: 0,
        weight: null,
        mainImage: null,
        images: null,
        detail: null,
        keywords: null,
        isHot: "0",
        isNew: "0",
        isRecommend: "0",
        isOnSale: "1",
        sortOrder: 0,
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
      this.categoryIdList = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.productId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加商品";
      this.activeTab = "basic";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const productId = row.productId || this.ids[0];
      getProduct(productId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改商品";
        this.activeTab = "basic";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.productId != null) {
            updateProduct(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addProduct(this.form).then(response => {
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
      const productIds = row.productId || this.ids;
      this.$modal.confirm('是否确认删除商品编号为"' + productIds + '"的数据项？').then(function() {
        return delProduct(productIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 商品上架 */
    handleOnSale(row) {
      this.$modal.confirm('是否确认上架商品"' + row.productName + '"？').then(function() {
        return onSale(row.productId);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("上架成功");
      }).catch(() => {});
    },
    /** 商品下架 */
    handleOffSale(row) {
      this.$modal.confirm('是否确认下架商品"' + row.productName + '"？').then(function() {
        return offSale(row.productId);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("下架成功");
      }).catch(() => {});
    }
  }
};
</script>
