<template>
  <div class="reward-progress-root">
    <van-progress
      class="progress-bar"
      :percentage="progressPercentage"
      :track-color="progressTrackColor"
      :color="progressColor"
      stroke-width="7"
      v-bind:show-pivot="false"
      :style="{
        top: progressMarginTop + 'px',
        marginInlineEnd: progressBarMarginEnd + 'px',
      }"
    />
    <div
      class="reward-list-container"
      :style="{ paddingInlineStart: rewardListPaddingStart + 'px' }"
    >
      <slot name="reward-items"></slot>
    </div>
  </div>
</template>
<script>
import Vue from "vue";
import { Progress } from "vant";
Vue.use(Progress);
export default {
  name: "RewardProgressBar",
  props: {
    rewardValues: [],
    currentRewardValue: Number,
    width: Number,
    rewardItemWidth: Number,
    progressTrackColor: String,
    progressColor: String,
    progressMarginTop: Number,
    progressBarMarginEnd: Number,
  },
  computed: {
    rewardListPaddingStart() {
      if (
        this.width &&
        this.rewardItemWidth &&
        this.rewardValues &&
        this.rewardValues.length > 0
      ) {
        return (this.width / this.rewardValues.length - this.rewardItemWidth)/2;
      }
      return 0;
    },
    progressPercentage() {
      if (this.rewardValues.length == 0 || this.currentRewardValue == 0) {
        return 0;
      }
      var findIndex = -1;
      var max = 0;
      const progress = this.rewardValues;
      if (progress && progress.length > 0) {
        progress.forEach((item, index) => {
          if (this.currentRewardValue >= item) {
            findIndex = index;
          }
          if (item > max) {
            max = item;
          }
        });
        var sectionStart = 0;
        var sectionEnd = 0;
        var nextIndex = findIndex + 1;
        if (findIndex >= 0) {
          sectionStart = progress[findIndex];
          if (nextIndex >= progress.length) {
            return 100;
          }
        }
        sectionEnd = progress[nextIndex];
        const sectionStartRatio = (100 / progress.length) * (findIndex + 1);
        const sectionRatio = parseInt(
          ((this.currentRewardValue - sectionStart) * 100) /
            (sectionEnd - sectionStart)
        );
        const sectionSpaceRatio =
          this.getProgressItemHorizontalSpaceValue(progress.length) /
          (this.width - this.progressBarMarginEnd);
        const result = sectionStartRatio + sectionRatio * sectionSpaceRatio;
        return result;
      }
      return 0;
    },
  },
  methods: {
    getProgressItemHorizontalSpaceValue(progressCount) {
      return parseInt(
        (this.width -
          this.progressBarMarginEnd -
          this.rewardItemWidth * progressCount) /
          progressCount
      );
    },
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/index.scss" as *;
.reward-progress-root {
  position: relative;
}
.progress-bar {
  position: relative;
  @include dir {
    transform: rotateY(180deg);
  }
}
.reward-list-container {
  position: relative;
  display: flex;
  flex-direction: row;
  align-items: flex-start;
  justify-content: space-between;
  top: 0px;
}
</style>
