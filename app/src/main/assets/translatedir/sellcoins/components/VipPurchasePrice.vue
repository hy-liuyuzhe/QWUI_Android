<template>
  <div class="vip-purchase-price-container">
    <span class="purchase-price-title">{{
      $t("sell_vips_purchase_price")
    }}</span>
    <span class="purchase-desc">{{
      $t("sell_vips_purchase_min_money_desc")
    }}</span>
    <div class="vip-prices-container">
      <div class="vip-price" v-for="(item, index) in vips" :key="index">
        <van-image
          class="purchase-vip-medal"
          :src="vipMedal(item.vipLevel)"
        ></van-image>
        <span class="purchase-vip-price">{{
          $t("sell_vips_vip_price", { price: item.price })
        }}</span>
      </div>
    </div>
    <span class="purchase-contact">{{ $t("sell_vips_purchase_contact") }}</span>
    <span class="ok-button" @click="onOKClick">{{ $t("common_ok") }}</span>
  </div>
</template>

<script>
import Vue from "vue";
import { Image as VanImage } from "vant";
import { getVipMedal } from "../constant/index";

Vue.use(VanImage);
export default {
  name: "VipPurchasePrice",
  data() {
    return {
      vips: [
        {
          vipLevel: 1,
          price: 7,
        },
        {
          vipLevel: 2,
          price: 35,
        },
        {
          vipLevel: 3,
          price: 70,
        },
        {
          vipLevel: 4,
          price: 140,
        },
      ],
    };
  },
  methods: {
    onOKClick() {
      this.$emit("on-ok-click");
    },
    vipMedal(vipLevel) {
      return getVipMedal(vipLevel);
    },
  },
};
</script>

<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@import "../../common/css/index.scss";

.vip-purchase-price-container {
  width: 606px;
  display: flex;
  flex-direction: column;
  background: #ffffff;
  border-radius: 32px;
  padding: 48px;
  align-items: center;
  .purchase-price-title {
    @extend .single-line-text;
    font-size: 36px;
    font-family: SFUIText-Bold, SFUIText;
    font-weight: bold;
    color: #222222;
    line-height: 48px;
  }
  .purchase-desc {
    width: 510px;
    font-size: 28px;
    font-family: SFUIText-Regular, SFUIText;
    font-weight: 400;
    color: #222222;
    line-height: 48px;
    margin-top: 16px;
    text-align: start;
  }
  .purchase-contact {
    width: 510px;
    height: 144px;
    font-size: 28px;
    font-family: SFUIText-Regular, SFUIText;
    font-weight: 400;
    color: #222222;
    line-height: 48px;
    text-align: start;
    margin-top: 16px;
    white-space: pre-line;
  }
  .vip-prices-container {
    width: 518px;
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    margin-top: 16px;
    .vip-price {
      width: 247px;
      height: 104px;
      background: #ffefdb;
      border-radius: 16px;
      display: flex;
      flex-wrap: wrap;
      @include compat.margin-start(8px);
      align-items: center;
      margin-top: 8px;
      .purchase-vip-medal {
        width: 80px;
        height: 80px;
        @include compat.margin-start(24px);
      }
      .purchase-vip-price {
        height: 48px;
        font-size: 28px;
        font-family: SFUIText-Bold, SFUIText;
        font-weight: bold;
        color: #df820e;
        line-height: 48px;
        @include compat.margin-start(4px);
      }
    }
  }
  .ok-button {
    width: 510px;
    height: 88px;
    background: #00e0d7;
    border-radius: 44px;
    font-size: 32px;
    text-align: center;
    font-family: SFUIText-Semibold, SFUIText;
    font-weight: 600;
    color: #ffffff;
    line-height: 88px;
    margin-top: 32px;
  }
}
</style>