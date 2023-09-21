<template>
    <div>

        <div id="chart" style="height: 400px" ref="chart"></div>
        <div  id="chart2" style="height: 400px" ref="chart2"></div>

        <div>
            <i style="margin-left: 530px;font-size:25px">队员做题数排行榜</i>
        </div>
        <div id="chart3" style="height: 400px" ref="chart3"></div>
    </div>
</template>

<script>
    export default {
        name: "Home",

        mounted() {
            this.initCharts();
        },
        methods:{
            // 柱状图
            initCharts() {
                // 初始化echarts
                this.chart = this.$echarts.init(this.$refs.chart)
                // 设置图表option(配置项)绘制图表
                // 绘制图表
                this.chart.setOption({

                    title: {
                        text: '竞赛队员年级分布',
                        left: 'center'
                    },
                    tooltip: {
                        trigger: 'item'
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left'
                    },
                    series: [
                        {
                            name: 'Access From',
                            type: 'pie',
                            radius: '50%',
                            data: [
                                { value: 5, name: '2016级' },
                                { value: 9, name: '2017级' },
                                { value: 13, name: '2018级' },
                                { value: 17, name: '2019级' },
                                { value: 10, name: '2020级' }
                            ],
                            emphasis: {
                                itemStyle: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }
                    ]
                })




                // 初始化echarts
                this.chart2 = this.$echarts.init(this.$refs.chart2)
                // 设置图表option(配置项)绘制图表
                // 绘制图表
                this.chart2.setOption({

                    title: {
                        text: '竞赛队员所获得奖项分布',
                        left: 'center'
                    },
                    tooltip: {
                        trigger: 'item'
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left'
                    },
                    series: [
                        {
                            name: 'Access From',
                            type: 'pie',
                            radius: '50%',
                            data: [
                                { value: 30, name: '国家级奖项' },
                                { value: 77, name: '省级奖项' },
                                { value: 138, name: '校级奖项' },

                            ],
                            emphasis: {
                                itemStyle: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }
                    ]
                })




                // 初始化echarts
                this.chart3 = this.$echarts.init(this.$refs.chart3)
                // 设置图表option(配置项)绘制图表
                // 绘制图表
                this.chart3.setOption({

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
                    xAxis: [
                        {
                            type: 'category',
                            data: ['莫德雷德', '奥博隆', '兰斯洛特', '摩根', '崔斯坦', '梅林', '贝迪威尔'],
                            axisTick: {
                                alignWithLabel: true
                            }
                        }
                    ],
                    yAxis: [
                        {
                            type: 'value'
                        }
                    ],
                    series: [
                        {
                            name: '做题数',
                            type: 'bar',
                            barWidth: '60%',
                            data: [673, 534, 522, 497, 427, 365, 348]
                        }
                    ]

                })




            },

        }
    }
</script>

<style scoped>

</style>