<template>
    <div>
        <div style="margin: 10px 0">
            <el-input style="width: 200px;margin-left: 5px" placeholder="输入模板类型" suffix-icon="el-icon-search" v-model="name">
            </el-input>

            <el-button style="margin-left: 5px" type="pimary"@click="load">搜索</el-button>
        </div>
        <div style="margin: 10px 0">
            <el-button type="primary"@click="handleAdd">新增<i class="el-icon-circle-plus-outline"></i></el-button>
            <el-upload action="https://jsonplaceholder.typicode.com/posts/" :show-file-list="false" accept="xlsx" style="display: inline-block">
                <el-button type="primary"style="margin-left: 10px">导入<i class="el-icon-download"></i></el-button>
            </el-upload>
            <el-button type="primary" @click="exp" style="margin-left: 10px">导出<i class="el-icon-upload2"></i></el-button>
        </div>
        <el-table :data="tableData" border stripe>
            <el-table-column prop="id" label="ID" width="40"></el-table-column>
            <el-table-column prop="name" label="模板标题" width="140"></el-table-column>
            <el-table-column prop="content" label="模板内容" >
                <template slot-scope="scope">
                    <el-button @click="view(scope.row.content)" type="primary">查看内容</el-button>
                </template>
            </el-table-column>

            <el-table-column prop="do" label="操作">

                <template slot-scope="scope" >
                    <el-button type="success" @click="handleUpdate(scope.row)">编辑</el-button>
                    <el-popconfirm
                            style="margin-left:8px "
                            confirm-button-text='好的'
                            cancel-button-text='不用了'
                            icon="el-icon-info"
                            icon-color="red"
                            title="这是一段内容确定删除吗？"
                            @confirm="del(scope.row.id)"
                    >
                        <el-button type="danger"slot="reference">删除</el-button>
                    </el-popconfirm>
                </template>


            </el-table-column>
        </el-table>
        <div style="padding: 10px 0">
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="pageNum"
                    :page-sizes="[5, 10, 15, 20]"
                    :page-size="pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total">
            </el-pagination>



        </div>

        <el-dialog  title="模板内容" :visible.sync="dialogFormVisible" width="60%" >
            <el-form label-width="80px" size="small">
                <el-form-item  label="模板标题">
                    <el-input v-model="form.name" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="模板内容">
                    <mavon-editor ref="md" v-model="form.content" :ishljs="true"/>
                </el-form-item>



            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="save">保存</el-button>
            </div>
        </el-dialog>


        <el-dialog  title="模板内容" :visible.sync="viewDialogVis" width="60%" >
            <el-card>
                <div>

                    <mavon-editor
                            class="md"
                            :value="content"
                            :subfield="false"
                            :defaultOpen="'preview'"
                            :toolbarsFlag="false"
                            :editable="false"
                            :scrollStyle="true"
                            :ishljs="true"
                    />
                    <div class="block" style="margin-left: 350px">
                        <el-rate v-model="value1"></el-rate>
                    </div>
                </div>

            </el-card>

        </el-dialog>


    </div>
</template>
<script>
    export default {
        name: "Formwork",
        data(){
            return{
                value1: null,
                tableData:[],
                dialogFormVisible:false,
                form:{},
                total:0,
                pageNum:1,
                pageSize:5,
                name:"",
                content:'',
                viewDialogVis:false,
                chart: null, // echarts图表实例
            }
        },created() {
            //请求分页数据
            this.load();


        },

        methods:{


            load(){
                this.request.get("http://localhost:9090/formwork/page",{
                    params:{
                        pageNum: this.pageNum,
                        pageSize:this.pageSize,
                        name:this.name,
                    }
                }).then(res=>{
                    console.log(res)
                    this.tableData = res.data
                    this.total=res.total

                })

            },
            view(content) {
                this.content = content
                this.viewDialogVis = true
            },
            handleAdd(){
                this.dialogFormVisible=true
                this.form={}
            },
            handleUpdate(row){
                this.dialogFormVisible=true
                this.form=row
            },
            save(){
                this.request.post("http://localhost:9090/formwork",this.form).then(res => {
                    if (res) {
                        this.$message.success("保存成功")
                        this.dialogFormVisible = false
                        this.load()
                    } else {
                        this.$message.error("保存失败")
                    }
                })
            },
            del(id){
                this.request.delete("http://localhost:9090/formwork/"+id).then(res => {
                    if (res) {
                        this.$message.success("删除成功")
                        this.dialogFormVisible = false
                        this.load()
                    } else {
                        this.$message.error("删除失败")
                    }
                })
            },

            handleSizeChange(pageSize){
                this.pageSize=pageSize
                this.load();
            },
            handleCurrentChange(pageNum){
                this.pageNum=pageNum
                this.load();
            },
            exp(){
                window.open("http://localhost:9090/user/export")
            },




            }
    }
</script>

<style scoped>

</style>