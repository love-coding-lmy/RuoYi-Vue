<template>
  <div class="app-container">
    <!-- 搜索区域 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="参数名称" prop="configName">
        <el-input
          v-model="queryParams.configName"
          placeholder="请输入参数名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="参数键名" prop="configKey">
        <el-input
          v-model="queryParams.configKey"
          placeholder="请输入参数键名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="参数分组" prop="configGroup">
        <el-select v-model="queryParams.configGroup" placeholder="请选择参数分组" clearable>
          <el-option label="基础配置" value="basic" />
          <el-option label="订单配置" value="order" />
          <el-option label="支付配置" value="payment" />
          <el-option label="物流配置" value="shipping" />
          <el-option label="积分配置" value="points" />
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
          v-hasPermi="['shop:system:add']"
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
          v-hasPermi="['shop:system:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-refresh"
          size="mini"
          @click="handleRefreshCache"
          v-hasPermi="['shop:system:refresh']"
        >刷新缓存</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 表格区域 -->
    <el-table v-loading="loading" :data="systemList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="参数ID" align="center" prop="configId" width="80" />
      <el-table-column label="参数名称" align="center" prop="configName" :show-overflow-tooltip="true" />
      <el-table-column label="参数键名" align="center" prop="configKey" width="180" :show-overflow-tooltip="true" />
      <el-table-column label="参数键值" align="center" prop="configValue" width="200" :show-overflow-tooltip="true" />
      <el-table-column label="参数类型" align="center" prop="configType" width="100">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.configType === 'string'" type="primary">字符串</el-tag>
          <el-tag v-else-if="scope.row.configType === 'number'" type="success">数字</el-tag>
          <el-tag v-else-if="scope.row.configType === 'boolean'" type="info">布尔</el-tag>
          <el-tag v-else-if="scope.row.configType === 'json'" type="warning">JSON</el-tag>
          <el-tag v-else type="info">{{ scope.row.configType }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="参数分组" align="center" prop="configGroup" width="120">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.configGroup === 'basic'" type="primary">基础配置</el-tag>
          <el-tag v-else-if="scope.row.configGroup === 'order'" type="success">订单配置</el-tag>
          <el-tag v-else-if="scope.row.configGroup === 'payment'" type="warning">支付配置</el-tag>
          <el-tag v-else-if="scope.row.configGroup === 'shipping'" type="info">物流配置</el-tag>
          <el-tag v-else-if="scope.row.configGroup === 'points'" type="success">积分配置</el-tag>
          <el-tag v-else type="info">{{ scope.row.configGroup }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="系统内置" align="center" prop="isSystem" width="100">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.isSystem === '0'" type="danger">是</el-tag>
          <el-tag v-else type="success">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="排序" align="center" prop="sort" width="80" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="180">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['shop:system:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['shop:system:remove']"
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
            <el-form-item label="参数名称" prop="configName">
              <el-input v-model="form.configName" placeholder="请输入参数名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="参数键名" prop="configKey">
              <el-input v-model="form.configKey" placeholder="请输入参数键名" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="参数键值" prop="configValue">
              <el-input v-model="form.configValue" placeholder="请输入参数键值" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="参数类型" prop="configType">
              <el-select v-model="form.configType" placeholder="请选择参数类型">
                <el-option label="字符串" value="string" />
                <el-option label="数字" value="number" />
                <el-option label="布尔" value="boolean" />
                <el-option label="JSON" value="json" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="参数分组" prop="configGroup">
              <el-select v-model="form.configGroup" placeholder="请选择参数分组">
                <el-option label="基础配置" value="basic" />
                <el-option label="订单配置" value="order" />
                <el-option label="支付配置" value="payment" />
                <el-option label="物流配置" value="shipping" />
                <el-option label="积分配置" value="points" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="系统内置" prop="isSystem">
              <el-radio-group v-model="form.isSystem">
                <el-radio label="0">是</el-radio>
                <el-radio label="1">否</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="排序" prop="sort">
              <el-input-number v-model="form.sort" :min="0" controls-position="right" style="width: 100%" />
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
import { listSystem, getSystem, addSystem, updateSystem, delSystem, refreshCache } from "@/api/shop/config";

export default {
  name: "System",
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
      // 系统参数表格数据
      systemList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        configName: null,
        configKey: null,
        configGroup: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        configName: [
          { required: true, message: "参数名称不能为空", trigger: "blur" }
        ],
        configKey: [
          { required: true, message: "参数键名不能为空", trigger: "blur" }
        ],
        configValue: [
          { required: true, message: "参数键值不能为空", trigger: "blur" }
        ],
        configType: [
          { required: true, message: "参数类型不能为空", trigger: "change" }
        ],
        configGroup: [
          { required: true, message: "参数分组不能为空", trigger: "change" }
        ],
        isSystem: [
          { required: true, message: "系统内置不能为空", trigger: "blur" }
        ],
        sort: [
          { required: true, message: "排序不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询系统参数列表 */
    getList() {
      this.loading = true;
      listSystem(this.queryParams).then(response => {
        this.systemList = response.rows;
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
        configId: null,
        configName: null,
        configKey: null,
        configValue: null,
        configType: null,
        configGroup: null,
        isSystem: "1",
        sort: 0
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
      this.ids = selection.map(item => item.configId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加系统参数";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const configId = row.configId || this.ids;
      getSystem(configId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改系统参数";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.configId != null) {
            updateSystem(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSystem(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 刷新缓存按钮操作 */
    handleRefreshCache() {
      this.$modal.confirm('确认要刷新系统参数缓存吗？').then(function() {
        return refreshCache();
      }).then(() => {
        this.$modal.msgSuccess("刷新成功");
      }).catch(() => {});
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const configIds = row.configId || this.ids;
      this.$modal.confirm('是否确认删除系统参数编号为"' + configIds + '"的数据项？').then(function() {
        return delSystem(configIds);
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
