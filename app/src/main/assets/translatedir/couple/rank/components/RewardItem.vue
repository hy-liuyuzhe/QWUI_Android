<template>
  <div class="item-z-layout">
    <div class="reward-content-v-layout">
      <span class="reward-name-text-view">{{ rewardInfo.name }}</span>
      <span class="reward-num-text-view">{{ rewardNumText }}</span>
      <van-image :src="rewardInfo.img" class="reward-img" />
    </div>
    <span class="title-text-view">{{ rankDesc }}</span>
  </div>
</template>
<script>
import Vue from "vue";
import { Image as VanImage } from "vant";

Vue.use(VanImage);
export default {
  name: "RewardItem",
  props: {
    rewardInfo: Object,
  },
  computed: {
    rewardNumText() {
      const { rewardValue } = this.rewardInfo;
      const days = rewardValue / (24 * 60 * 60);
      return this.$t("common_days", {
        days: days,
      });
    },
    rankDesc() {
      if (this.rewardInfo.minRanking === this.rewardInfo.maxRanking) {
        return this.$t("couple_ranking_single", {
          rank: this.rewardInfo.minRanking,
        });
      } else {
        return this.$t("couple_ranking_range", {
          min: this.rewardInfo.minRanking,
          max: this.rewardInfo.maxRanking,
        });
      }
    },
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";

@include z-layout(item) {
  width: 686px;

  @include v-layout(reward-content) {
    width: 100%;
    margin-top: 39px;
    border-left: 0px;
    border-top: 53px solid transparent;
    border-bottom: 48px solid transparent;
    border-right: 0px;
    border-image: url("../assets/couple_reward_bg.png") 53 0 48 0 fill;
    position: relative;
    @include text-view(
      reward-name,
      $font-size: 26px,
      $line-height: 35px,
      $font-color: #c94e9b,
      $font-weight: $--font-weight-medium,
      $text-align: center,
      $line-number: 1
    );
    @include text-view(
      reward-num,
      $font-size: 26px,
      $line-height: 35px,
      $font-color: #c94e9b,
      $font-weight: $--font-weight-medium,
      $text-align: center,
      $line-number: 1
    ) {
      margin-top: 10px;
    }
    .reward-img {
      max-width: 560px;
      max-height: 410px;
      margin-top: 16px;
    }
  }

  @include text-view(
    title,
    $font-size: 32px,
    $line-height: 76px,
    $font-color: $--color-white,
    $font-weight: $--font-weight-medium,
    $text-align: center,
    $line-number: 1
  ) {
    position: absolute;
    width: 328px;
    height: 76px;
    background-image: url("../assets/couple_reward_title_ic.png");
    background-repeat: no-repeat;
    background-size: cover;
    top: 0;
    left: 0;
    right: 0;
    margin: auto;
  }
}
</style>
