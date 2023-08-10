<template>
  <div class="sell-vip-root-container">
    <div class="sell-vip-bg"></div>
    <div class="sell-vip-content-container">
      <div class="header-title">
        <span class="remain-vips-title">{{ $t("sell_vips_remain_vip") }}</span>
        <div class="purchase-price-container" @click="onPurchasePriceClick">
          <span class="purchase-price-title">{{ $t("sell_vips_purchase_price") }}</span>
          <img class="help-img" src="../assets/sell_coins_help_ic.png" />
        </div>
      </div>
      <div class="remain-vips">
        <remain-vips class="remain-vip-item" v-for="(item, index) in remainVips" :key="index" v-bind:remainVipInfo="item"
          @on-remain-vip-click="onRemainVipClick($event)"></remain-vips>
      </div>
      <div class="vip-sell-content">
        <div class="vip-sell-search-container">
          <div class="vip-sell-id-search-title-container">
            <span class="vip-sell-id-search-title">{{ $t("sell_vips_input") }}</span>
          </div>
          <div class="vip-search-input-layout">
            <van-field v-model="shortId" type="number" class="vip-search-input" :placeholder="$t('sell_coins_enter_id')"
              :input-align="this.$i18n.locale === 'ar' ? 'right' : 'left'"></van-field>
            <div class="vip-search-btn" @click="onSearchUser">
              <div class="vip-search-btn-bg">
                <span class="vip-search-btn-text">{{ $t("sell_coins_search") }}</span>
              </div>
            </div>
          </div>
          <div class="user-search-result">
            <template v-if="userInfo != null">
              <div class="user-search-success">
                <van-image round class="avatar" :src="userInfo.url"></van-image>
                <user-level-item class="user-level" v-bind:level="userInfo.level"></user-level-item>
                <span class="user-name">{{ userInfo.name }}</span>
              </div>
            </template>
            <template v-if="userInfo === null && showUserInfo">
              <div class="user-search-failed">
                <van-image class="error-img" :src="require('../assets/warn_icon.png')"></van-image>
                <span class="error-info">{{ $t("sell_coins_user_not_exist") }}</span>
              </div>
            </template>
          </div>
        </div>
      </div>
    </div>
    <van-button class="confirm-btn" round color="#00E0D7" v-bind:disabled="userInfo === null || selectedVipLevel === null"
      @click="onConfirmClick">
      <span class="confirm-title">{{ $t("sell_coins_confirm") }}</span>
    </van-button>
    <van-popup v-model="showPurchasePrice">
      <vip-purchase-price @on-ok-click="onPurchasePriceConfirm" />
    </van-popup>
  </div>
</template>
<script>
import _ from "lodash";
import RemainVips from "./RemainVips";
import api from "../api/index";
import UserLevelItem from "../../common/components/UserLevelItem";
import VipPurchasePrice from "./VipPurchasePrice";
import commonApi from "../../common/api/index";
import { RES_COMMON_CODE } from "../../../common/network/constant";
import Vue from "vue";
import { Button } from "vant";
import { Toast } from "vant";
import { Popup } from "vant";

Vue.use(Button);
Vue.use(Toast);
Vue.use(Popup);
export default {
  name: "SellVips",
  data() {
    return {
      shortId: null,
      remainVips: [],
      userInfo: null,
      showUserInfo: false,
      selectedVipLevel: null,
      showPurchasePrice: false,
    };
  },
  components: {
    "remain-vips": RemainVips,
    "user-level-item": UserLevelItem,
    "vip-purchase-price": VipPurchasePrice,
  },
  created() {
    this.getMerchantVipList();
  },
  methods: {
    onPurchasePriceClick() {
      this.showPurchasePrice = true;
    },
    onPurchasePriceConfirm() {
      this.showPurchasePrice = false;
    },
    onRemainVipClick(vipLevel) {
      this.selectedVipLevel = vipLevel;
      this.remainVips.forEach((element) => {
        element.selected = element.vipLevel == vipLevel;
      });
    },
    async onConfirmClick() {
      console.log("onConfirmClick, " + this.userInfo.sid)
      let uidRes = await commonApi.getUserInfoByGoodOrShortId(this.userInfo.sid)
      console.log(uidRes)
      if (uidRes.code !== RES_COMMON_CODE.SUCCESS) {
        return
      }
      let req = {
        buyer: uidRes.data.uid,
        vipLevel: this.selectedVipLevel,
      };
      const res = await api.sellVip(req);
      let msg;
      console.log("sellVip")
      console.log(">> req: ")
      console.log(req)
      console.log(">> res: ")
      console.log(res)
      if (res.code === 200) {
        msg = this.$t("common_success");
        this.getMerchantVipList(this.selectedVipLevel);
      } else if (res.code === 525) {
        msg = this.$t("sell_coins_user_not_exist");
      } else if (res.code === 810) {
        msg = this.$t("sell_vips_no_enough");
      } else {
        msg = "common_failed";
      }
      Toast({
        message: msg,
        duration: 2000,
      });
    },
    async getMerchantVipList(selectedVipLevel) {
      const res = await api.getMerchantVipList();
      if (res.code === 200) {
        const remainVips = res.data;
        if (remainVips) {
          remainVips.forEach((element, index) => {
            if (selectedVipLevel == undefined) {
              element.selected = index == 0;
            } else {
              element.selected = element.vipLevel == selectedVipLevel;
            }
            if (element.selected) {
              this.selectedVipLevel = element.vipLevel;
            }
          });
          this.remainVips = remainVips;
        }
      }
    },
    async onSearchUser() {
      if (this.shortId !== null && this.shortId !== "") {
        const res = await api.getUserInfoByGoodOrShortId(this.shortId);
        if (res.code === 200) {
          this.userInfo = res.data;
          this.showUserInfo = true;
        } else {
          this.userInfo = null;
          this.showUserInfo = true;
        }
      }
    },
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;

.sell-vip-root-container {
  width: 100%;
}

.sell-vip-bg {
  width: 100%;
  height: 100%;
  background: #f5f7fa;
  position: fixed;
}

.sell-vip-content-container {
  position: absolute;
  width: 750px;
  display: flex;
  flex-direction: column;
  background: white;
}

.header-title {
  display: flex;
  flex-direction: row;
  margin-top: 24px;
  padding-inline-start: 40px;
  padding-inline-end: 40px;
}

.remain-vips-title {
  font-size: 28px;
  font-family: SFUIText-Regular, SFUIText;
  font-weight: 400;
  color: #aaaaaa;
  line-height: 40px;
}

.purchase-price-container {
  display: flex;
  flex-direction: row;
  @include compat.margin-start(auto);
  @include compat.margin-end(0);
  margin-top: 4px;
}

.purchase-price-title {
  font-size: 24px;
  font-family: SFUIText-Regular, SFUIText;
  font-weight: 400;
  color: #777770;
  line-height: 29px;
}

.help-img {
  width: 32px;
  height: 32px;
  @include compat.margin-start(8px);
}

.remain-vips {
  width: 726px;
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  align-items: flex-start;
  padding-top: 8px;
  @include compat.margin-start(24px);
  @include compat.margin-end(0);
  padding-bottom: 40px;
}

.remain-vip-item {
  width: 295px;
  height: 120px;
  @include compat.margin-start(16px);
  margin-top: 16px;
}

.vip-sell-content {
  width: 702px;
  display: flex;
  background: #f5f7fa;
  flex-direction: column;
  align-items: center;
  padding-inline-start: 24px;
  padding-inline-end: 24px;
}

.vip-sell-search-container {
  width: 638px;
  background: #ffffff;
  border-radius: 24px;
  flex-direction: column;
  align-items: flex-start;
  padding-inline-start: 32px;
  padding-inline-end: 32px;
  margin-top: 24px;
  padding-bottom: 32px;
}

.vip-sell-id-search-title-container {
  width: 638px;
  display: flex;
  flex-direction: row;
  margin-top: 32px;
}

.vip-sell-id-search-title {
  height: 40px;
  font-size: 32px;
  font-family: SFUIText-Regular, SFUIText;
  font-weight: 400;
  color: #25252f;
  line-height: 40px;
}

.vip-search-input-layout {
  width: 638px;
  height: 88px;
  background: white;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 16px;
}

.vip-search-input {
  font-size: 36px;
  font-family: SFUIText-Medium, SFUIText;
  font-weight: 500;
  color: #222222;
  line-height: 40px;
}

.vip-search-btn-bg {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 144px;
  height: 56px;
  background: #00e0d7;
  border-radius: 32px;
}

.vip-search-btn-text {
  font-size: 28px;
  font-family: SFUIText-Semibold, SFUIText;
  font-weight: 600;
  color: #ffffff;
  line-height: 34px;
}

.vip-search-btn {
  flex: 1;
}

.user-search-result {
  width: 638px;
  display: flex;
  flex-direction: column;
  margin-top: 32px;
}

.user-search-success {
  width: 638px;
  height: 104px;
  background: #f2f8fb;
  border-radius: 16px;
  display: flex;
  flex-direction: row;
  align-items: center;
}

.avatar {
  width: 72px;
  height: 72px;
  @include compat.margin-start(24px);
}

.user-level {
  @include compat.margin-start(16px);
}

.user-name {
  height: 40px;
  font-size: 26px;
  font-family: SFUIText-Regular, SFUIText;
  font-weight: 400;
  color: #25252f;
  line-height: 40px;
  @include compat.margin-start(8px);
}

.user-search-failed {
  width: 638px;
  height: 104px;
  background: #f2f8fb;
  border-radius: 16px;
  display: flex;
  flex-direction: row;
  align-items: center;
}

.error-img {
  width: 32px;
  height: 32px;
  @include compat.margin-start(24px);
}

.error-info {
  font-size: 26px;
  font-family: SFUIText-Regular, SFUIText;
  font-weight: 400;
  color: #fa5353;
  line-height: 40px;
  @include compat.margin-start(8);
}

.confirm-btn {
  width: 638px;
  height: 96px;
  background: rgba(0, 224, 215, 0.5);
  border-radius: 48px;
  position: fixed;
  bottom: 32px;
  left: 56px;
}

.confirm-title {
  height: 38px;
  font-size: 32px;
  font-family: SFUIText-Semibold, SFUIText;
  font-weight: 600;
  color: #ffffff;
  line-height: 38px;
}
</style>
