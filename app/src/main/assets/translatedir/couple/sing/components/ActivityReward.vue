<template>
  <div class="activity-reward-z-layout">
    <van-image class="bg-image-view" :src="bgImg" />
    <div class="activity-reward-v-layout">
      <span
        class="reward-intimacy-text-view"
        v-if="intimacyRewardInfo !== undefined"
        >{{
          $t("couple_intimacy_exp_num", { num: intimacyRewardInfo.rewardNum })
        }}</span
      >
      <div class="reward-avatar-frame-layout">
        <van-image
          class="second-reward-icon-image-view"
          :src="secondeMainRewardInfo.rewardResourceUrl"
          v-if="secondeMainRewardInfo !== undefined"
        />
        <van-image
          class="reward-icon-image-view"
          :src="mainRewardInfo.rewardResourceUrl"
          v-if="mainRewardInfo !== undefined"
        />
      </div>
      <span class="reward-days-text-view" v-if="mainRewardInfo !== undefined">{{
        mainRewardDesc
      }}</span>
      <span class="reward-rank-text-view">{{
        $t("couple_top_num", { num: rank })
      }}</span>
    </div>
  </div>
</template>
<script>
import Vue from "vue";
import { Image as VanImage } from "vant";
import _ from "lodash";

Vue.use(VanImage);
export default {
  name: "ActivityReward",
  props: {
    rewardInfos: Array,
    rank: Number,
  },
  computed: {
    intimacyRewardInfo() {
      return _.find(this.rewardInfos, function(o) {
        return o.rewardResourceType === 14;
      });
    },
    mainRewardInfo() {
      return _.find(this.rewardInfos, function(o) {
        return o.rewardResourceType === 3;
      });
    },
    secondeMainRewardInfo() {
      return _.findLast(this.rewardInfos, function(o) {
        return o.rewardResourceType === 3;
      });
    },
    bgImg() {
      return require(`../assets/couple_sing_reward_bg_${this.rank}.png`);
    },
    mainRewardDesc() {
      if (this.mainRewardInfo.rewardDuration) {
        return this.$t("reward_car", {
          days: Math.ceil(this.mainRewardInfo.rewardDuration / 86400),
        });
      } else if (this.mainRewardInfo.rewardNum) {
        return this.mainRewardInfo.rewardNum;
      } else {
        return "";
      }
    },
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";
@include z-layout(activity-reward) {
  width: 179px;
  height: 358px;
  @include image-view(bg, 179px, 358px) {
    position: absolute;
    top: 0;
    left: 0;
  }
  @include v-layout(activity-reward) {
    width: 179px;
    height: 358px;
    position: absolute;
    top: 0;
    left: 0;
    @include text-view(
      reward-intimacy,
      $font-size: 20px,
      $line-height: 28px,
      $font-color: $--color-white,
      $font-weight: $--font-weight-regular,
      $text-align: center
    ) {
      width: 138px;
      margin-top: 39px;
    }
    .reward-avatar-frame-layout {
      position: absolute;
      width: 120px;
      height: 120px;
      top: 96px;
      left: 24px;
      @include image-view(reward-icon, 80px, 80px) {
        position: absolute;
        top: 25px;
        left: 0;
        @include dir {
          right: 0;
          left: unset;
        }
      }
      @include image-view(second-reward-icon, 80px, 80px) {
        position: absolute;
        top: 25px;
        left: 40px;
        @include dir {
          right: 40px;
          left: unset;
        }
      }
    }
    @include text-view(
      reward-days,
      $font-size: 20px,
      $line-height: 28px,
      $font-color: $--color-white,
      $font-weight: $--font-weight-regular,
      $text-align: center,
      $line-number: 1
    ) {
      width: 100%;
      position: absolute;
      top: 220px;
      left: 0px;
    }
    @include text-view(
      reward-rank,
      $font-size: 28px,
      $line-height: 40px,
      $font-color: $--color-white,
      $font-weight: $--font-weight-medium,
      $text-align: center,
      $line-number: 1
    ) {
      width: 100%;
      margin-top: auto;
      margin-bottom: 5px;
    }
  }
}
</style>
