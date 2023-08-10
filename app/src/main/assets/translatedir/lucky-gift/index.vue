<template>
  <div class="page">
    <img class="bg" src="./assets/lucky_gift_bg.png" />
    <div class="tip">{{ $t("lg_rule_tip") }}</div>
    <img src="./assets/lucky_gift_coin.png" width="100%"/>
    <div class="rule-container">
      <div class="rule-text" v-html="ruleContent"></div>
      <div class="rule-title-container">
        <div class="rule-title">{{ $t("lg_rule_title") }}</div>
      </div>
    </div>
    <div class="declaimer">{{ $t("lg_rule_declaimer") }}</div>
  </div>
</template>

<style lang="scss" scoped>
.page {
  display: flex;
  flex-direction: column;
  padding: 50px 30px 40px 30px;
  box-sizing: border-box;
  position: relative;
}

.bg {
  left: 0;
  top: 0;
  width: 100vw;
  height: 100vh;
  position: fixed;
  z-index: -1;
}
.tip {
  font-size: 40px;
  font-family: SFUIText-Bold, SFUIText;
  font-weight: bold;
  color: #fff8e0;
  line-height: 52px;
  padding: 0 90px 0 90px;
}

.rule-container {
  background-image: linear-gradient(135deg, #03b190, #038a7c);
  border-radius: 16px;
  border: 3px solid #ffe6b3;
  padding: 70px 28px 30px 28px;
  margin-top: 70px;
  margin-bottom: 60px;
  position: relative;
}

.rule-text {
  font-size: 28px;
  font-family: PingFangSC-Regular, PingFang SC;
  font-weight: 400;
  color: #fff8e0;
  line-height: 50px;
  text-align: start;
}

.rule-title-container {
  position: absolute;
  width: 426px;
  height: 80px;
  top: -40px;
  left: 0;
  right: 0;
  margin: 0 auto;
  font-size: 42px;
  font-family: PingFangSC-Semibold, PingFang SC;
  font-weight: 600;
  color: #ffffff;
  background-image: url("./assets/lucky_gift_title_bg.png");
  background-size: 100% 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.declaimer {
  font-size: 28px;
  font-family: PingFangSC-Regular, PingFang SC;
  font-weight: 400;
  color: #fff8e0;
  margin-top: auto;
}
</style>

<script>
import Vue from "vue";
import { Image as VanImage, Row, Col } from "vant";
// import { hideNavigationBar, closeWindow } from "../../common/bridge";
// import NavigationBar from "@/common/components/NavigationBar";
// import { NAVIGATION_BAR_STYLE } from "@/common/constant/index";
import _ from "lodash";
import commonApi from "@/common/api/index";
import { GLOBAL_CONFIG_TYPE } from "@/common/constant/index";
import { RES_COMMON_CODE } from "../../common/network/constant";

Vue.use(VanImage);
Vue.use(Row);
Vue.use(Col);

export default {
  name: "LuckyGift",
  components: {
    // "navigation-bar": NavigationBar,
  },
  data() {
    return {
      shareRatio: 0.8,
    };
  },
  created() {
    // hideNavigationBar();
    document.title = this.$t("lg_title");
    this.getLuckyGiftShareRatioConfig();
  },
  computed: {
    ruleContent() {
      return (
        this.$t("lg_rule_content_1") +
        "<br>" +
        this.$t("lg_rule_content_2") +
        "<br>" +
        this.$t("lg_rule_content_3") +
        "<br>" +
        this.$t("lg_rule_content_4", { ratio: `${this.shareRatio * 100}%` }) +
        "<br>" +
        this.$t("lg_rule_content_5") +
        "<br>" + 
        this.$t("lg_rule_content_6")
      );
    },
  },
  methods: {
    // onNavBackClick() {
    //   closeWindow()
    // },
    async getLuckyGiftShareRatioConfig() {
      const { code, data } = await commonApi.getGlobalConfig([
        GLOBAL_CONFIG_TYPE.GLOBAL_LUCKYGIFT_SHARING_PERCENT,
      ]);
      if (code !== RES_COMMON_CODE.SUCCESS) {
        return;
      }
      if (_.isEmpty(data.configs)) {
        return;
      }
      let configList =
        data.configs[GLOBAL_CONFIG_TYPE.GLOBAL_LUCKYGIFT_SHARING_PERCENT];
      if (_.isEmpty(configList)) {
        return;
      }
      const ratio = parseFloat(configList[0]);
      if (_.isNil(ratio)) {
        return;
      }
      this.shareRatio = ratio;
    },
  },
};
</script>
