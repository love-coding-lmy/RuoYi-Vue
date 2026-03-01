<template>
  <div class="app-container shop-order-container">
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
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['shop:order:add']"
        >新增订单</el-button>
      </el-col>
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

    <!-- 表格容器 -->
    <div class="table-container">
      <el-table
        v-loading="loading"
        :data="orderList"
        @selection-change="handleSelectionChange"
        height="100%"
        style="width: 100%"
      >
        <el-table-column type="selection" min-width="55" align="center" />
        <el-table-column label="订单ID" align="center" prop="orderId" min-width="80" />
        <el-table-column label="订单号" align="center" prop="orderNo" min-width="180" />
        <el-table-column label="用户" align="center" prop="username" min-width="100" show-overflow-tooltip />
        <el-table-column label="商品数量" align="center" prop="productCount" min-width="80" />
        <el-table-column label="应付金额" align="center" prop="payAmount" min-width="100">
          <template slot-scope="scope">
            <span>¥{{ scope.row.payAmount }}</span>
          </template>
        </el-table-column>
        <el-table-column label="订单状态" align="center" prop="orderStatusText" min-width="90">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.orderStatus === 'pending'" type="warning">待付款</el-tag>
            <el-tag v-else-if="scope.row.orderStatus === 'paid'" type="primary">已付款</el-tag>
            <el-tag v-else-if="scope.row.orderStatus === 'shipped'" type="success">已发货</el-tag>
            <el-tag v-else-if="scope.row.orderStatus === 'completed'" type="info">已完成</el-tag>
            <el-tag v-else-if="scope.row.orderStatus === 'cancelled'" type="danger">已取消</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="收货人" align="center" prop="receiverName" min-width="100" show-overflow-tooltip />
        <el-table-column label="联系电话" align="center" prop="receiverPhone" min-width="120" show-overflow-tooltip />
        <el-table-column label="创建时间" align="center" prop="createTime" min-width="160" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width" min-width="200">
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
    </div>

    <!-- 创建订单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="900px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-tabs v-model="activeTab">
          <el-tab-pane label="基本信息" name="basic">
            <el-row>
              <el-col :span="12">
                <el-form-item label="用户" prop="userId">
                  <el-select v-model="form.userId" placeholder="请选择用户" filterable clearable @change="handleUserChange">
                    <el-option
                      v-for="user in userList"
                      :key="user.userId"
                      :label="user.userName"
                      :value="user.userId"
                    >
                      <span style="float: left">{{ user.userName }}</span>
                      <span style="float: right; color: #8492a6; font-size: 13px">{{ user.nickName }}</span>
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="支付方式" prop="payType">
                  <el-select v-model="form.payType" placeholder="请选择支付方式">
                    <el-option label="微信" value="wechat" />
                    <el-option label="支付宝" value="alipay" />
                    <el-option label="余额" value="balance" />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
          </el-tab-pane>
          <el-tab-pane label="商品信息" name="product">
            <el-form-item label="选择商品">
              <el-button type="primary" size="mini" icon="el-icon-plus" @click="openProductSelect">添加商品</el-button>
            </el-form-item>
            <el-table :data="form.orderItems" size="small">
              <el-table-column label="商品名称" prop="productName" min-width="200" show-overflow-tooltip />
              <el-table-column label="单价" prop="productPrice" min-width="100">
                <template slot-scope="scope">
                  ¥{{ scope.row.productPrice }}
                </template>
              </el-table-column>
              <el-table-column label="数量" min-width="150">
                <template slot-scope="scope">
                  <el-input-number v-model="scope.row.quantity" :min="1" size="mini" controls-position="right" @change="handleQuantityChange(scope.row)" />
                </template>
              </el-table-column>
              <el-table-column label="小计" prop="subtotal" min-width="100">
                <template slot-scope="scope">
                  ¥{{ (scope.row.productPrice * scope.row.quantity).toFixed(2) }}
                </template>
              </el-table-column>
              <el-table-column label="操作" min-width="80">
                <template slot-scope="scope">
                  <el-button type="text" size="small" icon="el-icon-delete" @click="removeOrderItem(scope.$index)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
            <el-row style="margin-top: 10px;">
              <el-col :span="24" style="text-align: right;">
                <span style="font-size: 16px;">商品总额：</span>
                <span style="font-size: 18px; color: red; font-weight: bold;">¥{{ totalAmount }}</span>
              </el-col>
            </el-row>
          </el-tab-pane>
          <el-tab-pane label="收货信息" name="address">
            <el-row>
              <el-col :span="12">
                <el-form-item label="收货人" prop="receiverName">
                  <el-input v-model="form.receiverName" placeholder="请输入收货人姓名" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="联系电话" prop="receiverPhone">
                  <el-input v-model="form.receiverPhone" placeholder="请输入联系电话" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="省份" prop="receiverProvince">
                  <el-input v-model="form.receiverProvince" placeholder="请输入省份" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="城市" prop="receiverCity">
                  <el-input v-model="form.receiverCity" placeholder="请输入城市" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="区县" prop="receiverDistrict">
                  <el-input v-model="form.receiverDistrict" placeholder="请输入区县" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="详细地址" prop="receiverAddress">
                  <el-input v-model="form.receiverAddress" placeholder="请输入详细地址" />
                </el-form-item>
              </el-col>
            </el-row>
          </el-tab-pane>
          <el-tab-pane label="其他信息" name="other">
            <el-row>
              <el-col :span="12">
                <el-form-item label="运费">
                  <el-input-number v-model="form.freightAmount" :min="0" :precision="2" controls-position="right" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="优惠金额">
                  <el-input-number v-model="form.discountAmount" :min="0" :precision="2" controls-position="right" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="积分抵扣">
                  <el-input-number v-model="form.usePoints" :min="0" controls-position="right" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="积分抵扣金额">
                  <el-input-number v-model="form.pointsAmount" :min="0" :precision="2" controls-position="right" />
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="订单备注">
                  <el-input v-model="form.orderRemark" type="textarea" placeholder="请输入订单备注" />
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

    <!-- 选择商品对话框 -->
    <el-dialog title="选择商品" :visible.sync="productSelectOpen" width="800px" append-to-body>
      <el-table :data="productList" @selection-change="handleProductSelectionChange">
        <el-table-column type="selection" min-width="55" align="center" />
        <el-table-column label="商品名称" prop="productName" min-width="200" show-overflow-tooltip />
        <el-table-column label="商品编码" prop="productCode" min-width="120" show-overflow-tooltip />
        <el-table-column label="销售价" prop="price" min-width="100">
          <template slot-scope="scope">
            ¥{{ scope.row.price }}
          </template>
        </el-table-column>
        <el-table-column label="库存" prop="stock" min-width="80" />
      </el-table>
      <pagination
        v-show="productTotal > 0"
        :total="productTotal"
        :page.sync="productQueryParams.pageNum"
        :limit.sync="productQueryParams.pageSize"
        @pagination="getProductList"
      />
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="confirmSelectProducts">确 定</el-button>
        <el-button @click="productSelectOpen = false">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 订单详情抽屉 -->
    <el-drawer :title="detailTitle" :visible.sync="detailOpen" direction="rtl" size="50%">
      <el-descriptions :column="2" border class="drawer-content">
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
      <el-table :data="orderDetail.orderItems" size="small" class="drawer-content">
        <el-table-column label="商品" prop="productName" min-width="300">
          <template slot-scope="scope">
            <el-link type="primary" @click="handleViewProduct(scope.row)">{{ scope.row.productName }}</el-link>
          </template>
        </el-table-column>
        <el-table-column label="单价" prop="productPrice" min-width="100">
          <template slot-scope="scope">
            ¥{{ scope.row.productPrice }}
          </template>
        </el-table-column>
        <el-table-column label="数量" prop="quantity" min-width="80" align="center" />
        <el-table-column label="小计" prop="subtotal" min-width="100">
          <template slot-scope="scope">
            ¥{{ scope.row.subtotal }}
          </template>
        </el-table-column>
      </el-table>

      <div class="drawer-footer">
        <el-button @click="detailOpen = false">关 闭</el-button>
      </div>
    </el-drawer>

    <!-- 商品详情抽屉 -->
    <el-drawer title="商品详情" :visible.sync="productDetailOpen" direction="rtl" size="70%">
      <div class="product-drawer-content" v-if="productDetail.productId">
        <!-- 商品基本信息 -->
        <div class="product-section">
          <h4 class="section-title">基本信息</h4>
          <el-row :gutter="20">
            <el-col :span="12">
              <div class="info-item">
                <span class="label">商品名称：</span>
                <span class="value">{{ productDetail.productName }}</span>
              </div>
              <div class="info-item">
                <span class="label">商品编码：</span>
                <span class="value">{{ productDetail.productCode }}</span>
              </div>
              <div class="info-item">
                <span class="label">商品副标题：</span>
                <span class="value">{{ productDetail.productSubTitle || '-' }}</span>
              </div>
              <div class="info-item">
                <span class="label">分类：</span>
                <span class="value">{{ productDetail.categoryName || '-' }}</span>
              </div>
              <div class="info-item">
                <span class="label">单位：</span>
                <span class="value">{{ productDetail.unit || '-' }}</span>
              </div>
            </el-col>
            <el-col :span="12">
              <div class="main-image-container">
                <image-preview v-if="productDetail.mainImage" :src="productDetail.mainImage" :width="200" :height="200" />
              </div>
            </el-col>
          </el-row>
        </div>

        <!-- 价格信息 -->
        <div class="product-section">
          <h4 class="section-title">价格信息</h4>
          <el-row :gutter="20">
            <el-col :span="6">
              <div class="price-item">
                <span class="price-label">销售价</span>
                <span class="price-value primary">¥{{ productDetail.price }}</span>
              </div>
            </el-col>
            <el-col :span="6">
              <div class="price-item">
                <span class="price-label">原价</span>
                <span class="price-value">¥{{ productDetail.originalPrice || '-' }}</span>
              </div>
            </el-col>
            <el-col :span="6">
              <div class="price-item">
                <span class="price-label">会员价</span>
                <span class="price-value">¥{{ productDetail.memberPrice || '-' }}</span>
              </div>
            </el-col>
            <el-col :span="6">
              <div class="price-item">
                <span class="price-label">成本价</span>
                <span class="price-value">¥{{ productDetail.costPrice || '-' }}</span>
              </div>
            </el-col>
          </el-row>
        </div>

        <!-- 库存信息 -->
        <div class="product-section">
          <h4 class="section-title">库存信息</h4>
          <el-row :gutter="20">
            <el-col :span="6">
              <div class="info-item">
                <span class="label">库存：</span>
                <span class="value">{{ productDetail.stock }}</span>
              </div>
            </el-col>
            <el-col :span="6">
              <div class="info-item">
                <span class="label">销量：</span>
                <span class="value">{{ productDetail.sales }}</span>
              </div>
            </el-col>
            <el-col :span="6">
              <div class="info-item">
                <span class="label">预警值：</span>
                <span class="value">{{ productDetail.lowStock || '-' }}</span>
              </div>
            </el-col>
            <el-col :span="6">
              <div class="info-item">
                <span class="label">重量：</span>
                <span class="value">{{ productDetail.weight || '-' }}g</span>
              </div>
            </el-col>
          </el-row>
        </div>

        <!-- 商品状态 -->
        <div class="product-section">
          <h4 class="section-title">商品状态</h4>
          <el-row :gutter="20">
            <el-col :span="6">
              <el-tag v-if="productDetail.isOnSale === '1'" type="success" size="medium">上架</el-tag>
              <el-tag v-else type="info" size="medium">下架</el-tag>
            </el-col>
            <el-col :span="6">
              <el-tag v-if="productDetail.isHot === '1'" type="danger" size="medium">热销</el-tag>
              <el-tag v-else type="info" size="medium">普通</el-tag>
            </el-col>
            <el-col :span="6">
              <el-tag v-if="productDetail.isNew === '1'" type="success" size="medium">新品</el-tag>
              <el-tag v-else type="info" size="medium">非新品</el-tag>
            </el-col>
            <el-col :span="6">
              <el-tag v-if="productDetail.isRecommend === '1'" type="warning" size="medium">推荐</el-tag>
              <el-tag v-else type="info" size="medium">不推荐</el-tag>
            </el-col>
          </el-row>
        </div>

        <!-- 商品详情 -->
        <div class="product-section">
          <h4 class="section-title">商品详情</h4>
          <div class="detail-content" v-html="productDetail.detail"></div>
        </div>

        <!-- 其他信息 -->
        <div class="product-section">
          <h4 class="section-title">其他信息</h4>
          <el-row :gutter="20">
            <el-col :span="12">
              <div class="info-item">
                <span class="label">关键词：</span>
                <span class="value">{{ productDetail.keywords || '-' }}</span>
              </div>
            </el-col>
            <el-col :span="12">
              <div class="info-item">
                <span class="label">排序：</span>
                <span class="value">{{ productDetail.sortOrder }}</span>
              </div>
            </el-col>
          </el-row>
          <el-row :gutter="20" style="margin-top: 10px;">
            <el-col :span="24">
              <div class="info-item">
                <span class="label">备注：</span>
                <span class="value">{{ productDetail.remark || '-' }}</span>
              </div>
            </el-col>
          </el-row>
          <el-row :gutter="20" style="margin-top: 10px;">
            <el-col :span="12">
              <div class="info-item">
                <span class="label">创建时间：</span>
                <span class="value">{{ productDetail.createTime }}</span>
              </div>
            </el-col>
            <el-col :span="12">
              <div class="info-item">
                <span class="label">更新时间：</span>
                <span class="value">{{ productDetail.updateTime }}</span>
              </div>
            </el-col>
          </el-row>
        </div>
      </div>
      <div class="drawer-footer">
        <el-button @click="productDetailOpen = false">关 闭</el-button>
      </div>
    </el-drawer>
  </div>
</template>

<style scoped>
.shop-order-container {
  display: flex;
  flex-direction: column;
  height: calc(100vh - 84px);
}
.table-container {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}
.drawer-content {
  padding: 0 20px;
}
.product-drawer-content {
  padding: 20px;
  padding-bottom: 60px;
  overflow-y: auto;
  height: calc(100vh - 60px);
}
.product-section {
  margin-bottom: 25px;
  padding: 15px;
  background: #f5f7fa;
  border-radius: 4px;
}
.section-title {
  margin: 0 0 15px 0;
  font-size: 16px;
  font-weight: bold;
  color: #303133;
  border-left: 3px solid #409EFF;
  padding-left: 10px;
}
.info-item {
  margin-bottom: 10px;
  font-size: 14px;
}
.info-item .label {
  color: #909399;
  display: inline-block;
  min-width: 80px;
}
.info-item .value {
  color: #303133;
  font-weight: 500;
}
.price-item {
  text-align: center;
  padding: 15px;
  background: #fff;
  border-radius: 4px;
}
.price-label {
  display: block;
  font-size: 14px;
  color: #909399;
  margin-bottom: 8px;
}
.price-value {
  display: block;
  font-size: 24px;
  font-weight: bold;
  color: #606266;
}
.price-value.primary {
  color: #F56C6C;
}
.main-image-container {
  text-align: center;
  padding: 10px;
  background: #fff;
  border-radius: 4px;
}
.detail-content {
  padding: 15px;
  background: #fff;
  border-radius: 4px;
  min-height: 100px;
  overflow-y: auto;
}
.detail-content >>> img {
  max-width: 100%;
}
.product-detail-content {
  max-height: 300px;
  overflow-y: auto;
}
.drawer-footer {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  padding: 10px 20px;
  background: #fff;
  border-top: 1px solid #e8e8e8;
  text-align: right;
}
</style>

<script>
import { listOrder, getOrder, delOrder, shipOrder, completeOrder, addOrder } from "@/api/shop/order";
import { listUser } from "@/api/system/user";
import { listProduct, getProduct } from "@/api/shop/product/product";

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
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 当前Tab
      activeTab: "basic",
      // 详情弹窗标题
      detailTitle: "",
      // 详情弹窗
      detailOpen: false,
      // 订单详情
      orderDetail: {},
      // 商品详情弹窗
      productDetailOpen: false,
      // 商品详情
      productDetail: {},
      // 用户列表
      userList: [],
      // 商品选择弹窗
      productSelectOpen: false,
      // 商品列表
      productList: [],
      // 商品总数
      productTotal: 0,
      // 选中的商品
      selectedProducts: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderNo: null,
        username: null,
        orderStatus: null,
        receiverName: null,
        receiverPhone: null
      },
      // 商品查询参数
      productQueryParams: {
        pageNum: 1,
        pageSize: 10,
        isOnSale: '1'
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        userId: [
          { required: true, message: "请选择用户", trigger: "change" }
        ],
        payType: [
          { required: true, message: "请选择支付方式", trigger: "change" }
        ],
        receiverName: [
          { required: true, message: "请输入收货人姓名", trigger: "blur" }
        ],
        receiverPhone: [
          { required: true, message: "请输入联系电话", trigger: "blur" }
        ],
        receiverAddress: [
          { required: true, message: "请输入详细地址", trigger: "blur" }
        ]
      }
    };
  },
  computed: {
    // 计算商品总额
    totalAmount() {
      let total = 0;
      if (this.form.orderItems && this.form.orderItems.length > 0) {
        this.form.orderItems.forEach(item => {
          total += (item.productPrice || 0) * (item.quantity || 0);
        });
      }
      return total.toFixed(2);
    },
    // 计算应付金额
    payAmount() {
      let productAmount = parseFloat(this.totalAmount) || 0;
      let freight = parseFloat(this.form.freightAmount) || 0;
      let discount = parseFloat(this.form.discountAmount) || 0;
      let points = parseFloat(this.form.pointsAmount) || 0;
      return (productAmount + freight - discount - points).toFixed(2);
    }
  },
  created() {
    this.getList();
    this.getUserList();
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
    /** 获取用户列表 */
    getUserList() {
      listUser({ pageNum: 1, pageSize: 1000 }).then(response => {
        this.userList = response.rows;
      });
    },
    /** 获取商品列表 */
    getProductList() {
      listProduct(this.productQueryParams).then(response => {
        this.productList = response.rows;
        this.productTotal = response.total;
      });
    },
    /** 用户选择变化 */
    handleUserChange(userId) {
      // 可以在这里加载用户的默认收货地址
    },
    /** 打开商品选择对话框 */
    openProductSelect() {
      this.productSelectOpen = true;
      this.getProductList();
    },
    /** 商品选择变化 */
    handleProductSelectionChange(selection) {
      this.selectedProducts = selection;
    },
    /** 确认选择商品 */
    confirmSelectProducts() {
      if (this.selectedProducts.length === 0) {
        this.$modal.msgWarning("请至少选择一件商品");
        return;
      }
      this.selectedProducts.forEach(product => {
        // 检查是否已添加
        const exists = this.form.orderItems && this.form.orderItems.some(item => item.productId === product.productId);
        if (!exists) {
          this.form.orderItems = this.form.orderItems || [];
          this.form.orderItems.push({
            productId: product.productId,
            productName: product.productName,
            productImage: product.mainImage,
            productPrice: product.price,
            quantity: 1,
            subtotal: product.price
          });
        }
      });
      this.productSelectOpen = false;
      this.selectedProducts = [];
    },
    /** 商品数量变化 */
    handleQuantityChange(row) {
      row.subtotal = (row.productPrice * row.quantity).toFixed(2);
    },
    /** 删除订单商品 */
    removeOrderItem(index) {
      this.form.orderItems.splice(index, 1);
    },
    /** 取消按钮 */
    cancel() {
      this.open = false;
      this.reset();
    },
    /** 表单重置 */
    reset() {
      this.form = {
        orderId: null,
        userId: null,
        username: null,
        orderStatus: "paid",
        productCount: 0,
        productAmount: 0,
        freightAmount: 0,
        discountAmount: 0,
        pointsAmount: 0,
        payAmount: 0,
        usePoints: 0,
        earnPoints: 0,
        payType: "wechat",
        receiverName: null,
        receiverPhone: null,
        receiverProvince: null,
        receiverCity: null,
        receiverDistrict: null,
        receiverAddress: null,
        orderRemark: null,
        orderItems: []
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
      this.ids = selection.map(item => item.orderId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "创建订单";
      this.activeTab = "basic";
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (!this.form.orderItems || this.form.orderItems.length === 0) {
            this.$modal.msgError("请至少选择一件商品");
            return;
          }
          // 根据选择的用户ID获取用户名
          const user = this.userList.find(u => u.userId === this.form.userId);
          if (user) {
            this.form.username = user.userName;
          }

          // 计算金额
          this.form.productCount = this.form.orderItems.reduce((sum, item) => sum + item.quantity, 0);
          this.form.productAmount = this.totalAmount;
          this.form.payAmount = this.payAmount;
          // 生成订单号
          const now = new Date();
          const orderNo = "ORD" + now.getFullYear() +
            String(now.getMonth() + 1).padStart(2, '0') +
            String(now.getDate()).padStart(2, '0') +
            String(now.getHours()).padStart(2, '0') +
            String(now.getMinutes()).padStart(2, '0') +
            String(now.getSeconds()).padStart(2, '0') +
            Math.floor(Math.random() * 1000);
          this.form.orderNo = orderNo;

          addOrder(this.form).then(response => {
            this.$modal.msgSuccess("新增成功");
            this.open = false;
            this.getList();
          });
        }
      });
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
    /** 查看商品详情 */
    handleViewProduct(row) {
      const productId = row.productId;
      getProduct(productId).then(response => {
        this.productDetail = response.data;
        this.productDetailOpen = true;
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
