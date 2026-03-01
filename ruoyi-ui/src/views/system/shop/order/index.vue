<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="订单号" prop="orderNo">
        <el-input
          v-model="queryParams.orderNo"
          placeholder="请输入订单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用户名" prop="username">
        <el-input
          v-model="queryParams.username"
          placeholder="请输入用户名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="订单状态" prop="orderStatus">
        <el-select v-model="queryParams.orderStatus" placeholder="请选择" clearable>
          <el-option label="待付款" value="pending" />
          <el-option label="已付款" value="paid" />
          <el-option label="已发货" value="shipped" />
          <el-option label="已完成" value="completed" />
          <el-option label="已取消" value="cancelled" />
        </el-select>
      </el-form-item>
      <el-form-item label="收货人" prop="receiverName">
        <el-input
          v-model="queryParams.receiverName"
          placeholder="请输入收货人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="联系电话" prop="receiverPhone">
        <el-input
          v-model="queryParams.receiverPhone"
          placeholder="请输入联系电话"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建时间">
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

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['shop:order:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="orderList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="订单ID" align="center" prop="orderId" width="80" />
      <el-table-column label="订单号" align="center" prop="orderNo" width="180" />
      <el-table-column label="用户" align="center" prop="username" width="100" />
      <el-table-column label="商品数量" align="center" prop="productCount" width="80" />
      <el-table-column label="应付金额" align="center" prop="payAmount" width="100">
        <template slot-scope="scope">
          <span>¥{{ scope.row.payAmount }}</span>
        </template>
      </el-table-column>
      <el-table-column label="订单状态" align="center" prop="orderStatusText" width="90">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.orderStatus === 'pending'" type="warning">待付款</el-tag>
          <el-tag v-else-if="scope.row.orderStatus === 'paid'" type="primary">已付款</el-tag>
          <el-tag v-else-if="scope.row.orderStatus === 'shipped'" type="success">已发货</el-tag>
          <el-tag v-else-if="scope.row.orderStatus === 'completed'" type="info">已完成</el-tag>
          <el-tag v-else-if="scope.row.orderStatus === 'cancelled'" type="danger">已取消</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="收货人" align="center" prop="receiverName" width="100" />
      <el-table-column label="联系电话" align="center" prop="receiverPhone" width="120" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="160" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="200">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleDetail(scope.row)"
            v-hasPermi="['shop:order:query']"
          >详情</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-sold-out"
            @click="handleShip(scope.row)"
            v-hasPermi="['shop:order:edit']"
            v-if="scope.row.orderStatus === 'paid'"
          >发货</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-finished"
            @click="handleComplete(scope.row)"
            v-hasPermi="['shop:order:edit']"
            v-if="scope.row.orderStatus === 'shipped'"
          >完成</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['shop:order:remove']"
            v-if="scope.row.orderStatus === 'cancelled'"
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

    <!-- 订单详情对话框 -->
    <el-dialog :title="detailTitle" :visible.sync="detailOpen" width="800px" append-to-body>
      <el-descriptions :column="2" border>
        <el-descriptions-item label="订单号">{{ orderDetail.orderNo }}</el-descriptions-item>
        <el-descriptions-item label="订单状态">
          <el-tag v-if="orderDetail.orderStatus === 'pending'" type="warning">待付款</el-tag>
          <el-tag v-else-if="orderDetail.orderStatus === 'paid'" type="primary">已付款</el-tag>
          <el-tag v-else-if="orderDetail.orderStatus === 'shipped'" type="success">已发货</el-tag>
          <el-tag v-else-if="orderDetail.orderStatus === 'completed'" type="info">已完成</el-tag>
          <el-tag v-else-if="orderDetail.orderStatus === 'cancelled'" type="danger">已取消</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="用户">{{ orderDetail.username }}</el-descriptions-item>
        <el-descriptions-item label="支付方式">
          <span v-if="orderDetail.payType === 'wechat'">微信</span>
          <span v-else-if="orderDetail.payType === 'alipay'">支付宝</span>
          <span v-else-if="orderDetail.payType === 'balance'">余额</span>
          <span v-else>-</span>
        </el-descriptions-item>
        <el-descriptions-item label="商品总额">¥{{ orderDetail.productAmount }}</el-descriptions-item>
        <el-descriptions-item label="运费">¥{{ orderDetail.freightAmount }}</el-descriptions-item>
        <el-descriptions-item label="优惠金额">¥{{ orderDetail.discountAmount }}</el-descriptions-item>
        <el-descriptions-item label="积分抵扣">¥{{ orderDetail.pointsAmount }}</el-descriptions-item>
        <el-descriptions-item label="应付金额"><span style="color: red; font-weight: bold;">¥{{ orderDetail.payAmount }}</span></el-descriptions-item>
        <el-descriptions-item label="使用积分">{{ orderDetail.usePoints }}</el-descriptions-item>
        <el-descriptions-item label="获得积分">{{ orderDetail.earnPoints }}</el-descriptions-item>
        <el-descriptions-item label="收货人">{{ orderDetail.receiverName }}</el-descriptions-item>
        <el-descriptions-item label="联系电话">{{ orderDetail.receiverPhone }}</el-descriptions-item>
        <el-descriptions-item label="收货地址" :span="2">{{ orderDetail.fullAddress }}</el-descriptions-item>
        <el-descriptions-item label="订单备注" :span="2">{{ orderDetail.orderRemark || '-' }}</el-descriptions-item>
        <el-descriptions-item label="创建时间">{{ orderDetail.createTime }}</el-descriptions-item>
        <el-descriptions-item label="支付时间">{{ orderDetail.payTime || '-' }}</el-descriptions-item>
        <el-descriptions-item label="发货时间">{{ orderDetail.shipTime || '-' }}</el-descriptions-item>
        <el-descriptions-item label="完成时间">{{ orderDetail.finishTime || '-' }}</el-descriptions-item>
      </el-descriptions>

      <el-divider content-position="left">商品明细</el-divider>
      <el-table :data="orderDetail.orderItems" size="small">
        <el-table-column label="商品" prop="productName" />
        <el-table-column label="单价" prop="productPrice" width="100">
          <template slot-scope="scope">
            ¥{{ scope.row.productPrice }}
          </template>
        </el-table-column>
        <el-table-column label="数量" prop="quantity" width="80" align="center" />
        <el-table-column label="小计" prop="subtotal" width="100">
          <template slot-scope="scope">
            ¥{{ scope.row.subtotal }}
          </template>
        </el-table-column>
      </el-table>

      <div slot="footer" class="dialog-footer">
        <el-button @click="detailOpen = false">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listOrder, getOrder, delOrder, shipOrder, completeOrder } from "@/api/shop/order";

export default {
  name: "ShopOrder",
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
      // 订单表格数据
      orderList: [],
      // 日期范围
      dateRange: [],
      // 详情弹窗标题
      detailTitle: "",
      // 详情弹窗
      detailOpen: false,
      // 订单详情
      orderDetail: {},
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderNo: null,
        username: null,
        orderStatus: null,
        receiverName: null,
        receiverPhone: null
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询订单列表 */
    getList() {
      this.loading = true;
      const query = this.addDateRange(this.queryParams, this.dateRange, 'beginTime', 'endTime');
      listOrder(query).then(response => {
        this.orderList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
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
      this.ids = selection.map(item => item.orderId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 查看详情 */
    handleDetail(row) {
      const orderId = row.orderId;
      getOrder(orderId).then(response => {
        this.orderDetail = response.data;
        this.detailOpen = true;
        this.detailTitle = "订单详情 - " + this.orderDetail.orderNo;
      });
    },
    /** 订单发货 */
    handleShip(row) {
      this.$modal.confirm('是否确认发货订单号"' + row.orderNo + '"？').then(function() {
        return shipOrder(row.orderId);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("发货成功");
      }).catch(() => {});
    },
    /** 订单完成 */
    handleComplete(row) {
      this.$modal.confirm('是否确认完成订单号"' + row.orderNo + '"？').then(function() {
        return completeOrder(row.orderId);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("操作成功");
      }).catch(() => {});
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const orderIds = row.orderId || this.ids;
      this.$modal.confirm('是否确认删除订单编号为"' + orderIds + '"的数据项？').then(function() {
        return delOrder(orderIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    }
  }
};
</script>
