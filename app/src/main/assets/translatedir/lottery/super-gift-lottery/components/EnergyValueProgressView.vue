<template>
  <div class="energy-value-progress-z-layout">
    <van-progress
      class="progress-view"
      :percentage="progress"
      track-color="rgba(236,168,16,0.3)"
      color="#ECA810"
      stroke-width="7"
      :show-pivot="false"
    />
    <van-image
      class="start-image-view"
      :src="
        require('../assets/super_gift_lottery_energy_value_progress_start_ic.png')
      "
    />
    <div class="boxs-h-layout">
      <div
        class="box-v-layout"
        v-for="(energy, index) in energyProgress"
        :key="index"
      >
        <van-image class="box-image-view" :src="boxSrc(energy.chargeCoinNum)" />
        <span class="energy-amount-text-view">{{ energy.chargeCoinNum }}</span>
        <span
          v-if="isCanNotReceived(energy.chargeCoinNum)"
          class="receive-button"
          @click="$emit('on-receive-box-click', energy)"
        >
          {{ $t("super_gift_lottery_receive") }}
        </span>
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
  name: "EnergyValueProgressView",
  props: {
    activityId: {
      type: Number,
      default: 0,
    },
    currentEnergy: {
      type: Number,
      default: 0,
    },
    energyProgress: [],
    receivedEnergyValues: [],
  },
  methods: {
    boxSrc(energyValue) {
      if (this.currentEnergy >= energyValue) {
        return require(`../assets/super_gift_lottery_opened_box_ic.png`);
      }

      return require(`../assets/super_gift_lottery_closed_box_ic.png`);
    },
    isCanNotReceived(energyValue) {
      return this.currentEnergy > energyValue && !_.includes(this.receivedEnergyValues, energyValue);
    },
  },
  computed: {
    progress() {
      if (this.energyProgress.length == 0 || this.currentEnergy == 0) {
        return 0;
      }

      var findIndex = -1;
      var max = 0;
      const progress = this.energyProgress;
      if (progress && progress.length > 0) {
        progress.forEach((item, index) => {
          const chargeCoinNum = item.chargeCoinNum;
          if (this.currentEnergy >= chargeCoinNum) {
            findIndex = index;
          }
          if (chargeCoinNum > max) {
            max = chargeCoinNum;
          }
        });
        var sectionStart = 0;
        var sectionEnd = 0;
        var nextIndex = findIndex + 1;
        if (findIndex >= 0) {
          sectionStart = progress[findIndex].chargeCoinNum;
          if (nextIndex >= progress.length) {
            return 100;
          }
        }

        sectionEnd = progress[nextIndex].chargeCoinNum;
        const sectionStartRatio = (100 / progress.length) * (findIndex + 1);
        const sectionRatio = parseInt(
          ((this.currentEnergy - sectionStart) * 100) /
            (sectionEnd - sectionStart)
        );
        const result = sectionStartRatio + sectionRatio / progress.length;
        return result;
      }
      return 0;
    },
  },
};
</script>
  <style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";

@include z-layout(energy-value-progress) {
  width: 100%;
  height: 166px;
  .progress-view {
    width: 545px;
    position: absolute;
    top: 44px;
    left: 22px;
    @include dir {
      left: unset;
      right: 22px;
      transform: scaleX(-1);
    }
  }
  @include image-view(start, 26px, 26px) {
    position: absolute;
    top: 38px;
    left: 0px;
    @include dir {
      left: unset;
      right: 0px;
    }
  }
  @include h-layout(boxs, flex-start) {
    width: 514px;
    height: 166px;
    position: absolute;
    top: 0;
    right: 0;
    justify-content: space-between;
    @include dir {
      left: 0;
      right: unset;
    }
    @include v-layout(box) {
      @include image-view(box, 72px, 80px);
      @include text-view(
        energy-amount,
        $font-size: 22px,
        $line-height: 26px,
        $font-color: $--color-FFC529,
        $font-weight: $--font-weight-medium
      ) {
        margin-top: 16px;
      }
      @include button(
        receive,
        $width: 106px,
        $height: 36px,
        $text-size: 22px,
        $text-color: #6b1f15,
        $border-radius: 24px,
        $background: #eca810
      ) {
        padding-left: 0px;
        padding-right: 0px;
        margin-top: 8px;
      }
    }
  }
}
</style>