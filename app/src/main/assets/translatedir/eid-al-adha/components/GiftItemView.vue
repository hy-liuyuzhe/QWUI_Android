<template>
  <div class="gift-item-v-layout">
    <van-image class="gift-icon-image-view" :src="giftInfo.img" />
  </div>
</template>
<script>
import Vue from "vue";
import { Image as VanImage } from "vant";

Vue.use(VanImage);

export default {
  name: "GiftItemView",
  props: {
    giftInfo: {
      type: Object,
      default() {
        return {
          img: "",
        };
      },
    },
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";

@include v-layout(gift-item) {
  width: 180px;
  height: 248px;
  background-image: url("../assets/eid_al_adha_gift_bg.png");
  background-repeat: no-repeat;
  background-size: cover;

  @include image-view(gift-icon, 140px, 140px) {
    margin-top: 36px;
  }
}
</style>