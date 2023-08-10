<template>
  <div class="coins-item-h-layout">
    <span class="title-text-view">{{ title }}</span>
    <div class="coins-h-layout">
      <span class="coins-text-view">{{ coins }}</span>
      <van-image
        class="coin-image-view"
        :src="require('@/common/assets/common_coin_48_ic.png')"
      />
    </div>
  </div>
</template>
<script>
import Vue from "vue";
import { Image as VanImage } from "vant";

Vue.use(VanImage);

export default {
  name: "CoinsItemView",
  props: {
    title: {
      type: String,
      default: "",
    },
    coins: {
      type: Number,
      default: 0,
    },
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";

@include h-layout(coins-item) {
  width: 686px;
  justify-content: space-between;
  @include text-view(
    title,
    $font-size: 24px,
    $line-height: 29px,
    $font-color: $--color-AAAAAA,
    $font-weight: $--font-weight-regular
  );
  @include h-layout(coins) {
    @include text-view(
      coins,
      $font-size: 24px,
      $line-height: 29px,
      $font-color: $--color-AAAAAA,
      $font-weight: $--font-weight-regular
    );
    @include image-view(coin, 20px, 20px) {
      @include compat.margin-start(4px);
    }
  }
}
</style>