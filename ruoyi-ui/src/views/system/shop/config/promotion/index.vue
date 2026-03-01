<template>
  <div class="app-container">
    <!-- 搜索区域 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="活动名称" prop="promotionName">
        <el-input
          v-model="queryParams.promotionName"
          placeholder="请输入活动名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="活动类型" prop="promotionType">
        <el-select v-model="queryParams.promotionType" placeholder="请选择活动类型" clearable>
          <el-option label="秒杀" value="seckill" />
          <el-option label="满减" value="discount" />
          <el-option label="捆绑" value="bundle" />
          <el-option label="赠品" value="gift" />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option label="正常" value="0" />
          <el-option label="停用" value="1" />
          <el-option label="已结束" value="2" />
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
          v-hasPermi="['shop:promotion:add']"
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
          v-hasPermi="['shop:promotion:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['shop:promotion:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 表格区域 -->
    <el-table v-loading="loading" :data="promotionList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="活动ID" align="center" prop="promotionId" width="80" />
      <el-table-column label="活动名称" align="center" prop="promotionName" :show-overflow-tooltip="true" width="150" />
      <el-table-column label="活动类型" align="center" prop="promotionType" width="100">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.promotionType === 'seckill'" type="danger">秒杀</el-tag>
          <el-tag v-else-if="scope.row.promotionType === 'discount'" type="warning">满减</el-tag>
          <el-tag v-else-if="scope.row.promotionType === 'bundle'" type="success">捆绑</el-tag>
          <el-tag v-else-if="scope.row.promotionType === 'gift'" type="info">赠品</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="活动描述" align="center" prop="promotionDesc" :show-overflow-tooltip="true" width="200" />
      <el-table-column label="开始时间" align="center" prop="startTime" width="160" />
      <el-table-column label="结束时间" align="center" prop="endTime" width="160" />
      <el-table-column label="最低金额" align="center" prop="minAmount" width="100">
        <template slot-scope="scope">
          <span v-if="scope.row.minAmount">¥{{ scope.row.minAmount }}</span>
        </template>
      </el-table-column>
      <el-table-column label="优惠值" align="center" prop="discountValue" width="100">
        <template slot-scope="scope">
          <span v-if="scope.row.discountType === 'amount'">¥{{ scope.row.discountValue }}</span>
          <span v-else-if="scope.row.discountType === 'percent'">{{ scope.row.discountValue }}%</span>
        </template>
      </el-table-column>
      <el-table-column label="库存" align="center" prop="stock" width="80" />
      <el-table-column label="已售" align="center" prop="soldCount" width="80" />
      <el-table-column label="限购" align="center" prop="limitPerUser" width="80">
        <template slot-scope="scope">
          <span v-if="scope.row.limitPerUser === 0">不限</span>
          <span v-else>{{ scope.row.limitPerUser }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status" width="80">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === '0'" type="success">正常</el-tag>
          <el-tag v-else-if="scope.row.status === '1'" type="danger">停用</el-tag>
          <el-tag v-else type="info">已结束</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="180">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['shop:promotion:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['shop:promotion:remove']"
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
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="活动名称" prop="promotionName">
              <el-input v-model="form.promotionName" placeholder="请输入活动名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="活动类型" prop="promotionType">
              <el-select v-model="form.promotionType" placeholder="请选择活动类型">
                <el-option label="秒杀" value="seckill" />
                <el-option label="满减" value="discount" />
                <el-option label="捆绑" value="bundle" />
                <el-option label="赠品" value="gift" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="活动描述" prop="promotionDesc">
              <el-input v-model="form.promotionDesc" type="textarea" :rows="2" placeholder="请输入活动描述" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="开始时间" prop="startTime">
              <el-date-picker
                v-model="form.startTime"
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
                v-model="form.endTime"
                type="datetime"
                placeholder="选择结束时间"
                value-format="yyyy-MM-dd HH:mm:ss"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="最低参与金额" prop="minAmount">
              <el-input-number v-model="form.minAmount" :min="0" :precision="2" controls-position="right" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="优惠类型" prop="discountType">
              <el-select v-model="form.discountType" placeholder="请选择优惠类型">
                <el-option label="金额" value="amount" />
                <el-option label="百分比" value="percent" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="优惠值" prop="discountValue">
              <el-input-number v-model="form.discountValue" :min="0" :max="form.discountType === 'percent' ? 100 : 999999" :precision="form.discountType === 'percent' ? 0 : 2" controls-position="right" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="库存数量" prop="stock">
              <el-input-number v-model="form.stock" :min="0" controls-position="right" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="每人限购" prop="limitPerUser">
              <el-input-number v-model="form.limitPerUser" :min="0" controls-position="right" style="width: 100%" />
              <span style="color: #909399; font-size: 12px;">（0表示不限购）</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="优先级" prop="priority">
              <el-input-number v-model="form.priority" :min="0" controls-position="right" style="width: 100%" />
              <span style="color: #909399; font-size: 12px;">（数字越小优先级越高）</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-radio-group v-model="form.status">
                <el-radio label="0">正常</el-radio>
                <el-radio label="1">停用</el-radio>
                <el-radio label="2">已结束</el-radio>
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
import { listPromotion, getPromotion, delPromotion, addPromotion, updatePromotion, exportPromotion } from "@/api/shop/snack";

export default {
  name: "SnackPromotion",
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
      // 促销活动表格数据
      promotionList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 活动时间范围
      dateRange: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        promotionName: null,
        promotionType: null,
        status: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        promotionName: [
          { required: true, message: "活动名称不能为空", trigger: "blur" }
        ],
        promotionType: [
          { required: true, message: "活动类型不能为空", trigger: "change" }
        ],
        startTime: [
          { required: true, message: "开始时间不能为空", trigger: "change" }
        ],
        endTime: [
          { required: true, message: "结束时间不能为空", trigger: "change" }
        ],
        discountType: [
          { required: true, message: "优惠类型不能为空", trigger: "change" }
        ],
        discountValue: [
          { required: true, message: "优惠值不能为空", trigger: "blur" }
        ],
        stock: [
          { required: true, message: "库存数量不能为空", trigger: "blur" }
        ],
        priority: [
          { required: true, message: "优先级不能为空", trigger: "blur" }
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
    /** 查询促销活动列表 */
    getList() {
      this.loading = true;
      listPromotion(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.promotionList = response.rows;
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
        promotionId: null,
        promotionName: null,
        promotionType: null,
        promotionDesc: null,
        startTime: null,
        endTime: null,
        minAmount: 0,
        discountType: null,
        discountValue: 0,
        stock: 0,
        soldCount: 0,
        limitPerUser: 0,
        priority: 0,
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
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.promotionId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加促销活动";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const promotionId = row.promotionId || this.ids;
      getPromotion(promotionId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改促销活动";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.promotionId != null) {
            updatePromotion(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPromotion(this.form).then(response => {
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
      const promotionIds = row.promotionId || this.ids;
      this.$modal.confirm('是否确认删除促销活动编号为"' + promotionIds + '"的数据项？').then(function() {
        return delPromotion(promotionIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('shop/promotion/export', {
        ...this.queryParams
      }, `promotion_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>

<style lang="scss" scoped>
</style>
