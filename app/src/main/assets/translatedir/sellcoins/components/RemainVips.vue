<template>
  <div
    :class="[
      remainVipInfo.selected
        ? 'remain-vip-container-selected'
        : 'remain-vip-container-unselect',
    ]"
    @click="onRemainVipClick"
  >
    <van-image class="vip-medal" :src="vipMedal"></van-image>
    <span class="remain-count">{{
      $t("sell_vips_remain_count", { remain: remainVipInfo.vipNum })
    }}</span>
    <van-image
      class="selected-right"
      :src="arrowImg"
      v-show="remainVipInfo.selected"
    ></van-image>
  </div>
</template>
<script>
import Vue from "vue";
import { Image as VanImage } from "vant";
import { getVipMedal } from "../constant/index";

Vue.use(VanImage);
export default {
  name: "RemainVips",
  props: {
    remainVipInfo: Object,
  },
  computed: {
    vipMedal() {
      return getVipMedal(this.remainVipInfo.vipLevel);
    },
    arrowImg() {
      const languageCode = this.$i18n.locale;
      if (languageCode == "ar") {
        return require("../assets/sell_vips_selected_right_ic_ar.png");
      } else {
        return require("../assets/sell_vips_selected_right_ic.png");
      }
    },
  },
  methods: {
    onRemainVipClick() {
      this.$emit("on-remain-vip-click", this.remainVipInfo.vipLevel);
    },
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;

.remain-vip-container-unselect {
  background: #f2f8fb;
  border-radius: 16px;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  padding-inline-start: 32px;
}
.remain-vip-container-selected {
  background: #e2fffe;
  border-radius: 16px;
  border: 2px solid #00e0d7;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  padding-inline-start: 32px;
}
.vip-medal {
  width: 42px;
  height: 42px;
  margin-top: 23px;
}
.remain-count {
  height: 24px;
  font-size: 22px;
  font-family: SFUIText-Regular, SFUIText;
  font-weight: 400;
  color: #777777;
  line-height: 24px;
  margin-top: 8px;
}
.selected-right {
  width: 48px;
  height: 48px;
  @include compat.margin-start(auto);
  @include compat.margin-end(0px);
  margin-top: -23px;
}
</style>
