<template>
	<!-- 背景图片 -->
	<image src="/static/login.jpg" mode="" class="backImg"></image>
	
	<!-- 滚动显示区域 -->
	<scroll-view>
		
		<uni-row style="margin-top:16%;text-align: center;">
			<text style="color: white;font-size: 46px;font-weight: 700;">demo</text>
		</uni-row>
		
		
		<!-- 登录表单 -->
		<uni-forms action="#" class="formSet" label-align="center">
			
			<!-- 标题栏 -->
			<view style="margin-top:5%;">
				<text class="fontSet">登录</text>
			</view>
			
			<!-- 账号输入框 -->
			<uni-forms-item label="账号" required style="margin-top:10px;">
				<uni-easyinput 
				type="text"
				prefixIcon="person" 
				:placeholder="accountInfo"
				:placeholderStyle="accountInfoSty"
				:styles="accountSty"
				trim="all"
				v-model="inpAccount"
				id="Account"
				@blur="isAccount()"
					/>
			</uni-forms-item>
			
			<!-- 密码输入框 -->
			<uni-forms-item label="密码" required>
				<uni-easyinput 
				:clearable="false"
				type="password" 
				prefixIcon="locked"
				:placeholder="passwordInfo"
				:placeholderStyle="passwordInfoSty"
				:styles="passwordSty"
				trim="all"
				v-model="inpPass"
				@focus="activePas()"
				/>
			</uni-forms-item>
			
			<!-- 登录按钮 -->
			<uni-forms-item>
				<button 
				type="primary" 
				class="LoginBtn"
				@click="jumpTo()"
				>
				登录
				</button>
			</uni-forms-item>
				
			<view class="footer-links">
			        <text class="link-text" @click="navigateToForget">忘记密码</text>
			        <text class="link-text" @click="navigateToRegister">注册账号</text>
			</view>	
		</uni-forms>
		
		
	</scroll-view>
	<!-- 提示弹出框 -->
	<uni-popup ref="errRef1" type="message">
		<uni-popup-message type="error" message="用户不存在!登陆失败！" :duration="1500"></uni-popup-message>
	</uni-popup>
	<uni-popup ref="errRef2" type="message">
		<uni-popup-message type="error" message="密码错误!登录失败！" :duration="1500"></uni-popup-message>
	</uni-popup>
</template>

<script setup>
	import {ref,reactive,computed} from 'vue'
	import axios from 'axios'
	import uniForms from '@dcloudio/uni-ui/lib/uni-forms/uni-forms.vue'
	import uniFormsItem from '@dcloudio/uni-ui/lib/uni-forms-item/uni-forms-item.vue'
	import uniEasyinput from '@dcloudio/uni-ui/lib/uni-easyinput/uni-easyinput.vue'
	import uniIcons from '@dcloudio/uni-ui/lib/uni-icons/uni-icons.vue'
	//定义弹出框的ref
	var errRef1 = ref(null)
	var errRef2 = ref(null)
	//账号提示信息,样式,输入框样式:accountInfo\accountInfoSty\accountSty
	var accountInfo = ref("请输入邮箱/手机号")
	var accountInfoSty = ref('')
	var accountSty = reactive({
		backgroundColor:'white'
	})
	//密码提示信息\样式,输入框样式:
	var passwordInfo = ref("请输入密码")
	var passwordInfoSty = ref('')
	var passwordSty = reactive({
		backgroundColor:'white'
	})
	//输入账号:inpAccount
	var inpAccount = ref('')
	//输入密码:inpPass
	var inpPass = ref('')
	//路径:利用计算属性得到
	var url = computed(()=>{
		return "http://localhost:8080/userLogin?account="+inpAccount.value+"&password="+inpPass.value
	})
	
	
	//账号输入验证
	function isAccount(){
		if(inpAccount.value==''){
			accountInfo.value = "请输入邮箱/手机号"
			accountInfoSty = ''
			accountSty=''
			return;
		}
		var pattern = /^(1[3-9]\d{9}|[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,})$/;
		if(!pattern.test(inpAccount.value)){
			inpAccount.value=''
			accountInfo.value = "邮箱或手机号格式错误!"
			accountInfoSty = "color:red;"
			accountSty={borderColor:'red'}
		}
		else{
			accountInfo.value = ''
			accountInfoSty = ''
			accountSty=''
		}
	}
	
	// 登录
	function jumpTo(){
		if(inpPass.value=='' || inpAccount.value==''){
			if(inpAccount.value==''){
				inpAccount.value=''
				accountInfo.value = "邮箱或手机号不能为空!"
				accountInfoSty = "color:red;"
				accountSty={borderColor:'red'}
			}
			if(inpPass.value==''){
			inpPass.value=''
			passwordInfo.value = "密码不能为空!"
			passwordInfoSty = "color:red;"
			passwordSty={borderColor:'red'}
			}
			return;
		}
		axios.get(url.value).then((res)=>{
			console.log("sss:"+res.data)
			if(res.data=="NOACCOUNT"){
				errRef1.value?.open()
				return;
			}
			if(res.data=="PASSWORDFALSE"){
				errRef2.value?.open()
				return;
			}
			else{
				uni.navigateTo({
					url:"/pages/primaryPage/primaryPage"
			})
			}
		})
	}
	
	//获取焦点触发方法:主要还是对样式进行调整
	function activePas(){
		passwordInfo.value = "请输入密码!"
		passwordInfoSty = ''
		passwordSty= ''
	}
	
	
	//注册页面跳转
	function navigateToRegister(){
		uni.navigateTo({
			url:"/pages/create/create"
		})
	}
	
</script>

<style lang="scss">
	* {
	  margin: 0;
	  padding: 0;
	  box-sizing: border-box;
	
	}
	.footer-links{
		margin-left: 24px;
		display: flex;
		justify-content:space-between;
		flex-direction:row;
	}
	.link-text{
		color: #606266;
		font-size: 16px;
		margin:0 5px;
	}
	.link-text:hover{
		color: black;
	}
	.LoginBtn{
		width: 300px;
		height: 42px;
		margin-left: 25px;
		
	}
	.formSet{
		display: flex;
		margin: 32% auto;
		width: 350px;
		height: 275px;
		border-radius: 10px;
		background-color: rgba(255,255,255,0.8);
		z-index: 200;
	}
	
	.fontSet{
		color: black;
		font-family: "方正粗黑宋简体";
		font-size: 28px;
		font-weight: 200;
		margin-left: 147px;
	}
	.backImg{
		width: 100vw;
		height: 100vh;
		position: fixed;
		
	}

</style>
