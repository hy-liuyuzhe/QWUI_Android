<template>
  <div class="star-root">
    <van-image :src="titleImage" class="top-img"></van-image>
    <!--        <van-image :src="titleImage" class="title-img"></van-image>-->
    <div class="rule-container" @click="onRuleClick">
      <van-image
        :src="require('./assets/star_rule_bg_new.png')"
        class="rule-bg-img"
      >
      </van-image>
      <span class="rule-text">{{ $t("rule") }}</span>
    </div>
    <div class="time-container">
      <span class="time-text">{{ time }}</span>
    </div>
    <star-tab-layout
      class="star-tab-layout"
      @on-tab-click="onTabClick($event, tabIndex)"
    >
    </star-tab-layout>
    <my-rank class="my-rank" v-bind:rankInfo="myRankInfo"></my-rank>
    <van-popup v-model="showRule">
      <star-list-rule
        @close-btn-click="onRuleCloseClick($event)"
      ></star-list-rule>
    </van-popup>
  </div>
</template>
<script>
import Vue from "vue";
import { Image as VanImage } from "vant";
import StarTabLayout from "./components/StarTabLayout";
import MyRank from "./components/MyRank";
import StarListRule from "./components/StarListRule";
import { Popup } from "vant";
import { RANK_LIST_INDEX, RANK_TYPE } from "./constant/constant";
import moment from "moment";
import { Toast } from "vant";
import { setImmersionMode } from "../../common/bridge/index";

Vue.use(Toast);
Vue.use(Popup);
Vue.use(VanImage);
export default {
  name: "StarList",
  data() {
    return {
      showRule: false,
      tabIndex: RANK_LIST_INDEX.STAR,
    };
  },
  metaInfo: {
    title: "Starlight list",
    meta: [
      {
        name: "viewport",
        content: "width=device-width, initial-scale=1, maximum-scale=1",
      },
    ],
  },
  components: {
    "star-tab-layout": StarTabLayout,
    "my-rank": MyRank,
    "star-list-rule": StarListRule,
  },
  beforeCreate() {
    document.querySelector("body").setAttribute("style", "background:#21190D");
  },
  created() {
    setImmersionMode();
  },
  computed: {
    titleImage() {
      const languageCode = this.$i18n.locale;
      if (languageCode == "ar") {
        return require("./assets/star_top_bg_ar_new.png");
      } else if (languageCode == "zh" || languageCode === "zh-Hans") {
        return require("./assets/star_top_bg_zh_new.png");
      } else {
        return require("./assets/star_top_bg_en_new.png");
      }
    },
    config() {
      return this.$store.state.starlist.config;
    },
    myRankInfo() {
      if (this.tabIndex == RANK_LIST_INDEX.STAR) {
        return this.$store.state.starlist.mineStarRankInfo;
      } else {
        return this.$store.state.starlist.mineRichRankInfo;
      }
    },
    time() {
      if (this.config && this.config.startTime && this.config.endTime) {
        const startTime = moment
          .unix(this.config.startTime / 1000)
          .format("MM.DD HH:mm");
        const endTime = moment
          .unix(this.config.endTime / 1000)
          .format("MM.DD HH:mm");
        var timestamp = new Date().getTime() / 1000;
        if (timestamp > this.config.endTime) {
          Toast(this.$t("activity_end"));
        }
        return this.$t("activity_time", { start: startTime, end: endTime });
      } else {
        return "";
      }
    },
  },
  watch: {
    "$store.state.starlist.config": {
      handler: function(config) {
        if (config && config.startTime && config.endTime) {
          var timestamp = new Date().getTime();
          if (timestamp > config.endTime) {
            Toast(this.$t("activity_end"));
          }
        }
      },
    },
  },
  methods: {
    onRuleClick() {
      this.showRule = true;
    },
    onRuleCloseClick() {
      this.showRule = false;
    },
    async onTabClick(tabIndex) {
      this.tabIndex = tabIndex;
    },
  },
};
</script>
<style lang="scss" scoped>
@import "../greedy/css/common.scss";
.star-root {
  width: 750px;
}
.top-img {
  width: 750px;
  height: 680px;
}
.star-content {
  display: flex;
  flex-direction: column;
  align-content: center;
}
.title-img {
  width: 520px;
  height: 300px;
  position: absolute;
  left: 0;
  right: 0;
  margin: auto;
  top: 138px;
}
.rule-container {
  width: 120px;
  height: 56px;
  position: absolute;
  top: 110px;
  right: 0px;
  text-align: center;
  line-height: 56px;
  .rule-bg-img {
    width: 120px;
    height: 56px;
    position: absolute;
    top: 0;
    left: 0;
  }
  .rule-text {
    font-size: 28px;
    font-family: SFUIText-Bold, SFUIText;
    font-weight: bold;
    color: #ffffff;
    line-height: 34px;
    position: absolute;
    top: 10px;
    right: 0;
    left: 0;
    margin: auto;
  }
}
.time-container {
  width: 360px;
  height: 48px;
  line-height: 48px;
  position: absolute;
  background-image: url("./assets/star_time_bg_new.png");
  background-size: 100% 100%;
  left: 0;
  right: 0;
  margin: auto;
  top: 392px;
  .time-text {
    @extend .single-line-text;
    width: 241px;
    height: 34px;
    font-size: 28px;
    font-family: SFUIText-Medium, SFUIText;
    font-weight: 500;
    color: #292018;
    line-height: 34px;
  }
}
.star-tab-layout {
  position: absolute;
  top: 510px;
  left: 0px;
}
.my-rank {
  width: 750px;
  height: 130px;
  position: fixed;
  bottom: 0;
}
#rule-root {
  width: 600px;
  display: flex;
  flex-direction: column;
  align-items: center;
}
</style>
