<template>
  <div class="gift-wishes-item-root">
    <template v-if="gift.status == 0">
      <div
        :class="
          isSelected
            ? 'gift-wishes-select-item-root-selected'
            : 'gift-wishes-select-item-root'
        "
      >
        <van-image class="gift-image-view" :src="gift.img"></van-image>
        <img
          class="select-mark-image-view"
          :src="
            isSelected
              ? require('../assets/gift_wishes_selected_mark.png')
              : require('../assets/gift_wishes_unselected_mark.png')
          "
        />
      </div>
    </template>
    <template v-else>
      <div class="gift-wishes-item">
        <van-image
          class="gift-wishes-image-view"
          :src="gift.img"
        ></van-image>
        <span class="completed-text-view" v-show="gift.status == 2">{{
          $t("gift_wishes_completed")
        }}</span>
      </div>
    </template>
  </div>
</template>
<script>
import Vue from "vue";
import { Image as VanImage } from "vant";
Vue.use(VanImage);
export default {
  name: "GiftWishItem",
  props: {
    gift: Object,
    isSelected: Boolean,
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";
.gift-wishes-item-root {
  position: relative;
}
.gift-wishes-select-item-root {
  position: relative;
  width: 174px;
  height: 184px;
  background-image: url("../assets/gift_wishes_gift_bg.png");
  background-size: 100% 100%;
  .gift-image-view {
    position: absolute;
    top: 26px;
    left: 10px;
    width: 154px;
    height: 154px;
  }
  .select-mark-image-view {
    position: absolute;
    width: 60px;
    height: 80px;
    right: 0px;
    top: -6px;
  }
}
.gift-wishes-select-item-root-selected {
  @extend .gift-wishes-select-item-root;
  border-radius: 16px;
  border: 3px solid #ffe77d;
  box-sizing: border-box;
}
.gift-wishes-item {
  position: relative;
  width: 144px;
  height: 152px;
  background-image: url("../assets/gift_wishes_gift_bg.png");
  background-size: 100% 100%;
  .gift-wishes-image-view {
    position: absolute;
    top: 16px;
    left: 12px;
    width: 120px;
    height: 120px;
  }
  @include text-view(
    completed,
    $font-size: 22px,
    $line-height: 48px,
    $font-color: #fff8e0,
    $font-weight: $--font-weight-bold,
    $text-align: center
  ) {
    width: 144px;
    height: 48px;
    position: absolute;
    bottom: 0px;
    left: 0px;
    background-image: url("../assets/gift_wishes_completed_bg.png");
    background-size: 100% 100%;
  }
}
</style>
