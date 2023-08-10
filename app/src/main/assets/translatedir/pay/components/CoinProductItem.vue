<template>
  <div>
    <van-image
      :src="require('../../common/assets/common_coin_48_ic.png')"
      class="coin-icon"
    ></van-image>
    <span class="coin-num">{{ productInfo.goldCoins }}</span>
    <span class="add-coin-num" v-show="showAddCoins"
      >+{{ productInfo.chargeReward }}</span
    >
    <van-button
      class="price-btn"
      round
      type="info"
      color="#00E0D7"
      size="normal"
      @click="onRechargeClick"
    >
      <span class="price-title">{{
        $t("pay_price", {
          currency: productInfo.currency,
          price: productInfo.amount,
        })
      }}</span>
    </van-button>
  </div>
</template>
<script>
import Vue from "vue";
import { Image as VanImage } from "vant";
import { Button } from "vant";

Vue.use(VanImage);
Vue.use(Button);
export default {
  name: "CoinPruductItem",
  props: {
    productInfo: Object,
  },
  methods: {
    async onRechargeClick() {
      this.$emit("recharge-btn-click", this.productInfo.id);
    },
  },
  computed: {
    showAddCoins() {
      if (this.productInfo.chargeReward == 0) {
        return false;
      } else {
        return true;
      }
    },
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
.coin-icon {
  width: 56px;
  height: 56px;
  @include compat.margin-start(20px);
}
.coin-num {
  font-size: 32px;
  width: 104px;
  font-family: SFUIText-Semibold, SFUIText;
  font-weight: 600;
  color: #222222;
  @include compat.margin-start(16px);
}
.add-coin-num {
  font-size: 32px;
  font-family: SFUIText-Semibold, SFUIText;
  font-weight: 600;
  color: #ffc800;
  @include compat.margin-start(20px);
}
.price-btn {
  height: 56px;
  @include compat.margin-start(auto);
  @include compat.margin-end(24px);

  .price-title {
    height: 34px;
    font-size: 28px;
    font-family: SFUIText-Semibold, SFUIText;
    font-weight: 600;
    color: #ffffff;
    padding-left: 33px;
    padding-right: 33px;
  }
}
</style>
