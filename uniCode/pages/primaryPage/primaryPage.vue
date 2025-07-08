<template>
  <view>
    <button @click="open">待开发</button>
    <uni-popup ref="popupRef" type="message" border-radius="10px 10px 0 0">
      <uni-popup-message type="success" message="成功消息" :duration="1000"></uni-popup-message>
    </uni-popup>
  </view>
</template>

<script setup>
import { ref } from 'vue';

// 创建 popup 的 ref
const popupRef = ref(null);

// 打开弹窗方法
function open(){
  popupRef.value?.open()
}
</script>