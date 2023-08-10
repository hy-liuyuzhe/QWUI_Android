<template>
  <div class="super-gift-lottery-result-v-layout">
    <div v-if="prizeList.length == 1" class="one-prize-v-layout">
      <span class="congratulation-text-view">
        {{ $t("gift_lottery_congratulations") }}
      </span>
      <div class="prize-v-layout">
        <van-image class="prize-icon-image-view" :src="prizeList[0].icon" />
        <span class="prize-count-text-view">{{ prizeList[0].name }}</span>
      </div>
    </div>
    <div v-else-if="prizeList.length > 1" class="ten-prizes-v-layout">
      <span class="congratulation-text-view">
        {{ $t("gift_lottery_congratulations") }}
      </span>
      <div class="top-six-prizes-grid-layout">
        <div
          v-for="(prize, index) in prizeList.slice(0, 6)"
          :key="index"
          class="prize-v-layout"
        >
          <van-image class="prize-icon-image-view" :src="prize.icon" />
          <span class="prize-count-text-view">{{ prize.name }}</span>
        </div>
      </div>
      <div class="bottom-four-prizes-grid-layout">
        <div
          v-for="(prize, index) in prizeList.slice(6, 10)"
          :key="index"
          class="prize-v-layout"
        >
          <van-image class="prize-icon-image-view" :src="prize.icon" />
          <span class="prize-count-text-view">{{ prize.name }}</span>
        </div>
      </div>
    </div>
    <van-image
      class="close-image-view"
      :src="require('@/common/assets/common_dialog_bottom_white_close_ic.png')"
      @click="$emit('on-close-btn-click')"
    />
  </div>
</template>

<script>
import Vue from "vue";
import { Popup, Image as VanImage } from "vant";

Vue.use(Popup);
Vue.use(VanImage);

export default {
  name: "SuperGiftLotteryResultDialog",
  props: {
    prizeList: [],
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";

@include v-layout(super-gift-lottery-result) {
  width: 600px;

  @include text-view(
    congratulation,
    $font-size: 36px,
    $line-height: 48px,
    $font-color: $--color-FFC529,
    $font-weight: $--font-weight-bold
  ) {
    margin-top: 244px;
  }

  @include v-layout(one-prize) {
    width: 600px;
    height: 680px;
    background-repeat: no-repeat;
    background-position: center;
    background-size: 100% 100%;
    background-image: url("../assets/super_gift_lottery_one_prize_result_bg.png");
    @include v-layout(prize) {
      width: 272px;
      height: 272px;
      background-repeat: no-repeat;
      background-position: center;
      background-size: 100% 100%;
      background-image: url("../assets/super_gift_lottery_one_prize_result_prize_bg.png");
      margin-top: 40px;
      @include image-view(prize-icon, 200px, 200px) {
        margin-top: 15px;
      }

      @include text-view(
        prize-count,
        $font-size: 32px,
        $line-height: 38px,
        $font-color: rgba(255, 255, 255, 0.6),
        $font-weight: $--font-weight-medium
      );
    }
  }

  @include v-layout(ten-prizes) {
    width: 600px;
    height: 793px;
    background-repeat: no-repeat;
    background-position: center;
    background-size: 100% 100%;
    background-image: url("../assets/super_gift_lottery_ten_prizes_result_bg.png");
    @include v-layout(prize) {
      width: 140px;
      height: 140px;
      background-repeat: no-repeat;
      background-position: center;
      background-size: 100% 100%;
      background-image: url("../assets/super_gift_lottery_ten_prizes_result_prize_bg.png");
      @include image-view(prize-icon, 102px, 102px) {
        margin-top: 8px;
      }

      @include text-view(
        prize-count,
        $font-size: 24px,
        $line-height: 29px,
        $font-color: rgba(255, 255, 255, 0.6),
        $font-weight: $--font-weight-medium
      );
    }
    @include grid-layout(
      top-six-prizes,
      140px 140px 140px,
      140px 140px,
      4px,
      4px
    ) {
      margin-top: 21px;
    }
    @include grid-layout(
      bottom-four-prizes,
      140px 140px 140px 140px,
      140px,
      4px,
      4px
    ) {
      margin-top: 4px;
    }
  }

  @include image-view(close, 70px, 70px) {
    margin-top: 40px;
  }
}
</style>