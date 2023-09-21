<template>
    <div>
        <div style="margin: 10px 0">
            <el-input style="width: 200px;margin-left: 5px" placeholder="请输入姓名" suffix-icon="el-icon-search" v-model="name">
            </el-input>

            <el-button style="margin-left: 5px" type="pimary"@click="load">搜索</el-button>
        </div>
        <div style="margin: 10px 0">
            <el-button type="primary"@click="handleAdd">新增<i class="el-icon-circle-plus-outline"></i></el-button>
            <el-upload action="https://jsonplaceholder.typicode.com/posts/" :show-file-list="false" accept="xlsx" style="display: inline-block">
                <el-button type="primary"style="margin-left: 10px">导入<i class="el-icon-download"></i></el-button>
            </el-upload>
            <el-button type="primary" @click="exp" style="margin-left: 10px">导出<i class="el-icon-upload2"></i></el-button>

            <el-button type="primary" @click="cimsInputClick">查看各大OJ做题数</el-button>

        </div>
        <el-table :data="tableData" border stripe>
            <el-table-column prop="id" label="ID" width="40"></el-table-column>
            <el-table-column prop="name" label="姓名" width="140"></el-table-column>
            <el-table-column prop="dp" label="动态规划" width="110"></el-table-column>
            <el-table-column prop="greed" label="贪心" width="110"></el-table-column>
            <el-table-column prop="search" label="搜索" width="110"></el-table-column>
            <el-table-column prop="graph" label="图论" width="110"></el-table-column>
            <el-table-column prop="math" label="数论 " width="110"></el-table-column>
            <el-table-column prop="simulate" label="模拟" width="110"></el-table-column>
            <el-table-column prop="all" label="总和" width="110"></el-table-column>

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
                    <el-button style="margin-left: 8px" type="primary" @click="view(scope.row)" slot="reference">分析</el-button>

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

        <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%" >
            <el-form label-width="80px" size="small">
                <el-form-item label="姓名">
                    <el-select @change="getOptions" v-model="form.name" filterable placeholder="请选择">
                        <el-option
                                v-for="item in options"
                                :key="item"
                                :label="item"
                                :value="item">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="动态规划">
                    <el-input style="width: 205px" v-model="form.dp" autocomplete="off" type="number"></el-input>
                </el-form-item>
                <el-form-item label="贪心">
                    <el-input style="width: 205px" v-model="form.greed" autocomplete="off" type="number"></el-input>
                </el-form-item>
                <el-form-item label="搜索">
                    <el-input style="width: 205px" v-model="form.search" autocomplete="off" type="number"></el-input>
                </el-form-item>
                <el-form-item label="图论">
                    <el-input style="width: 205px" v-model="form.graph" autocomplete="off" type="number"></el-input>
                </el-form-item>
                <el-form-item label="数论">
                    <el-input style="width: 205px" v-model="form.math" autocomplete="off" type="number"></el-input>
                </el-form-item>
                <el-form-item label="模拟">
                    <el-input style="width: 205px" v-model="form.simulate" autocomplete="off"  type="number"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="save">保存</el-button>
            </div>
        </el-dialog>

        <el-dialog  @open="open()" title="分析结果" :visible.sync="viewDialogVis" width="60%" >

            <div>
               <div id="chart" style="height: 300px" ref="chart"></div>
            </div>
            <el-descriptions title="参考建议">
            <el-descriptions-item label="综合得分">{{this.score}}</el-descriptions-item>
                <el-descriptions-item label="分析">{{this.predict}}</el-descriptions-item>
            </el-descriptions>
        </el-dialog>




    </div>
</template>
<script>
    export default {
        name: "Problem",
        data(){
            return{
                tableData:[],
                dialogFormVisible:false,
                viewDialogVis:false,
                form:{},
                total:0,
                options:[],
                pageNum:1,
                pageSize:5,
                predict:"",
                score:"",
                v:[],
                name:"",
                chart: null, // echarts图表实例
            }
    },created() {
            //请求分页数据
            this.load();
            this.getOptions();
        },
        mounted() {
            this.initCharts();
        },
        methods:{

            load(){
                this.request.get("http://localhost:9090/problem/page",{
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
            view(row) {
                this.v=[]
                this.v.push(row.dp)
                this.v.push(row.greed)
                this.v.push(row.search)
                this.v.push(row.graph)
                this.v.push(row.math)
                this.v.push(row.simulate)
                this.viewDialogVis = true
                this.score=row.dp*0.181+row.greed*0.146+row.search*0.164+row.graph*0.208+row.math*0.234+row.simulate*0.065
                console.log( this.v)
                if(row.dp +row.math+row.graph>row.search+row.simulate+row.greed){
                    this.predict="该队员在进阶算法上表现不错，但需要多练习基础算法"
                }else{
                    this.predict="该队员基础算法掌握得不错，但需要多加强练习进阶算法"
                }
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
            handleAdd(){
                this.dialogFormVisible=true
                this.form={}
            },
            handleUpdate(row){
                this.dialogFormVisible=true
                this.form=row
            },
            save(){
                this.request.post("http://localhost:9090/problem",this.form).then(res => {
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
                this.request.delete("http://localhost:9090/problem/"+id).then(res => {
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
            cimsInputClick () {
                window.location.href = 'https://ojhunt.com/statistics'
            },

            // 柱状图
            initCharts() {
                // 初始化echarts
                this.chart = this.$echarts.init(this.$refs.chart)
                // 设置图表option(配置项)绘制图表
                // 绘制图表
                this.chart.setOption({

                    title: {
                        text: '队员做题偏好'
                    },
                    radar: {
                        // shape: 'circle',
                        indicator: [
                            { name: '动态规划', max: 150 },
                            { name: '贪心算法', max: 150 },
                            { name: '搜索算法', max: 150 },
                            { name: '图论', max: 150 },
                            { name: '数论', max: 150 },
                            { name: '模拟', max: 150 }
                        ]
                    },
                    series: [
                        {
                            type: 'radar',
                            data: [
                                {
                                    value: this.v,

                                },

                            ]
                        }
                    ]
                })

            }, open(){
                this.$nextTick(() => {
                    //  执行echarts方法
                    this.initCharts()
                })
            },

        }
    }
</script>

<style scoped>

</style>