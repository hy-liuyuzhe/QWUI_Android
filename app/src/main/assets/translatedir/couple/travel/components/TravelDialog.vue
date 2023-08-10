<template>
  <div class="travel-dialog-v-layout">
    <div class="travel-dialog-content-v-layout">
      <div class="travel-dialog-inner-border-bg"></div>
      <span class="title-text-view" v-if="title !== undefined">{{
        title
      }}</span>
      <span class="message-text-view" v-if="message !== undefined">{{
        message
      }}</span>
      <span
        class="main-button"
        @click="onMainButtonClick"
        v-if="mainButtonText !== undefined"
        >{{ mainButtonText }}</span
      >
      <slot />
    </div>
    <van-image
      class="top-image-view"
      :src="
        isEnd
          ? require('../assets/couple_travel_dialog_top_unactive_ic.png')
          : require('../assets/couple_travel_dialog_top_ic.png')
      "
    ></van-image>
    <van-image
      class="colse-image-view"
      :src="
        require('../../../common/assets/common_dialog_bottom_white_close_ic.png')
      "
      @click="onCloseClick"
    ></van-image>
  </div>
</template>
<script>
import _ from "lodash";
import Vue from "vue";
import { Image as VanImage } from "vant";

Vue.use(VanImage);
export default {
  name: "TravelDialog",
  props: {
    isEnd: Boolean,
    title: String,
    message: String,
    mainButtonText: String,
  },
  methods: {
    onCloseClick() {
      this.$emit("on-close-click");
    },
    onMainButtonClick() {
      this.$emit("on-main-button-click");
    },
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/text.scss" as text;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";
@include v-layout(travel-dialog) {
  width: 580px;
  @include v-layout(travel-dialog-content) {
    width: 580px;
    background: linear-gradient(180deg, #ffe9fa 0%, #fefefe 100%);
    border-radius: 32px;
    position: relative;
    margin-top: 160px;
    padding: 104px 62px 70px;
    .travel-dialog-inner-border-bg {
      width: 560px;
      height: calc(100% - 20px);
      position: absolute;
      top: 10px;
      left: 10px;
      border-radius: 28px;
      border: 1px solid rgba(255, 164, 164, 0.6);
      pointer-events: none;
    }
    @include text-view(
      title,
      $font-size: 40px,
      $line-height: 56px,
      $font-color: #812445,
      $font-weight: $--font-weight-bold,
      $text-align: center,
      $line-number: 1
    );
    @include text-view(
      message,
      $font-size: 28px,
      $line-height: 40px,
      $font-color: #bd5a6b,
      $font-weight: $--font-weight-regular,
      $text-align: start
    ) {
      margin-top: 16px;
    }
    .main-button {
      width: 408px;
      height: 108px;
      background-image: url("../assets/couple_travel_button_bg.png");
      background-size: 100% 100%;
      margin-top: 15px;
      font-size: 32px;
      font-family: PingFangSC-Semibold, PingFang SC;
      font-weight: 600;
      color: #ffffff;
      line-height: 108px;
      text-align: center;
    }
  }
  @include image-view(top, 380px, 240px) {
    position: absolute;
    top: 0;
    left: 100px;
  }
  @include image-view(colse, 60px, 60px) {
    margin-top: 98px;
  }
}
</style>
