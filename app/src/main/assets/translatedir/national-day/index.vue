<template>
  <div class="we-page">
    <van-image class="top-bg-image-view" :src="topBgImgSrc" />
    <div class="content-v-layout">
      <span class="time-text-view">{{ activityTime }}</span>
      <div class="gift-v-layout">
        <div class="gift-title-h-layout">
          <span class="gift-title-text-view">
            {{
              $t("national_day_gift_title_country", {
                num: needFlagNum,
                country: $t(`${countryCode}`),
              })
            }}
          </span>
        </div>
        <div class="gift-icon-z-layout">
          <van-image
            v-if="giftInfo"
            class="gift-icon-image-view"
            :src="giftInfo.img"
          />
        </div>
        <progress-view
          class="margin-top-32px"
          :current="flagNum"
          :total="needFlagNum"
        />
      </div>
      <div class="rank-v-layout">
        <div class="rank-title-h-layout">
          <span class="rank-title-text-view">
            {{ $t("national_day_rank_title") }}
          </span>
        </div>
        <div class="rank-explain-h-layout">
          <span class="rank-explain-text-view">
            {{ $t("national_day_rank_explain") }}
          </span>
        </div>
        <div class="rank-h-layout">
          <rank-other-item-view
            :rank="2"
            :userInfo="flagRank2User"
            :flagImg="flagImg"
          />
          <rank-first-item-view :userInfo="flagRank1User" :flagImg="flagImg" />
          <rank-other-item-view
            :rank="3"
            :userInfo="flagRank3User"
            :flagImg="flagImg"
          />
        </div>
      </div>
      <div v-if="rewardInfo" class="reward-v-layout">
        <div class="reward-title-h-layout">
          <span class="reward-title-text-view">
            {{
              $t("national_day_reward_title", {
                times: needWinNum,
                days: rewardDays,
              })
            }}
          </span>
        </div>
        <progress-view
          class="margin-top-52px"
          :current="winNum"
          :total="needWinNum"
        />
        <div class="reward-icon-z-layout">
          <van-image class="reward-icon-image-view" :src="rewardInfo.img" />
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import _ from "lodash";
import moment from "moment";
import Vue from "vue";
import { Image as VanImage, Toast, Popup } from "vant";
import { setImmersionMode, closeWindow } from "../../common/bridge";
import { NAVIGATION_BAR_STYLE } from "@/common/constant/index";
import ProgressView from "@/national-day/components/ProgressView";
import RankFirstItemView from "@/national-day/components/RankFirstItemView";
import RankOtherItemView from "@/national-day/components/RankOtherItemView";
import api from "./api/index";
import { RES_COMMON_CODE } from "../../common/network/constant";

Vue.use(VanImage);
Vue.use(Toast);
Vue.use(Popup);

export default {
  name: "NationalDay",
  components: {
    "progress-view": ProgressView,
    "rank-first-item-view": RankFirstItemView,
    "rank-other-item-view": RankOtherItemView,
  },
  data() {
    return {
      navigationStyle: NAVIGATION_BAR_STYLE.LIGHT,
      startTime: 0,
      endTime: 0,
      rewardInfo: null,
      giftInfo: null,
      needFlagNum: 500,
      flagNum: 0,
      needWinNum: 10,
      winNum: 0,
      flagRank1User: {
        uid: 0,
        name: "",
        avatar: require('@/common/assets/common_default_avatar_ic.png'),
        num: 0,
      },
      flagRank2User: {
        uid: 0,
        name: "",
        avatar: require('@/common/assets/common_default_avatar_ic.png'),
        num: 0,
      },
      flagRank3User: {
        uid: 0,
        name: "",
        avatar: require('@/common/assets/common_default_avatar_ic.png'),
        num: 0,
      },
      headImg: "",
      countryCode: "",
      flagImg: "",
    };
  },
  created() {
    setImmersionMode();
    this.getActivityInfo();
  },
  methods: {
    onNavBackClick() {
      window.history.length > 1 ? this.$router.go(-1) : closeWindow();
    },
    async getActivityInfo() {
      const { code, data } = await api.getFlagRaisingActivityInfo();
      if (code !== RES_COMMON_CODE.SUCCESS) {
        Toast(this.$t("common_failed_with_errorcode", { code }));
        return;
      }

      const {
        startTime,
        endTime,
        rewardInfos,
        giftInfos,
        needFlagNum,
        needWinNum,
        flagNum,
        winNum,
        flagUserInfoList,
        headImg,
        countryCode,
        flagImg,
      } = data;
      this.startTime = startTime;
      this.endTime = endTime;
      this.rewardInfo = _.isEmpty(rewardInfos) ? null : rewardInfos[0];
      this.giftInfo = _.isEmpty(giftInfos) ? null : giftInfos[0];
      this.needFlagNum = needFlagNum;
      this.flagNum = flagNum;
      this.needWinNum = needWinNum;
      this.winNum = winNum;
      this.headImg = headImg;
      this.countryCode = countryCode;
      this.flagImg = flagImg;
      flagUserInfoList.forEach((userInfo, index) => {
        if (_.isEmpty(userInfo.avatar)) {
          userInfo.avatar = require('@/common/assets/common_default_avatar_ic.png');
        }
        if (index === 0) {
          this.flagRank1User = userInfo;
        } else if (index === 1) {
          this.flagRank2User = userInfo;
        } else if (index === 2) {
          this.flagRank3User = userInfo;
        }
      });

      const currentTime = new Date().getTime();
      if (currentTime > endTime) {
        Toast(this.$t("common_activity_end"));
      }
    },
  },
  computed: {
    topBgImgSrc() {
      if (_.isEmpty(this.headImg)) {
        return "";
      }
      const languageCode = this.$i18n.locale;
      let headImg = this.headImg;
      const imgObj = JSON.parse(headImg);
      let defaultImg = imgObj.en;
      var img = ""
      if (languageCode === "en") {
        img = imgObj.en;
      } else if (languageCode === "ar") {
        img = imgObj.ar;
      } else if (languageCode === "hi") {
        img = imgObj.hi;
      } else if (languageCode === "tr") {
        img = imgObj.tr;
      } else if (languageCode === "zh") {
        img = imgObj.zh;
      } else {
        return ""
      }
      console.log("topBgImgSrc, img:" + img)
      if (img === null || img === undefined || _.isEmpty(img)) {
        return defaultImg
      } else {
        return img
      }
    },
    activityTime() {
      let time = `${moment(this.startTime).format("MM.DD")}-${moment(
        this.endTime
      ).format("MM.DD")}`;
      return this.$t("common_activity_time", { time });
    },
    rewardDays() {
      return this.rewardInfo.rewardValue / (24 * 60 * 60);
    },
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";

@include we(page) {
  width: 750px;
  box-sizing: border-box;
  background: linear-gradient(0deg, #190f04 0%, #26190a 91%);

  @include image-view(top-bg, 750px, 998px) {
    position: absolute;
    top: 0;
    left: 0;
  }

  @include v-layout(content) {
    position: relative;
    width: 100%;
    padding-bottom: 46px;

    @include text-view(
      time,
      $font-size: 32px,
      $line-height: 44px,
      $font-color: #ffe9c6,
      $font-weight: $--font-weight-medium
    ) {
      margin-top: 84px;
    }

    .margin-top-32px {
      margin-top: 32px;
    }

    .margin-top-52px {
      margin-top: 52px;
    }

    @include v-layout(gift) {
      width: 690px;
      height: 564px;
      background-image: url("./assets/national_day_common_item_bg.png");
      background-repeat: no-repeat;
      background-size: cover;
      margin-top: 837px;

      @include h-layout(gift-title) {
        width: 555px;
        height: 100px;
        @include text-view(
          gift-title,
          $font-size: 30px,
          $line-height: 44px,
          $font-color: #fff2c4,
          $font-weight: $--font-weight-medium,
          $text-align: center
        ) {
          width: 555px;
        }
      }

      @include z-layout(gift-icon) {
        width: 264px;
        height: 264px;
        background-image: url("./assets/national_day_reward_item_bg.png");
        background-size: cover;
        background-repeat: no-repeat;
        margin-top: 53px;

        @include image-view(gift-icon, 195px, 195px) {
          position: absolute;
          top: 50%;
          left: 50%;
          transform: translateX(-50%) translateY(-50%);
        }
      }
    }

    @include v-layout(rank) {
      width: 690px;
      height: 635px;
      background-image: url("./assets/national_day_rank_item_bg.png");
      background-repeat: no-repeat;
      background-size: cover;
      margin-top: 76px;

      @include h-layout(rank-title) {
        width: 555px;
        height: 100px;

        @include text-view(
          rank-title,
          $font-size: 30px,
          $line-height: 44px,
          $font-color: #fff2c4,
          $font-weight: $--font-weight-medium,
          $text-align: center
        ) {
          width: 555px;
        }
      }

      @include h-layout(rank-explain) {
        width: 650px;
        height: 130px;
        @include text-view(
          rank-explain,
          $font-size: 28px,
          $line-height: 44px,
          $font-color: #28c992,
          $font-weight: $--font-weight-medium,
          $text-align: center
        ) {
          width: 650px;
        }
      }

      @include h-layout(rank, flex-end) {
        width: 636px;
      }
    }

    @include v-layout(reward) {
      width: 690px;
      height: 564px;
      background-image: url("./assets/national_day_common_item_bg.png");
      background-repeat: no-repeat;
      background-size: cover;
      margin-top: 50px;

      @include h-layout(reward-title) {
        width: 555px;
        height: 100px;

        @include text-view(
          reward-title,
          $font-size: 30px,
          $line-height: 44px,
          $font-color: #fff2c4,
          $font-weight: $--font-weight-medium,
          $text-align: center
        ) {
          width: 555px;
        }
      }

      @include z-layout(reward-icon) {
        width: 264px;
        height: 264px;
        background-image: url("./assets/national_day_reward_item_bg.png");
        background-size: cover;
        background-repeat: no-repeat;
        margin-top: 14px;

        @include image-view(reward-icon, 212px, 212px) {
          position: absolute;
          top: 50%;
          left: 50%;
          transform: translateX(-50%) translateY(-50%);
        }
      }
    }
  }
}
</style>
