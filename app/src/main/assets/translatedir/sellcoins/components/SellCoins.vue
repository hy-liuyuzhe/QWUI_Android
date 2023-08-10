<template>
  <div class="root-container">
    <div class="sell-container">
      <div class="sell-header">
        <div class="remaining-coins-layout">
          <span class="remaining-coins-text">{{
            $t("sell_coins_remaining")
          }}</span>
        </div>
        <div class="my-coins-layout">
          <van-image
            :src="require('../../common/assets/common_coin_48_ic.png')"
            width="24"
            height="24"
          ></van-image>
          <span class="my-coins-text">{{ myCoins }}</span>
        </div>
      </div>
      <div class="sell-main">
        <div class="sell-content">
          <div :class="showUserInfo ? 'search-layout' : 'search-layout-short'">
            <div class="search-title-layout">
              <span class="search-title">{{ $t("sell_coins_input") }}</span>
            </div>
            <div class="search-input-layout">
              <van-field
                v-model="shortId"
                type="number"
                class="search-input"
                :placeholder="$t('sell_coins_enter_id')"
                :input-align="language === 'ar' ? 'right' : 'left'"
              ></van-field>
              <div class="search-btn" @click="onSearchUser">
                <div class="search-btn-bg">
                  <span class="search-btn-text">{{
                    $t("sell_coins_search")
                  }}</span>
                </div>
              </div>
            </div>
            <template v-if="userInfo !== null">
              <div class="search-bottom-layout">
                <div class="search-bottom-item">
                  <van-image
                    :src="userInfo.url"
                    class="user-avatar"
                    round
                  ></van-image>
                  <user-level-item
                    class="user-level"
                    v-bind:level="userInfo.level"
                  ></user-level-item>
                  <span class="user-name">{{ userInfo.name }}</span>
                </div>
              </div>
            </template>
            <template v-else-if="userInfo === null && showUserInfo">
              <div class="search-bottom-layout">
                <div class="search-bottom-item">
                  <div class="error-layout">
                    <van-image
                      :src="require('../assets/warn_icon.png')"
                      width="18"
                      height="18"
                    ></van-image>
                    <span class="error-info">{{
                      $t("sell_coins_user_not_exist")
                    }}</span>
                  </div>
                </div>
              </div>
            </template>
          </div>
          <div class="sell-layout">
            <div class="sell-title-layout">
              <span class="sell-title">{{ $t("sell_coins_amount") }}</span>
            </div>
            <div class="sell-input-layout">
              <van-field
                v-model="sellCoins"
                type="number"
                class="sell-input"
                :placeholder="$t('sell_coins_input_quantity')"
                :input-align="language === 'ar' ? 'right' : 'left'"
              ></van-field>
            </div>
            <div class="my-line"></div>
          </div>
          <div class="precautions-layout">
            <span class="precautions-text">{{
              $t("sell_coins_precautions")
            }}</span
            ><br />
            <span class="precautions-text">{{ $t("sell_coins_note1") }}</span
            ><br />
            <span class="precautions-text">{{ $t("sell_coins_note2") }}</span
            ><br />
            <span class="precautions-text">{{ $t("sell_coins_note3") }}</span>
          </div>
        </div>
      </div>
      <div class="sell-bottom">
        <template
          v-if="userInfo === null || sellCoins === '' || sellCoins === null"
        >
          <div class="confirm-btn-bg2">
            <span class="confirm-btn">{{ $t("sell_coins_confirm") }}</span>
          </div>
        </template>
        <template v-else>
          <div class="confirm-btn-bg" @click="onConfirm">
            <span class="confirm-btn">{{ $t("sell_coins_confirm") }}</span>
          </div>
        </template>
      </div>
    </div>
  </div>
</template>
<script>
import Vue from "vue";
import { Image as VanImage, Toast, Lazyload } from "vant";
import api from "../api/index";
import { Field } from "vant";
import { getLanguageCode } from "../../../common/locale";
import UserLevelItem from "../../common/components/UserLevelItem";
import { stat, getMyUserInfo } from "../../../common/bridge/index"
import moment from 'moment';

Vue.use(VanImage);
Vue.use(Toast);
Vue.use(Lazyload);
Vue.use(Field);
export default {
  name: "SellCoins",
  data() {
    return {
      myCoins: 0,
      shortId: null,
      userInfo: null,
      sellCoins: null,
      showUserInfo: false,
      language: "en",
    };
  },
  components: {
    "user-level-item": UserLevelItem,
  },
  created() {
    this.onGetLanguage();
    this.onGetMyCoins();
  },
  computed: {},
  methods: {
    async onGetLanguage() {
      this.language = await getLanguageCode();
    },
    async onGetMyCoins() {
      const res = await api.getMyCoins();
      if (res.code === 200) {
        const currency = res.data.currencyInfo[0];
        if (currency) {
          this.myCoins = currency.currencyValue;
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
    async onConfirm() {
      if (
        this.userInfo === "" ||
        this.sellCoins === "" ||
        this.sellCoins === null
      ) {
        return;
      }

      let myUserInfo = await getMyUserInfo()
      if (myUserInfo === null || myUserInfo === undefined) {
        return
      }

      // 订单id格式
      // COIN_SELL:{币商sid}:{买币者uid}:{时间戳}
      let orderId = "COIN_SELL:" + myUserInfo.sid + ":" + this.userInfo.uid + ":" + moment().valueOf()
      console.log("onConfirm, orderId: " + orderId)
      let req = {
        buyer: this.userInfo.uid,
        coinAmount: this.sellCoins,
        seqId: Date.parse(new Date()),
      };
      const res = await api.currencySellCoin(req);
      let msg;
      if (res.code === 200) {
        msg = this.$t("sell_coins_success");
        this.onGetMyCoins();
        this.reportPurchase(orderId, this.sellCoins)
      } else if (res.code === 955) {
        msg = this.$t("sell_coins_not_same_region");
      } else if (res.code === 532) {
        msg = this.$t("sell_coins_too_frequent");
      } else if (res.code === 518) {
        msg = this.$t("sell_coins_insufficient_balance");
      } else {
        msg = "failed";
      }
      Toast({
        message: msg,
        duration: 2000,
      });
    },
    async reportPurchase(orderId, sellCoins) {
      await stat({
        event_id: "purchase",
        events: {
          transaction_id: orderId,
          currency: "USD",
          value: this.changeTwoDecimal(sellCoins / 1400), //按1$兑换1400金币进行换算
          items: "{\"item_id\": '1', \"item_name\" : \"gold coin " + sellCoins + "\"}"
        },
      });
    },
    // 功能：将浮点数四舍五入，取小数点后2位
    changeTwoDecimal(x) {
      var f_x = parseFloat(x);
      if (isNaN(f_x)) {
        return 0;
      }
      f_x = Math.round(f_x *100) / 100;
      return f_x;
    }
  },
  metaInfo: {
    meta: [
      {
        name: "viewport",
        content: "width=device-width, initial-scale=1, maximum-scale=1",
      },
    ],
  },
};
</script>
<style lang="scss" scoped>
.root-container {
  width: 100%;
}

.sell-container {
  width: 750px;
  position: relative;
  top: 24px;
}

.sell-header {
  height: 162px;
}

.remaining-coins-layout {
  height: 50px;
  top: 0px;
  margin: 0 40px;
  position: absolute;
}

.remaining-coins-text {
  font-size: 28px;
  font-family: SFUIText-Regular, SFUIText;
  font-weight: 400;
  color: #aaaaaa;
  line-height: 40px;
}

.my-coins-layout {
  height: 66px;
  top: 56px;
  margin: 0 40px;
  position: absolute;
}

.my-coins-text {
  margin: 0 10px;
  font-size: 64px;
  font-family: DINAlternate-Bold, DINAlternate;
  font-weight: bold;
  color: #222222;
  line-height: 56px;
}

.sell-main {
  position: fixed;
  width: 750px;
  height: 100%;
  background: #f5f7fa;
}

.sell-content {
  width: 750px;
  height: 80%;
  display: flex;
  flex-flow: column;
}

.search-layout {
  margin: 20px;
  width: 702px;
  height: 324px;
  background: #ffffff;
  border-radius: 24px;
}

.search-layout-short {
  margin: 20px;
  width: 702px;
  height: 208px;
  background: #ffffff;
  border-radius: 24px;
}

.search-title-layout {
  margin: 32px;
  text-align: start;
}

.search-title {
  font-size: 32px;
  font-family: SFUIText-Regular, SFUIText;
  font-weight: 400;
  color: #25252f;
  line-height: 40px;
}

.search-btn-bg {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 144px;
  height: 56px;
  background: #00e0d7;
  border-radius: 32px;
}

.search-btn-text {
  font-size: 28px;
  font-family: SFUIText-Semibold, SFUIText;
  font-weight: 600;
  color: #ffffff;
  line-height: 34px;
}

.search-input-layout {
  margin: 0 20px;
  width: 638px;
  height: 88px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.search-input {
  /*flex: 5;*/
  font-size: 36px;
  font-family: SFUIText-Medium, SFUIText;
  font-weight: 500;
  color: #222222;
  line-height: 40px;
}

.search-btn {
  flex: 1;
}

.search-bottom-layout {
  margin: auto;
  width: 638px;
  height: 104px;
  background: #f2f8fb;
  border-radius: 16px;
  align-items: center;
}

.search-bottom-text {
  font-size: 26px;
  font-family: SFUIText-Regular, SFUIText;
  font-weight: 400;
  color: #25252f;
  line-height: 40px;
}

.search-bottom-item {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  margin: 16px 8px;
  position: absolute;
}

.error-layout {
  position: relative;
  text-align: start;
  margin: 16px;
}

.level-icon {
  width: 25px;
  height: 26px;
}

.level-text {
  color: rgba(255, 255, 255, 1);
  font-size: 20px;
  font-family: SFUIText-Semibold;
  white-space: nowrap;
  line-height: 24px;
}

.user-avatar {
  width: 72px;
  height: 72px;
  margin: 0 16px;
}

.user-name {
  margin: 0 10px;
  font-size: 26px;
  font-family: SFUIText-Regular;
  font-weight: 400;
  color: #25252f;
  line-height: 40px;
}

.sell-layout {
  margin: 0 20px;
  width: 702px;
  height: 208px;
  background: #ffffff;
  border-radius: 24px;
}

.sell-title-layout {
  margin: 32px;
  text-align: start;
}

.sell-title {
  font-size: 32px;
  font-family: SFUIText-Regular, SFUIText;
  font-weight: 400;
  color: #25252f;
  line-height: 40px;
}

.sell-input-layout {
  margin: 0 20px;
  width: 638px;
  height: 68px;
  justify-content: center;
  align-items: center;
}

.sell-input {
  font-size: 36px;
  font-family: SFUIText-Medium, SFUIText;
  font-weight: 500;
  color: #222222;
  line-height: 40px;
}

.my-line {
  margin: auto;
  width: 638px;
  height: 1px;
  background: #ebebec;
}

.precautions-layout {
  margin: 20px 30px;
  width: 702px;
  text-align: start;
}

.precautions-text {
  font-size: 28px;
  font-family: SFUIText-Regular, SFUIText;
  font-weight: 400;
  color: #aaaaaa;
  line-height: 34px;
  word-wrap: break-word;
}

.confirm-btn-bg {
  position: relative;
  bottom: -40px;
  margin: auto;
  width: 638px;
  height: 96px;
  background: #00e0d7;
  border-radius: 48px;
}

.confirm-btn-bg2 {
  position: relative;
  bottom: -40px;
  margin: auto;
  width: 638px;
  height: 96px;
  background: rgba(0, 224, 215, 0.5);
  border-radius: 48px;
}

.confirm-btn {
  position: relative;
  top: 30px;
  font-size: 32px;
  font-family: SFUIText-Semibold, SFUIText;
  font-weight: 600;
  color: #ffffff;
  line-height: 38px;
}

.sell-bottom {
  position: fixed;
  bottom: 10px;
  width: 750px;
  height: 160px;
  margin: auto;
}

.error-info {
  position: relative;
  top: -6px;
  margin: 0 10px;
  font-size: 26px;
  font-family: SFUIText-Regular, SFUIText;
  font-weight: 400;
  color: #fa5353;
  line-height: 40px;
}
</style>
