<template>
  <div class="energy-value-box-result-v-layout">
    <span class="congratulation-text-view">
      {{ $t("gift_lottery_congratulations") }}
    </span>
    <div class="prize-v-layout">
      <van-image class="prize-icon-image-view" :src="prize.rewardResourceUrl" />
    </div>
    <span class="prize-count-text-view">
      {{
        $t("super_gift_lottery_get_tickets_amount", { amount: prize.rewardNum })
      }}
    </span>
    <span class="accept-button" @click="$emit('on-accept-btn-click')">
      {{ $t("super_gift_lottery_happy_to_accept") }}
    </span>
  </div>
</template>
<script>
import Vue from "vue";
import { Popup, Image as VanImage } from "vant";

Vue.use(Popup);
Vue.use(VanImage);

export default {
  name: "EnergyValueBoxResultDialog",
  props: {
    prize: {
      type: Object,
      default() {
        return {
          rewardResourceUrl: "",
          rewardNum: 0,
        };
      },
    },
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";

@include v-layout(energy-value-box-result) {
  width: 600px;
  height: 788px;
  background-repeat: no-repeat;
  background-position: center;
  background-size: 100% 100%;
  background-image: url("../assets/super_gift_lottery_energy_value_box_result_bg.png");

  @include text-view(
    congratulation,
    $font-size: 36px,
    $line-height: 48px,
    $font-color: $--color-FFC529,
    $font-weight: $--font-weight-bold
  ) {
    margin-top: 244px;
  }

  @include v-layout(prize) {
    width: 252px;
    height: 252px;
    background-repeat: no-repeat;
    background-position: center;
    background-size: 100% 100%;
    background-image: url("../assets/super_gift_lottery_one_prize_result_prize_bg.png");
    margin-top: 13px;
    justify-content: center;
    @include image-view(prize-icon, 200px, 200px) {
      margin-top: 26px;
    }
  }

  @include text-view(
    prize-count,
    $font-size: 32px,
    $line-height: 48px,
    $font-color: $--color-FFC529,
    $font-weight: $--font-weight-regular
  ) {
    margin-top: 10px;
  }

  @include button(
    accept,
    $width: 400px,
    $height: 88px,
    $text-size: 32px,
    $text-color: #620308,
    $border-radius: 44px,
    $background: $--color-E5BB67
  ) {
    margin-top: 24px;
  }
}
</style>