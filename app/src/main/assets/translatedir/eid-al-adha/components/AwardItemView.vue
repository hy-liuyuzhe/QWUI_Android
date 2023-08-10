<template>
  <div class="award-item-v-layout">
    <van-image class="award-icon-image-view" :src="awardInfo.img" />
    <span class="award-rank-text-view">
      {{ $t("eid_al_adha_award_top_rank", { rank: awardInfo.maxRanking }) }}
    </span>
    <span class="award-num-text-view">{{ awardNumText }}</span>
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
    awardInfo: {
      type: Object,
      default() {
        return {
          maxRanking: 0,
          img: "",
          rewardNum: 0,
          rewardValue: 0,
        };
      },
    },
  },
  computed: {
    awardNumText() {
      const { rewardType, rewardValue, rewardNum } = this.awardInfo;
      if (rewardType === REWARD_TYPE.EXP) {
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

@include v-layout(award-item) {
  width: 280px;
  height: 406px;
  background-image: url("../assets/eid_al_adha_award_bg.png");
  background-repeat: no-repeat;
  background-size: cover;
  @include image-view(award-icon, 216px, 216px) {
    margin-top: 40px;
  }
  @include text-view(
    award-rank,
    $font-size: 28px,
    $line-height: 34px,
    $font-color: #ff4700,
    $font-weight: $--font-weight-bold
  ) {
    margin-top: 4px;
  }
  @include text-view(
    award-num,
    $font-size: 24px,
    $line-height: 29px,
    $font-color: rgba(114, 70, 55, 0.6),
    $font-weight: $--font-weight-regular
  ) {
    margin-top: 8px;
  }
}
</style>