<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="等级名称" prop="levelName">
        <el-input
          v-model="queryParams.levelName"
          placeholder="请输入等级名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
          v-hasPermi="['shop:memberLevel:add']"
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
          v-hasPermi="['shop:memberLevel:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['shop:memberLevel:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="memberLevelList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="levelId" width="80" />
      <el-table-column label="等级名称" align="center" prop="levelName" :show-overflow-tooltip="true" />
      <el-table-column label="等级图标" align="center" prop="levelIcon" width="100">
        <template slot-scope="scope">
          <img v-if="scope.row.levelIcon" :src="scope.row.levelIcon" style="width: 30px; height: 30px;" />
        </template>
      </el-table-column>
      <el-table-column label="积分范围" align="center" width="150">
        <template slot-scope="scope">
          {{ scope.row.minPoints }} - {{ scope.row.maxPoints }}
        </template>
      </el-table-column>
      <el-table-column label="折扣(%)" align="center" prop="discount" width="100">
        <template slot-scope="scope">
          {{ scope.row.discount }}%
        </template>
      </el-table-column>
      <el-table-column label="免运费" align="center" prop="freeShipping" width="100">
        <template slot-scope="scope">
          <el-tag :type="scope.row.freeShipping === 1 ? 'success' : 'info'">
            {{ scope.row.freeShipping === 1 ? '是' : '否' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="升级条件" align="center" prop="upgradeCondition" :show-overflow-tooltip="true" />
      <el-table-column label="权益描述" align="center" prop="benefits" :show-overflow-tooltip="true" />
      <el-table-column label="状态" align="center" prop="status" width="100">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.status"
            active-value="0"
            inactive-value="1"
            @change="handleStatusChange(scope.row)"
            v-hasPermi="['shop:memberLevel:edit']"
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
            v-hasPermi="['shop:memberLevel:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['shop:memberLevel:remove']"
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

    <!-- 添加或修改会员等级对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="140px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="等级名称" prop="levelName">
              <el-input v-model="form.levelName" placeholder="请输入等级名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="等级图标" prop="levelIcon">
              <el-input v-model="form.levelIcon" placeholder="请输入等级图标URL" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="最低积分" prop="minPoints">
              <el-input-number v-model="form.minPoints" :min="0" :step="100" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="最高积分" prop="maxPoints">
              <el-input-number v-model="form.maxPoints" :min="0" :step="100" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="折扣比例" prop="discount">
              <el-input-number v-model="form.discount" :min="1" :max="100" :step="1" style="width: 100%" />
              <span style="margin-left: 5px; color: #909399;">如95表示95折</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="免运费" prop="freeShipping">
              <el-radio-group v-model="form.freeShipping">
                <el-radio :label="1">是</el-radio>
                <el-radio :label="0">否</el-radio>
              </el-radio-group>
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
          <el-col :span="12">
            <el-form-item label="排序" prop="sort">
              <el-input-number v-model="form.sort" :min="0" :step="1" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="升级条件">
              <el-input v-model="form.upgradeCondition" type="textarea" placeholder="请输入升级条件描述" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="权益描述">
              <el-input v-model="form.benefits" type="textarea" placeholder="请输入权益描述" :rows="3" />
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
import { listMemberLevel, getMemberLevel, delMemberLevel, addMemberLevel, updateMemberLevel, changeStatus } from "@/api/shop/memberLevel";

export default {
  name: "MemberLevel",
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
      // 会员等级表格数据
      memberLevelList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        levelName: null,
        status: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        levelName: [
          { required: true, message: "等级名称不能为空", trigger: "blur" }
        ],
        minPoints: [
          { required: true, message: "最低积分不能为空", trigger: "blur" }
        ],
        maxPoints: [
          { required: true, message: "最高积分不能为空", trigger: "blur" }
        ],
        discount: [
          { required: true, message: "折扣比例不能为空", trigger: "blur" }
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
    /** 查询会员等级列表 */
    getList() {
      this.loading = true;
      listMemberLevel(this.queryParams).then(response => {
        this.memberLevelList = response.rows;
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
        levelId: null,
        levelName: null,
        levelIcon: null,
        minPoints: 0,
        maxPoints: 0,
        discount: 100,
        freeShipping: 0,
        upgradeCondition: null,
        benefits: null,
        status: "0",
        sort: 0,
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
      this.ids = selection.map(item => item.levelId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加会员等级";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const levelId = row.levelId || this.ids[0];
      getMemberLevel(levelId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改会员等级";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.levelId != null) {
            updateMemberLevel(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addMemberLevel(this.form).then(response => {
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
      const levelIds = row.levelId || this.ids;
      this.$modal.confirm('是否确认删除会员等级编号为"' + levelIds + '"的数据项？').then(function() {
        return delMemberLevel(levelIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('shop/memberLevel/export', {
        ...this.queryParams
      }, `memberLevel_${new Date().getTime()}.xlsx`)
    },
    /** 状态修改 */
    handleStatusChange(row) {
      let text = row.status === "0" ? "启用" : "停用";
      this.$modal.confirm('确认要"' + text + '""' + row.levelName + '"会员等级吗？').then(function() {
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
