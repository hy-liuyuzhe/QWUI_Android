<template>
  <div class="debris-exchange-item-h-layout">
    <van-image class="prize-icon-image-view" :src="exchangeItem.prize.icon" />
    <div class="exchange-detail-v-layout">
      <span class="prize-detail-text-view">{{ exchangeItem.prize.fullName }}</span>
      <div class="debris-detail-h-layout">
        <van-image
          class="debris-icon-image-view"
          :src="require('../assets/super_gift_lottery_debris_ic.png')"
        />
        <span class="debris-count-text-view">{{ exchangeItem.debris }}</span>
      </div>
    </div>
    <span
      class="exchange-button"
      @click="$emit('on-exchange-click', exchangeItem)"
    >
      {{ $t("super_gift_lottery_exchange") }}
    </span>
  </div>
</template>
<script>
import Vue from "vue";
import { Image as VanImage } from "vant";

Vue.use(VanImage);

export default {
  name: "DebrisExchangeItemView",
  props: {
    exchangeItem: {
      type: Object,
      default() {
        return {
          debris: 0,
          prize: {
            fullName: "",
            icon: "",
            duration: 0,
          },
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

@include h-layout(debris-exchange-item) {
  width: 100%;
  background: rgba(123, 39, 25, 0.8);
  border-radius: 14px;
  padding: 4px 16px;

  @include image-view(prize-icon, 96px, 96px);

  @include v-layout(exchange-detail, flex-start) {
    @include compat.margin-start(16px);

    @include text-view(
      prize-detail,
      $font-size: 26px,
      $line-height: 37px,
      $font-color: $--color-DB6E2C,
      $font-weight: $--font-weight-medium,
      $font-family: $--font-family-PingFangSC,
      $line-number: 1
    ) {
      width: 326px;
    }

    @include h-layout(debris-detail) {
      margin-top: 5px;

      @include image-view(debris-icon, 32px, 32px);

      @include text-view(
        debris-count,
        $font-size: 26px,
        $line-height: 37px,
        $font-color: $--color-DB6E2C,
        $font-weight: $--font-weight-medium,
        $font-family: $--font-family-PingFangSC,
        $line-number: 1
      ) {
        @include compat.margin-start(8px);
      }
    }
  }

  @include button(
    exchange,
    $width: 170px,
    $height: 40px,
    $text-size: 24px,
    $text-color: $--color-6B1F15,
    $border-radius: 24px,
    $background: $--color-ECA810
  );
}
</style>
