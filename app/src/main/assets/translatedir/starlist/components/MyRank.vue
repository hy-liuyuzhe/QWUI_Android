<template>
  <div>
    <div class="my-rank-root">
      <span class="my-rank-num">{{ rankNum }}</span>
      <van-image
        class="my-avatar"
        :src="rankInfo.avatar"
        round
        @click="onUserViewClick"
      ></van-image>
      <div class="my-name-container">
        <span class="my-name">{{ rankInfo.username }}</span>
        <span class="no-ranking-text" v-show="hasNoRanking">{{
          $t("no_ranking")
        }}</span>
      </div>
      <div class="my-score-container">
        <van-image class="my-score-type-icon" :src="scoreTypeIcon"></van-image>
        <span class="my-score-text">{{ rankInfo.amount }}</span>
      </div>
    </div>
  </div>
</template>
<script>
import Vue from "vue";
import { RANK_TYPE } from "../constant/constant";
import { isDeeplinkSupport } from "../../../common/bridge/index";
import { Image as VanImage } from "vant";

Vue.use(VanImage);
export default {
  name: "MyRank",
  props: {
    rankInfo: Object,
  },
  computed: {
    scoreTypeIcon() {
      if (this.rankInfo.type == RANK_TYPE.RANK_TYPE_RICH) {
        return require("../../common/assets/common_coin_28_ic.png");
      } else {
        return require("../assets/star_diamond.png");
      }
    },
    rankNum() {
      if (this.rankInfo.no > 0) {
        return this.rankInfo.no + "";
      } else {
        return "-";
      }
    },
    hasNoRanking() {
      return this.rankInfo.no <= 0;
    },
    scoreNum() {
      if (this.rankInfo.amount) {
        return this.rankInfo.amount;
      } else {
        return 0;
      }
    },
  },
  methods: {
    async onUserViewClick() {
      if (typeof this.rankInfo.uid === "undefined") {
        return;
      }

      var supportResult = await isDeeplinkSupport({ path: "/userProfile" });
      if (supportResult && supportResult.isSupport) {
        window.location.href =
          "wenext://wayak/userProfile?extra_uid=" + this.rankInfo.uid;
      }
    },
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@import "../../greedy/css/common.scss";
.my-rank-root {
  width: 750px;
  height: 130px;
  background-image: url("../assets/star_my_rank_bg_new.png");
  background-size: 100% 100%;
  display: flex;
  flex-direction: row;
  align-items: center;
}
.my-rank-num {
  font-size: 32px;
  font-family: DINAlternate-Bold, DINAlternate;
  font-weight: bold;
  color: #ffffff;
  line-height: 37px;
  @include compat.margin-start(39px);
}
.my-avatar {
  width: 70px;
  height: 70px;
  @include compat.margin-start(45px);
}
.my-name-container {
  margin-left: 24px;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  @include compat.margin-start(24px);

  .my-name {
    @extend .single-line-text;
    width: 361px;
    font-size: 28px;
    text-align: start;
    font-family: SFUIText-Medium, SFUIText;
    font-weight: 500;
    color: #ffffff;
    line-height: 34px;
  }
  .no-ranking-text {
    font-size: 24px;
    font-family: SFUIText-Regular, SFUIText;
    font-weight: 400;
    margin-top: 4px;
    color: #ffffff;
    line-height: 29px;
  }
}
.my-score-container {
  height: 31px;
  display: flex;
  @include compat.margin-start(auto);
  @include compat.margin-end(32px);

  flex-direction: row-reverse;
  .my-score-type-icon {
    width: 28px;
    height: 28px;
  }
  .my-score-text {
    font-size: 26px;
    font-family: SFUIText-Regular, SFUIText;
    font-weight: 400;
    color: #ffffff;
    line-height: 32px;
    @include compat.margin-end(4px);
  }
}
</style>