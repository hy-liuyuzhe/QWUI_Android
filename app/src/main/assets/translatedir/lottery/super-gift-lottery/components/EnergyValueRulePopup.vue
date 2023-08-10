<template>
  <div class="energy-value-v-layout">
    <div class="bottom-v-layout">
      <van-image
        class="top-bg-image-view"
        :src="
          require('../assets/super_gift_lottery_history_records_top_bg.png')
        "
      />
      <span class="energy-value-text-view">
        {{ $t("super_gift_lottery_energy_value_value") }}
      </span>
      <span class="energy-value-rule-text-view">
        {{ $t("super_gift_lottery_energy_value_rule_desc") }}
      </span>
    </div>
    <span class="title-text-view">
      {{ $t("super_gift_lottery_energy_value_rule") }}
    </span>
  </div>
</template>
  <script>
import Vue from "vue";
import { Image as VanImage, Toast } from "vant";

Vue.use(VanImage);
Vue.use(Toast);

export default {
  name: "EnergyValueRulePopup",
};
</script>
  <style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";

@include v-layout(energy-value) {
  width: 100%;
  height: 1037px;
  @include text-view(
    title,
    $font-size: 38px,
    $line-height: 130px,
    $font-color: $--color-FFFDE6,
    $font-weight: $--font-weight-bold,
    $text-align: center,
    $line-number: 1
  ) {
    width: 427px;
    position: absolute;
    top: 0;
    text-shadow: 0px 0px 9px #ed3818;
    background-repeat: no-repeat;
    background-position: center;
    background-size: 100% 100%;
    background-image: url("../assets/super_gift_lottery_history_records_title_bg.png");
  }

  @include v-layout(bottom) {
    position: relative;
    top: 57px;
    background: linear-gradient(180deg, #9a163a 0%, #712216 100%);
    border-radius: 32px 32px 2px 2px;
    width: 100%;
    height: 980px;

    @include image-view(top-bg, 750px, 300px) {
      position: absolute;
      top: 0;
    }

    @include text-view(
      energy-value,
      $font-size: 30px,
      $line-height: 36px,
      $font-color: $--color-FFDD99,
      $font-weight: $--font-weight-bold
    ) {
      margin-top: 90px;
      @include compat.margin-start(32px);
      @include compat.margin-end(32px);
      align-self: flex-start;
    }

    @include text-view(
      energy-value-rule,
      $font-size: 28px,
      $line-height: 34px,
      $font-color: $--color-white,
      $font-weight: $--font-weight-bold
    ) {
      margin-top: 16px;
      @include compat.margin-start(32px);
      @include compat.margin-end(32px);
      align-self: flex-start;
    }
  }
}
</style>