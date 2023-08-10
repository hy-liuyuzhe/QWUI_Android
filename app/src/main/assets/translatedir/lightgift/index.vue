<template>
  <div class="bg">
    <van-image class="title-icon" :src="titleImage" />
    <span class="date-text">{{ formatDate }}</span>
    <div class="reward-layout">
      <span class="top-text">{{ $t("light_gift_reward_top_tip") }}</span>
      <reward-content
        class="content-layout"
        :goodsDay="rewardGoodsDay"
        :exp="rewardExp"
      />
      <span class="bottom-text">{{ $t("light_gift_reward_bottom_tip") }}</span>
    </div>

    <div class="light-gift-layout">
      <span class="light-tip-text-view">{{ $t("light_gift_light_tip") }}</span>
      <div class="gift-item-layout">
        <div
          :class="[
            lightGift.times > 0
              ? 'gift-light-icon-layout'
              : 'gift-unlight-icon-layout',
          ]"
        >
          <van-image
            :class="[lightGift.times > 0 ? 'gift-icon' : 'unlight-gift-icon']"
            :src="lightGift.giftImg"
          />
        </div>
        <span
          :class="[lightGift.times > 0 ? 'light-num-text' : 'unlight-num-text']"
        >
          {{ lightGift.giftName }}
        </span>
      </div>
      <div class="light-times-layout">
        <span class="light-times-text-view">
          {{ $t("light_gift_all_light_times", { times: lightGift.times }) }}
        </span>
      </div>
    </div>

    <div class="name-gift-layout">
      <van-image class="title-icon" :src="nameGiftTitleImage" />
      <van-image
        class="name-gift-hint-icon"
        :src="require('./assets/name_gift_hint_ic.png')"
      />
    </div>
    <van-popup v-model="showNormalReward">
      <reward-result
        :result="normalRewardResult"
        @ok-btn-click="onNormalRewardOkClick($event)"
      />
    </van-popup>
    <van-popup v-model="showAdvancedReward">
      <reward-result
        :result="advancedRewardResult"
        @ok-btn-click="onAdvancedRewardOkClick($event)"
      />
    </van-popup>
  </div>
</template>

<script>
import _ from "lodash";
import Vue from "vue";
import { Toast, Popup, Image as VanImage } from "vant";
import moment from "moment";
import api from "./api/index";
import { setImmersionMode } from "../../common/bridge/index";
import RewardResult from "./components/RewardResult.vue";
import RewardContent from "./components/RewardContent";
import { RES_COMMON_CODE } from "../../common/network/constant";
import { LIGHT_ALL_GIFTS_TIMES } from "./storage/key";
import { getMyUid } from "../../common/bridge/index";

Vue.use(Popup);
Vue.use(Toast);
Vue.use(VanImage);

export default {
  name: "LightGift",
  components: {
    "reward-content": RewardContent,
    "reward-result": RewardResult,
  },
  created() {
    setImmersionMode();
    this.getLightUpActivity();
    // this.getLightUpRewardInfo();
  },
  data: function () {
    return {
      startTime: 0,
      endTime: 0,
      rewardGoodsDay: 30,
      rewardExp: 30,
      namedGiftChannces: 0,
      showNormalReward: false,
      normalRewardResult: null,
      showAdvancedReward: false,
      advancedRewardResult: null,
      lightGifts: [],
      lightGift: {
        times: 0,
        giftImg:
          "https://wayak-resource.wenext.media/gift/meteor_shower.png",
        giftName: "Meteor shower",
      },
    };
  },
  methods: {
    async getLightUpActivity() {
      const { code, data } = await api.getLightUpActivity();
      if (code === RES_COMMON_CODE.SUCCESS) {
        const { startTime, endTime, lightUpGiftInfoList } = data;
        this.startTime = startTime;
        this.endTime = endTime;
        if (!_.isEmpty(lightUpGiftInfoList)) {
          this.lightGift = lightUpGiftInfoList[0];
          this.lightGift.giftName = "Meteor shower";
          const times = _.floor(this.lightGift.times / 10);
          const uid = await getMyUid();
          const normalRewardStorageKey = `${LIGHT_ALL_GIFTS_TIMES}_meteor_shower_${uid}_normal_1`;
          if (times > 0 && !this.$localStorage.get(normalRewardStorageKey)) {
            this.$localStorage.set(normalRewardStorageKey, true);
            this.normalRewardResult = {
              goodsDay: 30,
              diamond: 30,
              times: this.lightGift.times,
            };
            this.showNormalReward = true;
          }
          const advancedRewardStorageKey = `${LIGHT_ALL_GIFTS_TIMES}_meteor_shower_${uid}_advanced_1`;
          if (times >= 3 && !this.$localStorage.get(advancedRewardStorageKey)) {
            this.$localStorage.set(advancedRewardStorageKey, true);
            this.advancedRewardResult = {
              vip4: require("./assets/reward_vip4_ic.png"),
              times: this.lightGift.times,
            };
            this.showAdvancedReward = true;
          }
        }
      } else {
        Toast(this.$t("common_failed"));
      }
    },
    async getLightUpRewardInfo() {
      const { code, data } = await api.getLightUpRewardInfo();
      if (code === RES_COMMON_CODE.SUCCESS) {
        const { namedGiftTimes, times } = data;
        this.namedGiftChannces = namedGiftTimes;
        this.normalRewardResult = data;
        const uid = await getMyUid();
        const storageKey = `${LIGHT_ALL_GIFTS_TIMES}_${uid}_1`;
        if (times > 0 && this.$localStorage.get(storageKey) < times) {
          this.$localStorage.set(storageKey, times);
          this.showNormalReward = true;
        }
      } else {
        Toast(this.$t("common_failed"));
      }
    },
    onNormalRewardOkClick() {
      this.showNormalReward = false;
    },
    onAdvancedRewardOkClick() {
      this.showAdvancedReward = false;
    },
  },
  computed: {
    titleImage() {
      let languageCode = this.$i18n.locale;
      if (languageCode === "ar") {
        return require(`./assets/title_ic_ar.png`);
      }

      if (languageCode === "zh" || languageCode === "zh-Hans") {
        return require(`./assets/title_ic_zh.png`);
      }

      return require(`./assets/title_ic_en.png`);
    },
    nameGiftTitleImage() {
      let languageCode = this.$i18n.locale;
      if (languageCode === "ar") {
        return require(`./assets/name_gift_title_ic_ar.png`);
      }

      if (languageCode === "zh" || languageCode === "zh-Hans") {
        return require(`./assets/name_gift_title_ic_zh.png`);
      }

      return require(`./assets/name_gift_title_ic_en.png`);
    },
    formatDate() {
      return `
      ${moment.unix(this.startTime / 1000).format("YYYY.MM.DD")}
      -
      ${moment.unix(this.endTime / 1000).format("YYYY.MM.DD")}
      `;
    },
  },
  metaInfo: {
    title: "Light Gift",
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
@import "../common/css/index.scss";

.bg {
  width: 750px;
  display: flex;
  flex-direction: column;
  align-items: center;
  background: #ff88a9;
  background-image: url("./assets/header_bg.png");
  background-size: 100%;
  background-repeat: no-repeat;
  padding-bottom: 79px;
  .title-icon {
    width: 720px;
    height: 128px;
    margin-top: 394px;
  }
  .date-text {
    height: 29px;
    font-size: 24px;
    font-family: SFUIText-Regular, SFUIText;
    font-weight: 400;
    color: #fffbb8;
    line-height: 29px;
    margin-top: 15px;
  }
  .reward-layout {
    width: 710px;
    height: 452px;
    margin-top: 24px;
    background-image: url("./assets/reward_bg.png");
    background-size: 100%;
    background-repeat: no-repeat;
    display: flex;
    flex-direction: column;
    align-items: center;
    .top-text {
      @extend .single-line-text;
      height: 34px;
      margin-top: 56px;
      font-size: 28px;
      font-family: SFUIText-Semibold, SFUIText;
      font-weight: 600;
      color: #8b3512;
      line-height: 34px;
    }
    .content-layout {
      margin-top: 24px;
    }
    .bottom-text {
      height: 31px;
      margin-top: 22px;
      margin-inline-start: 35px;
      margin-inline-end: 35px;
      font-size: 26px;
      font-family: SFUIText-Semibold, SFUIText;
      font-weight: 600;
      color: #8b3512;
      line-height: 31px;
    }
  }

  .light-gift-layout {
    width: 712px;
    height: 600px;
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-top: 4px;
    background-image: url("./assets/gifts_light_bg.png");
    background-size: 100%;
    background-repeat: no-repeat;
    .light-tip-text-view {
      font-size: 26px;
      font-family: SFUIText-Medium, SFUIText;
      font-weight: 500;
      color: #fdefcf;
      line-height: 31px;
      margin-top: 64px;
      margin-inline-start: 40px;
      margin-inline-end: 40px;
    }
    .gift-item-layout {
      width: 274px;
      height: 327px;
      display: flex;
      flex-direction: column;
      align-items: center;
      margin-top: 24px;
      .gift-icon-layout {
        width: 274px;
        height: 290px;
        background-size: 100%;
        background-repeat: no-repeat;
        .gift-icon {
          position: relative;
          width: 226px;
          height: 226px;
          top: 50%;
          transform: translateY(-50%);
        }
        .unlight-gift-icon {
          @extend .gift-icon;
          opacity: 0.5;
        }
      }
      .gift-light-icon-layout {
        @extend .gift-icon-layout;
        background-image: url("./assets/gift_light_item_bg.png");
      }
      .gift-unlight-icon-layout {
        @extend .gift-icon-layout;
        background-image: url("./assets/gift_unlight_item_bg.png");
      }
      .num-text {
        @extend .single-line-text;
        height: 29px;
        font-size: 24px;
        font-family: SFUIText-Medium, SFUIText;
        font-weight: 500;
        line-height: 29px;
        margin-top: 8px;
      }
      .light-num-text {
        @extend .num-text;
        color: #fdefcf;
      }
      .unlight-num-text {
        @extend .num-text;
        color: rgba(253, 239, 207, 0.5);
      }
    }
    .light-times-layout {
      width: 560px;
      height: 72px;
      background: linear-gradient(
        270deg,
        #f1496c 0%,
        rgba(254, 199, 136, 0.4) 49%,
        #f1496c 100%
      );
      display: flex;
      flex-direction: row;
      align-items: center;
      justify-content: center;
      margin-top: 24px;
      .light-times-text-view {
        font-size: 26px;
        font-family: SFUIText-Medium, SFUIText;
        font-weight: 500;
        color: #fdefcf;
        line-height: 31px;
      }
    }
  }

  .name-gift-layout {
    width: 712px;
    height: 751px;
    margin-top: 11px;
    display: flex;
    flex-direction: column;
    align-items: center;
    background-image: url("./assets/name_gift_bg.png");
    background-size: 100%;
    background-repeat: no-repeat;
    .title-icon {
      width: 633px;
      height: 123px;
      margin-top: 64px;
    }
    .name-gift-hint-icon {
      width: 654px;
      height: 400px;
    }
    .instruction-text {
      width: 650px;
      margin-top: 24px;
      font-size: 28px;
      font-family: SFUIText-Medium, SFUIText;
      font-weight: 500;
      color: #fdefcf;
      line-height: 34px;
      text-align: start;
      white-space: pre-line;
    }
  }
}
</style>