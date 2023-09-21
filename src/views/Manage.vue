
<template>

    <el-container style="height: 100vh; border: 1px solid #eee">
      <el-aside width="sideWidth+'px'" style="background-color: rgb(238, 241, 246); ">
        <Aside :iscollapse="iscollapse":logo-tex-show="logoTexShow"/>
      </el-aside>

      <el-container>
        <el-header style="border-bottom: 1px solid #ccc;">
         <Header :collapse-btn-class="collapseBtnClass" :collapse="collapse"/>
        </el-header>

        <el-main>
            <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>



</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'
import Aside from "@/components/Aside";
import Header from "@/components/Header";
export default {
  name: 'HomeView',
  components: {
    Header,
    Aside,
    HelloWorld
  },


  data(){
  return{

    collapseBtnClass:'el-icon-s-fold',
    iscollapse:false,
    sideWidth:200,
    logoTexShow:200,

  }
  },




  comments: {
    Aside,
    Header
  },
  methods:{

    collapse(){//收缩函数
    this.iscollapse=!this.iscollapse
    if(this.iscollapse){
      this.sideWidth=100
      this.collapseBtnClass='el-icon-s-unfold'
      this.logoTexShow=false
    }else{
      this.sideWidth=200
      this.collapseBtnClass='el-icon-s-fold'
      this.logoTexShow=true
    }

    },


  }
}
</script>
