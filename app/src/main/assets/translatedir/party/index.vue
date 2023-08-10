<template>
  <div class="party-container">
    <div class="party-header">
      <van-image :src="titleImage" class="title-img"></van-image>
      <span class="activity-time">{{ time }}</span>
      <div class="count-down-view-container">
        <count-down-item
          v-bind:endTime="endTime"
          v-bind:type="1"
          v-if="activityValid"
        >
        </count-down-item>
      </div>
      <party-tab-layout
        class="party-tab-layout"
        :tabIndex="tabIndex"
        @on-tab-click="onTabClick($event, tabIndex)"
      ></party-tab-layout>
    </div>
    <div class="party-content-container">
      <party-rules
        v-show="showRulesTab"
        v-bind:activityConfig="activityConfig"
      ></party-rules>
      <party-rankings v-show="showRankingTab"></party-rankings>
    </div>
    <div class="bottom-create-entry">
      <party-bottom-item
        v-bind:activityConfig="activityConfig"
        v-bind:rankInfo="myRankInfo"
      ></party-bottom-item>
    </div>
  </div>
</template>
<script>
import CountDownItem from "../recharge/components/CountDownItem";
import PartyTabLayout from "./components/PartyTabLayout";
import PartyRules from "./components/PartyRules";
import PartyRankings from "./components/PartyRankings";
import PartyBottomItem from "./components/PartyBottomItem";
import Vue from "vue";
import { Image as VanImage } from "vant";
import moment from "moment";
import { setImmersionMode } from "../../common/bridge/index";
import { ACTIVITY_TYPE } from "./constant/constant";

Vue.use(VanImage);
export default {
  name: "Party",
  data() {
    return {
      tabIndex: 1,
      activityType: ACTIVITY_TYPE.NEW_YEAR,
    };
  },
  components: {
    "count-down-item": CountDownItem,
    "party-tab-layout": PartyTabLayout,
    "party-rules": PartyRules,
    "party-rankings": PartyRankings,
    "party-bottom-item": PartyBottomItem,
  },
  created() {
    setImmersionMode();
    document.title = this.$t("party_activity_title");
    this.getActivityConfig();
  },
  computed: {
    titleImage() {
      const languageCode = this.$i18n.locale;
      if (languageCode == "ar") {
        return require("./assets/party_header_ic_ar.png");
      } else if (languageCode == "zh" || languageCode === "zh-Hans") {
        return require("./assets/party_header_ic_zh.png");
      } else {
        return require("./assets/party_header_ic_en.png");
      }
    },
    time() {
      if (
        this.activityConfig &&
        this.activityConfig.beginTime &&
        this.activityConfig.endTime
      ) {
        const startTime = moment
          .unix(this.activityConfig.beginTime)
          .format("YYYY.MM.DD");
        const endTime = moment
          .unix(this.activityConfig.endTime)
          .format("YYYY.MM.DD");
        return this.$t("activity_time", { start: startTime, end: endTime });
      }
      return "";
    },
    endTime() {
      if (this.activityConfig && this.activityConfig.endTime) {
        return this.activityConfig.endTime;
      }
      return 0;
    },
    activityValid() {
      if (this.activityConfig && this.activityConfig.endTime) {
        const timestamp = new Date().getTime() / 1000;
        return timestamp < this.activityConfig.endTime;
      }
      return false;
    },
    activityConfig() {
      return this.$store.state.party.activityConfig;
    },
    myRankInfo() {
      return this.$store.state.party.myRankInfo;
    },
    showRulesTab() {
      return this.tabIndex == 0;
    },
    showRankingTab() {
      return this.tabIndex == 1;
    },
  },
  methods: {
    async onTabClick(tabIndex) {
      this.tabIndex = tabIndex;
    },
    getActivityConfig() {
      this.$store.dispatch("party/getActivityConfig", {
        activityType: this.activityType,
      });
    },
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
.party-container {
  width: 750px;
  height: 100%;
  display: flex;
  flex-direction: column;
}
.party-header {
  width: 750px;
  height: 716px;
  display: flex;
  flex-direction: column;
  align-items: center;
  .title-img {
    width: 750px;
    height: 716px;
    z-index: -1;
  }
  .activity-time {
    height: 29px;
    font-size: 24px;
    font-family: SFUIText-Medium, SFUIText;
    font-weight: 500;
    color: #2e44d6;
    line-height: 29px;
    margin-top: -406px;
  }
  .count-down-view-container {
    margin-top: 74px;
    height: 52px;
    display: flex;
    flex-direction: column;
  }
  .party-tab-layout {
    margin-top: 70px;
  }
}
.party-content-container {
  width: 750px;
  min-height: 1000px;
  padding-bottom: 200px;
  background: #4e38df;
}
.bottom-create-entry {
  position: fixed;
  bottom: 0;
  z-index: 4;
}
</style>