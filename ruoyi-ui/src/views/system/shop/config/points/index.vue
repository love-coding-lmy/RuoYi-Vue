<template>
  <div class="app-container">
    <!-- 搜索区域 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="规则名称" prop="ruleName">
        <el-input
          v-model="queryParams.ruleName"
          placeholder="请输入规则名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="规则类型" prop="ruleType">
        <el-select v-model="queryParams.ruleType" placeholder="请选择规则类型" clearable>
          <el-option label="获取" value="get" />
          <el-option label="消费" value="consume" />
          <el-option label="签到" value="sign" />
          <el-option label="任务" value="task" />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option label="启用" value="0" />
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
          v-hasPermi="['shop:points:add']"
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
          v-hasPermi="['shop:points:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 表格区域 -->
    <el-table v-loading="loading" :data="pointsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="规则ID" align="center" prop="ruleId" width="80" />
      <el-table-column label="规则名称" align="center" prop="ruleName" :show-overflow-tooltip="true" />
      <el-table-column label="规则类型" align="center" prop="ruleType" width="100">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.ruleType === 'get'" type="success">获取</el-tag>
          <el-tag v-else-if="scope.row.ruleType === 'consume'" type="warning">消费</el-tag>
          <el-tag v-else-if="scope.row.ruleType === 'sign'" type="info">签到</el-tag>
          <el-tag v-else-if="scope.row.ruleType === 'task'" type="primary">任务</el-tag>
          <el-tag v-else type="info">{{ scope.row.ruleType }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="积分类型" align="center" prop="pointsType" width="100">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.pointsType === 'fixed'" type="success">固定值</el-tag>
          <el-tag v-else-if="scope.row.pointsType === 'ratio'" type="warning">比例</el-tag>
          <el-tag v-else type="info">{{ scope.row.pointsType }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="积分值" align="center" prop="pointsValue" width="100" />
      <el-table-column label="每日限额" align="center" prop="dailyLimit" width="100" />
      <el-table-column label="计算类型" align="center" prop="calcType" width="100">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.calcType === 'add'" type="success">增加</el-tag>
          <el-tag v-else-if="scope.row.calcType === 'subtract'" type="danger">减少</el-tag>
          <el-tag v-else type="info">{{ scope.row.calcType }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status" width="100">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.status"
            active-value="0"
            inactive-value="1"
            @change="handleStatusChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="180">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['shop:points:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['shop:points:remove']"
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
            <el-form-item label="规则名称" prop="ruleName">
              <el-input v-model="form.ruleName" placeholder="请输入规则名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="规则类型" prop="ruleType">
              <el-select v-model="form.ruleType" placeholder="请选择规则类型">
                <el-option label="获取" value="get" />
                <el-option label="消费" value="consume" />
                <el-option label="签到" value="sign" />
                <el-option label="任务" value="task" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="积分类型" prop="pointsType">
              <el-select v-model="form.pointsType" placeholder="请选择积分类型">
                <el-option label="固定值" value="fixed" />
                <el-option label="比例" value="ratio" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="积分值" prop="pointsValue">
              <el-input-number v-model="form.pointsValue" :min="0" :precision="2" controls-position="right" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="每日限额" prop="dailyLimit">
              <el-input-number v-model="form.dailyLimit" :min="0" controls-position="right" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="计算类型" prop="calcType">
              <el-select v-model="form.calcType" placeholder="请选择计算类型">
                <el-option label="增加" value="add" />
                <el-option label="减少" value="subtract" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="form.pointsType === 'ratio'">
            <el-form-item label="比例值" prop="ratioValue">
              <el-input-number v-model="form.ratioValue" :min="0" :max="100" :precision="2" controls-position="right" style="width: 100%" />
              <span style="margin-left: 5px; color: #909399;">%</span>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="描述" prop="description">
              <el-input v-model="form.description" type="textarea" placeholder="请输入规则描述" :rows="3" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-radio-group v-model="form.status">
                <el-radio label="0">启用</el-radio>
                <el-radio label="1">停用</el-radio>
              </el-radio-group>
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
import { listPoints, getPoints, addPoints, updatePoints, delPoints, changePointsStatus } from "@/api/shop/config";

export default {
  name: "Points",
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
      // 积分规则表格数据
      pointsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        ruleName: null,
        ruleType: null,
        status: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        ruleName: [
          { required: true, message: "规则名称不能为空", trigger: "blur" }
        ],
        ruleType: [
          { required: true, message: "规则类型不能为空", trigger: "change" }
        ],
        pointsType: [
          { required: true, message: "积分类型不能为空", trigger: "change" }
        ],
        pointsValue: [
          { required: true, message: "积分值不能为空", trigger: "blur" }
        ],
        dailyLimit: [
          { required: true, message: "每日限额不能为空", trigger: "blur" }
        ],
        calcType: [
          { required: true, message: "计算类型不能为空", trigger: "change" }
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
    /** 查询积分规则列表 */
    getList() {
      this.loading = true;
      listPoints(this.queryParams).then(response => {
        this.pointsList = response.rows;
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
        ruleId: null,
        ruleName: null,
        ruleType: null,
        pointsType: null,
        pointsValue: 0,
        dailyLimit: 0,
        calcType: null,
        ratioValue: null,
        description: null,
        status: "0"
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
      this.ids = selection.map(item => item.ruleId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加积分规则";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const ruleId = row.ruleId || this.ids;
      getPoints(ruleId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改积分规则";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.ruleId != null) {
            updatePoints(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPoints(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 状态修改 */
    handleStatusChange(row) {
      let text = row.status === "0" ? "启用" : "停用";
      this.$modal.confirm('确认要"' + text + '""' + row.ruleName + '"规则吗？').then(function() {
        return changePointsStatus(row.ruleId, row.status);
      }).then(() => {
        this.$modal.msgSuccess(text + "成功");
      }).catch(function() {
        row.status = row.status === "0" ? "1" : "0";
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ruleIds = row.ruleId || this.ids;
      this.$modal.confirm('是否确认删除积分规则编号为"' + ruleIds + '"的数据项？').then(function() {
        return delPoints(ruleIds);
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
