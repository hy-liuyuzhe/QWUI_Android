<template>
  <div>
    <van-image
      class="rank-num-icon"
      :src="rankIcon"
      v-show="showRankIcon"
    ></van-image>
    <span class="rank-num-text" v-show="showRankText">{{ rank }}</span>
    <div class="avatar-container" @click="onUserViewClick">
      <van-image class="user-avatar" :src="avatarUrl" round></van-image>
      <van-image
        class="rank-avatar-frame"
        :src="rankFrame"
        v-show="showRankIcon"
      ></van-image>
    </div>
    <span class="user-name">{{ rankInfo.username }}</span>
    <div class="score-container">
      <van-image class="score-type-icon" :src="scoreTypeIcon"></van-image>
      <span class="score-text">{{ rankInfo.amount }}</span>
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
  name: "RankListItem",
  props: {
    rankInfo: Object,
    rank: Number,
    type: Number,
  },
  computed: {
    rankIcon() {
      if (this.rank == 1) {
        return require("../assets/star_rank_1.png");
      } else if (this.rank == 2) {
        return require("../assets/star_rank_2.png");
      } else {
        return require("../assets/star_rank_3.png");
      }
    },
    showRankIcon() {
      return this.rank <= 3;
    },
    showRankText() {
      return this.rank > 3;
    },
    scoreTypeIcon() {
      if (this.type == RANK_TYPE.RANK_TYPE_RICH) {
        return require("../../common/assets/common_coin_28_ic.png");
      } else {
        return require("../assets/star_diamond.png");
      }
    },
    rankFrame() {
      if (this.rank == 1) {
        return require("../assets/star_rank_1_frame.png");
      } else if (this.rank == 2) {
        return require("../assets/star_rank_2_frame.png");
      } else {
        return require("../assets/star_rank_3_frame.png");
      }
    },
    avatarUrl() {
      if (this.rankInfo.avatar == "") {
        return require("../assets/star_default_avatar_ic.png");
      } else {
        return this.rankInfo.avatar;
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
.rank-num-icon {
  width: 40px;
  height: 40px;
  @include compat.margin-start(22px);
}
.rank-num-text {
  width: 36px;
  height: 37px;
  font-size: 32px;
  font-family: DINAlternate-Bold, DINAlternate;
  font-weight: bold;
  color: #ffffff;
  @include compat.margin-start(24px);
}
.avatar-container {
  display: inline;
  width: 128px;
  height: 128px;
  @include compat.margin-start(8px);
  position: relative;
  .user-avatar {
    width: 88px;
    height: 88px;
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    margin: auto;
  }
  .rank-avatar-frame {
    width: 128px;
    height: 128px;
    position: absolute;
    top: 0;
    left: 0;
  }
}
.user-name {
  @extend .single-line-text;
  width: 270px;
  font-size: 28px;
  text-align: start;
  font-family: SFUIText-Medium, SFUIText;
  font-weight: 500;
  color: #ffffff;
  @include compat.margin-start(12px);
}
.score-container {
  display: inline;
  height: 31px;
  display: flex;
  @include compat.margin-start(auto);
  @include compat.margin-end(32px);

  flex-direction: row-reverse;
  .score-type-icon {
    width: 28px;
    height: 28px;
  }
  .score-text {
    font-size: 26px;
    font-family: SFUIText-Regular, SFUIText;
    font-weight: 400;
    color: #ffffff;
    line-height: 32px;
    @include compat.margin-end(4px);
  }
}
</style>