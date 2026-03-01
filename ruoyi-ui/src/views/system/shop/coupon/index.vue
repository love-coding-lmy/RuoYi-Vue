<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="优惠券名称" prop="couponName">
        <el-input
          v-model="queryParams.couponName"
          placeholder="请输入优惠券名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="优惠券类型" prop="couponType">
        <el-select v-model="queryParams.couponType" placeholder="请选择优惠券类型" clearable>
          <el-option label="满减券" value="reduction" />
          <el-option label="折扣券" value="discount" />
          <el-option label="满赠券" value="gift" />
          <el-option label="运费券" value="shipping" />
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

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['shop:coupon:add']"
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
          v-hasPermi="['shop:coupon:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['shop:coupon:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="couponList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="couponId" width="80" />
      <el-table-column label="优惠券名称" align="center" prop="couponName" :show-overflow-tooltip="true" />
      <el-table-column label="类型" align="center" prop="couponTypeText" width="100" />
      <el-table-column label="使用条件" align="center" prop="couponCondition" :show-overflow-tooltip="true" />
      <el-table-column label="发行/已领/已用" align="center" width="150">
        <template slot-scope="scope">
          {{ scope.row.totalCount }} / {{ scope.row.receiveCount }} / {{ scope.row.useCount }}
        </template>
      </el-table-column>
      <el-table-column label="剩余数量" align="center" prop="remainCount" width="100" />
      <el-table-column label="有效期类型" align="center" prop="validTypeText" width="100" />
      <el-table-column label="有效期" align="center" width="180">
        <template slot-scope="scope">
          <span v-if="scope.row.validType === 'days'">领取后{{ scope.row.validDays }}天</span>
          <span v-else>{{ parseTime(scope.row.startTime, '{y}-{m}-{d}') }} ~ {{ parseTime(scope.row.endTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status" width="100">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.status"
            active-value="0"
            inactive-value="1"
            @change="handleStatusChange(scope.row)"
            v-hasPermi="['shop:coupon:edit']"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="180">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['shop:coupon:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['shop:coupon:remove']"
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

    <!-- 添加或修改优惠券对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="140px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="优惠券名称" prop="couponName">
              <el-input v-model="form.couponName" placeholder="请输入优惠券名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="优惠券类型" prop="couponType">
              <el-select v-model="form.couponType" placeholder="请选择优惠券类型" @change="handleCouponTypeChange">
                <el-option label="满减券" value="reduction" />
                <el-option label="折扣券" value="discount" />
                <el-option label="满赠券" value="gift" />
                <el-option label="运费券" value="shipping" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="最低消费金额" prop="minAmount">
              <el-input-number v-model="form.minAmount" :min="0" :precision="2" :step="10" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="发行总量" prop="totalCount">
              <el-input-number v-model="form.totalCount" :min="1" :step="100" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="form.couponType === 'reduction'">
            <el-form-item label="减免金额" prop="reduceAmount">
              <el-input-number v-model="form.reduceAmount" :min="0" :precision="2" :step="1" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="form.couponType === 'discount'">
            <el-form-item label="折扣力度" prop="discount">
              <el-input-number v-model="form.discount" :min="0.1" :max="9.9" :precision="1" :step="0.1" style="width: 100%" />
              <span style="margin-left: 5px; color: #909399;">如8.5表示8.5折</span>
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="form.couponType === 'gift'">
            <el-form-item label="赠品ID" prop="giftProductId">
              <el-input v-model="form.giftProductId" placeholder="请输入赠品ID" />
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="form.couponType === 'shipping'">
            <el-form-item label="是否免运费" prop="freeShipping">
              <el-radio-group v-model="form.freeShipping">
                <el-radio :label="1">是</el-radio>
                <el-radio :label="0">否</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="每人限领数量" prop="perUserLimit">
              <el-input-number v-model="form.perUserLimit" :min="1" :max="99" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="有效期类型" prop="validType">
              <el-select v-model="form.validType" placeholder="请选择有效期类型" @change="handleValidTypeChange">
                <el-option label="按时间" value="time" />
                <el-option label="按天数" value="days" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="form.validType === 'days'">
            <el-form-item label="有效天数" prop="validDays">
              <el-input-number v-model="form.validDays" :min="1" :max="365" style="width: 100%" />
              <span style="margin-left: 5px; color: #909399;">领取后有效天数</span>
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="form.validType === 'time'">
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
          <el-col :span="12" v-if="form.validType === 'time'">
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
            <el-form-item label="状态" prop="status">
              <el-radio-group v-model="form.status">
                <el-radio label="0">正常</el-radio>
                <el-radio label="1">停用</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="使用条件描述">
              <el-input v-model="form.couponCondition" type="textarea" placeholder="请输入使用条件描述" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入备注" />
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
import { listCoupon, getCoupon, delCoupon, addCoupon, updateCoupon, changeStatus } from "@/api/shop/coupon";

export default {
  name: "Coupon",
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
      // 优惠券表格数据
      couponList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        couponName: null,
        couponType: null,
        status: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        couponName: [
          { required: true, message: "优惠券名称不能为空", trigger: "blur" }
        ],
        couponType: [
          { required: true, message: "请选择优惠券类型", trigger: "change" }
        ],
        minAmount: [
          { required: true, message: "最低消费金额不能为空", trigger: "blur" }
        ],
        totalCount: [
          { required: true, message: "发行总量不能为空", trigger: "blur" }
        ],
        perUserLimit: [
          { required: true, message: "每人限领数量不能为空", trigger: "blur" }
        ],
        validType: [
          { required: true, message: "请选择有效期类型", trigger: "change" }
        ],
        status: [
          { required: true, message: "请选择状态", trigger: "change" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询优惠券列表 */
    getList() {
      this.loading = true;
      listCoupon(this.queryParams).then(response => {
        this.couponList = response.rows;
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
        couponId: null,
        couponName: null,
        couponType: "reduction",
        couponCondition: null,
        minAmount: 0,
        reduceAmount: 0,
        discount: 0,
        giftProductId: null,
        freeShipping: 0,
        totalCount: 100,
        perUserLimit: 1,
        validType: "time",
        validDays: null,
        startTime: null,
        endTime: null,
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
      this.ids = selection.map(item => item.couponId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加优惠券";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const couponId = row.couponId || this.ids[0];
      getCoupon(couponId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改优惠券";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.couponId != null) {
            updateCoupon(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCoupon(this.form).then(response => {
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
      const couponIds = row.couponId || this.ids;
      this.$modal.confirm('是否确认删除优惠券编号为"' + couponIds + '"的数据项？').then(function() {
        return delCoupon(couponIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('shop/coupon/export', {
        ...this.queryParams
      }, `coupon_${new Date().getTime()}.xlsx`)
    },
    /** 状态修改 */
    handleStatusChange(row) {
      let text = row.status === "0" ? "启用" : "停用";
      this.$modal.confirm('确认要"' + text + '""' + row.couponName + '"优惠券吗？').then(function() {
        return changeStatus(row);
      }).then(() => {
        this.$modal.msgSuccess(text + "成功");
      }).catch(function() {
        row.status = row.status === "0" ? "1" : "0";
      });
    },
    /** 优惠券类型改变 */
    handleCouponTypeChange(val) {
      if (val === 'reduction') {
        this.form.discount = 0;
        this.form.giftProductId = null;
        this.form.freeShipping = 0;
      } else if (val === 'discount') {
        this.form.reduceAmount = 0;
        this.form.giftProductId = null;
        this.form.freeShipping = 0;
      } else if (val === 'gift') {
        this.form.reduceAmount = 0;
        this.form.discount = 0;
        this.form.freeShipping = 0;
      } else if (val === 'shipping') {
        this.form.reduceAmount = 0;
        this.form.discount = 0;
        this.form.giftProductId = null;
      }
    },
    /** 有效期类型改变 */
    handleValidTypeChange(val) {
      if (val === 'days') {
        this.form.startTime = null;
        this.form.endTime = null;
      } else {
        this.form.validDays = null;
      }
    }
  }
};
</script>
