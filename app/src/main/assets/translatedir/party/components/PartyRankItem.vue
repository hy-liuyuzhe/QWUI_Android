<template>
  <div
    class="rank-item-container"
    @click="viewActivityDetailClick(rankInfo.activityId)"
  >
    <span class="rank-text">{{ rank == undefined ? "-" : rank }}</span>
    <van-image
      class="avatar"
      :src="rankInfo.creatorUrl"
      :radius="16"
      v-on:click.stop="onAvatarClick(rankInfo.creatorUid)"
    ></van-image>
    <div class="rank-info-container">
      <div class="rank-hot-container">
        <img class="rank-hot-img" :src="require('../assets/party_hot.png')" />
        <span class="hot-num">{{ rankInfo.hot }}</span>
      </div>
      <div class="rank-room-info-container">
        <img
          class="room-diamond-img"
          :src="require('../assets/party_diamond.png')"
        />
        <span class="diamond-num">{{ rankInfo.diamonds }}</span>
        <div class="line"></div>
        <img
          class="room-people-img"
          :src="require('../assets/party_people.png')"
        />
        <span class="people-num">{{ rankInfo.maxOnline }}</span>
      </div>
    </div>
    <van-image class="arrow-img" :src="arrowIcon"></van-image>
  </div>
</template>
<script>
import Vue from "vue";
import { Image as VanImage } from "vant";
import {
  dispatchDeeplink,
  isDeeplinkSupport,
} from "../../../common/bridge/index";

Vue.use(VanImage);
export default {
  name: "PartyRankItem",
  props: {
    rank: Number,
    rankInfo: Object,
  },
  computed: {
    arrowIcon() {
      return this.$i18n.locale == "ar"
        ? require("../assets/party_entry_ic_rtl.png")
        : require("../assets/party_entry_ic.png");
    },
  },
  methods: {
    async onAvatarClick(uid) {
      if (uid == undefined) {
        return;
      }
      var supportResult = await isDeeplinkSupport({ path: "/userProfile" });
      if (supportResult && supportResult.isSupport) {
        window.location.href = "wenext://wayak/userProfile?extra_uid=" + uid;
      }
    },
    async viewActivityDetailClick(activityId) {
      if (activityId == undefined) {
        return;
      }
      dispatchDeeplink({
        deeplink:
          "wenext://wayak/party/party_detail?extra_activity_id=" + activityId,
        path: "/party/party_detail",
      });
    },
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
.rank-item-container {
  width: 698px;
  height: 150px;
  display: flex;
  flex-direction: row;
  align-items: center;
}
.rank-text {
  height: 37px;
  font-size: 32px;
  font-family: DINAlternate-Bold, DINAlternate;
  font-weight: bold;
  color: #ffffff;
  line-height: 37px;
  @include compat.margin-start(25px);
  min-width: 36px;
}
.avatar {
  width: 96px;
  height: 96px;
  @include compat.margin-start(19px);
  border-radius: 16px;
  border: 1px solid #ee9bfe;
}
.arrow-img {
  width: 32px;
  height: 32px;
  @include compat.margin-start(auto);
  @include compat.margin-end(32px);
}
.rank-info-container {
  display: flex;
  flex-direction: column;
  @include compat.margin-start(20px);
  .rank-hot-container {
    display: flex;
    flex-direction: row;
    align-items: center;
    .rank-hot-img {
      width: 64px;
      height: 30px;
    }
    .hot-num {
      @include compat.margin-start(8px);
      height: 38px;
      font-size: 32px;
      font-family: SFUIText-Medium, SFUIText;
      font-weight: 500;
      color: #ffffff;
      line-height: 38px;
    }
  }
  .rank-room-info-container {
    display: flex;
    flex-direction: row;
    align-items: center;
    margin-top: 18px;
    .room-diamond-img {
      width: 28px;
      height: 28px;
    }
    .diamond-num {
      height: 31px;
      font-size: 26px;
      font-family: SFUIText-Regular, SFUIText;
      font-weight: 400;
      color: #ee9bfe;
      line-height: 31px;
      @include compat.margin-start(8px);
    }
    .line {
      width: 2px;
      height: 24px;
      @include compat.margin-start(8px);
      background: rgba(255, 255, 255, 0.2);
    }
    .room-people-img {
      width: 28px;
      height: 28px;
      @include compat.margin-start(8px);
    }
    .people-num {
      height: 31px;
      font-size: 26px;
      font-family: SFUIText-Regular, SFUIText;
      font-weight: 400;
      color: #ee9bfe;
      line-height: 31px;
      @include compat.margin-start(8px);
    }
  }
}
</style>