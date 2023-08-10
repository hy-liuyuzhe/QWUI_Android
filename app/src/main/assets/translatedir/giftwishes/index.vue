<template>
  <page
    bgColor="linear-gradient(180deg, #4AD7D2 0%, #4AD7D2 100%)"
    contentBGColor="#FFFFFF"
    :navigation="{
      backgroundImage: require('./assets/gift_wishes_title_bg.png'),
      style: 1,
    }"
  >
    <div class="page-content-v-layout">
      <van-image
        class="head-image-view"
        :src="titleImage"
      ></van-image>
      <span class="time-text-view">{{ time }}</span>
      <div class="gift-wishes-content-v-layout">
        <my-gift-wishes></my-gift-wishes>
        <gift-wish-award
          class="gift-wish-award"
          :levelInfos="levelInfos"
          :helpNum="helpNum"
          :configInfos="configInfos"
        ></gift-wish-award>
      </div>
    </div>
    <van-popup v-model="showVersionUpdateDialog" get-container="body" round>
      <gift-wish-dialog
        :messageText="
          $t('gift_wishes_update_version_tip', { lv: dialogRewardLevels })
        "
        :primaryButtonText="$t('common_go_update')"
        @on-primary-button-click="onVersionUpdateDialogGoUpdateClick"
      >
      </gift-wish-dialog>
    </van-popup>
    <van-popup v-model="showWinGoodDeliveredDialog" get-container="body" round>
      <gift-wish-dialog
        :titleText="$t('common_congratulations')"
        :messageText="
          $t('gift_wishes_package_delivered', { lv: dialogRewardLevels })
        "
        :primaryButtonText="$t('common_ok')"
        @on-primary-button-click="onWinGoodDeliveredDialogDialogOKClick"
      >
      </gift-wish-dialog>
    </van-popup>
  </page>
</template>
<script>
import Page from "@/common/components/Page.vue";
import Vue from "vue";
import { Image as VanImage, Popup } from "vant";
import MyGiftWishes from "./components/MyGiftWishes";
import GiftWishAward from "./components/GiftWishAward";
import api from "./api/index";
import _ from "lodash";
import moment from "moment";
import { Toast } from "vant";
import GiftWishDialog from "./components/GiftWishDialog";
import {
  android,
  getVersionCode,
  hideNavigationBar,
} from "../../common/bridge/index";
import {
  GIFT_WISH_WIN_GOOD_DELIVERED_TIP_LEVEL,
  GIFT_WISH_GOOD_DELIVERED_TIP_LEVEL_TIME,
} from "./storage/key";
import { isSameMonth } from "./../../common/util/time/index";

Vue.use(VanImage);
Vue.use(Popup);
Vue.use(Toast);
export default {
  name: "GiftWishes",
  components: {
    page: Page,
    "my-gift-wishes": MyGiftWishes,
    "gift-wish-award": GiftWishAward,
    "gift-wish-dialog": GiftWishDialog,
  },
  data() {
    return {
      startTime: 0,
      endTime: 0,
      giftWishes: [],
      hasGiftWishes: false,
      levelInfos: [],
      helpNum: 0,
      configInfos: [],
      dialogRewardLevels: "",
      showVersionUpdateDialog: false,
      showWinGoodDeliveredDialog: false,
    };
  },
  created() {
    hideNavigationBar();
    this.checkVersion();
    this.requestRamadanActivityInfo();
  },
  computed: {
    time() {
      if (this.startTime && this.endTime) {
        const startTime = moment
          .unix(this.startTime / 1000)
          .format("MM.DD HH:mm");
        const endTime = moment.unix(this.endTime / 1000).format("MM.DD HH:mm");
        var timestamp = new Date().getTime() / 1000;
        if (timestamp > this.endTime) {
          Toast(this.$t("activity_end"));
        }
        return this.$t("activity_time", { start: startTime, end: endTime });
      } else {
        return "";
      }
    },
    titleImage() {
      const languageCode = this.$i18n.locale;
      if (languageCode == "ar") {
        return require("./assets/gift_wishes_head_bg_ar.png");
      } else if (languageCode == "zh") {
        return require("./assets/gift_wishes_head_bg_zh.png");
      } else {
        return require("./assets/gift_wishes_head_bg_en.png");
      }
    },
  },
  methods: {
    async checkVersion() {
      let isAndroid = await android();
      if (isAndroid) {
        let versionCode = await getVersionCode();
        if (versionCode && versionCode <= 79) {
          this.showVersionUpdateDialog = true;
        }
      }
    },
    async requestRamadanActivityInfo() {
      const res = await api.getRamadanActivityInfo();
      if (res.code === 200) {
        let data = res.data;
        this.startTime = data.startTime;
        this.endTime = data.endTime;
        this.helpNum = data.totalNum;
        var levelInfos = new Array();
        var configInfos = new Array();
        const map = new Map(Object.entries(data.levelInfo));
        for (var i = 0; i < map.size; i++) {
          levelInfos.push(data.levelInfo[`${i + 1}`]);
          configInfos.push(data.configInfo[`${i + 1}`]);
        }
        this.levelInfos = levelInfos;
        this.configInfos = configInfos;
        this.popupWinGoodDeliveredDialogIfNeed();
      }
    },
    onVersionUpdateDialogGoUpdateClick() {
      this.showVersionUpdateDialog = false;
      location.href = "market://details?id=com.wenext.wayak";
    },
    onWinGoodDeliveredDialogDialogOKClick() {
      this.showWinGoodDeliveredDialog = false;
    },
    popupWinGoodDeliveredDialogIfNeed() {
      const shownTime = this.$localStorage.get(
        GIFT_WISH_GOOD_DELIVERED_TIP_LEVEL_TIME,
        0
      );
      var shownMaxRewardLevel = this.$localStorage.get(
        GIFT_WISH_WIN_GOOD_DELIVERED_TIP_LEVEL,
        0
      );
      const currentTimeMs = new Date().getTime();
      if (shownTime != 0 && !isSameMonth(shownTime, currentTimeMs)) {
        shownMaxRewardLevel = 0;
      }
      var rewardLevels = "";
      var maxRewardLevel = shownMaxRewardLevel;
      for (var i = 0; i < this.levelInfos.length; i++) {
        if (this.helpNum >= this.levelInfos[i] && i + 1 > shownMaxRewardLevel) {
          rewardLevels += `Lv${i + 1}、`;
          maxRewardLevel = i + 1;
        }
      }
      if (rewardLevels.length <= 0) {
        return;
      }
      rewardLevels = rewardLevels.slice(0, -1);
      this.dialogRewardLevels = rewardLevels;
      this.$localStorage.set(
        GIFT_WISH_WIN_GOOD_DELIVERED_TIP_LEVEL,
        maxRewardLevel
      );
      this.$localStorage.set(
        GIFT_WISH_GOOD_DELIVERED_TIP_LEVEL_TIME,
        currentTimeMs
      );
      this.showWinGoodDeliveredDialog = true;
    },
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";
@include v-layout(page-content) {
  position: relative;
  .head-image-view {
    position: absolute;
    top: 0;
    width: 750px;
    height: 618px;
  }
  @include text-view(
    time,
    $font-size: 28px,
    $line-height: 48px,
    $font-color: #01756f,
    $font-weight: $--font-weight-normal,
    $text-align: center
  ) {
    width: 420px;
    height: 48px;
    position: absolute;
    top: 192px;
    background-image: url("./assets/gift_wishes_time_bg.png");
    background-size: 100% 100%;
  }
  @include v-layout(gift-wishes-content) {
    position: absolute;
    top: 364px;
    .gift-wish-award {
      margin-top: 21px;
    }
  }
}
</style>
