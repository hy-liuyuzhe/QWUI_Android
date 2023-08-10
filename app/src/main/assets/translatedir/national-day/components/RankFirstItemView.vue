<template>
  <div class="rank-first-item-view-v-layout">
    <span class="name-text-view">{{ userInfo.name }}</span>
    <div class="flag-h-layout">
      <van-image
        class="flag-image-view"
        :src="flagImg"
      />
      <span class="flag-num-text-view">{{ `x${userInfo.num}` }}</span>
    </div>
    <div class="avatar-z-layout" @click="onAvatarClick">
      <van-image class="avatar-image-view" :src="userInfo.avatar" round />
      <van-image
        class="avatar-frame-image-view"
        :src="require('../assets/national_day_rank_1_avatar_frame_ic.png')"
      />
    </div>
    <van-image
      class="podium"
      :src="require('../assets/national_day_rank_1_podium_ic.png')"
    />
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

@include v-layout(rank-first-item-view) {
  width: 211px;

  @include text-view(
    name,
    $font-size: 32px,
    $line-height: 32px,
    $font-color: #fff2c4,
    $font-weight: $--font-weight-medium,
    $line-number: 1
  );

  @include h-layout(flag) {
    margin-top: 16px;

    @include image-view(flag, 62px, 31px) {
      @include dir {
        transform: rotateY(180deg);
      }
    }

    @include text-view(
      flag-num,
      $font-size: 28px,
      $line-height: 28px,
      $font-color: #28c992,
      $font-weight: $--font-weight-medium
    ) {
      @include compat.margin-start(13px);
    }
  }

  @include z-layout(avatar) {
    position: relative;
    width: 183px;
    height: 166px;
    top: 9px;
    z-index: 1;

    @include image-view(avatar, 144px, 144px) {
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translateX(-50%) translateY(-50%);
    }

    @include image-view(avatar-frame, 183px, 166px) {
      position: absolute;
      top: 0;
      left: 0;
    }
  }

  @include image-view(podium, 211px, 109px);
}
</style>