<template>
  <div class="root">
    <div class="top-layout">
      <span class="congratulation-text">
        {{ $t("gift_lottery_congratulations") }}
      </span>
      <div class="goods-layout">
        <van-image class="icon" :src="goods.icon"></van-image>
        <span class="desc">{{ goods.name }}</span>
      </div>
    </div>
    <van-image
      class="close-btn"
      :src="require('../assets/close_btn_ic.png')"
      @click="onCloseClick"
    ></van-image>
  </div>
</template>

<script>
import Vue from "vue";
import { Popup, Image as VanImage } from "vant";

Vue.use(Popup);
Vue.use(VanImage);

export default {
  name: "LotteryResult",
  props: {
    goods: {
      name: String,
      icon: String,
    },
  },
  methods: {
    onCloseClick() {
      this.$emit("close-btn-click");
    },
  },
};
</script>
<style lang="scss" scoped>
@import "../../../greedy/css/common.scss";
.root {
  width: 600px;
  display: flex;
  flex-direction: column;
  align-items: center;
  .top-layout {
    width: 600px;
    height: 708px;
    background-repeat: no-repeat;
    background-position: center;
    background-size: 100% 100%;
    background-image: url("../assets/lottery_result_bg.png");
    display: flex;
    flex-direction: column;
    align-items: center;
    .congratulation-text {
      @extend .single-line-text;
      width: 510px;
      height: 48px;
      margin-top: 272px;
      font-size: 36px;
      font-family: SFUIText-Bold, SFUIText;
      font-weight: bold;
      color: #ffffff;
      line-height: 48px;
    }
    .goods-layout {
      width: 272px;
      height: 272px;
      margin-top: 42px;
      background-repeat: no-repeat;
      background-position: center;
      background-size: 100% 100%;
      background-image: url("../assets/lottery_result_goods_bg.png");
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      .icon {
        width: 200px;
        height: 200px;
      }
      .desc {
        @extend .single-line-text;
        height: 38px;
        font-size: 32px;
        font-family: SFUIText-Medium, SFUIText;
        font-weight: 500;
        color: #f5e5ff;
        line-height: 38px;
      }
    }
  }
  .close-btn {
    width: 70px;
    height: 70px;
    margin-top: 40px;
  }
}
</style>