<template>
  <div class="app-container">
    <!-- 时间范围筛选 -->
    <el-form :model="queryParams" ref="queryForm" :inline="true" class="search-form">
      <el-form-item label="统计类型">
        <el-select v-model="trendType" placeholder="请选择" @change="handleTrendTypeChange">
          <el-option label="按日" value="day" />
          <el-option label="按周" value="week" />
          <el-option label="按月" value="month" />
        </el-select>
      </el-form-item>
      <el-form-item label="时间范围">
        <el-date-picker
          v-model="dateRange"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          value-format="yyyy-MM-dd"
          :picker-options="pickerOptions"
          @change="handleDateChange"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="handleReset">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 统计卡片 -->
    <el-row :gutter="20" class="statistics-cards">
      <el-col :xs="24" :sm="12" :lg="6" class="card-col">
        <div class="stat-card stat-card-blue">
          <div class="stat-icon">
            <i class="el-icon-shopping-bag-2" />
          </div>
          <div class="stat-content">
            <div class="stat-label">订单总数</div>
            <div class="stat-value">{{ overview.total_orders || 0 }}</div>
          </div>
        </div>
      </el-col>
      <el-col :xs="24" :sm="12" :lg="6" class="card-col">
        <div class="stat-card stat-card-green">
          <div class="stat-icon">
            <i class="el-icon-money" />
          </div>
          <div class="stat-content">
            <div class="stat-label">销售总额</div>
            <div class="stat-value">¥{{ (overview.total_amount || 0).toFixed(2) }}</div>
          </div>
        </div>
      </el-col>
      <el-col :xs="24" :sm="12" :lg="6" class="card-col">
        <div class="stat-card stat-card-orange">
          <div class="stat-icon">
            <i class="el-icon-date" />
          </div>
          <div class="stat-content">
            <div class="stat-label">今日订单</div>
            <div class="stat-value">{{ overview.today_orders || 0 }}</div>
          </div>
        </div>
      </el-col>
      <el-col :xs="24" :sm="12" :lg="6" class="card-col">
        <div class="stat-card stat-card-purple">
          <div class="stat-icon">
            <i class="el-icon-warning-outline" />
          </div>
          <div class="stat-content">
            <div class="stat-label">待发货</div>
            <div class="stat-value">{{ overview.paid_orders || 0 }}</div>
          </div>
        </div>
      </el-col>
    </el-row>

    <!-- 图表区域 -->
    <el-row :gutter="20">
      <!-- 销售趋势图 -->
      <el-col :xs="24" :sm="24" :lg="16" class="chart-col">
        <el-card shadow="hover" class="chart-card">
          <div slot="header" class="card-header">
            <span>销售趋势</span>
          </div>
          <trend-chart :chart-data="trendData" height="350px" />
        </el-card>
      </el-col>

      <!-- 订单状态分布 -->
      <el-col :xs="24" :sm="24" :lg="8" class="chart-col">
        <el-card shadow="hover" class="chart-card">
          <div slot="header" class="card-header">
            <span>订单状态分布</span>
          </div>
          <status-pie-chart :chart-data="statusData" height="350px" />
        </el-card>
      </el-col>
    </el-row>

    <!-- 热销商品排行 -->
    <el-row :gutter="20">
      <el-col :span="24">
        <el-card shadow="hover" class="chart-card">
          <div slot="header" class="card-header">
            <span>热销商品 TOP10</span>
          </div>
          <top-products-chart :chart-data="topProductsData" height="350px" />
        </el-card>
      </el-col>
    </el-row>

    <!-- 今日数据统计 -->
    <el-row :gutter="20">
      <el-col :span="24">
        <el-card shadow="hover" class="chart-card">
          <div slot="header" class="card-header">
            <span>今日数据概览</span>
          </div>
          <div class="today-stats">
            <div class="today-stat-item">
              <span class="label">今日订单数：</span>
              <span class="value">{{ overview.today_orders || 0 }}</span>
            </div>
            <div class="today-stat-item">
              <span class="label">今日销售额：</span>
              <span class="value">¥{{ (overview.today_amount || 0).toFixed(2) }}</span>
            </div>
            <el-divider />
            <div class="status-grid">
              <div class="status-item pending">
                <div class="status-label">待付款</div>
                <div class="status-count">{{ overview.pending_orders || 0 }}</div>
              </div>
              <div class="status-item paid">
                <div class="status-label">已付款</div>
                <div class="status-count">{{ overview.paid_orders || 0 }}</div>
              </div>
              <div class="status-item shipped">
                <div class="status-label">已发货</div>
                <div class="status-count">{{ overview.shipped_orders || 0 }}</div>
              </div>
              <div class="status-item completed">
                <div class="status-label">已完成</div>
                <div class="status-count">{{ overview.completed_orders || 0 }}</div>
              </div>
              <div class="status-item cancelled">
                <div class="status-label">已取消</div>
                <div class="status-count">{{ overview.cancelled_orders || 0 }}</div>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import * as echarts from 'echarts'
require('echarts/theme/macarons')
import { getOverview, getTrend, getStatusStatistics, getTopSellingProducts } from '@/api/shop/statistics'

// 销售趋势图组件
const TrendChart = {
  props: {
    chartData: {
      type: Object,
      default: () => ({ dates: [], counts: [], amounts: [] })
    },
    height: {
      type: String,
      default: '350px'
    }
  },
  data() {
    return {
      chart: null
    }
  },
  watch: {
    chartData: {
      deep: true,
      handler() {
        this.$nextTick(() => {
          this.initChart()
        })
      }
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.initChart()
    })
    window.addEventListener('resize', this.handleResize)
  },
  beforeDestroy() {
    if (this.chart) {
      this.chart.dispose()
      this.chart = null
    }
    window.removeEventListener('resize', this.handleResize)
  },
  methods: {
    handleResize() {
      if (this.chart) {
        this.chart.resize()
      }
    },
    initChart() {
      if (!this.$el) return
      if (this.chart) {
        this.chart.dispose()
      }
      this.chart = echarts.init(this.$el, 'macarons')

      const option = {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross'
          }
        },
        legend: {
          data: ['订单数量', '销售金额']
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: this.chartData.dates || []
        },
        yAxis: [
          {
            type: 'value',
            name: '订单数量',
            position: 'left'
          },
          {
            type: 'value',
            name: '销售金额',
            position: 'right'
          }
        ],
        series: [
          {
            name: '订单数量',
            type: 'line',
            smooth: true,
            data: this.chartData.counts || [],
            itemStyle: {
              color: '#409EFF'
            },
            areaStyle: {
              color: 'rgba(64, 158, 255, 0.1)'
            }
          },
          {
            name: '销售金额',
            type: 'line',
            smooth: true,
            yAxisIndex: 1,
            data: this.chartData.amounts || [],
            itemStyle: {
              color: '#67C23A'
            },
            areaStyle: {
              color: 'rgba(103, 194, 58, 0.1)'
            }
          }
        ]
      }

      this.chart.setOption(option)
    }
  },
  render(h) {
    return h('div', {
      style: { height: this.height }
    })
  }
}

// 订单状态饼图组件
const StatusPieChart = {
  props: {
    chartData: {
      type: Array,
      default: () => []
    },
    height: {
      type: String,
      default: '350px'
    }
  },
  data() {
    return {
      chart: null
    }
  },
  watch: {
    chartData: {
      deep: true,
      handler() {
        this.initChart()
      }
    }
  },
  mounted() {
    this.initChart()
    window.addEventListener('resize', this.handleResize)
  },
  beforeDestroy() {
    if (this.chart) {
      this.chart.dispose()
      this.chart = null
    }
    window.removeEventListener('resize', this.handleResize)
  },
  methods: {
    handleResize() {
      if (this.chart) {
        this.chart.resize()
      }
    },
    initChart() {
      if (!this.$el) return
      if (this.chart) {
        this.chart.dispose()
      }
      this.chart = echarts.init(this.$el, 'macarons')

      const data = this.chartData.map(item => ({
        name: item.status_name,
        value: item.count
      }))

      const option = {
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b}: {c} ({d}%)'
        },
        legend: {
          bottom: '10',
          left: 'center'
        },
        series: [
          {
            name: '订单状态',
            type: 'pie',
            radius: ['40%', '70%'],
            avoidLabelOverlap: false,
            itemStyle: {
              borderRadius: 10,
              borderColor: '#fff',
              borderWidth: 2
            },
            label: {
              show: false,
              position: 'center'
            },
            emphasis: {
              label: {
                show: true,
                fontSize: 16,
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: false
            },
            data: data
          }
        ]
      }

      this.chart.setOption(option)
    }
  },
  render(h) {
    return h('div', {
      style: { height: this.height }
    })
  }
}

// 热销商品柱状图组件
const TopProductsChart = {
  props: {
    chartData: {
      type: Array,
      default: () => []
    },
    height: {
      type: String,
      default: '350px'
    }
  },
  data() {
    return {
      chart: null
    }
  },
  watch: {
    chartData: {
      deep: true,
      handler() {
        this.initChart()
      }
    }
  },
  mounted() {
    this.initChart()
    window.addEventListener('resize', this.handleResize)
  },
  beforeDestroy() {
    if (this.chart) {
      this.chart.dispose()
      this.chart = null
    }
    window.removeEventListener('resize', this.handleResize)
  },
  methods: {
    handleResize() {
      if (this.chart) {
        this.chart.resize()
      }
    },
    initChart() {
      if (!this.$el) return
      if (this.chart) {
        this.chart.dispose()
      }
      this.chart = echarts.init(this.$el, 'macarons')

      const names = this.chartData.map(item => item.product_name)
      const counts = this.chartData.map(item => item.sales_count)

      const option = {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          data: names,
          axisLabel: {
            interval: 0,
            rotate: 30,
            formatter: function(value) {
              if (value.length > 10) {
                return value.substr(0, 10) + '...'
              }
              return value
            }
          }
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: '销量',
            type: 'bar',
            barWidth: '60%',
            data: counts,
            itemStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                { offset: 0, color: '#83bff6' },
                { offset: 0.5, color: '#188df0' },
                { offset: 1, color: '#188df0' }
              ])
            },
            emphasis: {
              itemStyle: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                  { offset: 0, color: '#2378f7' },
                  { offset: 0.7, color: '#2378f7' },
                  { offset: 1, color: '#83bff6' }
                ])
              }
            }
          }
        ]
      }

      this.chart.setOption(option)
    }
  },
  render(h) {
    return h('div', {
      style: { height: this.height }
    })
  }
}

export default {
  name: 'OrderStatistics',
  components: {
    TrendChart,
    StatusPieChart,
    TopProductsChart
  },
  data() {
    return {
      // 查询参数
      queryParams: {},
      trendType: 'day',
      dateRange: [],
      pickerOptions: {
        shortcuts: [
          {
            text: '最近一周',
            onClick(picker) {
              const end = new Date()
              const start = new Date()
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
              picker.$emit('pick', [start, end])
            }
          },
          {
            text: '最近一个月',
            onClick(picker) {
              const end = new Date()
              const start = new Date()
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
              picker.$emit('pick', [start, end])
            }
          },
          {
            text: '最近三个月',
            onClick(picker) {
              const end = new Date()
              const start = new Date()
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
              picker.$emit('pick', [start, end])
            }
          }
        ]
      },
      // 数据
      overview: {},
      trendData: { dates: [], counts: [], amounts: [] },
      statusData: [],
      topProductsData: []
    }
  },
  created() {
    this.setDefaultDateRange()
    this.getOverviewData()
    this.getTrendData()
    this.getStatusData()
    this.getTopProductsData()
  },
  methods: {
    // 设置默认日期范围（最近30天）
    setDefaultDateRange() {
      const end = new Date()
      const start = new Date()
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
      this.dateRange = [
        this.formatDate(start),
        this.formatDate(end)
      ]
    },
    formatDate(date) {
      const year = date.getFullYear()
      const month = String(date.getMonth() + 1).padStart(2, '0')
      const day = String(date.getDate()).padStart(2, '0')
      return `${year}-${month}-${day}`
    },
    // 获取统计概览
    getOverviewData() {
      getOverview().then(response => {
        this.overview = response.data
      })
    },
    // 获取趋势数据
    getTrendData() {
      const params = {
        type: this.trendType
      }
      // 只有设置了日期范围才传递参数
      if (this.dateRange && this.dateRange.length === 2) {
        params.startTime = this.dateRange[0]
        params.endTime = this.dateRange[1]
      }
      getTrend(params).then(response => {
        const data = response.data || []
        this.trendData = {
          dates: data.map(item => item.date),
          counts: data.map(item => item.order_count),
          amounts: data.map(item => parseFloat(item.order_amount || 0))
        }
      })
    },
    // 获取状态统计
    getStatusData() {
      getStatusStatistics().then(response => {
        this.statusData = response.data || []
      })
    },
    // 获取热销商品
    getTopProductsData() {
      getTopSellingProducts({ limit: 10 }).then(response => {
        this.topProductsData = response.data || []
      })
    },
    // 搜索
    handleQuery() {
      this.getTrendData()
    },
    // 重置
    handleReset() {
      this.trendType = 'day'
      this.setDefaultDateRange()
      this.handleQuery()
    },
    // 趋势类型改变
    handleTrendTypeChange() {
      this.handleQuery()
    },
    // 日期范围改变
    handleDateChange() {
      this.handleQuery()
    }
  }
}
</script>

<style lang="scss" scoped>
.search-form {
  margin-bottom: 20px;
  background: #fff;
  padding: 18px 18px 0;
  border-radius: 4px;

  ::v-deep .el-form-item {
    margin-bottom: 12px;
  }
}

.statistics-cards {
  margin-bottom: 20px;

  .card-col {
    margin-bottom: 20px;
  }

  .stat-card {
    background: #fff;
    border-radius: 8px;
    padding: 24px;
    display: flex;
    align-items: center;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    transition: all 0.3s;

    &:hover {
      transform: translateY(-4px);
      box-shadow: 0 4px 20px 0 rgba(0, 0, 0, 0.15);
    }

    .stat-icon {
      width: 60px;
      height: 60px;
      border-radius: 12px;
      display: flex;
      align-items: center;
      justify-content: center;
      margin-right: 16px;

      i {
        font-size: 28px;
        color: #fff;
      }
    }

    .stat-content {
      flex: 1;

      .stat-label {
        font-size: 14px;
        color: #909399;
        margin-bottom: 8px;
      }

      .stat-value {
        font-size: 24px;
        font-weight: bold;
        color: #303133;
      }
    }

    &.stat-card-blue .stat-icon {
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    }

    &.stat-card-green .stat-icon {
      background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
    }

    &.stat-card-orange .stat-icon {
      background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
    }

    &.stat-card-purple .stat-icon {
      background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);
    }
  }
}

.chart-col {
  margin-bottom: 20px;
}

.chart-card {
  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;

    span {
      font-size: 16px;
      font-weight: 600;
      color: #303133;
    }
  }
}

.today-stats {
  .today-stat-item {
    display: inline-block;
    margin-right: 40px;
    font-size: 14px;

    .label {
      color: #909399;
    }

    .value {
      color: #303133;
      font-weight: bold;
      font-size: 18px;
      margin-left: 8px;
    }
  }

  .status-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(120px, 1fr));
    gap: 16px;
    margin-top: 16px;
  }

  .status-item {
    background: #f5f7fa;
    border-radius: 8px;
    padding: 16px;
    text-align: center;
    transition: all 0.3s;

    &:hover {
      transform: translateY(-2px);
    }

    .status-label {
      font-size: 14px;
      color: #909399;
      margin-bottom: 8px;
    }

    .status-count {
      font-size: 24px;
      font-weight: bold;
      color: #303133;
    }

    &.pending .status-count {
      color: #e6a23c;
    }

    &.paid .status-count {
      color: #409eff;
    }

    &.shipped .status-count {
      color: #67c23a;
    }

    &.completed .status-count {
      color: #909399;
    }

    &.cancelled .status-count {
      color: #f56c6c;
    }
  }
}

@media (max-width: 768px) {
  .statistics-cards {
    .stat-card {
      .stat-icon {
        width: 50px;
        height: 50px;

        i {
          font-size: 24px;
        }
      }

      .stat-content {
        .stat-value {
          font-size: 20px;
        }
      }
    }
  }

  .today-stats {
    .today-stat-item {
      display: block;
      margin-bottom: 12px;
      margin-right: 0;
    }

    .status-grid {
      grid-template-columns: repeat(auto-fit, minmax(100px, 1fr));
      gap: 12px;
    }

    .status-item {
      padding: 12px;

      .status-count {
        font-size: 20px;
      }
    }
  }
}
</style>
