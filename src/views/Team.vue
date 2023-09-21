<template>
    <div>
        <div style="margin: 10px 0">
            <el-input style="width: 200px;margin-left: 5px" placeholder="请输入队名" suffix-icon="el-icon-search" v-model="teamname">
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
            <el-table-column prop="teamname" label="队名" width="140"></el-table-column>
            <el-table-column prop="stu1" label="队员1" width="200"></el-table-column>
            <el-table-column prop="stu2" label="队员2" width="200"></el-table-column>
            <el-table-column prop="stu3" label="队员3" width="200"></el-table-column>
            <el-table-column prop="coach" label="带队老师" ></el-table-column>
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
                        <el-button style="margin-left: 8px" type="primary" @click="view(scope.row)" slot="reference">赛绩</el-button>
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

        <el-dialog title="队伍配置" :visible.sync="dialogFormVisible" width="30%" >
            <el-form label-width="80px" size="small">
                <el-form-item label="队名">
                    <el-input style="width: 205px" v-model="form.teamname" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="队员1">
                    <el-select @change="getOptions" v-model="form.stu1" filterable placeholder="请选择">
                        <el-option
                                v-for="item in options"
                                :key="item"
                                :label="item"
                                :value="item">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="队员2">
                    <el-select @change="getOptions" v-model="form.stu2" filterable placeholder="请选择">
                        <el-option
                                v-for="item in options"
                                :key="item"
                                :value="item">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="队员3">
                    <el-select @change="getOptions" v-model="form.stu3" filterable placeholder="请选择">
                        <el-option
                                v-for="item in options"
                                :key="item"
                                :value="item">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="带队老师">
                    <el-select @change=" getOptionsteacher" v-model="form.coach" filterable placeholder="请选择">
                        <el-option
                                v-for="item in optionsteacher"
                                :key="item"
                                :value="item">
                        </el-option>
                    </el-select>
                </el-form-item>

            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="save">保存</el-button>
            </div>
        </el-dialog>

        <el-dialog  @open="open()" title="队伍赛绩" :visible.sync="viewDialogVis" width="60%" >

            <el-collapse v-model="activeNames" @change="handleChange">
                <el-collapse-item title="ICPC国际大学生程序设计竞赛" name="1">
                    <div>2019-11: 队伍获得ICPC国际大学生程序设计竞赛珠海赛区铜奖</div>
                    <div>2020-7: 队伍获得ICPC国际大学生程序设计竞赛澳门赛区银奖</div>
                </el-collapse-item>
                <el-collapse-item title="CCPC中国大学生程序设计竞赛" name="2">
                    <div>2020-4:队伍获得CCPC国际大学生程序设计竞赛广州赛区银奖</div>
                    <div>2021-9: 队伍获得CCPC国际大学生程序设计竞赛昆明赛区银奖</div>
                </el-collapse-item>
                <el-collapse-item title="GXCPC广西大学生程序设计竞赛" name="3">
                    <div>2019-6: 队伍获得GXCPC广西大学生程序设计竞赛银奖</div>
                    <div>2020-6: 队伍获得GXCPC广西大学生程序设计竞赛金奖</div>
                    <div>2021-6: 队伍获得GXCPC广西大学生程序设计竞赛金奖</div>
                </el-collapse-item>
                <el-collapse-item title="程序设计天梯赛" name="4">
                    <div>2022-5: 队伍获得程序设计天梯赛高校一等奖</div>
                </el-collapse-item>
            </el-collapse>
        </el-dialog>




    </div>
</template>
<script>
    export default {
        name: "Team",
        data(){
            return{
                activeNames: [],
                tableData:[],
                dialogFormVisible:false,
                form:{},
                total:0,
                pageNum:1,
                pageSize:5,
                teamname:"",
                chart: null, // echarts图表实例
                options:[],
                viewDialogVis:false,
                optionsteacher:[],

            }
        },created() {
            //请求分页数据
            this.load();
            this.getOptions();
            this.getOptionsteacher()

        },


        methods:{
            handleChange(val) {
                console.log(val);
            },

            load(){
                this.request.get("http://localhost:9090/team/page",{
                    params:{
                        pageNum: this.pageNum,
                        pageSize:this.pageSize,
                        teamname:this.teamname,
                    }
                }).then(res=>{
                    console.log(res)
                    this.tableData = res.data
                    this.total=res.total

                })

            },

            //获取下拉框中内容
            getOptions(){

                this.request.get("http://localhost:9090/team/stu")
                    .then(res =>
                    {
                        //请求返回的数据
                        //赋值
                        this.options = res
                        console.log(res)
                    }).catch((error) =>{
                    console.log(error);
                });
            },
            getOptionsteacher(){

                this.request.get("http://localhost:9090/team/teacher")
                    .then(res =>
                    {
                        //请求返回的数据
                        //赋值
                        this.optionsteacher = res
                        console.log(res)
                    }).catch((error) =>{
                    console.log(error);
                });
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
                this.request.post("http://localhost:9090/team",this.form).then(res => {
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
                this.request.delete("http://localhost:9090/team/"+id).then(res => {
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
            open(){
                this.$nextTick(() => {
                    //  执行echarts方法
                    this.initCharts()
                })
            },
            view(row) {

                this.viewDialogVis = true

            },

        },


    }
</script>

<style scoped>

</style>