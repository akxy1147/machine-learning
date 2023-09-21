<template>
    <el-menu :default-openeds="['1', '3']" style="min-height:100%;overflow-x: hidden"
             background-color="rgb(48, 65, 86)"
             text-color="#fff"
             active-text-color="#ffd04b"
             :collapse-transition="false"
             :collapse="iscollapse"
             router
    >
        <div style="height:60px;line-height: 60px;text-align: center">

            <img src="../assets/logo.png" alt="" style="width: 20px;position:relative;top:5px;margin-left: 5px">
            <b style="color: aliceblue" v-show="logoTexShow">队伍管理系统</b>

        </div>


        <el-menu-item index="/"><i style="margin-right: 20px" class="el-icon-s-home"></i>主页</el-menu-item>

        <el-submenu index="2">
            <template slot="title"><i class="el-icon-user-solid"></i>
                <span>队员管理</span>
            </template>
                <el-menu-item index="/user">队员个人信息</el-menu-item>
                <el-menu-item index="/problem">队员做题信息</el-menu-item>
                <el-menu-item index="/award">队员获奖信息</el-menu-item>
        </el-submenu>

        <el-menu-item index="/teacher"><i style="margin-right: 20px" class="el-icon-s-custom"></i>带队老师信息管理</el-menu-item>

        <el-menu-item index="/team"><i style="margin-right: 20px" class="el-icon-s-flag"></i>队伍管理</el-menu-item>
        <el-menu-item index="/formwork"><i style="margin-right: 20px" class="el-icon-collection"></i>模板管理</el-menu-item>


    </el-menu>
</template>

<script>
    export default {
        name: "Aside",

        props:{
            iscollapse:Boolean,
            logoTexShow:Boolean,
        }
    }
</script>

<style scoped>

</style>