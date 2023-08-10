<template>
  <div class="travel-map-v-layout">
    <div class="couple-avatars-z-layout">
      <van-image
        class="avatar-image-view"
        :src="myUserInfo.url"
        round
        error-icon="user-circle-o"
      ></van-image>
      <van-image
        class="avatar-image-view"
        :src="coupleUserInfo.avatar"
        v-if="hasCouple"
        round
        error-icon="user-circle-o"
      ></van-image>
      <van-image
        class="center-heart-image-view"
        :src="require('../assets/couple_travel_center_heart.png')"
        v-show="hasCouple"
      ></van-image>
    </div>
    <div class="travel-rewards-z-layout">
      <travel-reward
        class="travel-reward-1"
        :rewardInfo="travelRewardInfo[0]"
      />
      <travel-reward
        class="travel-reward-2"
        :rewardInfo="travelRewardInfo[1]"
      />
      <travel-reward
        class="travel-reward-3"
        :rewardInfo="travelRewardInfo[2]"
      />
      <travel-reward
        class="travel-reward-4"
        :rewardInfo="travelRewardInfo[3]"
      />
      <travel-reward
        class="travel-reward-5"
        :rewardInfo="travelRewardInfo[4]"
      />
      <travel-reward
        class="travel-reward-6"
        :rewardInfo="travelRewardInfo[5]"
      />
      <travel-reward
        class="travel-reward-7"
        :rewardInfo="travelRewardInfo[6]"
      />
      <travel-reward
        class="travel-reward-8"
        :rewardInfo="travelRewardInfo[7]"
      />
      <van-image
        class="finished-image-view"
        :src="require('../assets/couple_travel_finished_heart.png')"
        v-if="allFinished"
      />
    </div>
  </div>
</template>
<script>
import _ from "lodash";
import Vue from "vue";
import { Image as VanImage, Toast, Popup } from "vant";
import TravelReward from "./TravelReward.vue";

Vue.use(VanImage);
Vue.use(Toast);
Vue.use(Popup);
export default {
  name: "TravelLottery",
  data() {
    return {
      travelRewardInfo: this.travelActivityInfo.rewardInfos,
    };
  },
  components: {
    "travel-reward": TravelReward,
  },
  props: {
    myUserInfo: Object,
    coupleUserInfo: Object,
    travelActivityInfo: Object,
  },
  watch: {
    travelActivityInfo: function(data) {
      if (data) {
        this.travelRewardInfo = data.rewardInfos;
      }
    },
  },
  computed: {
    allFinished() {
      let unfinishedIndex = _.findIndex(this.travelRewardInfo, function(o) {
        return o.status === 0;
      });
      return unfinishedIndex === -1;
    },
    hasCouple() {
      return !_.isEmpty(this.coupleUserInfo);
    },
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/text.scss" as text;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";
@include v-layout(travel-map) {
  width: 694px;
  height: 898px;
  background-image: url("../assets/couple_travel_map.png");
  background-size: 100% 100%;
  @include z-layout(couple-avatars) {
    margin-top: 24px;
    @include image-view(avatar, 160px, 160px);
    @include image-view(center-heart, 94px, 60px) {
      position: absolute;
      top: 45px;
      left: 106px;
    }
  }
  @include z-layout(travel-rewards) {
    width: 100%;
    margin-top: 33px;
    .travel-reward-base {
      position: absolute;
    }
    .travel-reward-1 {
      @extend .travel-reward-base;
      top: 0;
      left: 129px;
    }
    .travel-reward-2 {
      @extend .travel-reward-base;
      top: 0;
      left: 412px;
    }
    .travel-reward-3 {
      @extend .travel-reward-base;
      top: 146px;
      left: 28px;
    }
    .travel-reward-4 {
      @extend .travel-reward-base;
      top: 126px;
      left: 272px;
    }
    .travel-reward-5 {
      @extend .travel-reward-base;
      top: 146px;
      left: 512px;
    }
    .travel-reward-6 {
      @extend .travel-reward-base;
      top: 290px;
      left: 98px;
    }
    .travel-reward-7 {
      @extend .travel-reward-base;
      top: 290px;
      left: 442px;
    }
    .travel-reward-8 {
      @extend .travel-reward-base;
      top: 397px;
      left: 270px;
    }
    @include image-view(finished, 388px, 328px) {
      position: absolute;
      top: 82px;
      left: 162px;
    }
  }
}
</style>
