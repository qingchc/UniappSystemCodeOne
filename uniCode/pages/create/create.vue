<template>
	<view>
		<uni-card :is-shadow="false" is-full>
			<text class="uni-h6">请输入你的联系方式和邮箱,便于你找回密码与登录</text>
		</uni-card>
		<uni-section title="注册信息" type="line">
			<view class="formArea">
				<!-- 表单区域 -->
				<uni-forms  
				labelWidth="80px"
				>
					<uni-forms-item label="邮箱" required>
						<uni-easyinput 
						v-model="mail" 
						trim="all"
						:placeholder="mailInfo"
						:placeholderStyle="mailInfoSty"
						:styles="mailSty"
						@blur="isMail()"
						/>
					</uni-forms-item>
					<uni-forms-item label="联系方式" required>
						<uni-easyinput 
						v-model="tel" 
						trim="all"
						:placeholder="telInfo"
						:placeholderStyle="telInfoSty"
						:styles="telSty"
						@blur="isTel()"
						/>
					</uni-forms-item>
					<uni-forms-item label="密码" required>
						<uni-easyinput 
						type="password" 
						v-model="passwordOne" 
						placeholder="请输入密码" 
						trim="all"
						@blur="cancelTip()"
						/>
						<text v-show="show3" style="color: red;margin-left:2px;">密码不能为空!</text>
					</uni-forms-item>
					
					<uni-forms-item label="二次密码" required>
						<uni-easyinput 
						type="password" 
						v-model="passwordSeconde" 
					    trim="all"
						:placeholder="passInfo"
						:styles="passSty"
						@blur="passEqu()"
						/>
						<text v-show="show" style="color: red;margin-left:2px;">两次密码输入不一致!</text>
						<text v-show="show2" style="color: red;margin-left:2px;">密码不能为空!</text>
					</uni-forms-item>
					
					<!-- 底部按钮 -->
					<view class="button-group">
						<button type="default" class="btn" @click="back()">返回</button>
						<button type="primary" class="btn" @click="regist()">注册</button>
					</view>
				</uni-forms>
			</view>	
		</uni-section>
	</view>
	<!-- 版权展示 -->
	<view class="footer">
		<text class="text">© 2025 泉州职业技术大学.</text>
	</view>
	<uni-popup ref="succeseRef" type="message">
		<uni-popup-message type="success" message="注册成功!3秒后将回到登录界面" :duration="3000"></uni-popup-message>
	</uni-popup>
</template>

<script setup>
	import {ref,reactive,computed} from 'vue'
	import axios from 'axios'
	//输入框数据
	var mail = ref('')
	var tel = ref('')
	var passwordOne = ref('')
	var passwordSeconde = ref('')
	var succeseRef = ref(null)
	//邮箱提示内容,提示内容样式,样式
	var mailInfoSty = ref('')
	var mailInfo = ref('请输入合法邮箱')
	var mailSty = reactive({
		backgroundColor:'white'
	})
	//联系方式提示内容,提示内容样式,样式
	var telInfoSty = ref('')
	var telInfo = ref('请输入11位电话号码')
	var telSty = reactive({
		backgroundColor:'white'
	})
	//二次密码框样式
	var passInfo = ref('请保证与第一遍输入一致')
	var passSty = reactive({
		backgroundColor:'white'
	})
	//错误信息展示标记
	var show = ref(false)
	var show2 = ref(false)
	var show3 = ref(false)
	
	
	
	
	//邮箱失去焦点方法
	function isMail(){
		if(mail.value==''){
			mailInfo.value = "请输入合法邮箱"
			mailInfoSty = ''
			mailSty={borderColor:'#e5e5e5'}
			mail.value=''
			return;
		}
		var mailurl = "http://localhost:8080/mailExis?mail="+mail.value
		axios.get(mailurl).then((res)=>{
			if(res.data=="YES"){
				mailInfo.value = "邮箱已存在!"
				mailInfoSty = "color:#f3a73f;"
				mailSty={borderColor:'#f3a73f'}
				mail.value=''
				return;
			}
		})
		var pattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
		if(!pattern.test(mail.value)){
			mailInfo.value = "邮箱输入非法!"
			mailInfoSty = "color:red;"
			mailSty={borderColor:'red'}
			mail.value=''
			return;
		}else{
			mailInfo.value = "请输入合法邮箱"
			mailInfoSty = ''
			mailSty={borderColor:'#e5e5e5'}
		}
	}
	//联系方式失去焦点方法
	function isTel(){
	//返回
		if(tel.value==''){
			telInfo.value = "请输入11位电话号码"
			telInfoSty = ''
			telSty={borderColor:'#e5e5e5'}
			return;
		}
		var telurl = "http://localhost:8080/telExis?tel="+tel.value
		axios.get(telurl).then((res)=>{
			if(res.data=="YES"){
				telInfo.value = "手机号已存在!"
				telInfoSty = "color:#f3a73f;"
				telSty={borderColor:'#f3a73f'}
				tel.value=''
				return;
			}
		})
		var pattern = /^1[3-9]\d{9}$/;
		if(!pattern.test(tel.value)){
			telInfo.value = "联系方式输入非法!"
			telInfoSty = "color:red;"
			telSty={borderColor:'red'}
			tel.value=''
			return;
		}else{
			telInfo.value = "请输入11位电话号码"
			telInfoSty = ''
			telSty={borderColor:'#e5e5e5'}
		}
	}
	//返回
	function back(){
		uni.navigateTo({
			url:"/pages/index/index"
		})
	}
	//密码判断
	function passEqu(){
		if(passwordOne.value != passwordSeconde.value){
			show.value=true
			passSty = {borderColor:'red'}
		}else{
			show.value=false
			show2.value=false
			passSty = {borderColor:'#e5e5e5'}
			passInfo.value = "请保证与第一遍输入一致"
			
		}
	}
	function cancelTip(){
		show3.value=false
	}
	//注册
	function regist(){
		if(mail.value==''||tel.value==''||passwordOne.value==''||passwordSeconde.value==''){
			console.log("2")
			if(mail.value==''){
				console.log("3")
				mailInfo.value = "邮箱不能为空!"
				mailInfoSty = "color:red;"
				mailSty={borderColor:'red'}
				mail.value=''
			}
			if(tel.value==''){
				telInfo.value = "联系方式不能为空!"
				telInfoSty = "color:red;"
				telSty={borderColor:'red'}
				tel.value=''
			}
			if(passwordOne.value==''){
				show3.value = true
			}
			if(passwordSeconde.value==''){
				show2.value = true
			}
		}
		else{			
			succeseRef.value?.open()
			console.log("http://localhost:8080/createUser?mail="+mail.value+"&tel="+tel.value+"&password="+passwordOne.value)
			axios.get("http://localhost:8080/createUser?mail="+mail.value+"&tel="+tel.value+"&password="+passwordOne.value).then((res)=>{
				if(res.data=="1"){
					setTimeout(()=>{
						uni.navigateTo({
							url:"/pages/index/index"
						})
					},3000)
				}
			})
		}
	}
</script>

<style lang="scss">
	.formArea {
		padding: 15px;
		height: 72.5vh;
		background-color: #fff;
	}
	.button-group {
		position: relative;
		bottom: -200px;
		display: flex;
		justify-content: space-around;
	}
	.btn{
		 width: 120px;
		 height: 39px;
		 font-size: 16px;
		 display: flex;
		 flex-direction: column;
		 justify-content: center;
	}
	.footer{
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		background-color: white;
		position: absolute;
		bottom: 0;
		width: 100vw;
		height: 16vh;
	}
</style>
