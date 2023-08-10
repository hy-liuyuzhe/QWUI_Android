<template>
  <div class="item-z-layout">
    <div class="content-v-layout">
      <div class="add-coin-h-layout">
        <van-image :src="require('../../common/assets/common_coin_48_ic.png')" class="add-coin-image-view"></van-image>
        <span class="add-coin-text-view" v-show="showAddCoins">{{
          $t("pay_charge_reward",
            { chargeReward: productInfo.chargeReward
        }) }}</span>
      </div>

      <van-image :src="productInfo.coinUrl" class="coin-recharge-image-view"></van-image>

      <span class="coin-num-text-view">{{ productInfo.goldCoins }}</span>

      <van-button class="price-btn-z-layout" round type="info" color="#00E0D7" size="normal" @click="onRechargeClick">
        <span class="price-btn-text-view">{{
          $t("pay_price", {
            currency: currencySymbol(productInfo.currency),
          price: productInfo.amount,
        })
        }}</span>
      </van-button>
    </div>

    <div class="first-recharge-v-layout" v-show="showFirstRecharge">
      <div class="first-recharge-bg-z-layout"></div>
      <span class="first-recharge-text-view">{{ $t("pay_first_recharge") }}</span>
    </div>

  </div>
</template>
<script>
import Vue from "vue";
import { getCurrencySymbol } from "../../../common/util/index.js"
import { Image as VanImage } from "vant";
import { Button } from "vant";

Vue.use(VanImage);
Vue.use(Button);

export default {
  name: "CoinPruductGridItem",
  props: {
    productInfo: Object,
  },
  methods: {
    async onRechargeClick() {
      this.$emit("recharge-btn-click", this.productInfo.id);
    },
    currencySymbol(currencyCode) {
      return getCurrencySymbol(this.$i18n.locale, currencyCode)
    }
  },
  computed: {
    showAddCoins() {
      if (this.productInfo.chargeReward == 0) {
        return false;
      } else {
        return true;
      }
    },
    showFirstRecharge() {
      if (this.productInfo.cornerMark === 1) {
        return true;
      } else {
        return false;
      }
    },
  }
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";

@include z-layout(item) {
  width: 100%;
  height: 422px;
  background: #fff;
  border-radius: 26px;
  border: 1px solid #E9E9E9;

  @include v-layout(first-recharge) {
    position: relative;
    width: 122px;
    left: -8px;

    @include dir {
      left: unset;
      right: -8px;
    }

    @include z-layout(first-recharge-bg) {
      top: 0;
      position: absolute;
      width: 100%;
      height: 100%;

      @include dir {
        transform: rotateY(180deg);
      }

      border-top: 3px solid transparent;
      border-right: 8px solid transparent;
      border-bottom: 12px solid transparent;
      border-left: 8px solid transparent;
      border-image: url("../assets/pay_first_recharge.png") 3 8 12 8 fill;
    }

    @include text-view(first-recharge,
      $font-size: 20px,
      $line-height: 20px,
      $font-color: $--color-white,
      $font-weight: 400) {
      position: relative;
      text-align: center;
      padding-left: 13px;
      padding-right: 13px;
      padding-top: 8px;
      padding-bottom: 17px;
    }
  }

  @include v-layout(content) {
    position: absolute;
    width: 100%;

    @include h-layout(add-coin) {
      position: absolute;
      top: 20px;
      left: unset;
      right: 20px;

      @include dir {
        left: 20px;
        right: unset;
      }

      flex-direction: row-reverse;

      @include image-view(add-coin, 30px, 30px);

      @include text-view(add-coin,
        $font-size: 22px,
        $line-height: 22px,
        $font-color: #777777,
        $font-weight: --font-weight-normal) {
        text-align: center;
        @include compat.margin-end(4px);
      }
    }

    @include image-view(coin-recharge, 240px, 240px) {
      top: 48px;
    }

    @include text-view(coin-num,
      $font-size: 38px,
      $line-height: 38px,
      $font-color: $--color-222222,
      $font-weight: $--font-weight-bold) {
      text-align: center;
      position: absolute;
      top: 288px;
    }

    @include z-layout(price-btn) {
      position: absolute;
      top: 342px;
      width: 270px;
      height: 56px;

      @include text-view(price-btn,
        $font-size: 38px,
        $line-height: 30px,
        $font-color: $--color-222222,
        $font-weight: $--font-weight-bold) {
        height: 34px;
        font-size: 28px;
        font-family: SFUIText-Semibold, SFUIText;
        font-weight: 600;
        color: #ffffff;
        padding-left: 33px;
        padding-right: 33px;
      }
    }
  }
}
</style>
