<template>
  <div class="travel-reward-z-layout" @click="onItemClick()">
    <img
      class="reward-image-view"
      :src="rewardInfo.cityImg"
    />
    <div class="lock-mask-z-layout" v-if="rewardInfo.status === 0">
      <van-image
        class="lock-image-view"
        :src="require('../assets/couple_travel_lock.png')"
      ></van-image>
    </div>
    <van-image-preview v-model="showImagePreview" :images="images">
      <template v-slot:index>
        <div class="reward-name-text-view">{{ rewardInfo.name }}</div>
      </template>
    </van-image-preview>
  </div>
</template>
<script>
import _ from "lodash";
import Vue from "vue";
import { Image as VanImage, ImagePreview } from "vant";

Vue.use(VanImage);
Vue.use(ImagePreview);
export default {
  name: "TravelReward",
  data() {
    return {
      showImagePreview: false,
      images: [],
    };
  },
  props: {
    rewardInfo: Object,
  },
  methods: {
    onItemClick() {
      if (_.isNil(this.rewardInfo.status)) {
        return;
      }
      if (this.rewardInfo.status !== 1) {
        return;
      }
      this.images = [this.rewardInfo.cityImg];
      this.showImagePreview = true;
    },
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/text.scss" as text;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";
@include z-layout(travel-reward) {
  width: 154px;
  height: 116px;
  @include image-view(reward, 154px, 116px) {
    border: 3px solid #ffffff;
    pointer-events: none;
    border-radius: 12px;
  }
  @include z-layout(lock-mask) {
    width: 154px;
    height: 116px;
    background: rgba(51, 51, 51, 0.5);
    border-radius: 12px;
    position: absolute;
    top: 0;
    left: 0;
    @include image-view(lock, 31px, 40px) {
      position: absolute;
      top: 37px;
      left: 60px;
    }
  }
  @include text-view(
    reward-name,
    $font-size: 36px,
    $line-height: 42px,
    $font-color: $--color-white,
    $font-weight: $--font-weight-bold,
    $text-align: start,
    $line-number: 1
  ) {
    height: 122px;
    padding-top: 80px;
  };
}
</style>
