<template>
  <div class="progress-view-v-layout">
    <div class="progress-bar-h-layout">
      <van-image
        class="decor-image-view"
        :src="require('../assets/national_day_progress_bar_start_ic.png')"
      />
      <van-progress
        class="progress-view"
        :percentage="progress"
        track-color="rgba(236,168,16,0.3)"
        color="linear-gradient(-90deg, #FCF180 0%, #D67537 100%)"
        stroke-width="7"
        :show-pivot="false"
      />
      <van-image
        class="decor-image-view"
        :src="require('../assets/national_day_progress_bar_end_ic.png')"
      />
    </div>
    <div class="progress-text-h-layout">
      <span class="current-text-view">{{ current }}</span>
      <span class="total-text-view">{{ `/${total}` }}</span>
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
  name: "ProgressView",
  props: {
    total: {
      type: Number,
      default: 0,
    },
    current: {
      type: Number,
      default: 0,
    },
  },
  computed: {
    progress() {
      if (this.total <= 0) {
        return 0;
      }

      if (this.current >= this.total) {
        return 100;
      }

      return (this.current * 100) / this.total;
    },
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";

@include v-layout(progress-view) {
  width: 562px;

  @include h-layout(progress-bar) {
    width: 100%;
    height: 37px;

    @include image-view(decor, 39px, 37px) {
      @include dir {
        transform: rotateY(180deg);
      }
    }

    .progress-view {
      width: 569px;
      border: 2px solid #dda35b;
      border-radius: 15px;
      margin: 0px 2px 0px 2px;
    }
  }

  @include h-layout(progress-text) {
    align-self: flex-end;
    margin-top: 22px;
    @include compat.margin-end(45px);

    @include text-view(
      current,
      $font-size: 28px,
      $line-height: 28px,
      $font-color: #28c992,
      $font-weight: $--font-weight-regular
    );

    @include text-view(
      total,
      $font-size: 28px,
      $line-height: 28px,
      $font-color: #fffeea,
      $font-weight: $--font-weight-regular
    );
  }
}
</style>