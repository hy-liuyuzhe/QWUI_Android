<template>
  <div class="accumulated-recharge-award-progress-z-layout">
    <van-progress
      class="progress-view"
      :percentage="progress"
      track-color="#FBD396"
      color="#CF9C71"
      stroke-width="7"
      :show-pivot="false"
    />
    <div class="awards-h-layout">
      <div
        class="award-v-layout"
        v-for="(level, index) in levelProgress"
        :key="index"
      >
        <van-image class="award-image-view" :src="levelSrc(level.level)" />
        <span class="award-amount-text-view">{{ `${level.maxAmount}$` }}</span>
      </div>
    </div>
  </div>
</template>
<script>
import _ from "lodash";
import Vue from "vue";
import { Image as VanImage, Progress } from "vant";

Vue.use(VanImage);
Vue.use(Progress);

export default {
  name: "MonthAccumulatedRechargeAwardProgressView",
  props: {
    currentLevel: {
      type: Number,
      default: 0,
    },
    levelProgress: [],
  },
  methods: {
    levelSrc(level) {
      return require(`../assets/trade_recharge_level_${level}_ic.png`);
    },
  },
  computed: {
    progress() {
      if (_.isEmpty(this.levelProgress)) {
        return 0;
      }

      const levelProgressLength = this.levelProgress.length;
      if (this.currentLevel > levelProgressLength) {
        return 100;
      }

      return (this.currentLevel * 100) / levelProgressLength;
    },
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";

@include z-layout(accumulated-recharge-award-progress) {
  width: 100%;
  height: 260px;
  background: linear-gradient(
    180deg,
    rgba(255, 245, 224, 0) 0%,
    #ffe7c3 48%,
    rgba(255, 245, 224, 0) 100%
  );
  .progress-view {
    width: 670px;
    position: absolute;
    top: 108px;
    left: 40px;
    @include dir {
      left: unset;
      right: 40px;
      transform: rotateY(180deg);
    }
  }
  @include h-layout(awards) {
    width: 638px;
    height: 260px;
    position: absolute;
    top: 0;
    left: 50%;
    transform: translateX(-50%);
    justify-content: space-between;

    @include v-layout(award) {
      width: 80px;
      @include image-view(award, 80px, 88px);
      @include text-view(
        award-amount,
        $font-size: 24px,
        $line-height: 29px,
        $font-color: #795228,
        $font-weight: $--font-weight-medium
      ) {
        margin-top: 24px;
      }
    }
  }
}
</style>