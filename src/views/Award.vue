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
        </div>
        <el-table :data="tableData" border stripe>
            <el-table-column prop="id" label="ID" width="40"></el-table-column>
            <el-table-column prop="name" label="姓名" width="140"></el-table-column>
            <el-table-column prop="national" label="国家级奖项"width="200" ></el-table-column>
            <el-table-column prop="province" label="省级奖项" width="200"></el-table-column>
            <el-table-column prop="school" label="校级奖项" width="200"></el-table-column>
            <el-table-column prop="all" label="总和" ></el-table-column>

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
                        <el-button style="margin-left: 8px" type="primary" @click="view(scope.row)" slot="reference">详情</el-button>
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
                <el-form-item label="国家级奖项">
                    <el-input style="width: 205px" v-model="form.national" autocomplete="off" type="number"></el-input>
                </el-form-item>
                <el-form-item label="省级奖项">
                    <el-input style="width: 205px" v-model="form.province" autocomplete="off" type="number"></el-input>
                </el-form-item>
                <el-form-item label="校级奖项">
                    <el-input style="width: 205px" v-model="form.school" autocomplete="off" type="number"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="save">保存</el-button>
            </div>
        </el-dialog>



        <el-dialog  @open="open()" title="获奖详情" :visible.sync="viewDialogVis" width="60%" >

            <div>
                <div id="chart" style="height: 500px" ref="chart"></div>
            </div>

        </el-dialog>




    </div>
</template>
<script>
    export default {
        name: "Award",
        data(){
            return{
                tableData:[],
                dialogFormVisible:false,
                form:{},
                total:0,
                pageNum:1,
                pageSize:5,
                viewDialogVis:false,
                name:"",
                chart: null, // echarts图表实例
            }
        },created() {
            //请求分页数据
            this.load();
            this.getOptions();

        },
        methods:{

            load(){
                this.request.get("http://localhost:9090/award/page",{
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
                this.request.post("http://localhost:9090/award",this.form).then(res => {
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
                this.request.delete("http://localhost:9090/award/"+id).then(res => {
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
            view(row) {

                this.viewDialogVis = true

            },

            // 柱状图
            initCharts() {
                // 初始化echarts
                this.chart = this.$echarts.init(this.$refs.chart)
                // 设置图表option(配置项)绘制图表
                // 绘制图表
                this.chart.setOption({


                        title: {
                            text: '获奖年度分布'
                        },
                        tooltip: {
                            trigger: 'axis'
                        },
                        legend: {
                            data: [ '国家级奖项', '省级奖项','校级奖项']
                        },
                        grid: {
                            left: '3%',
                            right: '4%',
                            bottom: '3%',
                            containLabel: true
                        },
                        toolbox: {
                            feature: {
                                saveAsImage: {}
                            }
                        },
                        xAxis: {
                            type: 'category',
                            boundaryGap: false,
                            data: ['2019', '2020', '2021', '2022', '2023']
                        },
                        yAxis: {
                            type: 'value'
                        },
                        series: [
                            {
                                name: '国家级奖项',
                                type: 'line',
                                stack: 'Total',
                                data: [1, 2, 4, 3, 1]
                            },
                            {
                                name: '省级奖项',
                                type: 'line',
                                stack: 'Total',
                                data: [4, 7, 12, 15, 5]
                            },
                            {
                                name: '校级奖项',
                                type: 'line',
                                stack: 'Total',
                                data: [7, 13, 24, 20, 15]
                            },

                        ]

                })

            },
            open(){
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