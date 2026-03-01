<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
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
          <el-option label="获取积分" value="earn" />
          <el-option label="消费积分" value="consume" />
          <el-option label="积分返还" value="refund" />
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
          v-hasPermi="['shop:pointsRule:add']"
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
          v-hasPermi="['shop:pointsRule:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['shop:pointsRule:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="pointsRuleList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="ruleId" width="80" />
      <el-table-column label="规则名称" align="center" prop="ruleName" :show-overflow-tooltip="true" />
      <el-table-column label="规则类型" align="center" prop="ruleType" width="100">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.ruleType === 'earn'" type="success">获取积分</el-tag>
          <el-tag v-else-if="scope.row.ruleType === 'consume'" type="warning">消费积分</el-tag>
          <el-tag v-else-if="scope.row.ruleType === 'refund'" type="info">积分返还</el-tag>
          <el-tag v-else type="info">{{ scope.row.ruleType }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="积分值" align="center" prop="pointsValue" width="100" />
      <el-table-column label="每日限制" align="center" prop="dailyLimit" width="100" />
      <el-table-column label="适用场景" align="center" prop="scene" :show-overflow-tooltip="true" />
      <el-table-column label="生效时间" align="center" width="180">
        <template slot-scope="scope">
          <span v-if="scope.row.startTime">{{ parseTime(scope.row.startTime, '{y}-{m}-{d}') }} ~ {{ parseTime(scope.row.endTime, '{y}-{m}-{d}') }}</span>
          <span v-else>永久有效</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status" width="100">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.status"
            active-value="0"
            inactive-value="1"
            @change="handleStatusChange(scope.row)"
            v-hasPermi="['shop:pointsRule:edit']"
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
            v-hasPermi="['shop:pointsRule:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['shop:pointsRule:remove']"
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

    <!-- 添加或修改积分规则对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="140px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="规则名称" prop="ruleName">
              <el-input v-model="form.ruleName" placeholder="请输入规则名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="规则类型" prop="ruleType">
              <el-select v-model="form.ruleType" placeholder="请选择规则类型">
                <el-option label="获取积分" value="earn" />
                <el-option label="消费积分" value="consume" />
                <el-option label="积分返还" value="refund" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="积分值" prop="pointsValue">
              <el-input-number v-model="form.pointsValue" :min="0" :step="1" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="每日限制" prop="dailyLimit">
              <el-input-number v-model="form.dailyLimit" :min="0" :step="1" style="width: 100%" />
              <span style="margin-left: 5px; color: #909399;">0表示不限制</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="计算方式" prop="calcType">
              <el-select v-model="form.calcType" placeholder="请选择计算方式">
                <el-option label="固定值" value="fixed" />
                <el-option label="比例" value="ratio" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="form.calcType === 'ratio'">
            <el-form-item label="比例值" prop="ratioValue">
              <el-input-number v-model="form.ratioValue" :min="0" :max="100" :precision="2" :step="1" style="width: 100%" />
              <span style="margin-left: 5px; color: #909399;">%</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否启用" prop="enabled">
              <el-radio-group v-model="form.enabled">
                <el-radio :label="1">是</el-radio>
                <el-radio :label="0">否</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="排序" prop="sort">
              <el-input-number v-model="form.sort" :min="0" :step="1" style="width: 100%" />
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
          <el-col :span="24">
            <el-form-item label="适用场景">
              <el-input v-model="form.scene" type="textarea" placeholder="请输入适用场景描述" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="规则描述">
              <el-input v-model="form.description" type="textarea" placeholder="请输入规则描述" :rows="3" />
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
import { listPointsRule, getPointsRule, delPointsRule, addPointsRule, updatePointsRule, changeStatus } from "@/api/shop/pointsRule";

export default {
  name: "PointsRule",
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
      pointsRuleList: [],
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
          { required: true, message: "请选择规则类型", trigger: "change" }
        ],
        pointsValue: [
          { required: true, message: "积分值不能为空", trigger: "blur" }
        ],
        calcType: [
          { required: true, message: "请选择计算方式", trigger: "change" }
        ],
        enabled: [
          { required: true, message: "请选择是否启用", trigger: "change" }
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
      listPointsRule(this.queryParams).then(response => {
        this.pointsRuleList = response.rows;
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
        ruleType: "earn",
        pointsValue: 0,
        dailyLimit: 0,
        calcType: "fixed",
        ratioValue: 0,
        scene: null,
        description: null,
        enabled: 1,
        sort: 0,
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
      const ruleId = row.ruleId || this.ids[0];
      getPointsRule(ruleId).then(response => {
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
            updatePointsRule(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPointsRule(this.form).then(response => {
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
      const ruleIds = row.ruleId || this.ids;
      this.$modal.confirm('是否确认删除积分规则编号为"' + ruleIds + '"的数据项？').then(function() {
        return delPointsRule(ruleIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('shop/pointsRule/export', {
        ...this.queryParams
      }, `pointsRule_${new Date().getTime()}.xlsx`)
    },
    /** 状态修改 */
    handleStatusChange(row) {
      let text = row.status === "0" ? "启用" : "停用";
      this.$modal.confirm('确认要"' + text + '""' + row.ruleName + '"积分规则吗？').then(function() {
        return changeStatus(row);
      }).then(() => {
        this.$modal.msgSuccess(text + "成功");
      }).catch(function() {
        row.status = row.status === "0" ? "1" : "0";
      });
    }
  }
};
</script>
