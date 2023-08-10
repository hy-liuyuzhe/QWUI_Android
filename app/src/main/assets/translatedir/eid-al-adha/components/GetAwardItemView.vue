<template>
  <div class="get-award-item-v-layout">
    <van-image class="award-icon-image-view" :src="awardInfo.img" />
    <span class="award-num-text-view">{{ awardNumText }}</span>
  </div>
</template>
<script>
import Vue from "vue";
import { Image as VanImage } from "vant";
import { REWARD_TYPE } from "../constant/index";

Vue.use(VanImage);

export default {
  name: "GetAwardItemView",
  props: {
    awardInfo: {
      type: Object,
      default() {
        return {
          img: "",
          rewardType: 0,
          rewardNum: 0,
          rewardValue: 0,
        };
      },
    },
  },
  computed: {
    awardNumText() {
      const { rewardType, rewardValue, rewardNum } = this.awardInfo;
      if (rewardType === REWARD_TYPE.EXP || rewardType === REWARD_TYPE.COINS) {
        return rewardValue * rewardNum;
      }

      const days = rewardValue / (24 * 60 * 60);
      return this.$t("eid_al_adha_award_num", {
        value: days,
        num: rewardNum,
      });
    },
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";

@include v-layout(get-award-item) {
  width: 160px;
  height: 222px;
  background-image: url("../assets/eid_al_adha_get_award_bg.png");
  background-repeat: no-repeat;
  background-size: cover;

  @include image-view(award-icon, 120px, 120px) {
    margin-top: 32px;
  }

  @include text-view(
    award-num,
    $font-size: 22px,
    $line-height: 26px,
    $font-color: $--color-white,
    $font-weight: $--font-weight-regular
  ) {
    margin-top: 12px;
  }
}
</style>