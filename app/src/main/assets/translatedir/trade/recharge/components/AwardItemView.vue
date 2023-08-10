<template>
  <div class="award-item-v-layout">
    <div class="award-image-z-layout">
      <van-image class="award-image-view" :src="award.img" />
    </div>
    <span class="award-amount-text-view">{{ awardAmountText }}</span>
  </div>
</template>
<script>
import Vue from "vue";
import { Image as VanImage } from "vant";
import { REWARD_TYPE } from "../constant/index";

Vue.use(VanImage);

export default {
  name: "AwardItemView",
  props: {
    award: {
      type: Object,
      default() {
        return {
          rewardType: 0,
          rewardValue: 0,
          rewardNum: 0,
          img: "",
        };
      },
    },
  },
  computed: {
    awardAmountText() {
      const { rewardType, rewardValue, rewardNum } = this.award;
      if (rewardType === REWARD_TYPE.EXP) {
        return rewardValue * rewardNum;
      }

      const days = rewardValue / (24 * 60 * 60);
      return this.$t("trade_recharge_good_award_amount", {
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

@include v-layout(award-item) {
  width: 144px;

  @include z-layout(award-image) {
    width: 144px;
    height: 148px;
    background-image: url("../assets/trade_recharge_award_bg.png");
    background-size: cover;
    background-repeat: no-repeat;

    @include image-view(award, 128px, 128px) {
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translateX(-50%) translateY(-50%);
    }
  }

  @include text-view(
    award-amount,
    $font-size: 24px,
    $line-height: 40px,
    $font-color: #795228,
    $font-weight: $--font-weight-regular
  ) {
    margin-top: 14px;
  }
}
</style>