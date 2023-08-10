<template>
  <div class="rank-other-item-view-v-layout">
    <span class="name-text-view">{{ userInfo.name }}</span>
    <div class="flag-h-layout">
      <van-image
        class="flag-image-view"
        :src="flagImg"
      />
      <span class="flag-num-text-view">{{ `x${userInfo.num}` }}</span>
    </div>
    <van-image
      :class="[
        'avatar-image-view',
        rank === 2 ? 'rank2-border' : 'rank3-border',
      ]"
      :src="userInfo.avatar"
      @click="onAvatarClick"
      round
    />
    <van-image class="podium" :src="podiumSrc" />
  </div>
</template>
<script>
import _ from "lodash";
import Vue from "vue";
import { Image as VanImage, Progress } from "vant";
import { goUserProfle } from "../../../common/bridge/index";

Vue.use(VanImage);
Vue.use(Progress);

export default {
  name: "RankFirstItemView",
  props: {
    rank: {
      type: Number,
      default: 0,
    },
    userInfo: {
      type: Object,
      default() {
        return {
          uid: 0,
          name: "",
          avatar: "",
          num: 0,
        };
      },
    },
    flagImg: String
  },
  computed: {
    podiumSrc() {
      if (this.rank === 2) {
        return require("../assets/national_day_rank_2_podium_ic.png");
      }

      if (this.rank === 3) {
        return require("../assets/national_day_rank_3_podium_ic.png");
      }

      return "";
    },
  },
  methods: {
    onAvatarClick() {
      const uid = this.userInfo.uid;
      if (uid <= 0) {
        return
      }

      goUserProfle(uid);
    },
  },
};
</script>
<style lang="scss" scoped>
@use "@/common/css/compat.scss" as compat;
@use "@/common/css/index.scss" as *;
@import "@/common/css/var.scss";

@include v-layout(rank-other-item-view) {
  width: 210px;

  @include text-view(name,
    $font-size: 32px,
    $line-height: 32px,
    $font-color: #fff2c4,
    $font-weight: $--font-weight-medium,
    $line-number: 1) {
    width: 200px;
  }

  @include h-layout(flag) {
    margin-top: 16px;

    @include image-view(flag, 62px, 31px) {
      @include dir {
        transform: rotateY(180deg);
      }
    }

    @include text-view(flag-num,
      $font-size: 28px,
      $line-height: 28px,
      $font-color: #28c992,
      $font-weight: $--font-weight-medium ) {
      @include compat.margin-start(13px);
    }
  }

  @include image-view(avatar, 117px, 117px) {
    position: relative;
    top: 9px;
    z-index: 1;
  }

  .rank2-border {
    border: 3px solid #e0e3ea;
  }

  .rank3-border {
    border: 3px solid #eec2a8;
  }

  @include image-view(podium, 210px, 80px);
}
</style>